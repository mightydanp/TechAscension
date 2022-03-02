package mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class RandomSurfaceServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, RandomSurfaceGenFeatureConfig> serverRandomSurfacesMap = new HashMap<>();

    RandomSurfaceRegistry RandomSurfaceRegistry = IndustrialTech.randomSurfaceRegistry;

    public Map<String, RandomSurfaceGenFeatureConfig> getServerRandomSurfacesMap(){
        return serverRandomSurfacesMap;
    }

    public boolean serverHasRandomSurfaces(){
        return serverRandomSurfacesMap.size() > 0;
    }

    public static Map<String, RandomSurfaceGenFeatureConfig> getServerRandomSurfacesMap(List<RandomSurfaceGenFeatureConfig> randomSurfacesIn) {
        Map<String, RandomSurfaceGenFeatureConfig> RandomSurfacesList = new LinkedHashMap<>();
        randomSurfacesIn.forEach(randomSurface -> RandomSurfacesList.put(randomSurface.name, randomSurface));

        return RandomSurfacesList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getRandomSurfaces().size() != getServerRandomSurfacesMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("random_surface", sync.get());
            return false;
        }

        getServerRandomSurfacesMap().forEach((name, RandomSurface) -> {
            sync.set(message.getRandomSurfaces().stream().anyMatch(o -> o.name.equals(name)));

            if(sync.get()) {
                Optional<RandomSurfaceGenFeatureConfig> optional = message.getRandomSurfaces().stream().filter(o -> o.name.equals(name)).findFirst();

                if(optional.isPresent()) {
                    RandomSurfaceGenFeatureConfig serverRandomSurface = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.randomSurfaceRegistry.toJsonObject(RandomSurface);
                    JsonObject materialJson = IndustrialTech.randomSurfaceRegistry.toJsonObject(serverRandomSurface);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("random_surface", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, RandomSurfaceGenFeatureConfig> clientRandomSurfaces = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/random_surface");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerRandomSurfacesMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("random_surface", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("random_surface", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = RandomSurfaceRegistry.getJsonObject(file.getName());
                RandomSurfaceGenFeatureConfig randomSurface = RandomSurfaceRegistry.getRandomSurface(jsonObject);
                clientRandomSurfaces.put(randomSurface.name, randomSurface);
            }

            getServerRandomSurfacesMap().values().forEach(serverRandomSurface -> {
                sync.set(clientRandomSurfaces.containsKey(serverRandomSurface.name));

                if(sync.get()) {
                    RandomSurfaceGenFeatureConfig clientRandomSurface = getServerRandomSurfacesMap().get(serverRandomSurface.name);
                    JsonObject jsonMaterial = RandomSurfaceRegistry.toJsonObject(serverRandomSurface);
                    JsonObject materialJson = RandomSurfaceRegistry.toJsonObject(clientRandomSurface);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("random_surface", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/random_surface");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (RandomSurfaceGenFeatureConfig randomSurface : getServerRandomSurfacesMap().values()) {
            String name = randomSurface.name;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = RandomSurfaceRegistry.toJsonObject(randomSurface);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/random_surface");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation"  + "/random_surface");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = RandomSurfaceRegistry.getJsonObject(file.getName());
                    RandomSurfaceGenFeatureConfig randomSurface = RandomSurfaceRegistry.getRandomSurface(jsonObject);

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

    public void loadRandomSurfaces(SyncMessage message) {
        Map<String, RandomSurfaceGenFeatureConfig> RandomSurfaces = message.getRandomSurfaces().stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverRandomSurfacesMap.clear();
        serverRandomSurfacesMap.putAll(RandomSurfaces);

        IndustrialTech.LOGGER.info("Loaded {} random surfaces from the server", RandomSurfaces.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, RandomSurfaceGenFeatureConfig randomSurface) {//friendlybotbuff
        buffer.writeUtf(randomSurface.name);
        buffer.writeInt(randomSurface.rarity);
        buffer.writeInt(randomSurface.biomes.size());
        for(String biome : randomSurface.biomes){
            buffer.writeUtf(biome);
        }

        buffer.writeInt(randomSurface.validBlocks.size());
        for(String validBlock : randomSurface.validBlocks){
            buffer.writeUtf(validBlock);
        }

        buffer.writeInt(randomSurface.biomes.size());
        for(String block : randomSurface.biomes){
            buffer.writeUtf(block);
        }

    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getRandomSurfaces().size());

        message.getRandomSurfaces().forEach((randomSurface) -> {
            singleToBuffer(buffer, randomSurface);
        });
    }

    public static RandomSurfaceGenFeatureConfig singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        int rarity = buffer.readInt();
        int biomesSize = buffer.readInt();
        List<String> biomes = new ArrayList<>();
        for(int i = 0; i < biomesSize; i++){
            String biome = buffer.readUtf();
            biomes.add(biome);
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

        return new RandomSurfaceGenFeatureConfig(name, rarity, biomes, validBlocks, blocks);
    }

    public static List<RandomSurfaceGenFeatureConfig> multipleFromBuffer(PacketBuffer buffer) {
        List<RandomSurfaceGenFeatureConfig> randomSurfaces = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            RandomSurfaceGenFeatureConfig randomSurface = singleFromBuffer(buffer);

            randomSurfaces.add(randomSurface);
        }

        return randomSurfaces;
    }

}