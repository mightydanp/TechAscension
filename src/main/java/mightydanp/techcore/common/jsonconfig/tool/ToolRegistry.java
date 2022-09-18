package mightydanp.techcore.common.jsonconfig.tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;
import net.minecraft.world.item.crafting.Ingredient;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolRegistry extends JsonConfigMultiFile<ITool> {

    @Override
    public void initiate() {
        setJsonFolderName("tool");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(ITool toolIn) {
        String name = toolIn.getName();
        if (registryMap.containsKey(toolIn.getName()))
            throw new IllegalArgumentException("tool with name(" + name + "), already exists.");
        registryMap.put(name, toolIn);
    }

    public ITool getToolByName(String tool) {
        return registryMap.get(tool);
    }

    public Set<ITool> getAllTool() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(ITool tool : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(tool.getName());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(tool.getName(), toJsonObject(tool));
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        ITool tool = fromJsonObject(jsonObject);

                        registryMap.put(tool.getName(), tool);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to tool list because a tool already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("tool json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ITool fromJsonObject(JsonObject jsonObjectIn){
        return new ITool() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }

            @Override
            public Map<Integer, List<List<Ingredient>>> getAssembleStepsItems() {
                Map<Integer, List<List<Ingredient>>> map = new HashMap<>();

                JsonObject assembleSteps = jsonObjectIn.getAsJsonObject("assemble_steps");

                for(int i = 0; i < assembleSteps.size(); i++){
                    List<List<Ingredient>> combinationsList = new ArrayList<>();

                    JsonArray combinations = assembleSteps.getAsJsonArray(String.valueOf(i));

                    for(int j = 0; j < combinations.size(); j++) {
                        JsonArray ingredients = combinations.getAsJsonArray();
                        List<Ingredient> ingredientsList = new ArrayList<>();

                        ingredients.forEach(jsonElement -> ingredientsList.add(Ingredient.fromJson(jsonElement)));

                        combinationsList.add(ingredientsList);
                    }

                    map.put(i + 1, combinationsList);
                }

                return map;
            }
        };
    }

    public JsonObject toJsonObject(ITool tool) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", tool.getName());

        JsonObject assembleSteps = new JsonObject();

        tool.getAssembleStepsItems().forEach((integer, lists) -> {
            JsonArray combinations = new JsonArray();

            lists.forEach(combination -> {
                JsonArray ingredients = new JsonArray();

                combination.forEach(ingredient -> ingredients.add(ingredient.toJson()));

                combinations.add(ingredients);
            });

            assembleSteps.add(String.valueOf(integer), combinations);

        });

        if(assembleSteps.size() > 0){
            jsonObject.add("assemble_steps", assembleSteps);
        }

        return jsonObject;
    }
}