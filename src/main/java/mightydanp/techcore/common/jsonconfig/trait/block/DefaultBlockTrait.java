package mightydanp.techcore.common.jsonconfig.trait.block;

import net.minecraft.resources.ResourceLocation;

public enum DefaultBlockTrait {
    chest(new BlockTraitCodec(new ResourceLocation("chest").toString(), 0, 0D, true))
    ;
    private final BlockTraitCodec codec;
    DefaultBlockTrait(BlockTraitCodec codec) {
        this.codec = codec;
    }

    public BlockTraitCodec getCodec(){
        return this.codec;
    }
}
