package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/7/2020.
 */
public class Language extends LanguageProvider {
    public Language(DataGenerator gen) {
        super(gen, Ref.mod_id, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (MaterialHandler material : ModMaterials.materials) {
            StringBuilder oreName = new StringBuilder();
            if (material.materialName.contains("_")) {
                int i = -1;
                for (String word : material.materialName.split("_")) {
                    int nameLength = material.materialName.split("_").length;
                    i++;
                    String str = word.substring(0, 1).toUpperCase() + word.substring(1);
                    if (i == 0) {
                        oreName.append(str);
                    }
                    if (i != 0 && i < nameLength - 1) {
                        oreName.append(str);
                    }
                    if(i == nameLength - 1){
                        oreName.append(" ").append(str).append(" Ore");
                    }
                }
            } else {
                String str = material.materialName.substring(0, 1).toUpperCase() + material.materialName.substring(1);
                oreName.append(str).append(" ").append("Ore");
            }
            for (RegistryObject<Block> block : material.blockOre) {
                add(block.get(), oreName.toString());
            }
        }

        // add(MODID + ".test.unicode", "\u0287s\u01DD\u2534 \u01DDpo\u0254\u1D09u\u2229");}
    }
}
