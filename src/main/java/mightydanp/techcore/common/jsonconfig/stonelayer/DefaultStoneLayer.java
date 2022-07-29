package mightydanp.techcore.common.jsonconfig.stonelayer;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by MightyDanp on 12/29/2021.
 */
public enum DefaultStoneLayer implements IStoneLayer {
    ANDESITE(new ResourceLocation("minecraft", "andesite")),
    DIORITE(new ResourceLocation("minecraft", "diorite")),
    GRANITE(new ResourceLocation("minecraft", "granite")),
    NETHERRACK(new ResourceLocation("minecraft", "netherrack")),
    STONE(new ResourceLocation("minecraft", "stone"));

    ResourceLocation resourceLocation;
    String modID;
    String blockName;

    DefaultStoneLayer(ResourceLocation resourceLocationIn){
        resourceLocation = resourceLocationIn;
        modID = resourceLocationIn.getNamespace();
        blockName = resourceLocationIn.getPath();
    }
    public String getModID() {
        return null;
    }

    public String getBlockName() {
        return blockName;
    }

    @Override
    public String getBlock() {
        return resourceLocation.toString();
    }
}
