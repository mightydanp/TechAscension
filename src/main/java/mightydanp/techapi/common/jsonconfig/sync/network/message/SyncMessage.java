package mightydanp.techapi.common.jsonconfig.sync.network.message;

import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.IJsonConfig;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 12/27/2021.
 */
public class SyncMessage {
    private final Map<Integer, List<?>> configs = new HashMap<>();

    public void setConfig(Integer configIDIn, List<?> listIn){
        configs.put(configIDIn, listIn);
    }

    public List<?> getConfig(Integer configIDIn){
        return configs.get(configIDIn);
    }

    public static SyncMessage read(FriendlyByteBuf buffer) {
        SyncMessage message = new SyncMessage();

        for(int i = 0; i < ConfigSync.configs.size(); i++){
            message.setConfig(i, ConfigSync.configs.get(i).getFirst().getAllValues());
        }

        return message;
    }

    public static void write(SyncMessage message, FriendlyByteBuf buffer) {
        for(int i = 0; i < ConfigSync.configs.size(); i++){
            Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> config = ConfigSync.configs.get(i);
            config.getSecond().multipleToBuffer(message, buffer);
        }
    }

    public static void onMessage(SyncMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            for(int i = 0; i < ConfigSync.configs.size(); i++){
                Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> config = ConfigSync.configs.get(i);
                config.getSecond().loadFromServer(message);
            }

            for(int i = 0; i < ConfigSync.configs.size(); i++){
                Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> config = ConfigSync.configs.get(i);
                config.getSecond().isClientAndServerConfigsSynced(message);
            }

        });

        context.get().setPacketHandled(true);
    }
}