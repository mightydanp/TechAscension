package mightydanp.industrialtech.api.common.jsonconfig.flag;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
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

        if(message.getMaterialFlags().size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("material_flag", sync.get());
            return false;
        }

        getServerMap().forEach((name, materialFlag) -> {
            sync.set(message.getMaterialFlags().stream().anyMatch(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)));

            if(sync.get()) {
                Optional<IMaterialFlag> optional = message.getMaterialFlags().stream().filter(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)).findFirst();

                if(optional.isPresent()) {
                    IMaterialFlag serverMaterialFlag = optional.get();
                    JsonObject jsonMaterial = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).toJsonObject(materialFlag);
                    JsonObject materialJson = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).toJsonObject(serverMaterialFlag);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("material_flag", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IMaterialFlag> clientMaterialFlags = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/material_flag");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("material_flag", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("material_flag", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = IndustrialTech.configSync.materialFlag.getFirst().getJsonObject(file.getName());
                IMaterialFlag materialFlag = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).getFromJsonObject(jsonObject);
                clientMaterialFlags.put(fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix()), materialFlag);
            }

            getServerMap().values().forEach(serverMaterialFlag -> {
                sync.set(clientMaterialFlags.containsKey(fixesToName(serverMaterialFlag.getPrefix(), serverMaterialFlag.getSuffix())));

                if(sync.get()) {
                    IMaterialFlag clientMaterialFlag = getServerMap().get(fixesToName(serverMaterialFlag.getPrefix(), serverMaterialFlag.getSuffix()));
                    JsonObject jsonMaterial = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).toJsonObject(serverMaterialFlag);
                    JsonObject materialJson = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).toJsonObject(clientMaterialFlag);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("material_flag", sync.get());

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
            JsonObject jsonObject = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).toJsonObject(materialFlag);
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
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/material_flag");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.materialFlag.getFirst().getJsonObject(file.getName());
                    IMaterialFlag materialFlag = ((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).getFromJsonObject(jsonObject);

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
        Map<String, IMaterialFlag> materialFlags = message.getMaterialFlags().stream()
                .collect(Collectors.toMap(s -> fixesToName(s.getPrefix(), s.getSuffix()), s -> s));

        getServerMap().clear();
        getServerMap().putAll(materialFlags);

        IndustrialTech.LOGGER.info("Loaded {} material flags from the server", materialFlags.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, IMaterialFlag materialFlag) {//friendlybotbuff
        buffer.writeUtf(fixesToName(materialFlag.getPrefix(), materialFlag.getSuffix()));
        buffer.writeUtf(materialFlag.getPrefix());
        buffer.writeUtf(materialFlag.getSuffix());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getMaterialFlags().size());

        message.getMaterialFlags().forEach((materialFlag) -> {
            singleToBuffer(buffer, materialFlag);
        });
    }

    @Override
    public IMaterialFlag singleFromBuffer(PacketBuffer buffer) {
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
    public List<IMaterialFlag> multipleFromBuffer(PacketBuffer buffer) {
        List<IMaterialFlag> materialFlags = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IMaterialFlag materialFlag = singleFromBuffer(buffer);

            materialFlags.add(materialFlag);
        }

        return materialFlags;
    }

}
