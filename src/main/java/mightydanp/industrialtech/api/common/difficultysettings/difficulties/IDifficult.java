package mightydanp.industrialtech.api.common.difficultysettings.difficulties;

import net.minecraft.network.chat.Component;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public interface IDifficult {
    int getId();
    Component getDisplayName();
    String getKey();
}
