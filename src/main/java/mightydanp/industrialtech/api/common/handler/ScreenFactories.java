package mightydanp.industrialtech.api.common.handler;

import com.google.common.base.Preconditions;
import mightydanp.industrialtech.api.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.industrialtech.api.common.libs.RefScreenIDs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by MightyDanp on 1/11/2022.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenFactories {
    @SubscribeEvent
    public static void registerFactories(final FMLClientSetupEvent event) {
        registerContainerScreenFactories();
        registerClientScreenFactories();
    }

    private static void registerContainerScreenFactories() {
        //ScreenManager.register(ModContainerTypes.CHEST.get(), ModChestScreen::new);
    }

    private static void registerClientScreenFactories() {
        /*
        ClientScreenHandler.registerScreenFactory(GuiIDs.Client.SURVIVAL_COMMAND_BLOCK, (id, additionalData) -> {
            final BlockPos pos = additionalData.readBlockPos();
            final SurvivalCommandBlockTileEntity tileEntity = getTileEntity(pos, SurvivalCommandBlockTileEntity.class);

            return new GuiSurvivalCommandBlock(tileEntity);
        });

         */

        ClientScreenHandler.registerScreenFactory(RefScreenIDs.syncScreenId, (id, additionalData) -> new SyncScreen());
    }

    @SuppressWarnings("unchecked")
    private static <T extends TileEntity> T getTileEntity(final BlockPos pos, final Class<T> tileEntityClass) {
        final ClientWorld world = getClientWorld();

        final TileEntity tileEntity = world.getBlockEntity(pos);

        Preconditions.checkNotNull(tileEntity, "No TileEntity found at %s", pos);
        Preconditions.checkState(tileEntityClass.isInstance(tileEntity), "Invalid TileEntity at %s: expected %s, got %s", pos, tileEntityClass, tileEntity.getClass());

        return (T) tileEntity;
    }

    private static ClientWorld getClientWorld() {
        return Preconditions.checkNotNull(Minecraft.getInstance().level, "Client world is null");
    }
}
