package mightydanp.industrialtech.api.common.blocks.state;

import mightydanp.industrialtech.api.common.handler.RegisterHelper;
import mightydanp.industrialtech.api.common.libs.ITBlockStateRef;
import net.minecraft.state.BooleanProperty;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class ITBlockStateProperties {
    public static BooleanProperty RESIN = RegisterHelper.createBlockState(ITBlockStateRef.RESIN);
}
