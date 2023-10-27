package mightydanp.techcore.common.datagen;

import mightydanp.techcore.common.libs.TCButtonRef;
import mightydanp.techcore.common.libs.TCScreenTextRef;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.LanguageProvider;
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
        for(Map.Entry<ResourceKey<Item>, Item> item : ForgeRegistries.ITEMS.getEntries().stream().filter(items -> items.getKey().location().getNamespace().equals(Ref.mod_id)).collect(Collectors.toList())) {
            String itemName = translateUpperCase(item.getKey().location().toString());
            add(item.getValue(), itemName);
        }

        add(TCButtonRef.returnToMainMenuScreen, TCButtonRef.returnToMainMenuScreenText);
        add(TCButtonRef.syncClientConfigsWithServers, TCButtonRef.syncClientConfigsWithServersText);
        add(TCScreenTextRef.syncWarningLine1, TCScreenTextRef.syncWarningLine1Text);
        add(TCScreenTextRef.syncWarningLine2, TCScreenTextRef.syncWarningLine2Text);
        add(TCScreenTextRef.syncWarningLine3, TCScreenTextRef.syncWarningLine3Text);
        add(TCScreenTextRef.clientWorldWarningLine1, TCScreenTextRef.clientWorldWarningLine1Text);
        add(TCScreenTextRef.clientWorldWarningLine2, TCScreenTextRef.clientWorldWarningLine2Text);

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
