package mightydanp.techcore.common.jsonconfig.generation.orevein;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureCodec;
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

public class OreVeinServer extends JsonConfigServer<OreVeinGenFeatureCodec> {

    @Override
    public Map<String, OreVeinGenFeatureCodec> getServerMapFromList(List<OreVeinGenFeatureCodec> codecs) {
        Map<String, OreVeinGenFeatureCodec> codecMap = new LinkedHashMap<>();
        codecs.forEach(oreVein -> codecMap.put(oreVein.name(), oreVein));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<OreVeinGenFeatureCodec> clientList = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureCodec.class::isInstance)
                .map(OreVeinGenFeatureCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(OreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<OreVeinGenFeatureCodec> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    OreVeinGenFeatureCodec clientOreVein = optionalClientCodec.get();
                    JsonObject serverJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(clientOreVein);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(OreVeinGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, OreVeinGenFeatureCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + OreVeinGenFeatureCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(OreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.oreVein.getFirst().getJsonObject(file.getName());
                    OreVeinGenFeatureCodec codec = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        OreVeinGenFeatureCodec clientCodec = clientMap.get(serverCodec.name());
                        JsonObject serverJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0){
                sync.set(false);
                ConfigSync.syncedJson.put(OreVeinGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(OreVeinGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation/"+ OreVeinGenFeatureCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (OreVeinGenFeatureCodec serverCodec : getServerMap().values()) {
            String name = serverCodec.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation/" + OreVeinGenFeatureCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/"  + OreVeinGenFeatureCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.oreVein.getFirst().getJsonObject(file.getName());
                    OreVeinGenFeatureCodec client = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).fromJsonObject(jsonObject);

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
        List<OreVeinGenFeatureCodec> list = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureCodec.class::isInstance)
                .map(OreVeinGenFeatureCodec.class::cast).toList();

        Map<String, OreVeinGenFeatureCodec> map = list.stream()
                .collect(Collectors.toMap(OreVeinGenFeatureCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + OreVeinGenFeatureCodec.codecName + " from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, OreVeinGenFeatureCodec codec) {
        buffer.writeWithCodec(OreVeinGenFeatureCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<OreVeinGenFeatureCodec> list = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureCodec.class::isInstance)
                .map(OreVeinGenFeatureCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public OreVeinGenFeatureCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(OreVeinGenFeatureCodec.CODEC);
    }

    @Override
    public List<OreVeinGenFeatureCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<OreVeinGenFeatureCodec> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            OreVeinGenFeatureCodec codec = singleFromBuffer(buffer);

            oreVeins.add(codec);
        }

        return oreVeins;
    }

}