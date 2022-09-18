package mightydanp.techcore.common.jsonconfig.tool;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.TechAscension;
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
 * Created by MightyDanp on 1/25/2022.
 */
public class ToolServer extends JsonConfigServer<ITool> {

    public Map<String, ITool> getServerMapFromList(List<ITool> toolsIn) {
        Map<String, ITool> toolsList = new LinkedHashMap<>();
        toolsIn.forEach(tool -> toolsList.put(tool.getName(), tool));

        return toolsList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ITool> list = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast).toList();
        
        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("tool", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, tool) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<ITool> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    ITool serverTool = optional.get();
                    JsonObject jsonMaterial = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(tool);
                    JsonObject materialJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(serverTool);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("tool", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, ITool> clientTools = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/tool");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("tool", sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.tool.getFirst().getJsonObject(file.getName());
                    ITool tool = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).fromJsonObject(jsonObject);
                    clientTools.put(tool.getName(), tool);
                }

                getServerMap().values().forEach(serverTool -> {
                    sync.set(clientTools.containsKey(serverTool.getName()));

                    if(sync.get()) {
                        ITool clientTool = getServerMap().get(serverTool.getName());
                        JsonObject jsonMaterial = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(serverTool);
                        JsonObject materialJson = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(clientTool);

                        sync.set(materialJson.equals(jsonMaterial));
                    }

                });
            }
        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("tool", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("tool", sync.get());

        return sync.get();
    }

    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/tool");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ITool tool : getServerMap().values()) {
            String name = tool.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).toJsonObject(tool);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/tool");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/tool");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.tool.getFirst().getJsonObject(file.getName());
                    ITool tool = ((ToolRegistry) TCJsonConfigs.tool.getFirst()).fromJsonObject(jsonObject);

                    String name = tool.getName();

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
        List<ITool> list = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast).toList();

        Map<String, ITool> tools = list.stream()
                .collect(Collectors.toMap(ITool::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(tools);

        TechAscension.LOGGER.info("Loaded {} tools from the server", tools.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ITool tool) {
        buffer.writeUtf(tool.getName());

        buffer.writeInt(tool.getAssembleStepsItems().size());

        tool.getAssembleStepsItems().forEach((integer, combinations) -> {
            buffer.writeInt(integer);
            buffer.writeInt(combinations.size());

            for (List<Ingredient> ingredients : combinations) {
                buffer.writeInt(ingredients.size());

                ingredients.forEach(ingredient -> ingredient.toNetwork(buffer));
            }
        });
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ITool> list = message.getConfig(TCJsonConfigs.toolID).stream()
                .filter(ITool.class::isInstance)
                .map(ITool.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((tool) -> singleToBuffer(buffer, tool));
    }

    @Override
    public ITool singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return new ITool() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Map<Integer, List<List<Ingredient>>> getAssembleStepsItems() {
                Map<Integer, List<List<Ingredient>>> assembleSteps = new HashMap<>();

                int assembleStepsSize = buffer.readInt();

                for(int i = 0; i < assembleStepsSize; i++){
                    int assembleStep = buffer.readInt();
                    int combinationsSize = buffer.readInt();

                    List<List<Ingredient>> combinationsList = new ArrayList<>();

                    for(int j = 0; j < combinationsSize; j++){
                        int ingredientSize = buffer.readInt();

                        List<Ingredient> ingredientsList = new ArrayList<>();

                        for(int k = 0; k < ingredientSize; k++){
                            ingredientsList.add(Ingredient.fromNetwork(buffer));
                        }

                        combinationsList.add(ingredientsList);
                    }

                    assembleSteps.put(assembleStep, combinationsList);
                }

                return assembleSteps;
            }
        };
    }

    @Override
    public List<ITool> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ITool> tools = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITool tool = singleFromBuffer(buffer);

            tools.add(tool);
        }

        return tools;
    }

}