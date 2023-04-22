package mightydanp.techcore.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.BlocksInWaterGenFeatureConfig;
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

public class BlocksInWaterServer extends JsonConfigServer<BlocksInWaterGenFeatureConfig> {

    @Override
    public Map<String, BlocksInWaterGenFeatureConfig> getServerMapFromList(List<BlocksInWaterGenFeatureConfig> blocksInWatersIn) {
        Map<String, BlocksInWaterGenFeatureConfig> BlocksInWatersList = new LinkedHashMap<>();
        blocksInWatersIn.forEach(blocksInWater -> BlocksInWatersList.put(blocksInWater.name(), blocksInWater));

        return BlocksInWatersList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<BlocksInWaterGenFeatureConfig> list = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureConfig.class::isInstance)
                .map(BlocksInWaterGenFeatureConfig.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("blocks_in_water", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverBlocksInWater) -> {
            sync.set(list.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<BlocksInWaterGenFeatureConfig> optional = list.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optional.isPresent()) {
                    BlocksInWaterGenFeatureConfig clientBlocksInWater = optional.get();
                    JsonObject serverJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(serverBlocksInWater);
                    JsonObject clientJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(clientBlocksInWater);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put("blocks_in_water", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, BlocksInWaterGenFeatureConfig> clientBlocksInWaters = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/blocks_in_water");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("blocks_in_water", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.blocksInWater.getFirst().getJsonObject(file.getName());
                    BlocksInWaterGenFeatureConfig blocksInWater = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).fromJsonObject(jsonObject);
                    clientBlocksInWaters.put(blocksInWater.name(), blocksInWater);
                }

                getServerMap().values().forEach(serverBlocksInWater -> {
                    sync.set(clientBlocksInWaters.containsKey(serverBlocksInWater.name()));

                    if(sync.get()) {
                        BlocksInWaterGenFeatureConfig clientBlocksInWater = getServerMap().get(serverBlocksInWater.name());
                        JsonObject serverJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(serverBlocksInWater);
                        JsonObject clientJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(clientBlocksInWater);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("blocks_in_water", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("blocks_in_water", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/blocks_in_water");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (BlocksInWaterGenFeatureConfig blocksInWater : getServerMap().values()) {
            String name = blocksInWater.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(blocksInWater);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/blocks_in_water");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation"  + "/blocks_in_water");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.blocksInWater.getFirst().getJsonObject(file.getName());
                    BlocksInWaterGenFeatureConfig blocksInWater = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).fromJsonObject(jsonObject);

                    String name = blocksInWater.name();

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
        List<BlocksInWaterGenFeatureConfig> list = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureConfig.class::isInstance)
                .map(BlocksInWaterGenFeatureConfig.class::cast).toList();

        Map<String, BlocksInWaterGenFeatureConfig> BlocksInWaters = list.stream()
                .collect(Collectors.toMap(BlocksInWaterGenFeatureConfig::name, s -> s));

        serverMap.clear();
        serverMap.putAll(BlocksInWaters);

        TechAscension.LOGGER.info("Loaded {} blocks in waters from the server", BlocksInWaters.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, BlocksInWaterGenFeatureConfig config) {
        buffer.writeWithCodec(BlocksInWaterGenFeatureConfig.CODEC, config);

    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<BlocksInWaterGenFeatureConfig> list = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureConfig.class::isInstance)
                .map(BlocksInWaterGenFeatureConfig.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((blocksInWater) -> singleToBuffer(buffer, blocksInWater));
    }

    @Override
    public BlocksInWaterGenFeatureConfig singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(BlocksInWaterGenFeatureConfig.CODEC);
    }

    public List<BlocksInWaterGenFeatureConfig> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<BlocksInWaterGenFeatureConfig> blocksInWaters = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            BlocksInWaterGenFeatureConfig blocksInWater = singleFromBuffer(buffer);

            blocksInWaters.add(blocksInWater);
        }

        return blocksInWaters;
    }

}