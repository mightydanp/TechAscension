package mightydanp.industrialtech.common.items;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.items.BasicBlockItem;
import mightydanp.industrialtech.api.common.items.BasicItem;
import mightydanp.industrialtech.common.libs.ItemRef;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.system.CallbackI;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class ModItems {
    public static RegistryObject<Item> unfired_clay_pot;
    public static RegistryObject<Item> unfired_clay_pan;
    public static RegistryObject<Item> fired_clay_pot;
    public static RegistryObject<Item> fired_clay_pan;

    public void init(){
        unfired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        unfired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.unfired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        fired_clay_pot = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pot_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
        fired_clay_pan = RegistryHandler.ITEMS.register(ItemRef.fired_clay_pan_name, () -> new BasicItem(new Item.Properties().maxStackSize(1)));
    }
}
