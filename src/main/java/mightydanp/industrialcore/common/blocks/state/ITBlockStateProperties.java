package mightydanp.industrialcore.common.blocks.state;

import mightydanp.industrialcore.common.handler.RegisterHelper;
import mightydanp.industrialcore.common.libs.ITBlockStateRef;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class ITBlockStateProperties {
    public static BooleanProperty RESIN = RegisterHelper.createBlockState(ITBlockStateRef.RESIN);
}
