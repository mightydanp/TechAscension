package mightydanp.industrialtech.api.common.jsonconfig.material.ore;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
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
public class OreTypeServer extends JsonConfigServer<IOreType> {

    @Override
    public Map<String, IOreType> getServerMapFromList(List<IOreType> oreTypesIn) {
        Map<String, IOreType> oreTypesList = new LinkedHashMap<>();
        oreTypesIn.forEach(oreType -> oreTypesList.put(oreType.getName(), oreType));

        return oreTypesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getOreTypes().size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());
            return false;
        }

        getServerMap().forEach((name, oreType) -> {
            sync.set(message.getOreTypes().stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IOreType> optional = message.getOreTypes().stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IOreType serverOreType = optional.get();
                    JsonObject jsonMaterial = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).toJsonObject(oreType);
                    JsonObject materialJson = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).toJsonObject(serverOreType);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IOreType> clientOreTypes = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/ore_type");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
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
                JsonObject jsonObject = IndustrialTech.configSync.oreType.getFirst().getJsonObject(file.getName());
                IOreType oreType = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).getFromJsonObject(jsonObject);
                clientOreTypes.put(oreType.getName(), oreType);
            }

            getServerMap().values().forEach(serverOreType -> {
                sync.set(clientOreTypes.containsKey(serverOreType.getName()));

                if(sync.get()) {
                    IOreType clientOreType = getServerMap().get(serverOreType.getName());
                    JsonObject jsonMaterial = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).toJsonObject(serverOreType);
                    JsonObject materialJson = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).toJsonObject(clientOreType);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("ore_type", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/ore_type");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IOreType oreType : getServerMap().values()) {
            String name = oreType.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).toJsonObject(oreType);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/ore_type");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/ore_type");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.oreType.getFirst().getJsonObject(file.getName());
                    IOreType oreType = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).getFromJsonObject(jsonObject);

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

    @Override
    public void loadFromServer(SyncMessage message) {
        Map<String, IOreType> oreTypes = message.getOreTypes().stream()
                .collect(Collectors.toMap(IOreType::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(oreTypes);

        IndustrialTech.LOGGER.info("Loaded {} ore types from the server", oreTypes.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, IOreType oreType) {//friendlybotbuff
        buffer.writeUtf(oreType.getName());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getOreTypes().size());

        message.getOreTypes().forEach((oreType) -> {
            singleToBuffer(buffer, oreType);
        });
    }

    @Override
    public IOreType singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return new IOreType() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public List<IOreType> multipleFromBuffer(PacketBuffer buffer) {
        List<IOreType> oreTypes = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IOreType oreType = singleFromBuffer(buffer);

            oreTypes.add(oreType);
        }

        return oreTypes;
    }

}