package mightydanp.industrialtech.api.common.jsonconfig.sync.network.message;

import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialServer;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 12/27/2021.
 */
public class SyncMessage {
    private List<IMaterialFlag> materialFlags;
    private List<ITMaterial> materials;
    private final boolean isSinglePlayer;
    private final String singlePlayerWorldName;

    public SyncMessage(boolean isSinglePlayerIn, @Nullable String singlePlayerWorldNameIn) {
        isSinglePlayer = isSinglePlayerIn;
        singlePlayerWorldName = singlePlayerWorldNameIn;

    }

    public List<IMaterialFlag> getMaterialFlags() {
        return this.materialFlags;
    }

    public List<ITMaterial> getMaterials() {
        return this.materials;
    }



    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public String getSinglePlayerWorldName() {
        return singlePlayerWorldName;
    }

    public SyncMessage setMaterialFlags(List<IMaterialFlag> materialFlagsIn){
        this.materialFlags = materialFlagsIn;
        return this;
    }

    public SyncMessage setMaterials(List<ITMaterial> materialsIn){
        this.materials = materialsIn;
        return this;
    }

    public static SyncMessage read(PacketBuffer buffer) {
        boolean isSinglePlayer = buffer.readBoolean();
        String singlePlayerWorldName = buffer.readUtf();

        SyncMessage message = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

        message.setMaterialFlags(MaterialFlagServer.multipleFromBuffer(buffer));
        message.setMaterials(MaterialServer.multipleFromBuffer(buffer));

        return message;
    }

    public static void write(SyncMessage message, PacketBuffer buffer) {
        buffer.writeBoolean(message.isSinglePlayer());
        buffer.writeUtf(message.singlePlayerWorldName);
        MaterialFlagServer.multipleToBuffer(message, buffer);
        MaterialServer.multipleToBuffer(message, buffer);
    }

    public static void onMessage(SyncMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {


            IndustrialTech.configSync.materialServer.loadMaterials(message);


            if(!message.isSinglePlayer()){
                IndustrialTech.configSync.isSinglePlayer = false;
                IndustrialTech.configSync.singlePlayerWorldName = "";
                IndustrialTech.configSync.materialServer.isClientAndServerConfigsSynced(message);
            }else{
                IndustrialTech.configSync.isSinglePlayer = true;
                IndustrialTech.configSync.singlePlayerWorldName = message.getSinglePlayerWorldName();
                IndustrialTech.configSync.materialServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
            }
            //

        });

        context.get().setPacketHandled(true);
    }
}