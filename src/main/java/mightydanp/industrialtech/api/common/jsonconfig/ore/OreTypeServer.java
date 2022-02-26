package mightydanp.industrialtech.api.common.jsonconfig.ore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/25/2022.
 */
public class OreTypeServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, IOreType> serverOreTypesMap = new HashMap<>();

    OreTypeRegistry oreTypeRegistry = IndustrialTech.oreTypeRegistry;

    public Map<String, IOreType> getServerOreTypesMap(){
        return serverOreTypesMap;
    }

    public boolean serverHasOreTypes(){
        return serverOreTypesMap.size() > 0;
    }

    public static List<IOreType> getAllOreTypes() {
        return new ArrayList<>(serverOreTypesMap.values());
    }


    public static Map<String, IOreType> getServerOreTypesMap(List<IOreType> oreTypesIn) {
        Map<String, IOreType> oreTypesList = new LinkedHashMap<>();
        oreTypesIn.forEach(oreType -> oreTypesList.put(oreType.getName(), oreType));

        return oreTypesList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getOreTypes().size() != getServerOreTypesMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());
            return false;
        }

        getServerOreTypesMap().forEach((name, oreType) -> {
            sync.set(message.getOreTypes().stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IOreType> optional = message.getOreTypes().stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IOreType serverOreType = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.oreTypeRegistry.toJsonObject(oreType);
                    JsonObject materialJson = IndustrialTech.oreTypeRegistry.toJsonObject(serverOreType);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IOreType> clientOreTypes = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/ore_type");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerOreTypesMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("ore_type", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("ore_type", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = oreTypeRegistry.getJsonObject(file.getAbsolutePath());
                IOreType oreType = oreTypeRegistry.getOreType(jsonObject);
                clientOreTypes.put(oreType.getName(), oreType);
            }

            getServerOreTypesMap().values().forEach(serverOreType -> {
                sync.set(clientOreTypes.containsKey(serverOreType.getName()));

                if(sync.get()) {
                    IOreType clientOreType = getServerOreTypesMap().get(serverOreType.getName());
                    JsonObject jsonMaterial = oreTypeRegistry.toJsonObject(serverOreType);
                    JsonObject materialJson = oreTypeRegistry.toJsonObject(clientOreType);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());

        return sync.get();
    }

    public void syncClientOreTypesWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/ore_type");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IOreType oreType : getServerOreTypesMap().values()) {
            String name = oreType.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = oreTypeRegistry.toJsonObject(oreType);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientOreTypesConfigsWithSinglePlayerWorlds(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/ore_type");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/ore_type");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = oreTypeRegistry.getJsonObject(file.getName());
                    IOreType oreType = oreTypeRegistry.getOreType(jsonObject);

                    String name = oreType.getName();

                    Path materialFile = Paths.get(singlePlayerSaveConfigFolder + "/" + name + ".json");
                    if (!Files.exists(materialFile)) {
                        Files.createDirectories(materialFile.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                            String s = GSON.toJson(jsonObject);
                            bufferedwriter.write(s);
                        }
                    }
                }
            }
        }
    }

    public void loadOreTypes(SyncMessage message) {
        Map<String, IOreType> oreTypes = message.getOreTypes().stream()
                .collect(Collectors.toMap(IOreType::getName, s -> s));

        serverOreTypesMap.clear();
        serverOreTypesMap.putAll(oreTypes);

        IndustrialTech.LOGGER.info("Loaded {} ore types from the server", oreTypes.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, IOreType oreType) {//friendlybotbuff
        buffer.writeUtf(oreType.getName());
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getOreTypes().size());

        message.getOreTypes().forEach((oreType) -> {
            singleToBuffer(buffer, oreType);
        });
    }

    public static IOreType singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return new IOreType() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    public static List<IOreType> multipleFromBuffer(PacketBuffer buffer) {
        List<IOreType> oreTypes = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IOreType oreType = singleFromBuffer(buffer);

            oreTypes.add(oreType);
        }

        return oreTypes;
    }

}