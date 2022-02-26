package mightydanp.industrialtech.api.common.jsonconfig.generation.orevein;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
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

public class OreVeinServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, OreGenFeatureConfig> serverOreVeinsMap = new HashMap<>();

    OreVeinRegistry OreVeinRegistry = IndustrialTech.oreVeinRegistry;

    public Map<String, OreGenFeatureConfig> getServerOreVeinsMap(){
        return serverOreVeinsMap;
    }

    public boolean serverHasOreVeins(){
        return serverOreVeinsMap.size() > 0;
    }

    public static Map<String, OreGenFeatureConfig> getServerOreVeinsMap(List<OreGenFeatureConfig> oreVeinsIn) {
        Map<String, OreGenFeatureConfig> OreVeinsList = new LinkedHashMap<>();
        oreVeinsIn.forEach(oreVein -> OreVeinsList.put(oreVein.veinName, oreVein));

        return OreVeinsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getOreVeins().size() != getServerOreVeinsMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        getServerOreVeinsMap().forEach((name, OreVein) -> {
            sync.set(message.getOreVeins().stream().anyMatch(o -> o.veinName.equals(name)));

            if(sync.get()) {
                Optional<OreGenFeatureConfig> optional = message.getOreVeins().stream().filter(o -> o.veinName.equals(name)).findFirst();

                if(optional.isPresent()) {
                    OreGenFeatureConfig serverOreVein = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.oreVeinRegistry.toJsonObject(OreVein);
                    JsonObject materialJson = IndustrialTech.oreVeinRegistry.toJsonObject(serverOreVein);

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
        Map<String, OreGenFeatureConfig> clientOreVeins = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/ore_vein");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerOreVeinsMap().size() != files.length){
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
                JsonObject jsonObject = OreVeinRegistry.getJsonObject(file.getAbsolutePath());
                OreGenFeatureConfig oreVein = OreVeinRegistry.getOreVein(jsonObject);
                clientOreVeins.put(oreVein.veinName, oreVein);
            }

            getServerOreVeinsMap().values().forEach(serverOreVein -> {
                sync.set(clientOreVeins.containsKey(serverOreVein.veinName));

                if(sync.get()) {
                    OreGenFeatureConfig clientOreVein = getServerOreVeinsMap().get(serverOreVein.veinName);
                    JsonObject jsonMaterial = OreVeinRegistry.toJsonObject(serverOreVein);
                    JsonObject materialJson = OreVeinRegistry.toJsonObject(clientOreVein);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    public void syncClientOreVeinsWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/ore_vein");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (OreGenFeatureConfig oreVein : getServerOreVeinsMap().values()) {
            String name = oreVein.veinName;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = OreVeinRegistry.toJsonObject(oreVein);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientOreVeinsConfigsWithSinglePlayerWorlds(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/ore_vein");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation"  + "/ore_vein");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = OreVeinRegistry.getJsonObject(file.getName());
                    OreGenFeatureConfig oreVein = OreVeinRegistry.getOreVein(jsonObject);

                    String name = oreVein.veinName;

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

    public void loadOreVeins(SyncMessage message) {
        Map<String, OreGenFeatureConfig> OreVeins = message.getOreVeins().stream()
                .collect(Collectors.toMap(s -> s.veinName, s -> s));

        serverOreVeinsMap.clear();
        serverOreVeinsMap.putAll(OreVeins);

        IndustrialTech.LOGGER.info("Loaded {} ore veins from the server", OreVeins.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, OreGenFeatureConfig oreVein) {//friendlybotbuff
        buffer.writeUtf(oreVein.veinName);
        buffer.writeInt(oreVein.rarity);
        buffer.writeInt(oreVein.minHeight);
        buffer.writeInt(oreVein.maxHeight);
        buffer.writeInt(oreVein.minRadius);
        buffer.writeInt(oreVein.minNumberOfSmallOreLayers);
        buffer.writeInt(oreVein.veinBlocksAndChances.size());

        for(Pair<String, Integer> veinBlockAndChance : oreVein.veinBlocksAndChances){
            buffer.writeUtf(veinBlockAndChance.getFirst());
            buffer.writeInt(veinBlockAndChance.getSecond());
        }
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getOreVeins().size());

        message.getOreVeins().forEach((oreVein) -> {
            singleToBuffer(buffer, oreVein);
        });
    }

    public static OreGenFeatureConfig singleFromBuffer(PacketBuffer buffer) {
        String veinName = buffer.readUtf();
        int rarity = buffer.readInt();
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
        int minRadius = buffer.readInt();
        int minNumberOfSmallOreLayers = buffer.readInt();
        int veinBlocksAndChancesSize = buffer.readInt();
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();

        for(int i = 0; i < veinBlocksAndChancesSize; i++){
            String block = buffer.readUtf();
            int chance = buffer.readInt();
            veinBlocksAndChances.add(new Pair<>(block, chance));
        }
        return new OreGenFeatureConfig(veinName, rarity, minHeight, maxHeight, minRadius, minNumberOfSmallOreLayers, veinBlocksAndChances);
    }

    public static List<OreGenFeatureConfig> multipleFromBuffer(PacketBuffer buffer) {
        List<OreGenFeatureConfig> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            OreGenFeatureConfig oreVein = singleFromBuffer(buffer);

            oreVeins.add(oreVein);
        }

        return oreVeins;
    }

}