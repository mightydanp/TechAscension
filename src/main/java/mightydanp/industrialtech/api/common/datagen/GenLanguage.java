package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/7/2020.
 */
public class GenLanguage extends LanguageProvider {
    public GenLanguage(DataGenerator gen) {
        super(gen, Ref.mod_id, "en_us");
    }

    @Override
    protected void addTranslations() {
        for(RegistryObject<Item> item : RegistryHandler.ITEMS.getEntries()) {
            String itemName = translateUpperCase(item.get().getRegistryName().toString());
            add(item.get(), itemName);
        }

        for(RegistryObject<Item> blockItem : RegistryHandler.BLOCKITEMS.getEntries()) {
            String itemName = translateUpperCase(blockItem.get().getRegistryName().toString());
            add(blockItem.get(), itemName);
        }
    }

    String translateUpperCase(String name){
        StringBuilder translatedName = new StringBuilder();
        if (name.contains("_")) {
            int i = 0;
            for (String word : name.split("_")) {
                String str = word.substring(0, 1).toUpperCase() + word.substring(1);
                if( i == 0 ){
                    String str1 = word.split(":")[1];
                    String str2 = str1.substring(0, 1).toUpperCase() + str1.substring(1);
                    translatedName.append(str2);
                    i++;
                }else{
                    translatedName.append(" ").append(str);
                }
            }
        }else{
            String str = name.substring(0, 1).toUpperCase() + name.substring(1);
            translatedName.append(str.split(":")[1]);
        }

        return translatedName.toString();
    }
}
