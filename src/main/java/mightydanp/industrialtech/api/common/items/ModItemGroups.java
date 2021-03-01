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
        public ItemStack createIcon() {
            return new ItemStack(Blocks.STONE);
        }
    };

    public static final ItemGroup item_tab = new ItemGroup(Ref.item_tab) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.STICK);
        }
    };

    public static final ItemGroup ore_tab = new ItemGroup(Ref.ore_tab) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.IRON_ORE);
        }
    };

    public static final ItemGroup plant_tab = new ItemGroup(Ref.plant_tab) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.WHEAT);
        }
    };
}
