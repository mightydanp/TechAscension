package mightydanp.industrialtech.api.common.libs;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by MightyDanp on 1/11/2022.
 */
public class RefScreenIDs {
    public static ResourceLocation syncScreenId = id("sync_screen");

    private static ResourceLocation id(final String id) {
        return new ResourceLocation(Ref.mod_id, id);
    }
}
