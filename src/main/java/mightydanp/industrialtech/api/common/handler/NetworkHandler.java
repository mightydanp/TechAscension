package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.network.message.OpenClientScreenMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

/**
 * Created by MightyDanp on 1/3/2022.
 */
public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Ref.mod_id, "main"), () -> "1.0", (s) -> true, (s) -> true);
    private static int id = 0;

    public static void onCommonSetup() {
        INSTANCE.registerMessage(id(), SyncMessage.class, SyncMessage::write, SyncMessage::read, SyncMessage::onMessage);
        INSTANCE.registerMessage(id(), OpenClientScreenMessage.class, OpenClientScreenMessage::encode, OpenClientScreenMessage::decode, OpenClientScreenMessage::handle);
    }

    private static int id() {
        return id++;
    }
}