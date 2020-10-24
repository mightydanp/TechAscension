package mightydanp.industrialtech.api.common.packet;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Created by MightyDanp on 10/21/2020.
 */
public class PacketHandler {
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Ref.mod_id, "main"),
                () -> "0.0.1",
                s -> true,
                s -> true);

        INSTANCE.messageBuilder(PacketOpenCustomInventoryGui.class, nextID())
                .encoder((packetOpenGui, packetBuffer) -> {})
                .decoder(buf -> new PacketOpenCustomInventoryGui())
                .consumer(PacketOpenCustomInventoryGui::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}