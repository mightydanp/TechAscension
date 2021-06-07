package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegistryHandler {
    public static List<Block> blocks = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Item> features = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.mod_id);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.mod_id);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.mod_id);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Ref.mod_id);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Ref.mod_id);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Ref.mod_id);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Ref.mod_id);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Ref.mod_id);

    public void init(){

    }

    public static void init(IEventBus IEventBus) {
        ITEMS.register(IEventBus);
        BLOCKS.register(IEventBus);
        BLOCK_ITEMS.register(IEventBus);
        TILES.register(IEventBus);
        CONTAINERS.register(IEventBus);
        ENTITIES.register(IEventBus);
        FEATURES.register(IEventBus);
        RECIPE_SERIALIZER.register(IEventBus);
    }

    public static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<F> feature){
        return FEATURES.register(name, feature);
    }
}