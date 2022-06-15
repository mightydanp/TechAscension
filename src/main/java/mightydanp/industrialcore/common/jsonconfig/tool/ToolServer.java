package mightydanp.industrialcore.common.jsonconfig.tool;

import com.google.gson.JsonObject;
import mightydanp.industrialcore.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialcore.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialcore.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
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

import static java.util.stream.Collectors.toList;

/**
 * Created by MightyDanp on 1/25/2022.
 */
public class ToolServer extends JsonConfigServer<ITool> {

    public Map<String, ITool> getServerMapFromList(List<ITool> textureIconsIn) {
        Map<String, ITool> textureIconsList = new LinkedHashMap<>();
        textureIconsIn.forEach(textureIcon -> textureIconsList.put(textureIcon.getName(), textureIcon));

        return textureIconsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ITool> list = message.getConfig(IndustrialTech.configSync.textureIconID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast)
                .collect(toList());
        
        if(list.size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("texture_icon", sync.get());
            return false;
        }

        getServerMap().forEach((name, textureIcon) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<ITool> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    ITool serverTextureIcon = optional.get();
                    JsonObject jsonMaterial = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).toJsonObject(textureIcon);
                    JsonObject materialJson = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).toJsonObject(serverTextureIcon);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("texture_icon", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ITool> clientTextureIcons = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/texture_icon");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("texture_icon", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("texture_icon", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = IndustrialTech.configSync.textureIcon.getFirst().getJsonObject(file.getName());
                ITool textureIcon = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).getFromJsonObject(jsonObject);
                clientTextureIcons.put(textureIcon.getName(), textureIcon);
            }

            getServerMap().values().forEach(serverTextureIcon -> {
                sync.set(clientTextureIcons.containsKey(serverTextureIcon.getName()));

                if(sync.get()) {
                    ITool clientTextureIcon = getServerMap().get(serverTextureIcon.getName());
                    JsonObject jsonMaterial = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).toJsonObject(serverTextureIcon);
                    JsonObject materialJson = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).toJsonObject(clientTextureIcon);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("texture_icon", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/texture_icon");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ITool textureIcon : getServerMap().values()) {
            String name = textureIcon.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).toJsonObject(textureIcon);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/texture_icon");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/texture_icon");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.textureIcon.getFirst().getJsonObject(file.getName());
                    ITool textureIcon = ((ToolRegistry)IndustrialTech.configSync.textureIcon.getFirst()).getFromJsonObject(jsonObject);

                    String name = textureIcon.getName();

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
        List<ITool> list = message.getConfig(IndustrialTech.configSync.textureIconID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast)
                .collect(toList());

        Map<String, ITool> textureIcons = list.stream()
                .collect(Collectors.toMap(ITool::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(textureIcons);

        IndustrialTech.LOGGER.info("Loaded {} texture icons from the server", textureIcons.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ITool textureIcon) {//friendlybotbuff
        buffer.writeUtf(textureIcon.getName());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ITool> list = message.getConfig(IndustrialTech.configSync.textureIconID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast)
                .collect(toList());

        buffer.writeVarInt(list.size());

        list.forEach((textureIcon) -> singleToBuffer(buffer, textureIcon));
    }

    @Override
    public ITool singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return new ITool() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public List<ITool> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ITool> textureIcons = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITool textureIcon = singleFromBuffer(buffer);

            textureIcons.add(textureIcon);
        }

        return textureIcons;
    }

}