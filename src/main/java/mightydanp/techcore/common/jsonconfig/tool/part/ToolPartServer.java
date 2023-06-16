package mightydanp.techcore.common.jsonconfig.tool.part;

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
public class ToolPartServer extends JsonConfigServer<ToolPartCodec> {

    @Override
    public Map<String, ToolPartCodec> getServerMapFromList(List<ToolPartCodec> codecs) {
        Map<String, ToolPartCodec> codecMap = new LinkedHashMap<>();
        codecs.forEach(toolPart -> codecMap.put(fixesToName(toolPart.prefix(), toolPart.suffix()), toolPart));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ToolPartCodec> clientList = message.getConfig(TCJsonConfigs.toolPartID).stream()
                .filter(ToolPartCodec.class::isInstance)
                .map(ToolPartCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(ToolPartCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> fixesToName(o.prefix(), o.suffix()).equals(name)));

            if(sync.get()) {
                Optional<ToolPartCodec> optionalClientCodec = clientList.stream().filter(o -> fixesToName(o.prefix(), o.suffix()).equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    ToolPartCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(ToolPartCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ToolPartCodec> clientToolParts = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + ToolPartCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(ToolPartCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.toolPart.getFirst().getJsonObject(file.getName());
                    ToolPartCodec codec = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).fromJsonObject(jsonObject);
                    clientToolParts.put(fixesToName(codec.prefix(), codec.suffix()), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientToolParts.containsKey(fixesToName(serverCodec.prefix(), serverCodec.suffix())));

                    if(sync.get()) {
                        ToolPartCodec clientCodec = getServerMap().get(fixesToName(serverCodec.prefix(), serverCodec.suffix()));
                        JsonObject serverJson = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(ToolPartCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(ToolPartCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + ToolPartCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ToolPartCodec toolPart : getServerMap().values()) {
            String name = fixesToName(toolPart.prefix(), toolPart.suffix());
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).toJsonObject(toolPart);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + ToolPartCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + ToolPartCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.toolPart.getFirst().getJsonObject(file.getName());
                    ToolPartCodec filePath = ((ToolPartRegistry) TCJsonConfigs.toolPart.getFirst()).fromJsonObject(jsonObject);

                    String name = fixesToName(filePath.prefix(), filePath.suffix());

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
        List<ToolPartCodec> list = message.getConfig(TCJsonConfigs.toolPartID).stream()
                .filter(ToolPartCodec.class::isInstance)
                .map(ToolPartCodec.class::cast).toList();

        Map<String, ToolPartCodec> codecMap = list.stream()
                .collect(Collectors.toMap(s -> fixesToName(s.prefix(), s.suffix()), s -> s));

        serverMap.clear();
        serverMap.putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + ToolPartCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ToolPartCodec codec) {
        buffer.writeWithCodec(ToolPartCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ToolPartCodec> list = message.getConfig(TCJsonConfigs.toolPartID).stream()
                .filter(ToolPartCodec.class::isInstance)
                .map(ToolPartCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((toolPart) -> singleToBuffer(buffer, toolPart));
    }

    @Override
    public ToolPartCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(ToolPartCodec.CODEC);
    }

    @Override
    public List<ToolPartCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ToolPartCodec> toolParts = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ToolPartCodec toolPart = singleFromBuffer(buffer);

            toolParts.add(toolPart);
        }

        return toolParts;
    }

}
