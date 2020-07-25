package mightydanp.industrialtech.common.datagen;

import muramasa.antimatter.datagen.providers.AntimatterBlockTagProvider;
import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class IndustrialTechBlockTagProvider extends AntimatterBlockTagProvider {

    public IndustrialTechBlockTagProvider(String providerDomain, String providerName, boolean replace, DataGenerator gen) {
        super(providerDomain, providerName, replace, gen);
    }

    @Override
    public void registerTags() {
        super.registerTags();
        this.getBuilder(BlockTags.LOGS).add(IndustrialTechData.RUBBER_LOG);
        this.getBuilder(BlockTags.LEAVES).add(IndustrialTechData.RUBBER_LEAVES);
        this.getBuilder(BlockTags.SAPLINGS).add(IndustrialTechData.RUBBER_SAPLING);
    }
}
