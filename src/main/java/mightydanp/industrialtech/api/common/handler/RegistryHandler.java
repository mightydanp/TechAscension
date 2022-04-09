package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegistryHandler extends RegisterHelper{
    public static ResourceKey<Registry<ITMaterial>> material;

    public static List<Block> blocks = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Item> features = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.mod_id);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ref.mod_id);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.mod_id);
    public static final DeferredRegister<BlockEntityType<?>> Block_Entities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Ref.mod_id);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Ref.mod_id);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Ref.mod_id);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Ref.mod_id);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Ref.mod_id);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Ref.mod_id);

    public void init(){

    }

    public static void init(IEventBus IEventBus) {
        ITEMS.register(IEventBus);
        BLOCKS.register(IEventBus);
        BLOCK_ITEMS.register(IEventBus);
        FLUIDS.register(IEventBus);
        Block_Entities.register(IEventBus);
        CONTAINERS.register(IEventBus);
        ENTITIES.register(IEventBus);
        FEATURES.register(IEventBus);
        RECIPE_SERIALIZER.register(IEventBus);
    }

    public static Block registerBlock(String modID, String registryName, Block block){
        block.setRegistryName(new ResourceLocation(modID, registryName));
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }

    public static Item registerItem(String modID, String registryName, Item item){
        item.setRegistryName(new ResourceLocation(modID, registryName));
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

    public static Fluid registerFluid(String modID, String registryName, Fluid fluid){
        fluid.setRegistryName(new ResourceLocation(modID, registryName));
        ForgeRegistries.FLUIDS.register(fluid);
        return fluid;
    }
}