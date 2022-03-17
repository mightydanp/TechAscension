package mightydanp.industrialtech.api.common.jsonconfig.tool.part;

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
 * Created by MightyDanp on 1/25/2022.
 */
public class ToolPartServer extends JsonConfigServer<IToolPart> {

    @Override
    public Map<String, IToolPart> getServerMapFromList(List<IToolPart> toolPartsIn) {
        Map<String, IToolPart> toolPartsList = new LinkedHashMap<>();
        toolPartsIn.forEach(toolPart -> toolPartsList.put(fixesToName(toolPart.getPrefix(), toolPart.getSuffix()), toolPart));

        return toolPartsList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getToolParts().size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("tool_part", sync.get());
            return false;
        }

        getServerMap().forEach((name, toolPart) -> {
            sync.set(message.getToolParts().stream().anyMatch(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)));

            if(sync.get()) {
                Optional<IToolPart> optional = message.getToolParts().stream().filter(o -> fixesToName(o.getPrefix(), o.getSuffix()).equals(name)).findFirst();

                if(optional.isPresent()) {
                    IToolPart serverToolPart = optional.get();
                    JsonObject jsonMaterial = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).toJsonObject(toolPart);
                    JsonObject materialJson = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).toJsonObject(serverToolPart);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("tool_part", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IToolPart> clientToolParts = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/tool_part");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("tool_part", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("tool_part", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = IndustrialTech.configSync.toolPart.getFirst().getJsonObject(file.getName());
                IToolPart toolPart = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).getFromJsonObject(jsonObject);
                clientToolParts.put(fixesToName(toolPart.getPrefix(), toolPart.getSuffix()), toolPart);
            }

            getServerMap().values().forEach(serverToolPart -> {
                sync.set(clientToolParts.containsKey(fixesToName(serverToolPart.getPrefix(), serverToolPart.getSuffix())));

                if(sync.get()) {
                    IToolPart clientToolPart = getServerMap().get(fixesToName(serverToolPart.getPrefix(), serverToolPart.getSuffix()));
                    JsonObject jsonMaterial = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).toJsonObject(serverToolPart);
                    JsonObject materialJson = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).toJsonObject(clientToolPart);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("tool_part", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/tool_part");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IToolPart toolPart : getServerMap().values()) {
            String name = fixesToName(toolPart.getPrefix(), toolPart.getSuffix());
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).toJsonObject(toolPart);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/tool_part");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/tool_part");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.toolPart.getFirst().getJsonObject(file.getName());
                    IToolPart toolPart = ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).getFromJsonObject(jsonObject);

                    String name = fixesToName(toolPart.getPrefix(), toolPart.getSuffix());

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
        Map<String, IToolPart> toolParts = message.getToolParts().stream()
                .collect(Collectors.toMap(s -> fixesToName(s.getPrefix(), s.getSuffix()), s -> s));

        serverMap.clear();
        serverMap.putAll(toolParts);

        IndustrialTech.LOGGER.info("Loaded {} tool parts from the server", toolParts.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, IToolPart toolPart) {//friendlybotbuff
        buffer.writeUtf(fixesToName(toolPart.getPrefix(), toolPart.getSuffix()));
        buffer.writeUtf(toolPart.getPrefix());
        buffer.writeUtf(toolPart.getSuffix());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getToolParts().size());

        message.getToolParts().forEach((toolPart) -> {
            singleToBuffer(buffer, toolPart);
        });
    }

    @Override
    public IToolPart singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        String prefix = buffer.readUtf();
        String suffix = buffer.readUtf();

        return new IToolPart() {
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
    public List<IToolPart> multipleFromBuffer(PacketBuffer buffer) {
        List<IToolPart> toolParts = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IToolPart toolPart = singleFromBuffer(buffer);

            toolParts.add(toolPart);
        }

        return toolParts;
    }

}
