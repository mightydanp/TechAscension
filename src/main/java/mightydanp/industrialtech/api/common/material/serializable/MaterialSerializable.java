package mightydanp.industrialtech.api.common.material.serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.material.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.material.fluidstate.FluidStateManager;
import mightydanp.industrialtech.api.common.material.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.material.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.material.icons.TextureIconManager;
import mightydanp.industrialtech.api.common.material.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.material.ore.IOreType;
import mightydanp.industrialtech.api.common.material.ore.OreTypeManager;
import mightydanp.industrialtech.api.common.material.tool.part.flag.IToolPart;
import mightydanp.industrialtech.api.common.material.tool.part.flag.ToolPartManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.List;

import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.*;
import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.GAS;

/**
 * Created by MightyDanp on 12/3/2021.
 */
public class MaterialSerializable{
}
