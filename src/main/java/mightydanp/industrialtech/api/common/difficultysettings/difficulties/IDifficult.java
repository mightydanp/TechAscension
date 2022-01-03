package mightydanp.industrialtech.api.common.difficultysettings.difficulties;

import net.minecraft.util.text.ITextComponent;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public interface IDifficult {
    int getId();
    ITextComponent getDisplayName();
    String getKey();
}
