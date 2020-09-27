package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class BlockOre extends OreBlock {

    private int color;

    public BlockOre(String nameIn, Properties properties, int oreColor) {
        super(properties);
        this.color = oreColor;
        this.setRegistryName(nameIn);
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    public int getColor() {
        return color;
    }

}
