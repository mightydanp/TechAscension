package mightydanp.industrialtech.api.common.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 3/26/2021.
 */
public class GenItemTags extends ItemTagsProvider {
    public GenItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }
}
