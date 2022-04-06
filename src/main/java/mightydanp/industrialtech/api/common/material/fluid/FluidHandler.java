package mightydanp.industrialtech.api.common.material.fluid;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.Tag;

/**
 * Created by MightyDanp on 11/2/2021.
 */
public class FluidHandler {
    public Tag.Named<Fluid> tag;
    public double acceleration = 0.014d;

    public FluidHandler(String name, Tag.Named<Fluid> tag, Block.Properties properties) {
        this(name, tag, properties, 0.014d);
    }

    public FluidHandler(String name, Tag.Named<Fluid> tag, Material material) {
        this(name, tag, material, 0.014d);
    }

    public FluidHandler(String name, Tag.Named<Fluid> tag, Material material, double acceleration) {
        this(name, tag, Block.Properties.of(material).friction(100f).noDrops(), acceleration);
    }

    public FluidHandler(String name, Tag.Named<Fluid> tag, Block.Properties properties, double acceleration) {
        this.tag = tag;
        this.acceleration = acceleration;
    }
}