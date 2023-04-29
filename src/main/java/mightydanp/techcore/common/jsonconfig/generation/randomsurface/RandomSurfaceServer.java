package mightydanp.techcore.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.definedstructure.DefinedStructureCodec;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
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


public class RandomSurfaceServer extends JsonConfigServer<RandomSurfaceGenFeatureConfig> {

    @Override
    public Map<String, RandomSurfaceGenFeatureConfig> getServerMapFromList(List<RandomSurfaceGenFeatureConfig> randomSurfacesIn) {
        Map<String, RandomSurfaceGenFeatureConfig> RandomSurfacesList = new LinkedHashMap<>();
        randomSurfacesIn.forEach(randomSurface -> RandomSurfacesList.put(randomSurface.name(), randomSurface));

        return RandomSurfacesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<RandomSurfaceGenFeatureConfig> clientList = message.getConfig(TCJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();
        
        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("random_surface", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<RandomSurfaceGenFeatureConfig> optionalClientCodec = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    RandomSurfaceGenFeatureConfig clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put("random_surface", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, RandomSurfaceGenFeatureConfig> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/random_surface");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("random_surface", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.randomSurface.getFirst().getJsonObject(file.getName());
                    RandomSurfaceGenFeatureConfig codec = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        RandomSurfaceGenFeatureConfig clientCodec = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("random_surface", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("random_surface", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/random_surface");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (RandomSurfaceGenFeatureConfig randomSurface : getServerMap().values()) {
            String name = randomSurface.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).toJsonObject(randomSurface);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/random_surface");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation"  + "/random_surface");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.randomSurface.getFirst().getJsonObject(file.getName());
                    RandomSurfaceGenFeatureConfig client = ((RandomSurfaceRegistry) TCJsonConfigs.randomSurface.getFirst()).fromJsonObject(jsonObject);

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
        List<RandomSurfaceGenFeatureConfig> list = message.getConfig(TCJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();

        Map<String, RandomSurfaceGenFeatureConfig> map = list.stream()
                .collect(Collectors.toMap(RandomSurfaceGenFeatureConfig::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} random surfaces from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, RandomSurfaceGenFeatureConfig config) {
        buffer.writeWithCodec(RandomSurfaceGenFeatureConfig.CODEC, config);

    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<RandomSurfaceGenFeatureConfig> list = message.getConfig(TCJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((randomSurface) -> singleToBuffer(buffer, randomSurface));
    }

    @Override
    public RandomSurfaceGenFeatureConfig singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(RandomSurfaceGenFeatureConfig.CODEC);
    }

    @Override
    public List<RandomSurfaceGenFeatureConfig> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<RandomSurfaceGenFeatureConfig> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            RandomSurfaceGenFeatureConfig codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}