package mightydanp.industrialcore.common.material;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public interface IFinishedMaterial {
    void serializeMaterialData(JsonObject p_218610_1_);

    default JsonObject serializeMaterial() {
        JsonObject jsonobject = new JsonObject();
        this.serializeMaterialData(jsonobject);
        return jsonobject;
    }

    ResourceLocation getId();
}