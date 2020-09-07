package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.common.lib.References;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/5/2020.
 */
@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegistryHandler {
    private static final List<Block> blocks = new ArrayList<>();
    private static final List<Item> items = new ArrayList<>();
    private static final List<TileEntityType> tileEntityTypes = new ArrayList<>();
    public static final List<ContainerType> containerTypes = new ArrayList<>();


    @SubscribeEvent
    void instanceMethod(Event e){

    }

    @SubscribeEvent
    public void registerBlockEvent(final RegistryEvent.Register<Block> event) {
        for(Block block: blocks)
            event.getRegistry().register(block);
    }

    @SubscribeEvent
    public void registerItemEvent(final RegistryEvent.Register<Item> event) {
        for(Item item: items)
            event.getRegistry().register(item);
    }


    @SubscribeEvent
    public static void onTileEntityTypeRegistration(final RegistryEvent.Register<TileEntityType<?>> event) {
        for(TileEntityType tileEntityType : tileEntityTypes)
            event.getRegistry().register(tileEntityType);
    }

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
        for(ContainerType containerTypeType : containerTypes)
            event.getRegistry().register(containerTypeType);
    }

    public static void registerItem(Item itemIn){
            if(!items.contains(itemIn)) {
                items.add(itemIn);
            }
    }

    public static void registerBlock(Block blockIn){
        if(!blocks.contains(blockIn)) {
            blocks.add(blockIn);
        }
    }

    public static void registerTileEntityType(TileEntityType tileEntityTypeIn) {
        if(!tileEntityTypes.contains(tileEntityTypeIn)){
            tileEntityTypes.add(tileEntityTypeIn);
        }
    }
}
