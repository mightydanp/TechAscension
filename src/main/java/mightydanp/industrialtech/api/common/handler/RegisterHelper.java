package mightydanp.industrialtech.api.common.handler;

import net.minecraft.state.BooleanProperty;

/**
 * Created by MightyDanp on 1/31/2021.
 */
public class RegisterHelper {
    public static BooleanProperty createBlockState(String stateName){
        return BooleanProperty.create(stateName);
    }
}
