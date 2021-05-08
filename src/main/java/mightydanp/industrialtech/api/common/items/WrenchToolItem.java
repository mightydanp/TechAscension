package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.common.ToolType;

/**
 * Created by MightyDanp on 4/7/2021.
 */

public class WrenchToolItem extends ITToolItem{
    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);

    public static List<Pair<Item, Integer>> craftingToolsNeeded = new ArrayList<Pair<Item, Integer>>();

    public static List<Pair<Item, Integer>> parts = new ArrayList<Pair<Item, Integer>>();

    public static List<Item> disassembleTools = new ArrayList<Item>();

    public WrenchToolItem() {
        super(EFFECTIVE_ON,  new Item.Properties().tab(ModItemGroups.tool_tab), craftingToolsNeeded, parts, disassembleTools);
    }
}
