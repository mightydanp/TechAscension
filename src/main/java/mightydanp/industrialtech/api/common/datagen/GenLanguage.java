package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.ITButtonRef;
import mightydanp.industrialtech.api.common.libs.ITScreenRef;
import mightydanp.industrialtech.api.common.libs.ITScreenTextRef;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 10/7/2020.
 */
public class GenLanguage extends LanguageProvider {
    public GenLanguage(DataGenerator gen) {
        super(gen, Ref.mod_id, "en_us");
    }

    @Override
    protected void addTranslations() {
        for(Map.Entry<RegistryKey<Item>, Item> item : ForgeRegistries.ITEMS.getEntries().stream().filter(items -> items.getKey().location().getNamespace().equals(Ref.mod_id)).collect(Collectors.toList())) {
            String itemName = translateUpperCase(item.getKey().location().toString());
            add(item.getValue(), itemName);
        }

        add(ITButtonRef.returnToServerScreen, ITButtonRef.returnToServerScreenText);
        add(ITButtonRef.syncClientConfigsWithServers, ITButtonRef.syncClientConfigsWithServersText);
        add(ITScreenTextRef.syncWarningLine1, ITScreenTextRef.syncWarningLine1Text);
        add(ITScreenTextRef.syncWarningLine2, ITScreenTextRef.syncWarningLine2Text);
        add(ITScreenTextRef.syncWarningLine3, ITScreenTextRef.syncWarningLine3Text);
        add(ITScreenTextRef.clientWorldWarningLine1, ITScreenTextRef.clientWorldWarningLine1Text);
        add(ITScreenTextRef.clientWorldWarningLine2, ITScreenTextRef.clientWorldWarningLine2Text);

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
