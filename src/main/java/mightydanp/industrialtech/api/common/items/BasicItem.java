package mightydanp.industrialtech.api.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BasicItem extends Item {
    public BasicItem(Properties properties) {
        super(properties);
        properties.tab(ModItemGroups.item_tab);
    }


}
