package mightydanp.industrialtech.api.common.jsonconfig.generation.smallore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
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

public class SmallOreVeinServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, SmallOreVeinGenFeatureConfig> serverSmallOreVeinsMap = new HashMap<>();

    SmallOreVeinRegistry smallOreVeinRegistry = IndustrialTech.smallOreVeinRegistry;

    public Map<String, SmallOreVeinGenFeatureConfig> getServerSmallOreVeinsMap(){
        return serverSmallOreVeinsMap;
    }

    public boolean serverHasSmallOreVeins(){
        return serverSmallOreVeinsMap.size() > 0;
    }

    public static Map<String, SmallOreVeinGenFeatureConfig> getServerSmallOreVeinsMap(List<SmallOreVeinGenFeatureConfig> oreVeinsIn) {
        Map<String, SmallOreVeinGenFeatureConfig> SmallOreVeinsList = new LinkedHashMap<>();
        oreVeinsIn.forEach(oreVein -> SmallOreVeinsList.put(oreVein.name, oreVein));

        return SmallOreVeinsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getSmallOreVeins().size() != getServerSmallOreVeinsMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        getServerSmallOreVeinsMap().forEach((name, smallOreVein) -> {
            sync.set(message.getSmallOreVeins().stream().anyMatch(o -> o.name.equals(name)));

            if(sync.get()) {
                Optional<SmallOreVeinGenFeatureConfig> optional = message.getSmallOreVeins().stream().filter(o -> o.name.equals(name)).findFirst();

                if(optional.isPresent()) {
                    SmallOreVeinGenFeatureConfig serverSmallOreVein = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.smallOreVeinRegistry.toJsonObject(smallOreVein);
                    JsonObject materialJson = IndustrialTech.smallOreVeinRegistry.toJsonObject(serverSmallOreVein);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, SmallOreVeinGenFeatureConfig> clientSmallOreVeins = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/ore_vein");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerSmallOreVeinsMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("ore_vein", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = smallOreVeinRegistry.getJsonObject(file.getName());
                SmallOreVeinGenFeatureConfig oreVein = smallOreVeinRegistry.getSmallOreVein(jsonObject);
                clientSmallOreVeins.put(oreVein.name, oreVein);
            }

            getServerSmallOreVeinsMap().values().forEach(serverSmallOreVein -> {
                sync.set(clientSmallOreVeins.containsKey(serverSmallOreVein.name));

                if(sync.get()) {
                    SmallOreVeinGenFeatureConfig clientSmallOreVein = getServerSmallOreVeinsMap().get(serverSmallOreVein.name);
                    JsonObject jsonMaterial = smallOreVeinRegistry.toJsonObject(serverSmallOreVein);
                    JsonObject materialJson = smallOreVeinRegistry.toJsonObject(clientSmallOreVein);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    public void syncClientWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/ore_vein");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (SmallOreVeinGenFeatureConfig oreVein : getServerSmallOreVeinsMap().values()) {
            String name = oreVein.name;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = smallOreVeinRegistry.toJsonObject(oreVein);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/ore_vein");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation"  + "/ore_vein");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = smallOreVeinRegistry.getJsonObject(file.getName());
                    SmallOreVeinGenFeatureConfig oreVein = smallOreVeinRegistry.getSmallOreVein(jsonObject);

                    String name = oreVein.name;

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

    public void loadSmallOreVeins(SyncMessage message) {
        Map<String, SmallOreVeinGenFeatureConfig> SmallOreVeins = message.getSmallOreVeins().stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverSmallOreVeinsMap.clear();
        serverSmallOreVeinsMap.putAll(SmallOreVeins);

        IndustrialTech.LOGGER.info("Loaded {} ore veins from the server", SmallOreVeins.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, SmallOreVeinGenFeatureConfig oreVein) {//friendlybotbuff
        buffer.writeUtf(oreVein.name);
        buffer.writeInt(oreVein.rarity);
        buffer.writeInt(oreVein.minHeight);
        buffer.writeInt(oreVein.maxHeight);
        buffer.writeInt(oreVein.biomes.size());
        for(String biome : oreVein.biomes){
            buffer.writeUtf(biome);
        }

        buffer.writeInt(oreVein.blocksAndChances.size());

        for(Pair<String, Integer> veinBlockAndChance : oreVein.blocksAndChances){
            buffer.writeUtf(veinBlockAndChance.getFirst());
            buffer.writeInt(veinBlockAndChance.getSecond());
        }
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getSmallOreVeins().size());

        message.getSmallOreVeins().forEach((smallOreVein) -> {
            singleToBuffer(buffer, smallOreVein);
        });
    }

    public static SmallOreVeinGenFeatureConfig singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        int rarity = buffer.readInt();
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
        int biomesSize = buffer.readInt();
        List<String> biomes = new ArrayList<>();
        for(int i = 0; i < biomesSize; i++){
            String biome = buffer.readUtf();
            biomes.add(biome);
        }

        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();

        int veinBlocksAndChancesSize = buffer.readInt();
        for(int i = 0; i < veinBlocksAndChancesSize; i++){
            String block = buffer.readUtf();
            int chance = buffer.readInt();
            veinBlocksAndChances.add(new Pair<>(block, chance));
        }
        return new SmallOreVeinGenFeatureConfig(name, rarity, minHeight, maxHeight, biomes, veinBlocksAndChances);
    }

    public static List<SmallOreVeinGenFeatureConfig> multipleFromBuffer(PacketBuffer buffer) {
        List<SmallOreVeinGenFeatureConfig> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            SmallOreVeinGenFeatureConfig oreVein = singleFromBuffer(buffer);

            oreVeins.add(oreVein);
        }

        return oreVeins;
    }

}