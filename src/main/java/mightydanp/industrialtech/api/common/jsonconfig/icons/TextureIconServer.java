package mightydanp.industrialtech.api.common.jsonconfig.icons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
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
public class TextureIconServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, ITextureIcon> serverTextureIconsMap = new HashMap<>();

    TextureIconRegistry textureIconRegistry = IndustrialTech.textureIconRegistry;

    public Map<String, ITextureIcon> getServerTextureIconsMap(){
        return serverTextureIconsMap;
    }

    public static List<ITextureIcon> getAllTextureIcons() {
        return new ArrayList<>(serverTextureIconsMap.values());
    }

    public boolean serverHasTextureIcons(){
        return serverTextureIconsMap.size() > 0;
    }

    public static Map<String, ITextureIcon> getServerTextureIconsMap(List<ITextureIcon> textureIconsIn) {
        Map<String, ITextureIcon> textureIconsList = new LinkedHashMap<>();
        textureIconsIn.forEach(textureIcon -> textureIconsList.put(textureIcon.getName(), textureIcon));

        return textureIconsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getTextureIcons().size() != getServerTextureIconsMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("texture_icon", sync.get());
            return false;
        }

        getServerTextureIconsMap().forEach((name, textureIcon) -> {
            sync.set(message.getTextureIcons().stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<ITextureIcon> optional = message.getTextureIcons().stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    ITextureIcon serverTextureIcon = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.textureIconRegistry.toJsonObject(textureIcon);
                    JsonObject materialJson = IndustrialTech.textureIconRegistry.toJsonObject(serverTextureIcon);

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
        Map<String, ITextureIcon> clientTextureIcons = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/texture_icon");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerTextureIconsMap().size() != files.length){
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
                JsonObject jsonObject = textureIconRegistry.getJsonObject(file.getAbsolutePath());
                ITextureIcon textureIcon = textureIconRegistry.getTextureIcon(jsonObject);
                clientTextureIcons.put(textureIcon.getName(), textureIcon);
            }

            getServerTextureIconsMap().values().forEach(serverTextureIcon -> {
                sync.set(clientTextureIcons.containsKey(serverTextureIcon.getName()));

                if(sync.get()) {
                    ITextureIcon clientTextureIcon = getServerTextureIconsMap().get(serverTextureIcon.getName());
                    JsonObject jsonMaterial = textureIconRegistry.toJsonObject(serverTextureIcon);
                    JsonObject materialJson = textureIconRegistry.toJsonObject(clientTextureIcon);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("texture_icon", sync.get());

        return sync.get();
    }

    public void syncClientTextureIconsWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/texture_icon");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ITextureIcon textureIcon : getServerTextureIconsMap().values()) {
            String name = textureIcon.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = textureIconRegistry.toJsonObject(textureIcon);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientTextureIconsConfigsWithSinglePlayerWorlds(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/texture_icon");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/texture_icon");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = textureIconRegistry.getJsonObject(file.getName());
                    ITextureIcon textureIcon = textureIconRegistry.getTextureIcon(jsonObject);

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

    public void loadTextureIcons(SyncMessage message) {
        Map<String, ITextureIcon> textureIcons = message.getTextureIcons().stream()
                .collect(Collectors.toMap(s -> s.getName(), s -> s));

        serverTextureIconsMap.clear();
        serverTextureIconsMap.putAll(textureIcons);

        IndustrialTech.LOGGER.info("Loaded {} texture icons from the server", textureIcons.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, ITextureIcon textureIcon) {//friendlybotbuff
        buffer.writeUtf(textureIcon.getName());
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getTextureIcons().size());

        message.getTextureIcons().forEach((textureIcon) -> {
            singleToBuffer(buffer, textureIcon);
        });
    }

    public static ITextureIcon singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return new ITextureIcon() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    public static List<ITextureIcon> multipleFromBuffer(PacketBuffer buffer) {
        List<ITextureIcon> textureIcons = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITextureIcon textureIcon = singleFromBuffer(buffer);

            textureIcons.add(textureIcon);
        }

        return textureIcons;
    }

}