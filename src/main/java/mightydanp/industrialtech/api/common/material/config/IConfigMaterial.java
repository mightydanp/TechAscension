package mightydanp.industrialtech.api.common.material.config;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Created by MightyDanp on 12/6/2021.
 */
public interface IConfigMaterial {
    ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public void serializeMaterialData(ForgeConfigSpec.Builder p_218610_1_);

    default ForgeConfigSpec serializeMaterial() {
        serializeMaterialData(BUILDER);
        return BUILDER.build();
    }

    ResourceLocation getId();
}
