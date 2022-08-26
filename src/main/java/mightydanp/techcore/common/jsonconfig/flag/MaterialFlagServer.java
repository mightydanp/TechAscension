package mightydanp.techcore.common.jsonconfig.flag;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
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

/**
 * Created by MightyDanp on 1/23/2022.
 */
public class MaterialFlagServer extends JsonConfigServer<IMaterialFlag> {
    @Override
    public Map<String, IMaterialFlag> getServerMapFromList(List<IMaterialFlag> materialFlagsIn) {
        Map<String, IMaterialFlag> materialFlagsList = new LinkedHashMap<>();
        materialFlagsIn.forEach(materialFlag -> materialFlagsList.put(fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix()), materialFlag));

        return materialFlagsList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<IMaterialFlag> list = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(IMaterialFlag.class::isInstance)
                .map(IMaterialFlag.class::cast).toList();

        if(list.size() != getServerMap().size()){
            sync.set(false);
            ConfigSync.syncedJson.put("material_flag", sync.get());
            return false;
        }

        getServerMap().forEach((name, materialFlag) -> {
            sync.set(list.stream().anyMatch(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)));

            if(sync.get()) {
                Optional<IMaterialFlag> optional = list.stream().filter(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)).findFirst();

                if(optional.isPresent()) {
                    IMaterialFlag serverMaterialFlag = optional.get();
                    JsonObject jsonMaterial = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(materialFlag);
                    JsonObject materialJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(serverMaterialFlag);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("material_flag", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IMaterialFlag> clientMaterialFlags = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/material_flag");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("material_flag", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.materialFlag.getFirst().getJsonObject(file.getName());
                    IMaterialFlag materialFlag = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).fromJsonObject(jsonObject);
                    clientMaterialFlags.put(fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix()), materialFlag);
                }

                getServerMap().values().forEach(serverMaterialFlag -> {
                    sync.set(clientMaterialFlags.containsKey(fixesToName(serverMaterialFlag.getPrefix(), serverMaterialFlag.getSuffix())));

                    if(sync.get()) {
                        IMaterialFlag clientMaterialFlag = getServerMap().get(fixesToName(serverMaterialFlag.getPrefix(), serverMaterialFlag.getSuffix()));
                        JsonObject jsonMaterial = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(serverMaterialFlag);
                        JsonObject materialJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(clientMaterialFlag);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }

        }else{
            sync.set(false);
            ConfigSync.syncedJson.put("material_flag", sync.get());
            return false;
        }

        ConfigSync.syncedJson.put("material_flag", sync.get());

        return sync.get();
    }


    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/material_flag");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IMaterialFlag materialFlag : getServerMap().values()) {
            String name = fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix());
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(materialFlag);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/material_flag");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/material_flag");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.materialFlag.getFirst().getJsonObject(file.getName());
                    IMaterialFlag materialFlag = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).fromJsonObject(jsonObject);

                    String name = fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix());

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
        List<IMaterialFlag> list = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(IMaterialFlag.class::isInstance)
                .map(IMaterialFlag.class::cast).toList();

        Map<String, IMaterialFlag> materialFlags = list.stream()
                .collect(Collectors.toMap(s -> fixesToName(s.getPrefix(), s.getSuffix()), s -> s));

        getServerMap().clear();
        getServerMap().putAll(materialFlags);

        TechAscension.LOGGER.info("Loaded {} material flags from the server", materialFlags.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IMaterialFlag materialFlag) {
        buffer.writeUtf(fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix()));
        buffer.writeUtf(materialFlag.getPrefix());
        buffer.writeUtf(materialFlag.getSuffix());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IMaterialFlag> list = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(IMaterialFlag.class::isInstance)
                .map(IMaterialFlag.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((materialFlag) -> {
            singleToBuffer(buffer, materialFlag);
        });
    }

    @Override
    public IMaterialFlag singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        String prefix = buffer.readUtf();
        String suffix = buffer.readUtf();

        return new IMaterialFlag() {
            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public String getSuffix() {
                return suffix;
            }

            @Override
            public Pair<String, String> getFixes() {
                return new Pair<>(prefix, suffix);
            }
        };
    }

    @Override
    public List<IMaterialFlag> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IMaterialFlag> materialFlags = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IMaterialFlag materialFlag = singleFromBuffer(buffer);

            materialFlags.add(materialFlag);
        }

        return materialFlags;
    }

}
