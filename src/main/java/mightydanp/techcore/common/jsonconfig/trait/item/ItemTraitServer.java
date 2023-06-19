package mightydanp.techcore.common.jsonconfig.trait.item;

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

public class ItemTraitServer extends JsonConfigServer<ItemTraitCodec> {

    public Map<String, ItemTraitCodec> getServerMapFromList(List<ItemTraitCodec> codecs) {
        Map<String, ItemTraitCodec> itemTraitsList = new LinkedHashMap<>();
        codecs.forEach(itemTrait -> itemTraitsList.put(itemTrait.registry(), itemTrait));

        return itemTraitsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ItemTraitCodec> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(ItemTraitCodec.class::isInstance)
                .map(ItemTraitCodec.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(ItemTraitCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(list.stream().anyMatch(o -> o.registry().equals(name)));

            if(sync.get()) {
                Optional<ItemTraitCodec> optionalClientCodec = list.stream().filter(o -> o.registry().equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    ItemTraitCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(ItemTraitCodec.codecName, sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ItemTraitCodec> codecMap = new HashMap<>();
        Path path = Paths.get(singlePlayerConfigs + "/trait/item");

        File[] fileArray = path.toFile().listFiles();

        if(fileArray != null){
            List<File> folders = Arrays.stream(fileArray).filter(file -> !file.getName().contains(".")).toList();

            for (File folder : folders) {

                File[] files = folder.listFiles();

                if (files != null) {
                    if (getServerMap().size() != files.length) {
                        sync.set(false);
                        ConfigSync.syncedJson.put(ItemTraitCodec.codecName, sync.get());
                        return false;
                    }

                    if (files.length > 0) {

                        for (File file : files) {
                            JsonObject jsonObject = TCJsonConfigs.itemTrait.getFirst().getJsonObject(file.getParentFile().getName() + ":" + file.getName().replace(".json", ""));
                            ItemTraitCodec codec = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).fromJsonObject(jsonObject);
                            codecMap.put(codec.registry(), codec);
                        }

                        getServerMap().values().forEach(serverCodec -> {
                            sync.set(codecMap.containsKey(serverCodec.registry()));

                            if (sync.get()) {
                                ItemTraitCodec clientCodec = getServerMap().get(serverCodec.registry());
                                JsonObject serverJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(serverCodec);
                                JsonObject clientJson = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(clientCodec);

                                sync.set(clientJson.equals(serverJson));
                            }

                        });
                    }

                } else {
                    if (getServerMap().size() > 0) {
                        sync.set(false);
                        ConfigSync.syncedJson.put(ItemTraitCodec.codecName, sync.get());
                        return false;
                    }
                }
            }
        }

        ConfigSync.syncedJson.put(ItemTraitCodec.codecName, sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        Path path = Paths.get("config/" + Ref.mod_id + "/server" + "/trait/item");

        File[] fileArray = path.toFile().listFiles();

        if(fileArray != null) {
            List<File> folders = Arrays.stream(fileArray).filter(file -> !file.getName().contains(".")).toList();

            for (File folder : folders) {
                if (folder.listFiles() != null) {
                    for (File file : Objects.requireNonNull(folder.listFiles())) {
                        Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
                    }
                }

                for (ItemTraitCodec codec : getServerMap().values()) {
                    String name = codec.registry();
                    Path filePath = Paths.get(folder + "/" + name + ".json");
                    JsonObject jsonObject = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).toJsonObject(codec);
                    String s = GSON.toJson(jsonObject);
                    if (!Files.exists(filePath)) {
                        Files.createDirectories(filePath.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                            bufferedwriter.write(s);
                        }
                    }
                }
            }
        }
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        Path path = Paths.get(folderName + "/trait/item");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/trait/item");

        File[] fileArray = path.toFile().listFiles();

        if (configFolder.toFile().listFiles() != null && fileArray != null) {

            List<File> folders = Arrays.stream(fileArray).filter(file -> !file.getName().contains(".")).toList();
            for(File folder : folders) {
                 if (folder.listFiles() == null){
                    for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                        JsonObject jsonObject = TCJsonConfigs.itemTrait.getFirst().getJsonObject(file.getParentFile().getName() + ":" + file.getName());
                        ItemTraitCodec codec = ((ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst()).fromJsonObject(jsonObject);

                        String name = codec.registry();

                        Path filePath = Paths.get(folder + "/" + name + ".json");
                        if (!Files.exists(filePath)) {
                            Files.createDirectories(filePath.getParent());

                            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                                String s = GSON.toJson(jsonObject);
                                bufferedwriter.write(s);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadFromServer(SyncMessage message) {
        List<ItemTraitCodec> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(ItemTraitCodec.class::isInstance)
                .map(ItemTraitCodec.class::cast).toList();

        Map<String, ItemTraitCodec> codecMap = list.stream()
                .collect(Collectors.toMap(ItemTraitCodec::registry, s -> s));

        serverMap.clear();
        serverMap.putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + ItemTraitCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ItemTraitCodec codec) {
        buffer.writeWithCodec(ItemTraitCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ItemTraitCodec> list = message.getConfig(TCJsonConfigs.itemTraitID).stream()
                .filter(ItemTraitCodec.class::isInstance)
                .map(ItemTraitCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((itemTrait) -> singleToBuffer(buffer, itemTrait));
    }

    @Override
    public ItemTraitCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(ItemTraitCodec.CODEC);
    }

    @Override
    public List<ItemTraitCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ItemTraitCodec> itemTraits = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ItemTraitCodec itemTrait = singleFromBuffer(buffer);

            itemTraits.add(itemTrait);
        }

        return itemTraits;
    }

}