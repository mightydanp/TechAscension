package mightydanp.techcore.common.jsonconfig.definedstructure;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
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

public class DefinedStructureServer extends JsonConfigServer<DefinedStructureCodec> {

    @Override
    public Map<String, DefinedStructureCodec> getServerMapFromList(List<DefinedStructureCodec> definedStructureIn) {
        Map<String, DefinedStructureCodec> definedStructuresList = new LinkedHashMap<>();
        definedStructureIn.forEach(DefinedStructure -> definedStructuresList.put(DefinedStructure.name(), DefinedStructure));

        return definedStructuresList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        List<DefinedStructureCodec> clientList = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(DefinedStructureCodec.class::isInstance)
                .map(DefinedStructureCodec.class::cast).toList();

        AtomicBoolean sync = new AtomicBoolean(true);


        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, definedStructure) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<DefinedStructureCodec> client = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(client.isPresent()) {
                    DefinedStructureCodec clientDefinedStructure = client.get();
                    JsonObject serverJson = ((DefinedStructureRegistry)TCJsonConfigs.definedStructure.getFirst()).toJsonObject(definedStructure);
                    JsonObject clientJson = ((DefinedStructureRegistry)TCJsonConfigs.definedStructure.getFirst()).toJsonObject(clientDefinedStructure);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put("defined_structure", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, DefinedStructureCodec> clientDefinedStructures = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/defined_structure");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }

            if(files.length > 0){
                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    DefinedStructureCodec definedStructure = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);
                    clientDefinedStructures.put(definedStructure.name(), definedStructure);
                }

                getServerMap().values().forEach(serverDefinedStructure -> {
                    sync.set(clientDefinedStructures.containsKey(serverDefinedStructure.name()));

                    if(sync.get()) {
                        DefinedStructureCodec clientDefinedStructure = getServerMap().get(serverDefinedStructure.name());
                        JsonObject serverJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverDefinedStructure);
                        JsonObject clientJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(clientDefinedStructure);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("defined_structure", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/defined_structure");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (DefinedStructureCodec definedStructure : getServerMap().values()) {
            String name = definedStructure.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(definedStructure);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/defined_structure");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/defined_structure");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    DefinedStructureCodec definedStructure = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);

                    String name = definedStructure.name();

                    Path filePath = Paths.get(singlePlayerSaveConfigFolder + "/" + name + ".json");
                    if (!Files.exists(filePath)) {
                        Files.createDirectories(filePath.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
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
        List<DefinedStructureCodec> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(DefinedStructureCodec.class::isInstance)
                .map(DefinedStructureCodec.class::cast).toList();

        Map<String, DefinedStructureCodec> definedStructures = list.stream()
                .collect(Collectors.toMap(DefinedStructureCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(definedStructures);

        TechAscension.LOGGER.info("Loaded {} fluid states from the server", definedStructures.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, DefinedStructureCodec definedStructure) {
        buffer.writeWithCodec(DefinedStructureCodec.CODEC, definedStructure);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<DefinedStructureCodec> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(DefinedStructureCodec.class::isInstance)
                .map(DefinedStructureCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((DefinedStructure) -> {
            singleToBuffer(buffer, DefinedStructure);
        });
    }

    @Override
    public DefinedStructureCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(DefinedStructureCodec.CODEC);
    }

    @Override
    public List<DefinedStructureCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<DefinedStructureCodec> definedStructures = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            DefinedStructureCodec definedStructure = singleFromBuffer(buffer);

            definedStructures.add(definedStructure);
        }

        return definedStructures;
    }

}