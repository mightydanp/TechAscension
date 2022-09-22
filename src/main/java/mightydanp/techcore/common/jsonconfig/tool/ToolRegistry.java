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

    public void buildAndRegisterTool(ITool tool){
        this.register(tool);
        this.saveJsonObject(tool.getName(), toJsonObject(tool));
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
            public Integer getUseDamage() {
                return jsonObjectIn.get("use_damage").getAsInt();
            }

            @Override
            public List<String> getEffectiveOn() {
                List<String> registry = new ArrayList<>();
                JsonArray array = jsonObjectIn.get("effective_on").getAsJsonArray();
                array.forEach(jsonElement -> registry.add(jsonElement.getAsString()));

                return registry;
            }

            @Override
            public List<Ingredient> getHandleItems() {
                List<Ingredient> items = new ArrayList<>();
                JsonArray array = jsonObjectIn.get("handle_items").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    items.add(Ingredient.fromJson(array.get(i).getAsJsonObject().get("item")));
                }

                return items;
            }

            @Override
            public List<Ingredient> getHeadItems() {
                List<Ingredient> items = new ArrayList<>();

                JsonArray array = jsonObjectIn.get("head_items").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    items.add(Ingredient.fromJson(array.get(i).getAsJsonObject().get("item")));
                }

                return items;
            }

            @Override
            public List<Ingredient> getBindingItems() {
                List<Ingredient> items = new ArrayList<>();

                JsonArray array = jsonObjectIn.get("binding_items").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    items.add(Ingredient.fromJson(array.get(i).getAsJsonObject().get("item")));
                }

                return items;
            }

            @Override
            public Map<Integer, List<Map<Ingredient, Integer>>> getAssembleStepsItems() {
                Map<Integer, List<Map<Ingredient, Integer>>> map = new HashMap<>();

                JsonObject assembleSteps = jsonObjectIn.getAsJsonObject("assemble_steps");

                for(int i = 0; i < assembleSteps.size(); i++){
                    List<Map<Ingredient, Integer>> combinationsList = new ArrayList<>();

                    JsonArray combinations = assembleSteps.get(String.valueOf(i)).getAsJsonArray();

                    for(int j = 0; j < combinations.size(); j++) {
                        JsonArray ingredients = combinations.get(j).getAsJsonArray();
                        Map<Ingredient, Integer> ingredientsList = new HashMap<>();

                        for(int k = 0; k < ingredients.size(); k++){
                            Ingredient ingredient = Ingredient.fromJson(ingredients.get(i).getAsJsonObject().get("item" + k));
                            int amount = ingredients.get(i).getAsJsonObject().get("amount" + k).getAsInt();

                            ingredientsList.put(ingredient, amount);

                        }

                        combinationsList.add(ingredientsList);
                    }

                    map.put(i + 1, combinationsList);
                }

                return map;
            }

            @Override
            public List<Map<Ingredient, Integer>> getDisassembleItems() {
                JsonObject disassembleItems = jsonObjectIn.getAsJsonObject("disassemble_items");

                List<Map<Ingredient, Integer>> list = new ArrayList<>();

                for(int i = 0; i < disassembleItems.size(); i++){
                    Map<Ingredient, Integer> combinationsList = new HashMap<>();

                    JsonArray combinations = disassembleItems.get(String.valueOf(i)).getAsJsonArray();

                    for(int j = 0; j < combinations.size(); j++) {
                        Ingredient ingredient = Ingredient.fromJson(combinations.get(i).getAsJsonObject().get("item" + j));
                        int amount = combinations.get(i).getAsJsonObject().get("amount" + j).getAsInt();

                        combinationsList.put(ingredient, amount);
                    }

                    list.add(combinationsList);
                }

                return list;
            }
        };
    }

    public JsonObject toJsonObject(ITool tool) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", tool.getName());

        jsonObject.addProperty("use_damage", tool.getUseDamage());

        JsonArray array = new JsonArray();

        tool.getEffectiveOn().forEach(array::add);

        if(array.size() > 0) {
            jsonObject.add("get_effective_on", array);
        }

        {
            JsonArray items = new JsonArray();
            tool.getHandleItems().forEach(ingredient -> items.add(ingredient.toJson()));

            jsonObject.add("handle_items", items);
        }

        {
            JsonArray items = new JsonArray();
            tool.getHeadItems().forEach(ingredient -> items.add(ingredient.toJson()));

            jsonObject.add("head_items", items);
        }

        {
            JsonArray items = new JsonArray();
            tool.getBindingItems().forEach(ingredient -> items.add(ingredient.toJson()));

            jsonObject.add("binding_items", items);
        }

        JsonObject assembleSteps = new JsonObject();

        tool.getAssembleStepsItems().forEach((integer, lists) -> {
            JsonArray combinations = new JsonArray();

            for (Map<Ingredient, Integer> combinationsMap : lists) {
                JsonObject ingredients = new JsonObject();

                for(int i = 0; i < combinationsMap.size(); i++){
                    ingredients.add("item" + i, combinationsMap.keySet().stream().toList().get(i).toJson());
                    ingredients.addProperty("amount" + i, combinationsMap.values().stream().toList().get(i));
                }

                combinations.add(ingredients);
            }
            assembleSteps.add(String.valueOf(integer), combinations);

        });

        if(assembleSteps.size() > 0){
            jsonObject.add("assemble_steps", assembleSteps);
        }

        JsonArray disassembleItems = new JsonArray();

        JsonArray combinations = new JsonArray();
        tool.getDisassembleItems().forEach(map -> {
            JsonObject ingredients = new JsonObject();
            for(int i = 0; i < tool.getDisassembleItems().size(); i++){
                ingredients.add("item" + i, map.keySet().stream().toList().get(i).toJson());
                ingredients.addProperty("amount" + i, map.values().stream().toList().get(i));
            }

            combinations.add(ingredients);
        });

        disassembleItems.add(combinations);

        if(disassembleItems.size() > 0){
            jsonObject.add("disassemble_items", disassembleItems);
        }

        return jsonObject;
    }
}