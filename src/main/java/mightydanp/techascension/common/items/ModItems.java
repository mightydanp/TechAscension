package mightydanp.techascension.common.items;

import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.IItems;
import mightydanp.techapi.common.resources.asset.data.ItemModelData;
import mightydanp.techapi.common.resources.asset.data.ItemModelDataTest;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techapi.common.resources.data.TADataHolder;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.BasicItem;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techascension.common.blocks.ModBlocks;
import mightydanp.techascension.common.libs.ItemRef;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class ModItems implements IItems {
    public static RegistryObject<Item> cattail_bottom_item;
    public static RegistryObject<Item> cattail_top_item;
    public static RegistryObject<Item> cattail_stalk;
    public static RegistryObject<Item> cattail_root;
    public static RegistryObject<Item> cattail_shoot;
    public static RegistryObject<Item> cattail_leaf;
    public static RegistryObject<Item> plant_fiber;
    public static RegistryObject<Item> twine;
    public static RegistryObject<Item> campfire_block;
    //public static RegistryObject<Item> unfired_clay_pot;
    //public static RegistryObject<Item> unfired_clay_pan;
    //public static RegistryObject<Item> fired_clay_pot;
    //public static RegistryObject<Item> fired_clay_pan;

    public static void init(){
        //cattail_root = RegistryHandler.ITEMS.register(ItemRef.cattail_root_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_shoot = RegistryHandler.ITEMS.register(ItemRef.cattail_shoot_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_stalk = RegistryHandler.ITEMS.register(ItemRef.cattail_stalk_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));
        //cattail_leaf = RegistryHandler.ITEMS.register(ItemRef.cattail_leaf_name, () -> new BasicItem(new Item.Properties().maxStackSize(64)));

        plant_fiber = RegistryHandler.ITEMS.register(ItemRef.plant_fiber_name, () -> new BasicItem(new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(64)));
        twine = RegistryHandler.ITEMS.register(ItemRef.twine_name, () -> new BasicItem(new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(64)));

        //unfired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //unfired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //fired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        //fired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
    }

    public static void initBlockItems(){
        cattail_bottom_item = RegistryHandler.BLOCK_ITEMS.register(ItemRef.cattail_bottom_name, () -> new BlockItem(ModBlocks.cattail_plant_bottom_block.get(), new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(64)));
        cattail_top_item = RegistryHandler.BLOCK_ITEMS.register(ItemRef.cattail_top_name, () -> new BlockItem(ModBlocks.cattail_plant_top_block.get(), new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(64)));
        campfire_block = RegistryHandler.BLOCK_ITEMS.register(ItemRef.campfire_block_name, () -> new BlockItem(ModBlocks.campfire_override.get(), new Item.Properties().tab(TCCreativeModeTab.item_tab).stacksTo(64)));
    }

    public static void initClient(){

        if(plant_fiber != null) {
            registerAItemColor(plant_fiber.get(), 0x3c5817, 0);
        }

        if(twine != null) {
            registerAItemColor(twine.get(), 0xffe3c0, 0);
        }
    }

    @Override
    public void initResource() {
        LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());

        //assets
        enLang.addTranslation("block." + Ref.mod_id + "." + ItemRef.plant_fiber_name, LangData.translateUpperCase(ItemRef.plant_fiber_name));
        AssetPackRegistry.itemModelDataHashMap.put(ItemRef.plant_fiber_name, new ItemModelData().setParent(new ResourceLocation("item/handheld")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/iconsets/" + ItemRef.plant_fiber_name)));

        enLang.addTranslation("block." + Ref.mod_id + "." + ItemRef.twine_name, LangData.translateUpperCase(ItemRef.twine_name));
        AssetPackRegistry.itemModelDataHashMap.put(ItemRef.twine_name, new ItemModelData().setParent(new ResourceLocation("item/handheld"))
                .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/iconsets/" + ItemRef.twine_name))
                .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/iconsets/" + ItemRef.twine_name + "_overlay")
                ));
        //ItemModelDataTest model = new ItemModelDataTest(new ResourceLocation(Ref.mod_id, ItemRef.plant_fiber_name));

        //model.itemModel.parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", new ResourceLocation(Ref.mod_id, "item/knapsack")).toJson();

        AssetPackRegistry.langDataMap.put("en_us", enLang);

    }

    public static void registerAItemColor(Item item, int color, int layerNumberIn) {
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
