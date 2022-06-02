package mightydanp.industrialcore.common.network.message;

import io.netty.buffer.Unpooled;
import mightydanp.industrialcore.common.handler.ClientScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Created by MightyDanp on 1/11/2022.
 */
public class OpenClientScreenMessage {
    private final ResourceLocation id;
    private final FriendlyByteBuf additionalData;

    public OpenClientScreenMessage(final ResourceLocation id, final FriendlyByteBuf additionalData) {
        this.id = id;
        this.additionalData = additionalData;
    }

    public static OpenClientScreenMessage decode(final FriendlyByteBuf buffer) {
        final ResourceLocation id = buffer.readResourceLocation();
        final FriendlyByteBuf additionalData = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray(32600)));

        return new OpenClientScreenMessage(id, additionalData);
    }

    public static void encode(final OpenClientScreenMessage message, final FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.id);
        buffer.writeByteArray(message.additionalData.readByteArray());
    }

    public static void handle(final OpenClientScreenMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientScreenHandler.openScreen(message.getId(), message.getAdditionalData()))
        );
        ctx.get().setPacketHandled(true);
    }

    public ResourceLocation getId() {
        return id;
    }

    public FriendlyByteBuf getAdditionalData() {
        return additionalData;
    }
}