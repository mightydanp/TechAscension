package mightydanp.techcore.common.tree;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tree.blocks.TCLogBlock;
import mightydanp.techcore.common.tree.blocks.items.TCLogBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class TCTree {
    public String name;
    public int color;

    public Material material;
    public SoundType soundType;
    public float strength;

    public Map<String, ResourceLocation> existingBlocks;
    public Map<String, ResourceLocation> existingItems;

    public RegistryObject<Block> log, stripedLog, plank, leaf, slab, stair, button, sapling, fence, door, trapDoor, pressurePlate;
    public RegistryObject<Item> stick, boat, sign, cutPlank;



    public TCTree(String name, int color, Material material, SoundType soundType, float strength){
        this.name = name;
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.strength = strength;
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
            String name = this.name + "_log";
            if (!existingBlocks.containsKey("log")) {
                log = RegistryHandler.BLOCKS.register(name, () -> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(strength).sound(soundType)));
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
            stripedLog = RegistryHandler.BLOCKS.register(name, () -> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(strength).sound(soundType)));

            //--item
            RegistryHandler.ITEMS.register(name, () -> new TCLogBlockItem(stripedLog, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = "striped_" + this.name + "_log";
            stripedLog = RegistryHandler.BLOCKS.register(name, () -> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(strength).sound(soundType)));

            //--item
            RegistryHandler.ITEMS.register(name, () -> new TCLogBlockItem(stripedLog, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
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
