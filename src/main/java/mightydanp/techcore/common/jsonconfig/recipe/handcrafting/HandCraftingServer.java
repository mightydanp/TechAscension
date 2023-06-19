package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

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

/**
 * Created by MightyDanp on 1/3/2022.
 */
public class HandCraftingServer extends JsonConfigServer<HandCraftingCodec> {

    @Override
    public Map<String, HandCraftingCodec> getServerMapFromList(List<HandCraftingCodec> handCraftingIn) {
        Map<String, HandCraftingCodec> codecMap = new LinkedHashMap<>();
        handCraftingIn.forEach(handCrafting -> codecMap.put(handCrafting.name(), handCrafting));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<HandCraftingCodec> clientList = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(HandCraftingCodec.class::isInstance)
                .map(HandCraftingCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(HandCraftingCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<HandCraftingCodec> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    HandCraftingCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(HandCraftingCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, HandCraftingCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/recipe" + "/" + HandCraftingCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(HandCraftingCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.handCrafting.getFirst().getJsonObject(file.getName());
                    HandCraftingCodec codec = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverHandCrafting -> {
                    sync.set(clientMap.containsKey(serverHandCrafting.name()));

                    if(sync.get()) {
                        HandCraftingCodec clientCodec = getServerMap().get(serverHandCrafting.name());
                        JsonObject serverJson = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(serverHandCrafting);
                        JsonObject clientJson = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(HandCraftingCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(HandCraftingCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/recipe" + "/" + HandCraftingCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (HandCraftingCodec handCrafting : getServerMap().values()) {
            String name = handCrafting.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(handCrafting);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName+ "/recipe" + "/" + HandCraftingCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()+ "/recipe" + "/" + HandCraftingCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.handCrafting.getFirst().getJsonObject(file.getName());
                    HandCraftingCodec codec = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).fromJsonObject(jsonObject);

                    String name = codec.name();

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
        List<HandCraftingCodec> list = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(HandCraftingCodec.class::isInstance)
                .map(HandCraftingCodec.class::cast).toList();

        Map<String, HandCraftingCodec> codecMap = list.stream()
                .collect(Collectors.toMap(HandCraftingCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + HandCraftingCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, HandCraftingCodec codec) {
        buffer.writeWithCodec(HandCraftingCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<HandCraftingCodec> list = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(HandCraftingCodec.class::isInstance)
                .map(HandCraftingCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public HandCraftingCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(HandCraftingCodec.CODEC);
    }

    @Override
    public List<HandCraftingCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<HandCraftingCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            HandCraftingCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}
