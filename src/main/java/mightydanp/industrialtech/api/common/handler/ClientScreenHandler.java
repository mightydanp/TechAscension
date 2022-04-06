package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MightyDanp on 1/11/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientScreenHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Map<ResourceLocation, IScreenFactory<?>> FACTORIES = new HashMap<>();

    public static void openScreen(final ResourceLocation id, final FriendlyByteBuf additionalData) {
        getScreenFactory(id).ifPresent(f -> f.createAndOpenScreen(id, additionalData, Minecraft.getInstance()));
    }

    public static <T extends AbstractContainerMenu> Optional<IScreenFactory<?>> getScreenFactory(final ResourceLocation id) {
        final IScreenFactory<?> factory = FACTORIES.get(id);

        if (factory == null) {
            LOGGER.warn("Failed to create screen for id: {}", id);
            return Optional.empty();
        }

        return Optional.of(factory);
    }

    public static <S extends Screen> void registerScreenFactory(final ResourceLocation id, final IScreenFactory<S> factory) {
        final IScreenFactory<?> oldFactory = FACTORIES.put(id, factory);

        if (oldFactory != null) {
            throw new IllegalStateException("Duplicate registration for " + id);
        }
    }

    @FunctionalInterface
    public interface IScreenFactory<S extends Screen> {
        default void createAndOpenScreen(final ResourceLocation id, final FriendlyByteBuf additionalData, final Minecraft mc) {
            final S screen = create(id, additionalData);
            mc.setScreen(screen);
        }

        S create(ResourceLocation id, FriendlyByteBuf additionalData);
    }
}