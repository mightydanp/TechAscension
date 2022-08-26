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

public class BlockTraitServer extends JsonConfigServer<IBlockTrait> {

    public Map<String, IBlockTrait> getServerMapFromList(List<IBlockTrait> blockTraitsIn) {
        Map<String, IBlockTrait> blockTraitsList = new LinkedHashMap<>();
        blockTraitsIn.forEach(blockTrait -> blockTraitsList.put(blockTrait.getRegistry(), blockTrait));

        return blockTraitsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<IBlockTrait> list = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(IBlockTrait.class::isInstance)
                .map(IBlockTrait.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("block_trait", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, blockTrait) -> {
            sync.set(list.stream().anyMatch(o -> o.getRegistry().equals(name)));

            if(sync.get()) {
                Optional<IBlockTrait> optional = list.stream().filter(o -> o.getRegistry().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IBlockTrait serverBlockTrait = optional.get();
                    JsonObject jsonMaterial = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(blockTrait);
                    JsonObject materialJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(serverBlockTrait);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("block_trait", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IBlockTrait> clientBlockTraits = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/block_trait");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("block_trait", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.blockTrait.getFirst().getJsonObject(file.getName());
                    IBlockTrait blockTrait = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).fromJsonObject(jsonObject);
                    clientBlockTraits.put(blockTrait.getRegistry(), blockTrait);
                }

                getServerMap().values().forEach(serverBlockTrait -> {
                    sync.set(clientBlockTraits.containsKey(serverBlockTrait.getRegistry()));

                    if(sync.get()) {
                        IBlockTrait clientBlockTrait = getServerMap().get(serverBlockTrait.getRegistry());
                        JsonObject jsonMaterial = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(serverBlockTrait);
                        JsonObject materialJson = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(clientBlockTrait);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("block_trait", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("block_trait", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/block_trait");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IBlockTrait blockTrait : getServerMap().values()) {
            String name = blockTrait.getRegistry();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).toJsonObject(blockTrait);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/block_trait");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/block_trait");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.blockTrait.getFirst().getJsonObject(file.getName());
                    IBlockTrait blockTrait = ((BlockTraitRegistry) TCJsonConfigs.blockTrait.getFirst()).fromJsonObject(jsonObject);

                    String name = blockTrait.getRegistry();

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
        List<IBlockTrait> list = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(IBlockTrait.class::isInstance)
                .map(IBlockTrait.class::cast).toList();

        Map<String, IBlockTrait> blockTraits = list.stream()
                .collect(Collectors.toMap(IBlockTrait::getRegistry, s -> s));

        serverMap.clear();
        serverMap.putAll(blockTraits);

        TechAscension.LOGGER.info("Loaded {} block traits from the server", blockTraits.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IBlockTrait blockTrait) {
        buffer.writeUtf(blockTrait.getRegistry());
        buffer.writeVarInt(blockTrait.getColor());

        if(blockTrait.getPounds() != null && blockTrait.getKilograms() == null) {
            buffer.writeUtf("pounds");
            buffer.writeDouble(blockTrait.getPounds());
        }else if(blockTrait.getKilograms() != null && blockTrait.getPounds() == null) {
            buffer.writeUtf("kilograms");
            buffer.writeDouble(blockTrait.getKilograms());
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IBlockTrait> list = message.getConfig(TCJsonConfigs.blockTraitID).stream()
                .filter(IBlockTrait.class::isInstance)
                .map(IBlockTrait.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((blockTrait) -> singleToBuffer(buffer, blockTrait));
    }

    @Override
    public IBlockTrait singleFromBuffer(FriendlyByteBuf buffer) {
        String registry = buffer.readUtf();
        Integer color = buffer.readVarInt();

        String weight = buffer.readUtf();

        return new IBlockTrait() {

            @Override
            public String getRegistry() {
                return registry;
            }

            @Override
            public Integer getColor() {
                return color;
            }

            @Override
            public Double getPounds() {
                return weight.equals("pounds") ? buffer.readDouble() : null;
            }

            @Override
            public Double getKilograms() {
                return weight.equals("kilograms") ? buffer.readDouble() : null;
            }
        };
    }

    @Override
    public List<IBlockTrait> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IBlockTrait> blockTraits = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IBlockTrait blockTrait = singleFromBuffer(buffer);

            blockTraits.add(blockTrait);
        }

        return blockTraits;
    }

}