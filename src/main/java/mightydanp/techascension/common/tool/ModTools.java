package mightydanp.techascension.common.tool;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.tool.ITool;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.TCMaterial;
import mightydanp.techcore.common.material.tools.ChiselToolItem;
import mightydanp.techcore.common.tool.TCTool;
import mightydanp.techcore.common.tool.TCTools;
import mightydanp.techcore.common.tool.part.*;
import mightydanp.techascension.common.tool.part.TABindingItem;
import mightydanp.techascension.common.tool.part.TADullHeadItem;
import mightydanp.techascension.common.tool.part.TAHandleItem;
import mightydanp.techascension.common.tool.part.TAHeadItem;
import mightydanp.techcore.common.material.tools.HammerToolItem;
import mightydanp.techcore.common.material.tools.KnifeToolItem;
import mightydanp.techcore.common.material.tools.PickaxeToolItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools extends TCTools{
    public static TCTool hammer, chisel, pickaxe, knife;

    public static String chiselName = "chisel";

    public static String hammerName = "hammer";
    public static String pickaxeName = "pickaxe";
    public static String knifeName = "knife";

    public static void init(){
        tools.put(chiselName, chisel = new TCTool(chiselName,
                new PartHolders.handlePartHolder(new Pair<>("", "_handle"), true, true, TAHandleItem.class),
                null,
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_binding"), false, false, TABindingItem.class),
                RegistryHandler.ITEMS.register(chiselName, ()-> new ChiselToolItem().setName(chiselName))
        ));

        tools.put(hammerName, hammer = new TCTool(hammerName,
                new PartHolders.handlePartHolder(new Pair<>("", "_wedge_handle"), false, true, TAHandleItem.class),
                null,
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_wedge"), false, true, TABindingItem.class),
                RegistryHandler.ITEMS.register(hammerName, ()-> new HammerToolItem().setName(hammerName))
        ));

        tools.put(pickaxeName, pickaxe = new TCTool(pickaxeName,
                new PartHolders.handlePartHolder(new Pair<>("", "_wedge_handle"), false, true, TAHandleItem.class),
                new PartHolders.dullHeadPartHolder(new Pair<>("dull_", "_head"), false, false, TADullHeadItem.class),
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_wedge"), false, true, TABindingItem.class),
                RegistryHandler.ITEMS.register(pickaxeName, ()-> new PickaxeToolItem().setName(pickaxeName))
        ));

        tools.put(knifeName, knife = new TCTool(knifeName,
                new PartHolders.handlePartHolder(new Pair<>("", "_handle"), true, true, TAHandleItem.class),
                new PartHolders.dullHeadPartHolder(new Pair<>("dull_", "_head"), true, true, TADullHeadItem.class),
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_binding"), false, false, TABindingItem.class),
                RegistryHandler.ITEMS.register(knifeName, ()-> new KnifeToolItem().setName(knifeName))
        ));
    }

    public static void postInit(){
        List<TCMaterial> stoneLayers = ((MaterialRegistry)TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(tcMaterial -> tcMaterial.isStoneLayer != null && tcMaterial.isStoneLayer).toList();
        {
            List<String> effectiveOn = List.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD.getRegistryName()));

            List<Ingredient> handlePartItems = List.of();
            List<Ingredient> headPartItems = List.of();
            List<Ingredient> bindingPartItems = List.of();

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>(Map.of(1, List.of(
                                    Map.of(Ingredient.of(hammer.toolItem.get()), 1)
                            ),
                            2, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1)),
                            3, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1))
                    ));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                assembleItems.put(1, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(2, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(3, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(toITool(chiselName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------
        {
            List<String> effectiveOn = List.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD.getRegistryName()));

            List<Ingredient> handlePartItems = List.of();
            List<Ingredient> headPartItems = List.of();
            List<Ingredient> bindingPartItems = List.of();

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>(Map.of(1, List.of(
                                    Map.of(Ingredient.of(hammer.toolItem.get()), 1)
                            ),
                            2, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1)),
                            3, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1))
                    ));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                assembleItems.put(1, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(2, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(3, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(toITool(hammerName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------
        {
            List<String> effectiveOn = List.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD.getRegistryName()));

            List<Ingredient> handlePartItems = List.of();
            List<Ingredient> headPartItems = List.of();
            List<Ingredient> bindingPartItems = List.of();

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>(Map.of(1, List.of(
                                    Map.of(Ingredient.of(hammer.toolItem.get()), 1)
                            ),
                            2, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1)),
                            3, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1))
                    ));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                assembleItems.put(1, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(2, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(3, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(toITool(pickaxeName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------
        {
            List<String> effectiveOn = List.of(String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD.getRegistryName()));

            List<Ingredient> handlePartItems = List.of();
            List<Ingredient> headPartItems = List.of();
            List<Ingredient> bindingPartItems = List.of();

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>(Map.of(1, List.of(
                                    Map.of(Ingredient.of(hammer.toolItem.get()), 1)
                            ),
                            2, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1)),
                            3, List.of(Map.of(Ingredient.of(hammer.toolItem.get()), 1))
                    ));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                assembleItems.put(1, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(2, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));
                assembleItems.put(3, List.of(
                        Map.of(Ingredient.of(material.rockItemBlock.get()), 1)));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(toITool(knifeName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
    }
}
