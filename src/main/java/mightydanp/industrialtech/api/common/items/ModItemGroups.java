package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class ModItemGroups {
    public static final ItemGroup block_tab = new ItemGroup(Ref.block_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.STONE);
        }
    };

    public static final ItemGroup item_tab = new ItemGroup(Ref.item_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public static final ItemGroup ore_tab = new ItemGroup(Ref.ore_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.IRON_ORE);
        }
    };

    public static final ItemGroup plant_tab = new ItemGroup(Ref.plant_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.WHEAT);
        }
    };

    public static final ItemGroup gem_tab = new ItemGroup(Ref.gem_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };

    public static final ItemGroup ore_products_tab = new ItemGroup(Ref.ore_products_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.GLOWSTONE_DUST);
        }
    };

    public static final ItemGroup tool_parts_tab = new ItemGroup(Ref.tool_parts_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public static final ItemGroup tool_tab = new ItemGroup(Ref.tool_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STONE_AXE);
        }
    };

    public static final ItemGroup tree_tab = new ItemGroup(Ref.tree_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.OAK_LOG);
        }
    };

    public static final ItemGroup fluid_tab = new ItemGroup(Ref.fluid_tab) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BUCKET);
        }
    };
}
