package mightydanp.industrialcore.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 3/26/2021.
 */
public class GenFluidTags extends FluidTagsProvider {
    public GenFluidTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }
}
