package mightydanp.techcore.common.jsonconfig.icons;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
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
public class TextureIconServer extends JsonConfigServer<TextureIconCodec> {
    @Override
    public Map<String, TextureIconCodec> getServerMapFromList(List<TextureIconCodec> codecs) {
        Map<String, TextureIconCodec> codecMap = new LinkedHashMap<>();
        codecs.forEach(textureIcon -> codecMap.put(textureIcon.name(), textureIcon));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<TextureIconCodec> clientList = message.getConfig(TCJsonConfigs.textureIconID).stream()
                .filter(TextureIconCodec.class::isInstance)
                .map(TextureIconCodec.class::cast).toList();
        
        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(TextureIconCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<TextureIconCodec> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    TextureIconCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(TextureIconCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, TextureIconCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + TextureIconCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(TextureIconCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.textureIcon.getFirst().getJsonObject(file.getName());
                    TextureIconCodec codec = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        TextureIconCodec clientCodec = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(TextureIconCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(TextureIconCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + TextureIconCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (TextureIconCodec serverCodec : getServerMap().values()) {
            String name = serverCodec.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + TextureIconCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + TextureIconCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.textureIcon.getFirst().getJsonObject(file.getName());
                    TextureIconCodec client = ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).fromJsonObject(jsonObject);

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
        List<TextureIconCodec> list = message.getConfig(TCJsonConfigs.textureIconID).stream()
                .filter(TextureIconCodec.class::isInstance)
                .map(TextureIconCodec.class::cast).toList();

        Map<String, TextureIconCodec> codecMap = list.stream()
                .collect(Collectors.toMap(TextureIconCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + TextureIconCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, TextureIconCodec codec) {
        buffer.writeWithCodec(TextureIconCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<TextureIconCodec> list = message.getConfig(TCJsonConfigs.textureIconID).stream()
                .filter(TextureIconCodec.class::isInstance)
                .map(TextureIconCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public TextureIconCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(TextureIconCodec.CODEC);
    }

    @Override
    public List<TextureIconCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<TextureIconCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            TextureIconCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}