package mightydanp.techapi.common.resources.asset.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;

public class ItemModelDataTest {

    public final ResourceLocation name;

    public ItemModelBuilder itemModel = new ItemModelBuilder(null, null);


    public ItemModelDataTest(ResourceLocation name) {
        this.name = name;
    }

    public JsonObject toJSON(){
        return itemModel.toJson();
    }
}
