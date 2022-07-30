package mightydanp.techcore.common.blocks.state;

import mightydanp.techcore.common.handler.RegisterHelper;
import mightydanp.techcore.common.libs.TCBlockStateRef;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class TCBlockStateProperties {
    public static BooleanProperty RESIN = RegisterHelper.createBlockState(TCBlockStateRef.RESIN);
}
