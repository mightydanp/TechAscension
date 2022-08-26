package mightydanp.techcore.common.jsonconfig.trait.item;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ItemTraitRegistry extends JsonConfigMultiFile<IItemTrait> {

    @Override
    public void initiate() {
        setJsonFolderName("trait/item");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        /*
        for (DefaultItemTrait itemTrait : DefaultItemTrait.values()) {
            register(itemTrait);
        }
        */

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IItemTrait itemTraitIn) {
        String registry = itemTraitIn.getRegistry();
        if (registryMap.containsKey(itemTraitIn.getRegistry()))
            throw new IllegalArgumentException("item trait for registry item(" + registry + "), already exists.");
        registryMap.put(registry, itemTraitIn);
    }

    public IItemTrait getItemTraitByName(String item_trait) {
        return registryMap.get(item_trait);
    }

    public Set<IItemTrait> getAllItemTrait() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(IItemTrait itemTrait : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(itemTrait.getRegistry());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(itemTrait.getRegistry(), toJsonObject(itemTrait));
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
                        IItemTrait itemTrait = fromJsonObject(jsonObject);

                        registryMap.put(itemTrait.getRegistry(), itemTrait);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to item trait because a item trait already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("item trait json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IItemTrait fromJsonObject(JsonObject jsonObjectIn){

        return new IItemTrait() {
            @Override
            public String getRegistry() {
                return jsonObjectIn.get("registry").getAsString();
            }

            @Override
            public Integer getColor() {
                return jsonObjectIn.get("color").getAsInt();
            }

            @Override
            public Integer getDurability() {
                return jsonObjectIn.get("durability").getAsInt();
            }

            @Override
            public ITextureIcon getTextureIcon() {
                return (ITextureIcon)TCJsonConfigs.textureIcon.getFirst().registryMap.get(jsonObjectIn.get("texture_icon").getAsString());
            }

            @Override
            public Double getPounds() {
                return jsonObjectIn.get("pounds").getAsDouble();
            }

            @Override
            public Double getKilograms() {
                return jsonObjectIn.get("kilograms").getAsDouble();
            }

            @Override
            public Double getMeters() {
                return jsonObjectIn.get("meters").getAsDouble();
            }

            @Override
            public Double getYards() {
                return jsonObjectIn.get("yards").getAsDouble();
            }
        };
    }

    public JsonObject toJsonObject(IItemTrait itemTrait) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("registry", itemTrait.getRegistry());
        jsonObject.addProperty("color", itemTrait.getColor());
        jsonObject.addProperty("durability", itemTrait.getDurability());
        jsonObject.addProperty("texture_icon", itemTrait.getTextureIcon().getName());


        if(itemTrait.getPounds() != null) {
            jsonObject.addProperty("pounds", itemTrait.getPounds());
        }

        if(itemTrait.getKilograms() != null) {
            jsonObject.addProperty("kilograms", itemTrait.getKilograms());
        }

        return jsonObject;
    }
}