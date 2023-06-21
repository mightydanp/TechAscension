package mightydanp.techascension.common.tool;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.tool.ToolCodec;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools extends TCTools{
    public static TCTool pickaxe, knife, chisel, hammer;
    public static String pickaxeName = "pickaxe";
    public static String knifeName = "knife";
    public static String hammerName = "hammer";

    public static String chiselName = "chisel";

    public static void init(){
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

        tools.put(hammerName, hammer = new TCTool(hammerName,
                new PartHolders.handlePartHolder(new Pair<>("", "_wedge_handle"), false, true, TAHandleItem.class),
                null,
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_wedge"), false, true, TABindingItem.class),
                RegistryHandler.ITEMS.register(hammerName, ()-> new HammerToolItem().setName(hammerName))
        ));

        tools.put(chiselName, chisel = new TCTool(chiselName,
                new PartHolders.handlePartHolder(new Pair<>("", "_handle"), true, true, TAHandleItem.class),
                null,
                new PartHolders.headPartHolder(new Pair<>("", "_head"), true, true, TAHeadItem.class),
                new PartHolders.bindingPartHolder(new Pair<>("", "_binding"), false, false, TABindingItem.class),
                RegistryHandler.ITEMS.register(chiselName, ()-> new ChiselToolItem().setName(chiselName))
        ));
    }

    public static void postInit(){
        List<TCMaterial> stoneLayers = ((MaterialRegistry)TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(tcMaterial -> tcMaterial.isStoneLayer != null && tcMaterial.isStoneLayer).toList();
//----------pickaxe
        {
            List<BlockState> effectiveOn = List.of(net.minecraft.world.level.block.Blocks.ACTIVATOR_RAIL.defaultBlockState(), Blocks.COAL_ORE.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState(), Blocks.DETECTOR_RAIL.defaultBlockState(), Blocks.DIAMOND_BLOCK.defaultBlockState(), Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.POWERED_RAIL.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState(), Blocks.GOLD_ORE.defaultBlockState(), Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.ICE.defaultBlockState(), Blocks.IRON_BLOCK.defaultBlockState(), Blocks.IRON_ORE.defaultBlockState(), Blocks.LAPIS_BLOCK.defaultBlockState(), Blocks.LAPIS_ORE.defaultBlockState(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState(), Blocks.PACKED_ICE.defaultBlockState(), Blocks.BLUE_ICE.defaultBlockState(), Blocks.RAIL.defaultBlockState(), Blocks.REDSTONE_ORE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), Blocks.CHISELED_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(), Blocks.CUT_RED_SANDSTONE.defaultBlockState(), Blocks.RED_SANDSTONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.POLISHED_GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.POLISHED_DIORITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.POLISHED_ANDESITE.defaultBlockState(), Blocks.STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SANDSTONE_SLAB.defaultBlockState(), Blocks.PETRIFIED_OAK_SLAB.defaultBlockState(), Blocks.COBBLESTONE_SLAB.defaultBlockState(), Blocks.BRICK_SLAB.defaultBlockState(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), Blocks.NETHER_BRICK_SLAB.defaultBlockState(), Blocks.QUARTZ_SLAB.defaultBlockState(), Blocks.RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.PURPUR_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_STONE.defaultBlockState(), Blocks.STONE_BUTTON.defaultBlockState(), Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), Blocks.POLISHED_GRANITE_SLAB.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_DIORITE_SLAB.defaultBlockState(), Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState(), Blocks.END_STONE_BRICK_SLAB.defaultBlockState(), Blocks.SMOOTH_SANDSTONE_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ_SLAB.defaultBlockState(), Blocks.GRANITE_SLAB.defaultBlockState(), Blocks.ANDESITE_SLAB.defaultBlockState(), Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState(), Blocks.DIORITE_SLAB.defaultBlockState(), Blocks.SHULKER_BOX.defaultBlockState(), Blocks.BLACK_SHULKER_BOX.defaultBlockState(), Blocks.BLUE_SHULKER_BOX.defaultBlockState(), Blocks.BROWN_SHULKER_BOX.defaultBlockState(), Blocks.CYAN_SHULKER_BOX.defaultBlockState(), Blocks.GRAY_SHULKER_BOX.defaultBlockState(), Blocks.GREEN_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_BLUE_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_GRAY_SHULKER_BOX.defaultBlockState(), Blocks.LIME_SHULKER_BOX.defaultBlockState(), Blocks.MAGENTA_SHULKER_BOX.defaultBlockState(), Blocks.ORANGE_SHULKER_BOX.defaultBlockState(), Blocks.PINK_SHULKER_BOX.defaultBlockState(), Blocks.PURPLE_SHULKER_BOX.defaultBlockState(), Blocks.RED_SHULKER_BOX.defaultBlockState(), Blocks.WHITE_SHULKER_BOX.defaultBlockState(), Blocks.YELLOW_SHULKER_BOX.defaultBlockState(), Blocks.PISTON.defaultBlockState(), Blocks.STICKY_PISTON.defaultBlockState(), Blocks.PISTON_HEAD.defaultBlockState());

            List<Ingredient> handlePartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> headPartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> bindingPartItems = List.of(Ingredient.EMPTY);

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>();

            List<Map<Ingredient, Integer>> step1 = assembleItems.getOrDefault(1, new ArrayList<>());
            List<Map<Ingredient, Integer>> step2 = assembleItems.getOrDefault(2, new ArrayList<>());
            List<Map<Ingredient, Integer>> step3 = assembleItems.getOrDefault(3, new ArrayList<>());

            step1.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step2.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step3.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));


            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                step1.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step2.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step3.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            assembleItems.put(1, step1);
            assembleItems.put(2, step2);
            assembleItems.put(3, step3);

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(new ToolCodec(pickaxeName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------knife
        {
            List<BlockState> effectiveOn = List.of(Blocks.SHULKER_BOX.defaultBlockState(), Blocks.BLACK_SHULKER_BOX.defaultBlockState(), Blocks.BLUE_SHULKER_BOX.defaultBlockState(), Blocks.BROWN_SHULKER_BOX.defaultBlockState(), Blocks.CYAN_SHULKER_BOX.defaultBlockState(), Blocks.GRAY_SHULKER_BOX.defaultBlockState(), Blocks.GREEN_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_BLUE_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_GRAY_SHULKER_BOX.defaultBlockState(), Blocks.LIME_SHULKER_BOX.defaultBlockState(), Blocks.MAGENTA_SHULKER_BOX.defaultBlockState(), Blocks.ORANGE_SHULKER_BOX.defaultBlockState(), Blocks.PINK_SHULKER_BOX.defaultBlockState(), Blocks.PURPLE_SHULKER_BOX.defaultBlockState(), Blocks.RED_SHULKER_BOX.defaultBlockState(), Blocks.WHITE_SHULKER_BOX.defaultBlockState(), Blocks.YELLOW_SHULKER_BOX.defaultBlockState(), Blocks.PISTON.defaultBlockState(), Blocks.STICKY_PISTON.defaultBlockState(), Blocks.PISTON_HEAD.defaultBlockState());

            List<Ingredient> handlePartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> headPartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> bindingPartItems = List.of(Ingredient.EMPTY);

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>();

            List<Map<Ingredient, Integer>> step1 = assembleItems.getOrDefault(1, new ArrayList<>());
            List<Map<Ingredient, Integer>> step2 = assembleItems.getOrDefault(2, new ArrayList<>());
            List<Map<Ingredient, Integer>> step3 = assembleItems.getOrDefault(3, new ArrayList<>());

            step1.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step2.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step3.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                step1.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step2.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step3.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            assembleItems.put(1, step1);
            assembleItems.put(2, step2);
            assembleItems.put(3, step3);

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(new ToolCodec(knifeName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------hammer
        {
            List<BlockState> effectiveOn = List.of(Blocks.ACTIVATOR_RAIL.defaultBlockState(), Blocks.COAL_ORE.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState(), Blocks.DETECTOR_RAIL.defaultBlockState(), Blocks.DIAMOND_BLOCK.defaultBlockState(), Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.POWERED_RAIL.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState(), Blocks.GOLD_ORE.defaultBlockState(), Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.ICE.defaultBlockState(), Blocks.IRON_BLOCK.defaultBlockState(), Blocks.IRON_ORE.defaultBlockState(), Blocks.LAPIS_BLOCK.defaultBlockState(), Blocks.LAPIS_ORE.defaultBlockState(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState(), Blocks.PACKED_ICE.defaultBlockState(), Blocks.BLUE_ICE.defaultBlockState(), Blocks.RAIL.defaultBlockState(), Blocks.REDSTONE_ORE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), Blocks.CHISELED_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(), Blocks.CUT_RED_SANDSTONE.defaultBlockState(), Blocks.RED_SANDSTONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.POLISHED_GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.POLISHED_DIORITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.POLISHED_ANDESITE.defaultBlockState(), Blocks.STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SANDSTONE_SLAB.defaultBlockState(), Blocks.PETRIFIED_OAK_SLAB.defaultBlockState(), Blocks.COBBLESTONE_SLAB.defaultBlockState(), Blocks.BRICK_SLAB.defaultBlockState(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), Blocks.NETHER_BRICK_SLAB.defaultBlockState(), Blocks.QUARTZ_SLAB.defaultBlockState(), Blocks.RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.PURPUR_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_STONE.defaultBlockState(), Blocks.STONE_BUTTON.defaultBlockState(), Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), Blocks.POLISHED_GRANITE_SLAB.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_DIORITE_SLAB.defaultBlockState(), Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState(), Blocks.END_STONE_BRICK_SLAB.defaultBlockState(), Blocks.SMOOTH_SANDSTONE_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ_SLAB.defaultBlockState(), Blocks.GRANITE_SLAB.defaultBlockState(), Blocks.ANDESITE_SLAB.defaultBlockState(), Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState(), Blocks.DIORITE_SLAB.defaultBlockState(), Blocks.SHULKER_BOX.defaultBlockState(), Blocks.BLACK_SHULKER_BOX.defaultBlockState(), Blocks.BLUE_SHULKER_BOX.defaultBlockState(), Blocks.BROWN_SHULKER_BOX.defaultBlockState(), Blocks.CYAN_SHULKER_BOX.defaultBlockState(), Blocks.GRAY_SHULKER_BOX.defaultBlockState(), Blocks.GREEN_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_BLUE_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_GRAY_SHULKER_BOX.defaultBlockState(), Blocks.LIME_SHULKER_BOX.defaultBlockState(), Blocks.MAGENTA_SHULKER_BOX.defaultBlockState(), Blocks.ORANGE_SHULKER_BOX.defaultBlockState(), Blocks.PINK_SHULKER_BOX.defaultBlockState(), Blocks.PURPLE_SHULKER_BOX.defaultBlockState(), Blocks.RED_SHULKER_BOX.defaultBlockState(), Blocks.WHITE_SHULKER_BOX.defaultBlockState(), Blocks.YELLOW_SHULKER_BOX.defaultBlockState(), Blocks.PISTON.defaultBlockState(), Blocks.STICKY_PISTON.defaultBlockState(), Blocks.PISTON_HEAD.defaultBlockState());

            List<Ingredient> handlePartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> headPartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> bindingPartItems = List.of(Ingredient.EMPTY);

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>();

            List<Map<Ingredient, Integer>> step1 = assembleItems.getOrDefault(1, new ArrayList<>());
            List<Map<Ingredient, Integer>> step2 = assembleItems.getOrDefault(2, new ArrayList<>());
            List<Map<Ingredient, Integer>> step3 = assembleItems.getOrDefault(3, new ArrayList<>());

            step1.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step2.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step3.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                step1.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step2.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step3.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            assembleItems.put(1, step1);
            assembleItems.put(2, step2);
            assembleItems.put(3, step3);

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(new ToolCodec(hammerName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------chisel
        {
            List<BlockState> effectiveOn = List.of(Blocks.ACTIVATOR_RAIL.defaultBlockState(), Blocks.COAL_ORE.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState(), Blocks.DETECTOR_RAIL.defaultBlockState(), Blocks.DIAMOND_BLOCK.defaultBlockState(), Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.POWERED_RAIL.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState(), Blocks.GOLD_ORE.defaultBlockState(), Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.ICE.defaultBlockState(), Blocks.IRON_BLOCK.defaultBlockState(), Blocks.IRON_ORE.defaultBlockState(), Blocks.LAPIS_BLOCK.defaultBlockState(), Blocks.LAPIS_ORE.defaultBlockState(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState(), Blocks.PACKED_ICE.defaultBlockState(), Blocks.BLUE_ICE.defaultBlockState(), Blocks.RAIL.defaultBlockState(), Blocks.REDSTONE_ORE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), Blocks.CHISELED_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(), Blocks.CUT_RED_SANDSTONE.defaultBlockState(), Blocks.RED_SANDSTONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.POLISHED_GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.POLISHED_DIORITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.POLISHED_ANDESITE.defaultBlockState(), Blocks.STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SANDSTONE_SLAB.defaultBlockState(), Blocks.PETRIFIED_OAK_SLAB.defaultBlockState(), Blocks.COBBLESTONE_SLAB.defaultBlockState(), Blocks.BRICK_SLAB.defaultBlockState(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), Blocks.NETHER_BRICK_SLAB.defaultBlockState(), Blocks.QUARTZ_SLAB.defaultBlockState(), Blocks.RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.PURPUR_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), Blocks.SMOOTH_STONE.defaultBlockState(), Blocks.STONE_BUTTON.defaultBlockState(), Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), Blocks.POLISHED_GRANITE_SLAB.defaultBlockState(), Blocks.SMOOTH_RED_SANDSTONE_SLAB.defaultBlockState(), Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_DIORITE_SLAB.defaultBlockState(), Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState(), Blocks.END_STONE_BRICK_SLAB.defaultBlockState(), Blocks.SMOOTH_SANDSTONE_SLAB.defaultBlockState(), Blocks.SMOOTH_QUARTZ_SLAB.defaultBlockState(), Blocks.GRANITE_SLAB.defaultBlockState(), Blocks.ANDESITE_SLAB.defaultBlockState(), Blocks.RED_NETHER_BRICK_SLAB.defaultBlockState(), Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState(), Blocks.DIORITE_SLAB.defaultBlockState(), Blocks.SHULKER_BOX.defaultBlockState(), Blocks.BLACK_SHULKER_BOX.defaultBlockState(), Blocks.BLUE_SHULKER_BOX.defaultBlockState(), Blocks.BROWN_SHULKER_BOX.defaultBlockState(), Blocks.CYAN_SHULKER_BOX.defaultBlockState(), Blocks.GRAY_SHULKER_BOX.defaultBlockState(), Blocks.GREEN_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_BLUE_SHULKER_BOX.defaultBlockState(), Blocks.LIGHT_GRAY_SHULKER_BOX.defaultBlockState(), Blocks.LIME_SHULKER_BOX.defaultBlockState(), Blocks.MAGENTA_SHULKER_BOX.defaultBlockState(), Blocks.ORANGE_SHULKER_BOX.defaultBlockState(), Blocks.PINK_SHULKER_BOX.defaultBlockState(), Blocks.PURPLE_SHULKER_BOX.defaultBlockState(), Blocks.RED_SHULKER_BOX.defaultBlockState(), Blocks.WHITE_SHULKER_BOX.defaultBlockState(), Blocks.YELLOW_SHULKER_BOX.defaultBlockState(), Blocks.PISTON.defaultBlockState(), Blocks.STICKY_PISTON.defaultBlockState(), Blocks.PISTON_HEAD.defaultBlockState());

            List<Ingredient> handlePartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> headPartItems = List.of(Ingredient.EMPTY);
            List<Ingredient> bindingPartItems = List.of(Ingredient.EMPTY);

            Map<Integer, List<Map<Ingredient, Integer>>> assembleItems =
                    new HashMap<>();

            List<Map<Ingredient, Integer>> step1 = assembleItems.getOrDefault(1, new ArrayList<>());
            List<Map<Ingredient, Integer>> step2 = assembleItems.getOrDefault(2, new ArrayList<>());
            List<Map<Ingredient, Integer>> step3 = assembleItems.getOrDefault(3, new ArrayList<>());

            step1.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step2.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));
            step3.add(Map.of(Ingredient.of(hammer.toolItem.get()), 1));

            List<Map<Ingredient, Integer>> disassembleItems =
                    new ArrayList<>(List.of(
                            Map.of(Ingredient.of(hammer.toolItem.get()), 1,
                                    Ingredient.of(chisel.toolItem.get()), 1
                            )));

            for (TCMaterial material : stoneLayers) {
                step1.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step2.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));
                step3.add(Map.of(Ingredient.of(material.rockItemBlock.get()), 1));

                disassembleItems.add(new HashMap<>(Map.of(Ingredient.of(material.rockItemBlock.get()), 1,
                        Ingredient.of(chisel.toolItem.get()), 1
                )));
            }

            assembleItems.put(1, step1);
            assembleItems.put(2, step2);
            assembleItems.put(3, step3);

            ((ToolRegistry) TCJsonConfigs.tool.getFirst()).buildAndRegisterTool(new ToolCodec(chiselName, 1, effectiveOn, handlePartItems, headPartItems, bindingPartItems, assembleItems, disassembleItems));
        }
//----------
    }
}
