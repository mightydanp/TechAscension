package mightydanp.techcore.common.tree;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tree.blocks.TCLogBlock;
import mightydanp.techcore.common.tree.blocks.items.TCLogBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class TCTree {
    public String name;
    public int color;

    public Material material;
    public SoundType soundType;
    public float strength;

    public RegistryObject<Block> log, stripedLog, plank, leaf, slab, stair, button, sapling, fence, door, trapDoor, pressurePlate;
    public RegistryObject<Item> stick, boat, sign;



    public TCTree(String name, int color, Material material, SoundType soundType, float strength){
        this.name = name;
        this.color = color;
        this.material = material;
        this.soundType = soundType;
        this.strength = strength;
    }

    public void save(){
        //-- Item --\\

        //-- Blocks with Items -- \\
        String logName = name + "_log";
        log = RegistryHandler.BLOCKS.register(logName, ()-> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(strength).sound(soundType)));
        //--
        RegistryHandler.ITEMS.register(name, ()-> new TCLogBlockItem(log, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
//--//--//--//--//--//--//--//--//
        String stripedLogName = "striped_" + name + "_log";
        log = RegistryHandler.BLOCKS.register(stripedLogName, ()-> new TCLogBlock(BlockBehaviour.Properties.of(material).strength(strength).sound(soundType)));
        //--
        RegistryHandler.ITEMS.register(name, ()-> new TCLogBlockItem(log, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
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
