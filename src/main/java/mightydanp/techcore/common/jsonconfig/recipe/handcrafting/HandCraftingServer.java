package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

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
 * Created by MightyDanp on 1/3/2022.
 */
public class HandCraftingServer extends JsonConfigServer<IHandCrafting> {

    public Map<String, IHandCrafting> getServerMapFromList(List<IHandCrafting> handCraftingIn) {
        Map<String, IHandCrafting> handCraftingList = new LinkedHashMap<>();
        handCraftingIn.forEach(handCrafting -> handCraftingList.put(handCrafting.getName(), handCrafting));

        return handCraftingList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<IHandCrafting> list = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(IHandCrafting.class::isInstance)
                .map(IHandCrafting.class::cast).toList();

        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("hand_crafting", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, handCrafting) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IHandCrafting> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IHandCrafting serverHandCrafting = optional.get();
                    JsonObject json = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(handCrafting);
                    JsonObject jsonServer = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(serverHandCrafting);

                    sync.set(jsonServer.equals(json));
                }
            }
        });

        ConfigSync.syncedJson.put("hand_crafting", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IHandCrafting> clientHandCraftingList = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/recipe" + "/hand_crafting");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("hand_crafting", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.handCrafting.getFirst().getJsonObject(file.getName());
                    IHandCrafting handCrafting = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).getFromJsonObject(jsonObject);
                    clientHandCraftingList.put(handCrafting.getName(), handCrafting);
                }

                getServerMap().values().forEach(serverHandCrafting -> {
                    sync.set(clientHandCraftingList.containsKey(serverHandCrafting.getName()));

                    if(sync.get()) {
                        IHandCrafting clientHandCrafting = getServerMap().get(serverHandCrafting.getName());
                        JsonObject jsonMaterial = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(serverHandCrafting);
                        JsonObject materialJson = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(clientHandCrafting);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("hand_crafting", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("hand_crafting", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/recipe" + "/hand_crafting");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IHandCrafting handCrafting : getServerMap().values()) {
            String name = handCrafting.getName();
            Path file = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).toJsonObject(handCrafting);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(file)) {
                Files.createDirectories(file.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(file)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        Path singlePlayerSaveConfigFolder = Paths.get(folderName+ "/recipe" + "/hand_crafting");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()+ "/recipe" + "/hand_crafting");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.handCrafting.getFirst().getJsonObject(file.getName());
                    IHandCrafting handCrafting = ((HandCraftingRegistry) TCJsonConfigs.handCrafting.getFirst()).getFromJsonObject(jsonObject);

                    String name = handCrafting.getName();

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
        List<IHandCrafting> list = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(IHandCrafting.class::isInstance)
                .map(IHandCrafting.class::cast).toList();

        Map<String, IHandCrafting> handCrafting = list.stream()
                .collect(Collectors.toMap(IHandCrafting::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(handCrafting);

        TechAscension.LOGGER.info("Loaded {} hand crafting from the server", handCrafting.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IHandCrafting handCrafting) {
        buffer.writeUtf(handCrafting.getName());

        buffer.writeInt(handCrafting.getInputAmount());

        buffer.writeInt(handCrafting.getInput().size());

        for(Ingredient ingredient : handCrafting.getInput()) {
            ingredient.toNetwork(buffer);
        }

        buffer.writeInt(handCrafting.getOutputAmount());

        buffer.writeInt(handCrafting.getOutput().size());

        for(Ingredient ingredient : handCrafting.getOutput()) {
            ingredient.toNetwork(buffer);
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IHandCrafting> list = message.getConfig(TCJsonConfigs.handCraftingID).stream()
                .filter(IHandCrafting.class::isInstance)
                .map(IHandCrafting.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((handCrafting) -> singleToBuffer(buffer, handCrafting));
    }

    @Override
    public IHandCrafting singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        int inputAmount = buffer.readInt();
        int inputSize = buffer.readInt();

        NonNullList<Ingredient> inputs = NonNullList.withSize(inputSize, Ingredient.EMPTY);

        inputs.replaceAll(ignored -> Ingredient.fromNetwork(buffer));

        int outputAmount = buffer.readInt();

        int outputSize = buffer.readInt();

        NonNullList<Ingredient> outputs = NonNullList.withSize(outputSize, Ingredient.EMPTY);

        outputs.replaceAll(ignored -> Ingredient.fromNetwork(buffer));

        return new IHandCrafting() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public NonNullList<Ingredient> getInput() {
                return inputs;
            }

            @Override
            public Integer getInputAmount() {
                return inputAmount;
            }

            @Override
            public NonNullList<Ingredient> getOutput() {
                return outputs;
            }

            @Override
            public Integer getOutputAmount() {
                return outputAmount;
            }
        };
    }

    @Override
    public List<IHandCrafting> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IHandCrafting> handCraftingList = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IHandCrafting handCrafting = singleFromBuffer(buffer);

            handCraftingList.add(handCrafting);
        }

        return handCraftingList;
    }

}
