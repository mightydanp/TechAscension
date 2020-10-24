package mightydanp.industrialtech.api.common.packet;

import mightydanp.industrialtech.api.client.gui.screen.CustomPlayerInventoryScreen;
import mightydanp.industrialtech.api.common.inventory.container.CustomPlayerInventoryContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * Created by MightyDanp on 10/21/2020.
 */
public class PacketOpenCustomInventoryGui {

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if(player != null)
                NetworkHooks.openGui(player, new SimpleNamedContainerProvider(CustomPlayerInventoryContainer::new, new StringTextComponent("custom_inventory")
            ));
        });
        return true;
    }
}
