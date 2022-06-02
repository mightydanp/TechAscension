package mightydanp.industrialtech.common.tool.tools;

import com.google.common.collect.ImmutableSet;
import mightydanp.industrialcore.common.items.ITToolItem;
import mightydanp.industrialcore.common.items.ModItemGroups;

import java.util.*;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;

/**
 * Created by MightyDanp on 4/7/2021.
 */

public class WrenchToolItem extends ITToolItem {
    private static final Set<String> EFFECTIVE_ON = ImmutableSet.of(String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));

    public WrenchToolItem() {
        super("wrench", new ArrayList<>(EFFECTIVE_ON),  new Item.Properties().tab(ModItemGroups.tool_tab));
    }
}
