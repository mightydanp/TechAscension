package mightydanp.techcore.common.jsonconfig.material.ore;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import net.minecraft.network.FriendlyByteBuf;

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

        if(message.getConfig(TCJsonConfigs.oreTypeID).size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("ore_type", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, oreType) -> {
            List<IOreType> list = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                    .filter(IOreType.class::isInstance)
                    .map(IOreType.class::cast).toList();

            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IOreType> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IOreType serverOreType = optional.get();
                    JsonObject jsonMaterial = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(oreType);
                    JsonObject materialJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(serverOreType);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("ore_type", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IOreType> clientOreTypes = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/ore_type");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("ore_type", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.oreType.getFirst().getJsonObject(file.getName());
                    IOreType oreType = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).getFromJsonObject(jsonObject);
                    clientOreTypes.put(oreType.getName(), oreType);
                }

                getServerMap().values().forEach(serverOreType -> {
                    sync.set(clientOreTypes.containsKey(serverOreType.getName()));

                    if(sync.get()) {
                        IOreType clientOreType = getServerMap().get(serverOreType.getName());
                        JsonObject jsonMaterial = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(serverOreType);
                        JsonObject materialJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(clientOreType);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("ore_type", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("ore_type", sync.get());

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
            JsonObject jsonObject = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(oreType);
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
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/ore_type");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.oreType.getFirst().getJsonObject(file.getName());
                    IOreType oreType = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).getFromJsonObject(jsonObject);

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
        List<IOreType> list = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                .filter(IOreType.class::isInstance)
                .map(IOreType.class::cast).toList();

        Map<String, IOreType> oreTypes = list.stream()
                .collect(Collectors.toMap(IOreType::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(oreTypes);

        TechAscension.LOGGER.info("Loaded {} ore types from the server", oreTypes.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IOreType oreType) {//friendlybotbuff
        buffer.writeUtf(oreType.getName());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IOreType> list = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                .filter(IOreType.class::isInstance)
                .map(IOreType.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((oreType) -> {
            singleToBuffer(buffer, oreType);
        });
    }

    @Override
    public IOreType singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return new IOreType() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public List<IOreType> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IOreType> oreTypes = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IOreType oreType = singleFromBuffer(buffer);

            oreTypes.add(oreType);
        }

        return oreTypes;
    }

}