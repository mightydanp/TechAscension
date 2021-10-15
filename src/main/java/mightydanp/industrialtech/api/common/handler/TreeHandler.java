package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.LeaveBlock;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.items.BlockFuelItem;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.EnumTreeFlags;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 7/23/2021.
 */
public class TreeHandler {
    public String treeName;
    public int color;
    public int leafDecayLength;
    public int leafBurnTime;
    public int logBurnTime;
    public int plankBurnTime;
    public int saplingBurnTime;
    public List<ITToolItem> toolForLogHole;
    public Tree treeFeature;

    public RegistryObject<Block> log_block, plank_block, leave_block, sapling_block;

    public RegistryObject<Item> log_item, plank_item, leave_item, sapling_item;

    public List<EnumTreeFlags> treeFlags = new ArrayList<>();

    public TreeHandler (String treeNameIn, Tree treeFeatureIn, int colorIn, int leafDecayLengthIn, int leafBurnTimeIn, int logBurnTimeIn, int plankBurnTimeIn, int saplingBurnTimeIn, EnumTreeFlags... flagsIn){
        treeName = treeNameIn;
        color = colorIn;
        treeFeature = treeFeatureIn;
        leafDecayLength = leafDecayLengthIn;
        leafBurnTime = leafBurnTimeIn;
        logBurnTime = logBurnTimeIn;
        plankBurnTime = plankBurnTimeIn;
        saplingBurnTime = saplingBurnTimeIn;
        treeFlags.addAll(Arrays.asList(flagsIn));
        registerFlags(flagsIn);
    }

    public TreeHandler (String treeNameIn, Tree treeFeatureIn, int colorIn, int leafDecayLengthIn, int leafBurnTimeIn, int logBurnTimeIn, List<ITToolItem> toolForLogHoleIn, int plankBurnTimeIn, int saplingBurnTimeIn, EnumTreeFlags... flagsIn){
        treeName = treeNameIn;
        treeFeature = treeFeatureIn;
        color = colorIn;
        leafDecayLength = leafDecayLengthIn;
        leafBurnTime = leafBurnTimeIn;
        logBurnTime = logBurnTimeIn;
        toolForLogHole = toolForLogHoleIn;
        plankBurnTime = plankBurnTimeIn;
        saplingBurnTime = saplingBurnTimeIn;
        treeFlags.addAll(Arrays.asList(flagsIn));
        registerFlags(flagsIn);
    }

    public void registerFlags(EnumTreeFlags... flags){
        for(EnumTreeFlags flag : flags) {
            if (flag == EnumTreeFlags.LEAVE) {
                leave_block = RegistryHandler.BLOCKS.register(treeName + "_leave", () -> new LeaveBlock(treeName + "_leave", AbstractBlock.Properties.of(Material.LEAVES), leafDecayLength));
                leave_item = RegistryHandler.ITEMS.register(treeName + "_leave", () -> new BlockFuelItem(leave_block.get(), new Item.Properties().tab(ModItemGroups.tree_tab), leafBurnTime));
            }

            if (flag == EnumTreeFlags.NORMAL_LOG) {
                if (treeFlags.contains(EnumTreeFlags.NORMAL_LOG)) {
                    log_block = RegistryHandler.BLOCKS.register(treeName + "_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD)));
                }else{
                    if(treeFlags.contains(EnumTreeFlags.LOG_SIDE_HOLE)) {
                        //log_block = RegistryHandler.BLOCKS.register(treeName + "_log", () -> new HoleBlock(AbstractBlock.Properties.of(Material.WOOD), toolForLogHole));
                    }
                }
                log_item = RegistryHandler.ITEMS.register(treeName + "_log", () -> new BlockFuelItem(log_block.get(), new Item.Properties().tab(ModItemGroups.tree_tab), logBurnTime));
            }

            if (flag == EnumTreeFlags.PLANK) {
                plank_block = RegistryHandler.BLOCKS.register(treeName + "_plank", () -> new Block(AbstractBlock.Properties.of(Material.WOOD)));
                plank_item = RegistryHandler.ITEMS.register(treeName + "_plank", () -> new BlockFuelItem(plank_block.get(), new Item.Properties().tab(ModItemGroups.tree_tab), plankBurnTime));
            }

            if (flag == EnumTreeFlags.SAPLING) {
                sapling_block = RegistryHandler.BLOCKS.register(treeName + "_sapling", () -> new SaplingBlock(new OakTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
                sapling_item = RegistryHandler.ITEMS.register(treeName + "_sapling", () -> new BlockFuelItem(sapling_block.get(), new Item.Properties().tab(ModItemGroups.tree_tab), saplingBurnTime));
            }
        }
    }

    public void registerColorHandlerForBlock() {
        setupABlockColor(log_block);
        setupABlockColor(plank_block);
    }

    public void setupABlockColor(RegistryObject<Block> block){
        RenderTypeLookup.setRenderLayer(block.get(), RenderType.cutout());
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, block.get());
    }

    public void registerColorForItem(){
        registerAItemColor(log_item, 0);
        registerAItemColor(plank_item, 0);
    }

    public void registerAItemColor(RegistryObject<Item> item, int layerNumberIn){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == layerNumberIn)
                    return color;
                else
                    return 0xFFFFFFFF;
            }, item.get());
        }
    }
}
