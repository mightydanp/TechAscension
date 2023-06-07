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
    public Map<String, DefinedStructureCodec> getServerMapFromList(List<DefinedStructureCodec> codecsIn) {
        Map<String, DefinedStructureCodec> codecMap = new LinkedHashMap<>();
        codecsIn.forEach(codec -> codecMap.put(codec.name(), codec));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<DefinedStructureCodec> clientList = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(DefinedStructureCodec.class::isInstance)
                .map(DefinedStructureCodec.class::cast).toList();


        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(DefinedStructureCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<DefinedStructureCodec> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    DefinedStructureCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((DefinedStructureRegistry)TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((DefinedStructureRegistry)TCJsonConfigs.definedStructure.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(DefinedStructureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, DefinedStructureCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + DefinedStructureCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(DefinedStructureCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){
                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    DefinedStructureCodec codec = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        DefinedStructureCodec clientCodec = clientMap.get(serverCodec.name());
                        JsonObject serverJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(DefinedStructureCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(DefinedStructureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + DefinedStructureCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (DefinedStructureCodec serverCodec : getServerMap().values()) {
            String name = serverCodec.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + DefinedStructureCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + DefinedStructureCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    DefinedStructureCodec client = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);

                    String name = client.name();

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

        Map<String, DefinedStructureCodec> map = list.stream()
                .collect(Collectors.toMap(DefinedStructureCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + DefinedStructureCodec.codecName + " from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, DefinedStructureCodec codec) {
        buffer.writeWithCodec(DefinedStructureCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<DefinedStructureCodec> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(DefinedStructureCodec.class::isInstance)
                .map(DefinedStructureCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public DefinedStructureCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(DefinedStructureCodec.CODEC);
    }

    @Override
    public List<DefinedStructureCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<DefinedStructureCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            DefinedStructureCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}