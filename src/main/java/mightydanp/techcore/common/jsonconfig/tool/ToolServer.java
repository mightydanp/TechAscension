package mightydanp.techcore.common.jsonconfig.tool;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.TechAscension;
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
public class ToolServer extends JsonConfigServer<ToolCodec> {

    public Map<String, ToolCodec> getServerMapFromList(List<ToolCodec> codecs) {
        Map<String, ToolCodec> codecMap = new LinkedHashMap<>();
        codecs.forEach(codec -> codecMap.put(codec.name(), codec));

        return codecMap;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ToolCodec> clientList = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ToolCodec.class::isInstance)
                .map(ToolCodec.class::cast).toList();
        
        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(ToolCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<ToolCodec> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    ToolCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(ToolCodec.codecName, sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ToolCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" +ToolCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(ToolCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.tool.getFirst().getJsonObject(file.getName());
                    ToolCodec codec = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        ToolCodec clientCodec = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(ToolCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(ToolCodec.codecName, sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" +ToolCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ToolCodec tool : getServerMap().values()) {
            String name = tool.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(tool);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" +ToolCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" +ToolCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.tool.getFirst().getJsonObject(file.getName());
                    ToolCodec toolCodec = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).fromJsonObject(jsonObject);

                    String name = toolCodec.name();

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
        List<ToolCodec> list = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ToolCodec.class::isInstance)
                .map(ToolCodec.class::cast).toList();

        Map<String, ToolCodec> map = list.stream()
                .collect(Collectors.toMap(ToolCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + ToolCodec.codecName + " from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ToolCodec codec) {
        buffer.writeWithCodec(ToolCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ToolCodec> list = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ToolCodec.class::isInstance)
                .map(ToolCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((tool) -> singleToBuffer(buffer, tool));
    }

    @Override
    public ToolCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(ToolCodec.CODEC);
    }

    @Override
    public List<ToolCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ToolCodec> tools = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ToolCodec tool = singleFromBuffer(buffer);

            tools.add(tool);
        }

        return tools;
    }

}