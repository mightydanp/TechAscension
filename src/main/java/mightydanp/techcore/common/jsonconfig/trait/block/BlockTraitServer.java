package mightydanp.techcore.common.jsonconfig.trait.block;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
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

public class BlockTraitServer extends JsonConfigServer<BlockTraitCodec> {

    public Map<String, BlockTraitCodec> getServerMapFromList(List<BlockTraitCodec> blockTraitsIn) {
        Map<String, BlockTraitCodec> codecMap = new LinkedHashMap<>();
        blockTraitsIn.forEach(blockTrait -> codecMap.put(blockTrait.registry(), blockTrait));

        return codecMap;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<BlockTraitCodec> clientList = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(BlockTraitCodec.class::isInstance)
                .map(BlockTraitCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(BlockTraitCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> o.registry().equals(name)));

            if(sync.get()) {
                Optional<BlockTraitCodec> optionalClientCodec = clientList.stream().filter(o -> o.registry().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    BlockTraitCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(BlockTraitCodec.codecName, sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, BlockTraitCodec> clientBlockTraits = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + BlockTraitCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(BlockTraitCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.blockTrait.getFirst().getJsonObject(file.getName());
                    BlockTraitCodec codec = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).fromJsonObject(jsonObject);
                    clientBlockTraits.put(codec.registry(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientBlockTraits.containsKey(serverCodec.registry()));

                    if(sync.get()) {
                        BlockTraitCodec clientCodec = getServerMap().get(serverCodec.registry());
                        JsonObject serverJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(BlockTraitCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(BlockTraitCodec.codecName, sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + BlockTraitCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (BlockTraitCodec blockTrait : getServerMap().values()) {
            String name = blockTrait.registry();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(blockTrait);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + BlockTraitCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + BlockTraitCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.blockTrait.getFirst().getJsonObject(file.getName());
                    BlockTraitCodec codec = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).fromJsonObject(jsonObject);

                    String name = codec.registry();

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
        List<BlockTraitCodec> list = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(BlockTraitCodec.class::isInstance)
                .map(BlockTraitCodec.class::cast).toList();

        Map<String, BlockTraitCodec> codecMap = list.stream()
                .collect(Collectors.toMap(BlockTraitCodec::registry, s -> s));

        serverMap.clear();
        serverMap.putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + BlockTraitCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, BlockTraitCodec codec) {
        buffer.writeWithCodec(BlockTraitCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<BlockTraitCodec> list = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(BlockTraitCodec.class::isInstance)
                .map(BlockTraitCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((blockTrait) -> singleToBuffer(buffer, blockTrait));
    }

    @Override
    public BlockTraitCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(BlockTraitCodec.CODEC);
    }

    @Override
    public List<BlockTraitCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<BlockTraitCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            BlockTraitCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}