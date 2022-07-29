package mightydanp.industrialapi.common.resources.asset.data;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.common.IndustrialTech;

public class LangData {
    public String language;
    public JsonObject translations = new JsonObject();


    public boolean addTranslation(String localizationIn, String translationIn) {
        if (!translations.has(localizationIn)) {
            translations.addProperty(localizationIn, translationIn);
            return true;
        } else {
            IndustrialTech.LOGGER.warn("Cannot add translation, [" + translationIn + "], because the localization, [" + localizationIn + "], already exist for " + language);
            return false;
        }
    }

    public static String translateUpperCase(String name){
        StringBuilder translatedName = new StringBuilder();
        if (name.contains("_")) {
            int i = 0;
            for (String word : name.split("_")) {
                String str = word.substring(0, 1).toUpperCase() + word.substring(1);
                if( i == 0 ){
                    translatedName.append(str);
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
