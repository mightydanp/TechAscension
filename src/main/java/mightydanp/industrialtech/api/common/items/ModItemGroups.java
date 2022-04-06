package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class ModItemGroups {
    public static final CreativeModeTab block_tab = new CreativeModeTab(Ref.block_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.STONE);
        }
    };

    public static final CreativeModeTab item_tab = new CreativeModeTab(Ref.item_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public static final CreativeModeTab ore_tab = new CreativeModeTab(Ref.ore_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.IRON_ORE);
        }
    };

    public static final CreativeModeTab plant_tab = new CreativeModeTab(Ref.plant_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.WHEAT);
        }
    };

    public static final CreativeModeTab gem_tab = new CreativeModeTab(Ref.gem_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };

    public static final CreativeModeTab ore_products_tab = new CreativeModeTab(Ref.ore_products_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.GLOWSTONE_DUST);
        }
    };

    public static final CreativeModeTab stone_layer_tab = new CreativeModeTab(Ref.stone_layer_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STONE);
        }
    };

    public static final CreativeModeTab tool_parts_tab = new CreativeModeTab(Ref.tool_parts_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public static final CreativeModeTab tool_tab = new CreativeModeTab(Ref.tool_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STONE_AXE);
        }
    };

    public static final CreativeModeTab tree_tab = new CreativeModeTab(Ref.tree_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.OAK_LOG);
        }
    };

    public static final CreativeModeTab fluid_tab = new CreativeModeTab(Ref.fluid_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BUCKET);
        }
    };
}
