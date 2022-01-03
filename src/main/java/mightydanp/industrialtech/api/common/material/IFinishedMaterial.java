package mightydanp.industrialtech.api.common.material;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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