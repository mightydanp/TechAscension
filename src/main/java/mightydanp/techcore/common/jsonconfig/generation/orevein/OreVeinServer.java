package mightydanp.techcore.common.jsonconfig.generation.orevein;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureConfig;
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

public class OreVeinServer extends JsonConfigServer<OreVeinGenFeatureConfig> {

    @Override
    public Map<String, OreVeinGenFeatureConfig> getServerMapFromList(List<OreVeinGenFeatureConfig> oreVeinsIn) {
        Map<String, OreVeinGenFeatureConfig> OreVeinsList = new LinkedHashMap<>();
        oreVeinsIn.forEach(oreVein -> OreVeinsList.put(oreVein.name(), oreVein));

        return OreVeinsList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<OreVeinGenFeatureConfig> list = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureConfig.class::isInstance)
                .map(OreVeinGenFeatureConfig.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("ore_vein", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, oreVein) -> {
            sync.set(list.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<OreVeinGenFeatureConfig> optional = list.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optional.isPresent()) {
                    OreVeinGenFeatureConfig clientOreVein = optional.get();
                    JsonObject serverJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(oreVein);
                    JsonObject clientJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(clientOreVein);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, OreVeinGenFeatureConfig> clientOreVeins = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/ore_vein");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("ore_vein", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.oreVein.getFirst().getJsonObject(file.getName());
                    OreVeinGenFeatureConfig oreVein = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).fromJsonObject(jsonObject);
                    clientOreVeins.put(oreVein.name(), oreVein);
                }

                getServerMap().values().forEach(serverOreVein -> {
                    sync.set(clientOreVeins.containsKey(serverOreVein.name()));

                    if(sync.get()) {
                        OreVeinGenFeatureConfig clientOreVein = getServerMap().get(serverOreVein.name());
                        JsonObject serverJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(serverOreVein);
                        JsonObject clientJson = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(clientOreVein);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0){
                sync.set(false);
                ConfigSync.syncedJson.put("ore_vein", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/ore_vein");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (OreVeinGenFeatureConfig oreVein : getServerMap().values()) {
            String name = oreVein.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).toJsonObject(oreVein);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/ore_vein");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation"  + "/ore_vein");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.oreVein.getFirst().getJsonObject(file.getName());
                    OreVeinGenFeatureConfig oreVein = ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).fromJsonObject(jsonObject);

                    String name = oreVein.name();

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
        List<OreVeinGenFeatureConfig> list = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureConfig.class::isInstance)
                .map(OreVeinGenFeatureConfig.class::cast).toList();

        Map<String, OreVeinGenFeatureConfig> OreVeins = list.stream()
                .collect(Collectors.toMap(OreVeinGenFeatureConfig::name, s -> s));

        serverMap.clear();
        serverMap.putAll(OreVeins);

        TechAscension.LOGGER.info("Loaded {} ore veins from the server", OreVeins.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, OreVeinGenFeatureConfig config) {
        buffer.writeWithCodec(OreVeinGenFeatureConfig.CODEC, config);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<OreVeinGenFeatureConfig> list = message.getConfig(TCJsonConfigs.oreVeinID).stream()
                .filter(OreVeinGenFeatureConfig.class::isInstance)
                .map(OreVeinGenFeatureConfig.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((oreVein) -> singleToBuffer(buffer, oreVein));
    }

    @Override
    public OreVeinGenFeatureConfig singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(OreVeinGenFeatureConfig.CODEC);
    }

    @Override
    public List<OreVeinGenFeatureConfig> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<OreVeinGenFeatureConfig> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            OreVeinGenFeatureConfig oreVein = singleFromBuffer(buffer);

            oreVeins.add(oreVein);
        }

        return oreVeins;
    }

}