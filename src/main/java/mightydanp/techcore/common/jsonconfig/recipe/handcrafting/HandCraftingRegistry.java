package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;
import net.minecraft.core.NonNullList;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class HandCraftingRegistry extends JsonConfigMultiFile<IHandCrafting> {

    @Override
    public void initiate() {
        setJsonFolderName("recipe/hand_crafting");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        IHandCrafting recipe = new IHandCrafting() {
            @Override
            public String getName() {
                return "test";
            }

            @Override
            public Integer getInputAmount() {
                return 1;
            }

            @Override
            public NonNullList<Ingredient> getInput() {
                NonNullList<Ingredient> list = NonNullList.create();
                list.add(Ingredient.of(new ItemStack(Items.BRICKS.asItem(), 1)));

                return list;
            }

            @Override
            public Integer getOutputAmount() {
                return 4;
            }

            @Override
            public NonNullList<Ingredient> getOutput() {
                NonNullList<Ingredient> list = NonNullList.create();
                list.add(Ingredient.of(new ItemStack(Items.ACACIA_DOOR.asItem(), 1)));

                return list;
            }
        };

        register(recipe);

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IHandCrafting handCraftingIn) {
        String name = handCraftingIn.getName();
        NonNullList<Ingredient> input = handCraftingIn.getInput();
        NonNullList<Ingredient> output = handCraftingIn.getOutput();
        if (registryMap.containsKey(handCraftingIn.getName()))
            throw new IllegalArgumentException("hand crafting with name(" + name + "), already exists.");
        registryMap.put(name, handCraftingIn);
    }

    public IHandCrafting getHandCraftingByName(String hand_crafting) {
        return registryMap.get(hand_crafting);
    }

    public Set<IHandCrafting> getAllHandCrafting() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(IHandCrafting handCrafting : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(handCrafting.getName());

            if (jsonObject.size() == 0) {
                jsonObject = toJsonObject(handCrafting);
                saveJsonObject(handCrafting.getName(), jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(getFromJsonObject(jsonObject))) {
                        IHandCrafting handCrafting = getFromJsonObject(jsonObject);

                        registryMap.put(handCrafting.getName(), handCrafting);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to hand crafting list because a hand crafting already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("hand crafting json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IHandCrafting getFromJsonObject(JsonObject jsonObjectIn) {
        return new IHandCrafting() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }

            @Override
            public Integer getInputAmount() {
                return jsonObjectIn.get("input_amount").getAsInt();
            }

            @Override
            public NonNullList<Ingredient> getInput() {
                return itemsFromJson(GsonHelper.getAsJsonArray(jsonObjectIn, "input_ingredients"));
            }

            @Override
            public Integer getOutputAmount() {
                return jsonObjectIn.get("output_amount").getAsInt();
            }

            @Override
            public NonNullList<Ingredient> getOutput() {
                return itemsFromJson(GsonHelper.getAsJsonArray(jsonObjectIn, "output_ingredients"));
            }
        };
    }

    public JsonObject toJsonObject(IHandCrafting handCrafting) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", handCrafting.getName());

        jsonObject.addProperty("input_amount", handCrafting.getInputAmount());

        JsonArray inputList = new JsonArray();

        for(Ingredient ingredient : handCrafting.getInput()){
            inputList.add(ingredient.toJson());
        }

        if(inputList.size() > 0){
            jsonObject.add("input_ingredients", inputList);
        }

        jsonObject.addProperty("output_amount", handCrafting.getOutputAmount());

        JsonArray outputList = new JsonArray();

        for(Ingredient ingredient : handCrafting.getOutput()){
            outputList.add(ingredient.toJson());
        }

        if(outputList.size() > 0){
            jsonObject.add("output_ingredients", outputList);
        }

        return jsonObject;
    }

    public static ItemStack itemStackFromJson(JsonObject p_151275_) {
        return CraftingHelper.getItemStack(p_151275_, true, true);
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray p_44276_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < p_44276_.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(p_44276_.get(i));
            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }

        return nonnulllist;
    }
}