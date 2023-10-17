package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class HandCraftingRegistry extends JsonConfigMultiFile<HandCraftingCodec> {

    @Override
    public void initiate() {
        setJsonFolderName("recipe/" + HandCraftingCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(HandCraftingCodec codec) {
        String name = codec.name();

        if (registryMap.containsKey(codec.name()))
            throw new IllegalArgumentException(HandCraftingCodec.codecName + " with name(" + name + "), already exists.");
        registryMap.put(name, codec);
    }

    public HandCraftingCodec getHandCraftingByName(String name) {
        return registryMap.get(name);
    }

    public Set<HandCraftingCodec> getAllHandCrafting() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(HandCraftingCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());

            if (jsonObject.size() == 0) {
                jsonObject = toJsonObject(codec);
                saveJsonObject(codec.name(), jsonObject);
            }
        }
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        HandCraftingCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + HandCraftingCodec.codecName + " list because a " + HandCraftingCodec.codecName + " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(HandCraftingCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public HandCraftingCodec fromJsonObject(JsonObject jsonObjectIn) {
        return HandCraftingCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + HandCraftingCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(HandCraftingCodec codec) {
        return HandCraftingCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + HandCraftingCodec.codecName + ", please fix this"))).getAsJsonObject();
     }

    public static ItemStack itemStackFromJson(JsonObject jsonObject) {
        return CraftingHelper.getItemStack(jsonObject, true, true);
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for(int i = 0; i < json.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }
}