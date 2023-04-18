package mightydanp.techcore.common.jsonconfig.definedstructure;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DefinedStructureServer extends JsonConfigServer<IDefinedStructure> {

    @Override
    public Map<String, IDefinedStructure> getServerMapFromList(List<IDefinedStructure> DefinedStructuresIn) {
        Map<String, IDefinedStructure> definedStructuresList = new LinkedHashMap<>();
        DefinedStructuresIn.forEach(DefinedStructure -> definedStructuresList.put(DefinedStructure.getName(), DefinedStructure));

        return definedStructuresList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        List<IDefinedStructure> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(IDefinedStructure.class::isInstance)
                .map(IDefinedStructure.class::cast).toList();

        AtomicBoolean sync = new AtomicBoolean(true);


        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, DefinedStructure) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IDefinedStructure> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IDefinedStructure serverDefinedStructure = optional.get();
                    JsonObject jsonMaterial = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(DefinedStructure);
                    JsonObject materialJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverDefinedStructure);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        ConfigSync.syncedJson.put("defined_structure", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IDefinedStructure> clientDefinedStructures = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/defined_structure");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }

            if(files.length > 0){
                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    IDefinedStructure DefinedStructure = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);
                    clientDefinedStructures.put(DefinedStructure.getName(), DefinedStructure);
                }

                getServerMap().values().forEach(serverDefinedStructure -> {
                    sync.set(clientDefinedStructures.containsKey(serverDefinedStructure.getName()));

                    if(sync.get()) {
                        IDefinedStructure clientDefinedStructure = getServerMap().get(serverDefinedStructure.getName());
                        JsonObject jsonMaterial = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(serverDefinedStructure);
                        JsonObject materialJson = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(clientDefinedStructure);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("defined_structure", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("defined_structure", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/defined_structure");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IDefinedStructure DefinedStructure : getServerMap().values()) {
            String name = DefinedStructure.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).toJsonObject(DefinedStructure);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/defined_structure");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/defined_structure");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.definedStructure.getFirst().getJsonObject(file.getName());
                    IDefinedStructure DefinedStructure = ((DefinedStructureRegistry) TCJsonConfigs.definedStructure.getFirst()).fromJsonObject(jsonObject);

                    String name = DefinedStructure.getName();

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
        List<IDefinedStructure> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(IDefinedStructure.class::isInstance)
                .map(IDefinedStructure.class::cast).toList();

        Map<String, IDefinedStructure> DefinedStructures = list.stream()
                .collect(Collectors.toMap(IDefinedStructure::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(DefinedStructures);

        TechAscension.LOGGER.info("Loaded {} fluid states from the server", DefinedStructures.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IDefinedStructure DefinedStructure) {
        buffer.writeUtf(DefinedStructure.getName());
        buffer.writeWithCodec(DefinedStructureRegistry.codec, DefinedStructure.getBlockMap());

    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IDefinedStructure> list = message.getConfig(TCJsonConfigs.definedStructureID).stream()
                .filter(IDefinedStructure.class::isInstance)
                .map(IDefinedStructure.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((DefinedStructure) -> {
            singleToBuffer(buffer, DefinedStructure);
        });
    }

    @Override
    public IDefinedStructure singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        Map<BlockPos, BlockState> map = buffer.readWithCodec(DefinedStructureRegistry.codec);

        return new IDefinedStructure() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Map<BlockPos, BlockState> getBlockMap() {
                return map;
            }
        };
    }

    @Override
    public List<IDefinedStructure> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IDefinedStructure> DefinedStructures = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IDefinedStructure DefinedStructure = singleFromBuffer(buffer);

            DefinedStructures.add(DefinedStructure);
        }

        return DefinedStructures;
    }

}