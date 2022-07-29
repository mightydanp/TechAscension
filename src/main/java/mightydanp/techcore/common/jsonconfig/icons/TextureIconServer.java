package mightydanp.techcore.common.jsonconfig.icons;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techcore.common.jsonconfig.ICJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techascension.common.TechAscension;
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
 * Created by MightyDanp on 1/25/2022.
 */
public class TextureIconServer extends JsonConfigServer<ITextureIcon> {

    public Map<String, ITextureIcon> getServerMapFromList(List<ITextureIcon> textureIconsIn) {
        Map<String, ITextureIcon> textureIconsList = new LinkedHashMap<>();
        textureIconsIn.forEach(textureIcon -> textureIconsList.put(textureIcon.getName(), textureIcon));

        return textureIconsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ITextureIcon> list = message.getConfig(ICJsonConfigs.textureIconID).stream()
                .filter(ITextureIcon.class::isInstance)
                .map(ITextureIcon.class::cast).toList();
        
        if(list.size() != getServerMap().size()){
            sync.set(false);
            ConfigSync.syncedJson.put("texture_icon", sync.get());
            return false;
        }

        getServerMap().forEach((name, textureIcon) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<ITextureIcon> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    ITextureIcon serverTextureIcon = optional.get();
                    JsonObject jsonMaterial = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).toJsonObject(textureIcon);
                    JsonObject materialJson = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).toJsonObject(serverTextureIcon);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        ConfigSync.syncedJson.put("texture_icon", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ITextureIcon> clientTextureIcons = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/texture_icon");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("texture_icon", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            ConfigSync.syncedJson.put("texture_icon", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = ICJsonConfigs.textureIcon.getFirst().getJsonObject(file.getName());
                ITextureIcon textureIcon = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).getFromJsonObject(jsonObject);
                clientTextureIcons.put(textureIcon.getName(), textureIcon);
            }

            getServerMap().values().forEach(serverTextureIcon -> {
                sync.set(clientTextureIcons.containsKey(serverTextureIcon.getName()));

                if(sync.get()) {
                    ITextureIcon clientTextureIcon = getServerMap().get(serverTextureIcon.getName());
                    JsonObject jsonMaterial = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).toJsonObject(serverTextureIcon);
                    JsonObject materialJson = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).toJsonObject(clientTextureIcon);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        ConfigSync.syncedJson.put("texture_icon", sync.get());

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

        for (ITextureIcon textureIcon : getServerMap().values()) {
            String name = textureIcon.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).toJsonObject(textureIcon);
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
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/texture_icon");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = ICJsonConfigs.textureIcon.getFirst().getJsonObject(file.getName());
                    ITextureIcon textureIcon = ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).getFromJsonObject(jsonObject);

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
        List<ITextureIcon> list = message.getConfig(ICJsonConfigs.textureIconID).stream()
                .filter(ITextureIcon.class::isInstance)
                .map(ITextureIcon.class::cast).toList();

        Map<String, ITextureIcon> textureIcons = list.stream()
                .collect(Collectors.toMap(ITextureIcon::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(textureIcons);

        TechAscension.LOGGER.info("Loaded {} texture icons from the server", textureIcons.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ITextureIcon textureIcon) {
        buffer.writeUtf(textureIcon.getName());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ITextureIcon> list = message.getConfig(ICJsonConfigs.textureIconID).stream()
                .filter(ITextureIcon.class::isInstance)
                .map(ITextureIcon.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((textureIcon) -> singleToBuffer(buffer, textureIcon));
    }

    @Override
    public ITextureIcon singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return new ITextureIcon() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public List<ITextureIcon> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ITextureIcon> textureIcons = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITextureIcon textureIcon = singleFromBuffer(buffer);

            textureIcons.add(textureIcon);
        }

        return textureIcons;
    }

}