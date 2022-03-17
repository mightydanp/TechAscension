package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ToolType;

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
 * Created by MightyDanp on 1/25/2022.
 */
public class ToolTypeServer extends JsonConfigServer<IToolType> {

    @Override
    public Map<String, IToolType> getServerMapFromList(List<IToolType> toolTypesIn) {
        Map<String, IToolType> toolTypesList = new LinkedHashMap<>();
        toolTypesIn.forEach(toolType -> toolTypesList.put(fixesToName(toolType.getPrefix(), toolType.getSuffix()), toolType));

        return toolTypesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getToolTypes().size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("tool_type", sync.get());
            return false;
        }

        getServerMap().forEach((name, toolType) -> {
            sync.set(message.getToolTypes().stream().anyMatch(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)));

            if(sync.get()) {
                Optional<IToolType> optional = message.getToolTypes().stream().filter(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)).findFirst();

                if(optional.isPresent()) {
                    IToolType serverToolType = optional.get();
                    JsonObject jsonMaterial = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).toJsonObject(toolType);
                    JsonObject materialJson = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).toJsonObject(serverToolType);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("tool_type", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IToolType> clientToolTypes = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/tool_type");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("tool_type", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("tool_type", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = IndustrialTech.configSync.toolType.getFirst().getJsonObject(file.getName());
                IToolType toolType = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).getFromJsonObject(jsonObject);
                clientToolTypes.put(fixesToName(toolType.getPrefix(), toolType.getSuffix()), toolType);
            }

            getServerMap().values().forEach(serverToolType -> {
                sync.set(clientToolTypes.containsKey(fixesToName(serverToolType.getPrefix(), serverToolType.getSuffix())));

                if(sync.get()) {
                    IToolType clientToolType = getServerMap().get(fixesToName(serverToolType.getPrefix(), serverToolType.getSuffix()));
                    JsonObject jsonMaterial = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).toJsonObject(serverToolType);
                    JsonObject materialJson = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).toJsonObject(clientToolType);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("tool_type", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/tool_type");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IToolType toolType : getServerMap().values()) {
            String name = fixesToName(toolType.getPrefix(), toolType.getSuffix());
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).toJsonObject(toolType);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/tool_type");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/tool_type");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.toolType.getFirst().getJsonObject(file.getName());
                    IToolType toolType = ((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).getFromJsonObject(jsonObject);

                    String name = fixesToName(toolType.getPrefix(), toolType.getSuffix());

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
        Map<String, IToolType> toolTypes = message.getToolTypes().stream()
                .collect(Collectors.toMap(s -> fixesToName(s.getPrefix(), s.getSuffix()), s -> s));

        serverMap.clear();
        serverMap.putAll(toolTypes);

        IndustrialTech.LOGGER.info("Loaded {} tool types from the server", toolTypes.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, IToolType toolType) {//friendlybotbuff
        buffer.writeUtf(fixesToName(toolType.getPrefix(), toolType.getSuffix()));
        buffer.writeUtf(toolType.getPrefix());
        buffer.writeUtf(toolType.getSuffix());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getToolTypes().size());

        message.getToolTypes().forEach((toolType) -> {
            singleToBuffer(buffer, toolType);
        });
    }

    @Override
    public IToolType singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        String prefix = buffer.readUtf();
        String suffix = buffer.readUtf();

        return new IToolType() {
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

            @Override
            public ToolType getToolType() {
                return ToolType.get(fixesToName(prefix, suffix));
            }
        };
    }

    @Override
    public List<IToolType> multipleFromBuffer(PacketBuffer buffer) {
        List<IToolType> toolTypes = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IToolType toolType = singleFromBuffer(buffer);

            toolTypes.add(toolType);
        }

        return toolTypes;
    }

}
