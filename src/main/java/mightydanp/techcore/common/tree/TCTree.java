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
import net.minecraft.world.level.block.state.properties.WoodType;
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

    public RegistryObject<Block> log, stripedLog, planks, leaves, sapling, slab, stairs, button, fence, fenceGate, door, trapDoor, pressurePlate, sign;
    public RegistryObject<Item> stick, plank, resin;//boat-cant do because it requires plank block. If someone adds a plank that's not in this class then there is no plank for it to use because it cant grab it from registry.



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

    public void save() {
        //-- Item --\\
        {
            String name = this.name + "_stick";

            if (!existingItems.containsKey("stick")) {
                stick = RegistryHandler.ITEMS.register(name, () -> new Item(new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            String name = this.name + "_plank";

            if (!existingItems.containsKey("plank")) {
                plank = RegistryHandler.ITEMS.register(name, () -> new Item(new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            String name = this.name + "_resin";

            if (!existingItems.containsKey("resin")) {
                plank = RegistryHandler.ITEMS.register(name, () -> new Item(new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
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
                leaves = RegistryHandler.BLOCKS.register(name, () -> block);
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
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_slab";
            Block block = new SlabBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("slab")) {
                slab = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("slab")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_stairs";
            Block block = new StairBlock(()-> null, BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("stairs")) {
                stairs = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("stairs")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_button";
            Block block = new WoodButtonBlock(BlockBehaviour.Properties.of(material).noCollission().strength(0.5F * destroyTimeMultiplier, 0.5F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("button")) {
                button = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("button")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_fence";
            Block block = new FenceBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("fence")) {
                fence = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("fence")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_fence_gate";
            Block block = new FenceGateBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("fence_gate")) {
                fenceGate = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("fence_gate")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_door";
            Block block = new DoorBlock(BlockBehaviour.Properties.of(material).noOcclusion().strength(3.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType));
            if (!existingItems.containsKey("door")) {
                door = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("door")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_trap_door";
            Block block = new TrapDoorBlock(BlockBehaviour.Properties.of(material).strength(2.0F * destroyTimeMultiplier, 3.0F * explosionResistanceMultiplier).sound(soundType).noOcclusion().isValidSpawn(Blocks::never));
            if (!existingItems.containsKey("trap_door")) {
                trapDoor = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("trap_door")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_pressure_plate";
            Block block = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(material).strength(0.5F * destroyTimeMultiplier, 0.5F * explosionResistanceMultiplier).sound(soundType).noCollission());
            if (!existingItems.containsKey("pressure_plate")) {
                pressurePlate = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("pressure_plate")) {
                RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(TCCreativeModeTab.tree_tab)));
            }
        }
//--//--//--//--//--//--//--//--//
        {
            //--block
            String name = this.name + "_sign";
            Block block = new StandingSignBlock(BlockBehaviour.Properties.of(material).strength(destroyTimeMultiplier, explosionResistanceMultiplier).sound(soundType).noCollission(), WoodType.register(WoodType.create(name)));
            if (!existingItems.containsKey("sign")) {
                pressurePlate = RegistryHandler.BLOCKS.register(name, () -> block);
            }

            //--item
            if (!existingItems.containsKey("sign")) {
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
