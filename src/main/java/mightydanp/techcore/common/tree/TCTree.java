package mightydanp.techcore.common.tree;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tree.blocks.TCLogBlock;
import mightydanp.techcore.common.tree.blocks.items.TCLogBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class TCTree {
    public String name;
    public int color;

    public Material material;
    public SoundType soundType;
    public float destroyTimeMultiplier;
    public float explosionResistanceMultiplier;

    public AbstractTreeGrower treeGrower;

    public Map<String, ResourceLocation> existingBlocks;
    public Map<String, ResourceLocation> existingItems;

    public RegistryObject<Block> log, stripedLog, planks, leaves, sapling, slab, stair, button, fence, door, trapDoor, pressurePlate;
    public RegistryObject<Item> stick, boat, sign, plank;



    public TCTree(String name, int color, Material material, SoundType soundType, float destroyTimeMultiplier, float explosionResistanceMultiplier, AbstractTreeGrower treeGrower){
        this.name = name;
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.destroyTimeMultiplier = destroyTimeMultiplier;
        this.explosionResistanceMultiplier = explosionResistanceMultiplier;
        this.treeGrower = treeGrower;
    }

    public TCTree existingBlock(String process, ResourceLocation blockResourceLocation){
        existingBlocks.put(process, blockResourceLocation);
         return this;
    }

    public TCTree existingItem(String process, ResourceLocation itemResourceLocation){
        existingItems.put(process, itemResourceLocation);
        return this;
    }

    public void save(){
        //-- Item --\\

        //-- Blocks with Items -- \\
        {
            //--block
            String name = this.name + "_sapling";
            Block block = new SaplingBlock(treeGrower, BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 2.0F * explosionResistanceMultiplier).sound(soundType));

            if (!existingBlocks.containsKey("sapling")) {
                sapling = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("sapling")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_log";
            if (!existingBlocks.containsKey("log")) {
                log = RegistryHandler.BLOCKS.register(name, () -> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 2.0F * explosionResistanceMultiplier).sound(soundType)));
            }

            //--item
            if (!existingItems.containsKey("log")) {
                RegistryHandler.ITEMS.register(name, () -> new TCLogBlockItem(log, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = "striped_" + this.name + "_log";
            if (!existingBlocks.containsKey("striped_log")) {
                stripedLog = RegistryHandler.BLOCKS.register(name, () -> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 2.0F * explosionResistanceMultiplier).sound(soundType)));
            }

            //--item
            if (!existingItems.containsKey("striped_log")) {
                RegistryHandler.ITEMS.register(name, () -> new TCLogBlockItem(stripedLog, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_leaves";
            Block block = new LeavesBlock(BlockBehaviour.Properties.of(material).strength(0.2F  * destroyTimeMultiplier, 0.2F * explosionResistanceMultiplier).randomTicks().sound(soundType).noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never));
            if (!existingBlocks.containsKey("leaves")) {
                stripedLog = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("leaves")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_planks";
            Block block = new Block(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 2.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("planks")) {

                planks = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("planks")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
    }
    public void saveResources(){

    }

    public void clientRenderLayerInit(){

    }

    public void registerColorForBlock(){

    }

    public void registerColorForItem(){

    }

    public void setupABlockColor(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, block);
    }

    public void registerAItemColor(Item item, int layerNumberIn) {
        if (item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex <= layerNumberIn)
                    return color;
                else
                    return 0xFFFFFFFF;
            }, item);
        }
    }


}
