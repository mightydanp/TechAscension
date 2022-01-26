package mightydanp.industrialtech.api.common.jsonconfig.sync.network.message;

import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateServer;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconServer;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeServer;
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
    private List<IFluidState> fluidStates;
    private List<ITextureIcon> textureIcons;
    private List<IOreType> oreTypes;
    private List<ITMaterial> materials;
    private List<IToolPart> toolParts;
    private List<IToolType> toolTypes;
    private final boolean isSinglePlayer;
    private final String singlePlayerWorldName;

    public SyncMessage(boolean isSinglePlayerIn, @Nullable String singlePlayerWorldNameIn) {
        isSinglePlayer = isSinglePlayerIn;
        singlePlayerWorldName = singlePlayerWorldNameIn;

    }

    public List<IMaterialFlag> getMaterialFlags() {
        return this.materialFlags;
    }

    public List<IFluidState> getFluidStates() {
        return fluidStates;
    }

    public List<ITextureIcon> getTextureIcons() {
        return textureIcons;
    }

    public List<IOreType> getOreTypes() {
        return oreTypes;
    }

    public List<IToolPart> getToolParts() {
        return toolParts;
    }

    public List<IToolType> getToolTypes() {
        return toolTypes;
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

    public SyncMessage setFluidStates(List<IFluidState> fluidStatesIn) {
        this.fluidStates = fluidStatesIn;
        return this;
    }

    public SyncMessage setTextureIcons(List<ITextureIcon> textureIconsIn) {
        this.textureIcons = textureIconsIn;
        return this;
    }

    public SyncMessage setOreTypes(List<IOreType> oreTypes) {
        this.oreTypes = oreTypes;
        return this;
    }

    public SyncMessage setToolParts(List<IToolPart> toolPartsIn) {
        this.toolParts = toolPartsIn;
        return this;
    }

    public SyncMessage setToolTypes(List<IToolType> toolTypesIn) {
        this.toolTypes = toolTypesIn;
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
        message.setFluidStates(FluidStateServer.multipleFromBuffer(buffer));
        message.setTextureIcons(TextureIconServer.multipleFromBuffer(buffer));
        message.setOreTypes(OreTypeServer.multipleFromBuffer(buffer));
        message.setToolParts(ToolPartServer.multipleFromBuffer(buffer));
        message.setToolTypes(ToolTypeServer.multipleFromBuffer(buffer));

        message.setMaterials(MaterialServer.multipleFromBuffer(buffer));

        return message;
    }

    public static void write(SyncMessage message, PacketBuffer buffer) {
        buffer.writeBoolean(message.isSinglePlayer());
        buffer.writeUtf(message.singlePlayerWorldName);
        MaterialFlagServer.multipleToBuffer(message, buffer);
        FluidStateServer.multipleToBuffer(message, buffer);
        TextureIconServer.multipleToBuffer(message, buffer);
        OreTypeServer.multipleToBuffer(message, buffer);
        ToolPartServer.multipleToBuffer(message, buffer);
        ToolTypeServer.multipleToBuffer(message, buffer);

        MaterialServer.multipleToBuffer(message, buffer);
    }

    public static void onMessage(SyncMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {


            IndustrialTech.configSync.materialServer.loadMaterials(message);
            IndustrialTech.configSync.materialFlagServer.loadMaterialFlags(message);
            IndustrialTech.configSync.fluidStateServer.loadFluidStates(message);
            IndustrialTech.configSync.oreTypeServer.loadOreTypes(message);
            IndustrialTech.configSync.toolPartServer.loadToolParts(message);
            IndustrialTech.configSync.toolTypeServer.loadToolTypes(message);
            IndustrialTech.configSync.textureIconServer.loadTextureIcons(message);


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