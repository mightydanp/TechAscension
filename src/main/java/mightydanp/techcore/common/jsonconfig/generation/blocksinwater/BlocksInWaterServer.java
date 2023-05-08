package mightydanp.techcore.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.BlocksInWaterGenFeatureCodec;
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

public class BlocksInWaterServer extends JsonConfigServer<BlocksInWaterGenFeatureCodec> {

    @Override
    public Map<String, BlocksInWaterGenFeatureCodec> getServerMapFromList(List<BlocksInWaterGenFeatureCodec> blocksInWatersIn) {
        Map<String, BlocksInWaterGenFeatureCodec> codecMap = new LinkedHashMap<>();
        blocksInWatersIn.forEach(blocksInWater -> codecMap.put(blocksInWater.name(), blocksInWater));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<BlocksInWaterGenFeatureCodec> clientList = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureCodec.class::isInstance)
                .map(BlocksInWaterGenFeatureCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(BlocksInWaterGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<BlocksInWaterGenFeatureCodec> optional = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optional.isPresent()) {
                    BlocksInWaterGenFeatureCodec clientCodec = optional.get();
                    JsonObject serverJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(BlocksInWaterGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, BlocksInWaterGenFeatureCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + BlocksInWaterGenFeatureCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(BlocksInWaterGenFeatureCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.blocksInWater.getFirst().getJsonObject(file.getName());
                    BlocksInWaterGenFeatureCodec codec = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        BlocksInWaterGenFeatureCodec clientBlocksInWater = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(clientBlocksInWater);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(BlocksInWaterGenFeatureCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(BlocksInWaterGenFeatureCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/" + BlocksInWaterGenFeatureCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (BlocksInWaterGenFeatureCodec serverCodec : getServerMap().values()) {
            String name = serverCodec.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/" + BlocksInWaterGenFeatureCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation() + "/generation"  + "/" + BlocksInWaterGenFeatureCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.blocksInWater.getFirst().getJsonObject(file.getName());
                    BlocksInWaterGenFeatureCodec client = ((BlocksInWaterRegistry) TCJsonConfigs.blocksInWater.getFirst()).fromJsonObject(jsonObject);

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
        List<BlocksInWaterGenFeatureCodec> list = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureCodec.class::isInstance)
                .map(BlocksInWaterGenFeatureCodec.class::cast).toList();

        Map<String, BlocksInWaterGenFeatureCodec> map = list.stream()
                .collect(Collectors.toMap(BlocksInWaterGenFeatureCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + BlocksInWaterGenFeatureCodec.codecName + " from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, BlocksInWaterGenFeatureCodec config) {
        buffer.writeWithCodec(BlocksInWaterGenFeatureCodec.CODEC, config);

    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<BlocksInWaterGenFeatureCodec> list = message.getConfig(TCJsonConfigs.blocksInWaterID).stream()
                .filter(BlocksInWaterGenFeatureCodec.class::isInstance)
                .map(BlocksInWaterGenFeatureCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((blocksInWater) -> singleToBuffer(buffer, blocksInWater));
    }

    @Override
    public BlocksInWaterGenFeatureCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(BlocksInWaterGenFeatureCodec.CODEC);
    }

    public List<BlocksInWaterGenFeatureCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<BlocksInWaterGenFeatureCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            BlocksInWaterGenFeatureCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}