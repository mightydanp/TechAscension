package mightydanp.industrialtech.api.common.jsonconfig.sync.network.message;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 12/27/2021.
 */
public class SyncMessage {
    private Map<Integer, List<?>> configs = new HashMap<>();

    private final boolean isSinglePlayer;
    private final String singlePlayerWorldName;

    public void setConfig(Integer configIDIn, List<?> listIn){
        configs.put(configIDIn, listIn);
    }

    public List<?> getConfig(Integer configIDIn){
        return configs.get(configIDIn);
    }

    public SyncMessage(boolean isSinglePlayerIn, @Nullable String singlePlayerWorldNameIn) {
        isSinglePlayer = isSinglePlayerIn;
        singlePlayerWorldName = singlePlayerWorldNameIn;

    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public String getSinglePlayerWorldName() {
        return singlePlayerWorldName;
    }

    public static SyncMessage read(PacketBuffer buffer) {
        boolean isSinglePlayer = buffer.readBoolean();
        String singlePlayerWorldName = buffer.readUtf();

        SyncMessage message = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

        ConfigSync configSync = IndustrialTech.configSync;

        for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
            message.setConfig(i, IndustrialTech.configSync.configs.get(i).getFirst().getAllValues());
        }

        return message;
    }

    public static void write(SyncMessage message, PacketBuffer buffer) {
        buffer.writeBoolean(message.isSinglePlayer());
        buffer.writeUtf(message.singlePlayerWorldName);

        for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
            Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
            config.getSecond().multipleToBuffer(message, buffer);
        }

    }

    public static void onMessage(SyncMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

            ConfigSync configSync = IndustrialTech.configSync;

            for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                config.getSecond().loadFromServer(message);
            }

            if(!message.isSinglePlayer()){
                configSync.isSinglePlayer = false;
                configSync.singlePlayerWorldName = "";

                for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                    Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                    config.getSecond().isClientAndServerConfigsSynced(message);
                }

            }else{
                configSync.isSinglePlayer = true;
                configSync.singlePlayerWorldName = message.getSinglePlayerWorldName();

                for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                    Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                    config.getSecond().isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                }
            }
            //

        });

        context.get().setPacketHandled(true);
    }
}