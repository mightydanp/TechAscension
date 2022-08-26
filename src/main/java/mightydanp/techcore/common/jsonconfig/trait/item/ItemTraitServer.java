package mightydanp.techcore.common.jsonconfig.trait.item;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
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

public class ItemTraitServer extends JsonConfigServer<IItemTrait> {

    public Map<String, IItemTrait> getServerMapFromList(List<IItemTrait> itemTraitsIn) {
        Map<String, IItemTrait> itemTraitsList = new LinkedHashMap<>();
        itemTraitsIn.forEach(itemTrait -> itemTraitsList.put(itemTrait.getRegistry(), itemTrait));

        return itemTraitsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<IItemTrait> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(IItemTrait.class::isInstance)
                .map(IItemTrait.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("item_trait", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, itemTrait) -> {
            sync.set(list.stream().anyMatch(o -> o.getRegistry().equals(name)));

            if(sync.get()) {
                Optional<IItemTrait> optional = list.stream().filter(o -> o.getRegistry().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IItemTrait serverItemTrait = optional.get();
                    JsonObject jsonMaterial = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(itemTrait);
                    JsonObject materialJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(serverItemTrait);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("item_trait", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IItemTrait> clientItemTraits = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/item_trait");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("item_trait", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.itemTrait.getFirst().getJsonObject(file.getName());
                    IItemTrait itemTrait = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).fromJsonObject(jsonObject);
                    clientItemTraits.put(itemTrait.getRegistry(), itemTrait);
                }

                getServerMap().values().forEach(serverItemTrait -> {
                    sync.set(clientItemTraits.containsKey(serverItemTrait.getRegistry()));

                    if(sync.get()) {
                        IItemTrait clientItemTrait = getServerMap().get(serverItemTrait.getRegistry());
                        JsonObject jsonMaterial = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(serverItemTrait);
                        JsonObject materialJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(clientItemTrait);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("item_trait", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("item_trait", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/item_trait");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IItemTrait itemTrait : getServerMap().values()) {
            String name = itemTrait.getRegistry();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(itemTrait);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/item_trait");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/item_trait");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.itemTrait.getFirst().getJsonObject(file.getName());
                    IItemTrait itemTrait = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).fromJsonObject(jsonObject);

                    String name = itemTrait.getRegistry();

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
        List<IItemTrait> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(IItemTrait.class::isInstance)
                .map(IItemTrait.class::cast).toList();

        Map<String, IItemTrait> itemTraits = list.stream()
                .collect(Collectors.toMap(IItemTrait::getRegistry, s -> s));

        serverMap.clear();
        serverMap.putAll(itemTraits);

        TechAscension.LOGGER.info("Loaded {} itemtraits from the server", itemTraits.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IItemTrait itemTrait) {
        buffer.writeUtf(itemTrait.getRegistry());
        buffer.writeVarInt(itemTrait.getColor());

        if(itemTrait.getPounds() != null && itemTrait.getKilograms() == null) {
            buffer.writeUtf("pounds");
            buffer.writeDouble(itemTrait.getPounds());
        }else if(itemTrait.getKilograms() != null && itemTrait.getPounds() == null) {
            buffer.writeUtf("kilograms");
            buffer.writeDouble(itemTrait.getKilograms());
        }

        if(itemTrait.getMeters() != null && itemTrait.getYards() == null) {
            buffer.writeUtf("meters");
            buffer.writeDouble(itemTrait.getPounds());
        }else if(itemTrait.getYards() != null && itemTrait.getMeters() == null) {
            buffer.writeUtf("yards");
            buffer.writeDouble(itemTrait.getKilograms());
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IItemTrait> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(IItemTrait.class::isInstance)
                .map(IItemTrait.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((itemTrait) -> singleToBuffer(buffer, itemTrait));
    }

    @Override
    public IItemTrait singleFromBuffer(FriendlyByteBuf buffer) {

        return new IItemTrait() {

            @Override
            public String getRegistry() {
                return buffer.readUtf();
            }

            @Override
            public Integer getColor() {
                return buffer.readVarInt();
            }

            @Override
            public Integer getDurability() {
                return buffer.readVarInt();
            }

            @Override
            public ITextureIcon getTextureIcon() {
                return (ITextureIcon)TCJsonConfigs.textureIcon.getFirst().registryMap.get(buffer.readUtf());
            }

            final String weight = buffer.readUtf();

            @Override
            public Double getPounds() {
                return weight.equals("pounds") ? buffer.readDouble() : null;
            }

            @Override
            public Double getKilograms() {
                return weight.equals("kilograms") ? buffer.readDouble() : null;
            }

            final String length = buffer.readUtf();

            @Override
            public Double getMeters() {
                return length.equals("meters") ? buffer.readDouble() : null;
            }

            @Override
            public Double getYards() {
                return length.equals("yards") ? buffer.readDouble() : null;
            }
        };
    }

    @Override
    public List<IItemTrait> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IItemTrait> itemTraits = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IItemTrait itemTrait = singleFromBuffer(buffer);

            itemTraits.add(itemTrait);
        }

        return itemTraits;
    }

}