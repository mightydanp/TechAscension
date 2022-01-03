package mightydanp.industrialtech.common.blocks.state;

import mightydanp.industrialtech.api.common.handler.RegisterHelper;
import mightydanp.industrialtech.api.common.libs.ITBlockStateRef;
import net.minecraft.state.BooleanProperty;

/**
 * Created by MightyDanp on 1/31/2021.
 */
public class ModBlockStateProperties {
    public static BooleanProperty RESIN = RegisterHelper.createBlockState(ITBlockStateRef.RESIN);

}
