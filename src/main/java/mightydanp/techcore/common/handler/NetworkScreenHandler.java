package mightydanp.techcore.common.handler;

import io.netty.buffer.Unpooled;
import mightydanp.techcore.common.network.message.OpenClientScreenMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Created by MightyDanp on 1/11/2022.
 */
public class NetworkScreenHandler {

    public static void openClientGui(final ServerPlayer player, final ResourceLocation id) {
        openClientGui(player, id, buf -> {
        });
    }

    public static void openClientGui(final ServerPlayer player, final ResourceLocation id, final BlockPos pos) {
        openClientGui(player, id, buf -> buf.writeBlockPos(pos));
    }

    public static void openClientGui(final ServerPlayer player, final ResourceLocation id, final Consumer<FriendlyByteBuf> extraDataWriter) {
        if (player.level.isClientSide) return;
        player.closeContainer();
        player.containerMenu = player.inventoryMenu;

        final FriendlyByteBuf extraData = new FriendlyByteBuf(Unpooled.buffer());
        extraDataWriter.accept(extraData);
        extraData.readerIndex(0); // Reset to the beginning in case the factories read from it

        final FriendlyByteBuf output = new FriendlyByteBuf(Unpooled.buffer());
        output.writeVarInt(extraData.readableBytes());
        output.writeBytes(extraData);

        if (output.readableBytes() > 32600 || output.readableBytes() < 1) {
            throw new IllegalArgumentException("Invalid PacketBuffer for openClientGui, found " + output.readableBytes() + " bytes");
        }

        final OpenClientScreenMessage message = new OpenClientScreenMessage(id, output);
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void writeNullableFacing(@Nullable final Direction facing, final FriendlyByteBuf buffer) {
        final boolean hasFacing = facing != null;

        buffer.writeBoolean(hasFacing);

        if (hasFacing) {
            buffer.writeEnum(facing);
        }
    }

    @Nullable
    public static Direction readNullableFacing(final FriendlyByteBuf buffer) {
        final boolean hasFacing = buffer.readBoolean();

        if (hasFacing) {
            return buffer.readEnum(Direction.class);
        }

        return null;
    }
}

