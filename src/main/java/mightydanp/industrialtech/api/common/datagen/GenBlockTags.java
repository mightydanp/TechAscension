package mightydanp.industrialtech.api.common.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 3/26/2021.
 */
public class GenBlockTags extends BlockTagsProvider {
    public GenBlockTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }
}
