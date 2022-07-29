package mightydanp.industrialcore.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonObject;
import mightydanp.industrialapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialcore.common.jsonconfig.ICJsonConfigs;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
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
        randomSurfacesIn.forEach(randomSurface -> RandomSurfacesList.put(randomSurface.name, randomSurface));

        return RandomSurfacesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<RandomSurfaceGenFeatureConfig> list = message.getConfig(ICJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();
        
        if(list.size() != getServerMap().size()){
            sync.set(false);
            ConfigSync.syncedJson.put("random_surface", sync.get());
            return false;
        }

        getServerMap().forEach((name, RandomSurface) -> {
            sync.set(list.stream().anyMatch(o -> o.name.equals(name)));

            if(sync.get()) {
                Optional<RandomSurfaceGenFeatureConfig> optional = list.stream().filter(o -> o.name.equals(name)).findFirst();

                if(optional.isPresent()) {
                    RandomSurfaceGenFeatureConfig serverRandomSurface = optional.get();
                    JsonObject jsonMaterial = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).toJsonObject(RandomSurface);
                    JsonObject materialJson = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).toJsonObject(serverRandomSurface);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        ConfigSync.syncedJson.put("random_surface", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, RandomSurfaceGenFeatureConfig> clientRandomSurfaces = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/random_surface");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("random_surface", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            ConfigSync.syncedJson.put("random_surface", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = ICJsonConfigs.randomSurface.getFirst().getJsonObject(file.getName());
                RandomSurfaceGenFeatureConfig randomSurface = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).getFromJsonObject(jsonObject);
                clientRandomSurfaces.put(randomSurface.name, randomSurface);
            }

            getServerMap().values().forEach(serverRandomSurface -> {
                sync.set(clientRandomSurfaces.containsKey(serverRandomSurface.name));

                if(sync.get()) {
                    RandomSurfaceGenFeatureConfig clientRandomSurface = getServerMap().get(serverRandomSurface.name);
                    JsonObject jsonMaterial = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).toJsonObject(serverRandomSurface);
                    JsonObject materialJson = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).toJsonObject(clientRandomSurface);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
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
            String name = randomSurface.name;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).toJsonObject(randomSurface);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/random_surface");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation"  + "/random_surface");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = ICJsonConfigs.randomSurface.getFirst().getJsonObject(file.getName());
                    RandomSurfaceGenFeatureConfig randomSurface = ((RandomSurfaceRegistry)ICJsonConfigs.randomSurface.getFirst()).getFromJsonObject(jsonObject);

                    String name = randomSurface.name;

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
        List<RandomSurfaceGenFeatureConfig> list = message.getConfig(ICJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();

        Map<String, RandomSurfaceGenFeatureConfig> RandomSurfaces = list.stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(RandomSurfaces);

        IndustrialTech.LOGGER.info("Loaded {} random surfaces from the server", RandomSurfaces.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, RandomSurfaceGenFeatureConfig config) {//friendlybotbuff
        buffer.writeUtf(config.name);
        buffer.writeInt(config.rarity);

        buffer.writeInt(config.dimensions.size());
        config.dimensions.forEach(buffer::writeUtf);

        buffer.writeInt(config.validBiomes.size());
        config.validBiomes.forEach(buffer::writeUtf);

        buffer.writeInt(config.invalidBiomes.size());
        config.invalidBiomes.forEach(buffer::writeUtf);

        buffer.writeInt(config.validBlocks.size());
        config.validBlocks.forEach(buffer::writeUtf);

        buffer.writeInt(config.blocks.size());
        config.blocks.forEach(buffer::writeUtf);

    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<RandomSurfaceGenFeatureConfig> list = message.getConfig(ICJsonConfigs.randomSurfaceID).stream()
                .filter(RandomSurfaceGenFeatureConfig.class::isInstance)
                .map(RandomSurfaceGenFeatureConfig.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((randomSurface) -> singleToBuffer(buffer, randomSurface));
    }

    @Override
    public RandomSurfaceGenFeatureConfig singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        int rarity = buffer.readInt();

        int dimensions = buffer.readInt();
        List<String> dimensionsList = new ArrayList<>();
        for(int i = 0; i < dimensions; i++){
            String dimension = buffer.readUtf();
            dimensionsList.add(dimension);
        }

        int validBiomes = buffer.readInt();
        List<String> validBiomesList = new ArrayList<>();
        for(int i = 0; i < validBiomes; i++){
            String validBiome = buffer.readUtf();
            validBiomesList.add(validBiome);
        }

        int invalidBiomes = buffer.readInt();
        List<String> invalidBiomesList = new ArrayList<>();
        for(int i = 0; i < invalidBiomes; i++){
            String invalidBiome = buffer.readUtf();
            invalidBiomesList.add(invalidBiome);
        }

        List<String> validBlocks = new ArrayList<>();

        int validBlocksSize = buffer.readInt();
        for(int i = 0; i < validBlocksSize; i++){
            String block = buffer.readUtf();
            validBlocks.add(block);
        }

        List<String> blocks = new ArrayList<>();

        int blocksSize = buffer.readInt();
        for(int i = 0; i < blocksSize; i++){
            String block = buffer.readUtf();
            blocks.add(block);
        }

        return new RandomSurfaceGenFeatureConfig(name, rarity, dimensionsList, validBiomesList, invalidBiomesList, validBlocks, blocks);
    }

    @Override
    public List<RandomSurfaceGenFeatureConfig> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<RandomSurfaceGenFeatureConfig> randomSurfaces = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            RandomSurfaceGenFeatureConfig randomSurface = singleFromBuffer(buffer);

            randomSurfaces.add(randomSurface);
        }

        return randomSurfaces;
    }

}