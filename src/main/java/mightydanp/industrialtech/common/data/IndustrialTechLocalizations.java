package mightydanp.industrialtech.common.data;

import mightydanp.industrialtech.common.lib.References;
import muramasa.antimatter.datagen.providers.AntimatterLanguageProvider;
import net.minecraft.data.DataGenerator;

public class IndustrialTechLocalizations {

    public static class en_US extends AntimatterLanguageProvider {

        public en_US(DataGenerator gen) {
            super(References.ID, References.NAME + " en_us Localization", "en_us", gen);
        }

        @Override
        protected void addTranslations() {
            super.processTranslations(References.ID, "en_us");
            add(References.ID + ".advancements." + References.ID, References.NAME);
            add(References.ID + ".advancements." + References.ID + ".desc", "Getting familiar with your surroundings");
            add(References.ID + ".tooltip.occurrence", "Indicates occurrence of ");
        }

    }

}
