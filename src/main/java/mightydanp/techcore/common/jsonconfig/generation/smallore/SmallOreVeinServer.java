package mightydanp.techcore.common.jsonconfig.generation.smallore;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.SmallOreVeinGenFeatureCodec;
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


public class SmallOreVeinServer extends JsonConfigServer<SmallOreVeinGenFeatureCodec> {

    @Override
    public Map<String, SmallOreVeinGenFeatureCodec> getServerMapFromList(List<SmallOreVeinGenFeatureCodec> codecs) {
        Map<String, SmallOreVeinGenFeatureCodec> codecMap = new LinkedHashMap<>();
        codecs.forEach(codec -> codecMap.put(codec.name(), codec));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<SmallOreVeinGenFeatureCodec> clientList = message.getConfig(TCJsonConfigs.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureCodec.class::isInstance)
                .map(SmallOreVeinGenFeatureCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(SmallOreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<SmallOreVeinGenFeatureCodec> optional = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optional.isPresent()) {
                    SmallOreVeinGenFeatureCodec clientCodec = optional.get();
                    JsonObject jsonMaterial = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).toJsonObject(serverCodec);
                    JsonObject materialJson = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).toJsonObject(clientCodec);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put(SmallOreVeinGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, SmallOreVeinGenFeatureCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + SmallOreVeinGenFeatureCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(SmallOreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.smallOre.getFirst().getJsonObject(file.getName());
                    SmallOreVeinGenFeatureCodec codec = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        SmallOreVeinGenFeatureCodec clientCodec = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(SmallOreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(SmallOreVeinGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/" + SmallOreVeinGenFeatureCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (SmallOreVeinGenFeatureCodec serverCodec : getServerMap().values()) {
            String name = serverCodec.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/" + SmallOreVeinGenFeatureCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation"  + "/" + SmallOreVeinGenFeatureCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.smallOre.getFirst().getJsonObject(file.getName());
                    SmallOreVeinGenFeatureCodec client = ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).fromJsonObject(jsonObject);

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
        List<SmallOreVeinGenFeatureCodec> list = message.getConfig(TCJsonConfigs.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureCodec.class::isInstance)
                .map(SmallOreVeinGenFeatureCodec.class::cast).toList();

        Map<String, SmallOreVeinGenFeatureCodec> map = list.stream()
                .collect(Collectors.toMap(SmallOreVeinGenFeatureCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + SmallOreVeinGenFeatureCodec.codecName +" from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, SmallOreVeinGenFeatureCodec config) {
        buffer.writeWithCodec(SmallOreVeinGenFeatureCodec.CODEC, config);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<SmallOreVeinGenFeatureCodec> list = message.getConfig(TCJsonConfigs.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureCodec.class::isInstance)
                .map(SmallOreVeinGenFeatureCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach(codec -> singleToBuffer(buffer, codec));
    }

    @Override
    public SmallOreVeinGenFeatureCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(SmallOreVeinGenFeatureCodec.CODEC);}

    @Override
    public List<SmallOreVeinGenFeatureCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<SmallOreVeinGenFeatureCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            SmallOreVeinGenFeatureCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}