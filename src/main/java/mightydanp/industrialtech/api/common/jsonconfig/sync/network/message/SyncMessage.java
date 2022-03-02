package mightydanp.industrialtech.api.common.jsonconfig.sync.network.message;

import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateServer;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater.BlocksInWaterServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface.RandomSurfaceServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.smallore.SmallOreVeinServer;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconServer;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeServer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.IStoneLayer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeServer;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialServer;
import mightydanp.industrialtech.api.common.world.gen.feature.*;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 12/27/2021.
 */
public class SyncMessage {
    private List<IMaterialFlag> materialFlags = new ArrayList<>();
    private List<IFluidState> fluidStates  = new ArrayList<>();
    private List<ITextureIcon> textureIcons  = new ArrayList<>();
    private List<IOreType> oreTypes  = new ArrayList<>();
    private List<IToolPart> toolParts  = new ArrayList<>();
    private List<IToolType> toolTypes  = new ArrayList<>();
    private List<IStoneLayer> stoneLayers = new ArrayList<>();

    private List<ITMaterial> materials  = new ArrayList<>();

    private List<OreVeinGenFeatureConfig> oreVeins = new ArrayList<>();
    private List<SmallOreVeinGenFeatureConfig> smallOreVeins = new ArrayList<>();
    private List<BlocksInWaterGenFeatureConfig> blocksInWater = new ArrayList<>();
    private List<RandomSurfaceGenFeatureConfig> randomSurface = new ArrayList<>();

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

    public List<IStoneLayer> getStoneLayers() {
        return stoneLayers;
    }

    public List<ITMaterial> getMaterials() {
        return this.materials;
    }

    public List<OreVeinGenFeatureConfig> getOreVeins() {
        return oreVeins;
    }

    public List<SmallOreVeinGenFeatureConfig> getSmallOreVeins() {
        return smallOreVeins;
    }

    public List<BlocksInWaterGenFeatureConfig> getBlocksInWaters() {
        return blocksInWater;
    }

    public List<RandomSurfaceGenFeatureConfig> getRandomSurfaces() {
        return randomSurface;
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

    public SyncMessage setStoneLayers(List<IStoneLayer> stoneLayers) {
        this.stoneLayers = stoneLayers;
        return this;
    }

    public SyncMessage setMaterials(List<ITMaterial> materialsIn){
        this.materials = materialsIn;
        return this;
    }

    public SyncMessage setOreVeins(List<OreVeinGenFeatureConfig> oreVeins) {
        this.oreVeins = oreVeins;
        return this;
    }

    public SyncMessage setSmallOreVeins(List<SmallOreVeinGenFeatureConfig> smallOreVeins) {
        this.smallOreVeins = smallOreVeins;
        return this;
    }

    public SyncMessage setBlocksInWater(List<BlocksInWaterGenFeatureConfig> blocksInWater) {
        this.blocksInWater = blocksInWater;
        return this;
    }

    public SyncMessage setRandomSurface(List<RandomSurfaceGenFeatureConfig> randomSurface) {
        this.randomSurface = randomSurface;
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
        message.setStoneLayers(StoneLayerServer.multipleFromBuffer(buffer));

        message.setMaterials(MaterialServer.multipleFromBuffer(buffer));

        message.setOreVeins(OreVeinServer.multipleFromBuffer(buffer));
        message.setSmallOreVeins(SmallOreVeinServer.multipleFromBuffer(buffer));
        message.setBlocksInWater(BlocksInWaterServer.multipleFromBuffer(buffer));
        message.setRandomSurface(RandomSurfaceServer.multipleFromBuffer(buffer));

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
        StoneLayerServer.multipleToBuffer(message, buffer);

        MaterialServer.multipleToBuffer(message, buffer);

        OreVeinServer.multipleToBuffer(message, buffer);
        SmallOreVeinServer.multipleToBuffer(message, buffer);
        BlocksInWaterServer.multipleToBuffer(message, buffer);
        RandomSurfaceServer.multipleToBuffer(message, buffer);
    }

    public static void onMessage(SyncMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {

            IndustrialTech.configSync.materialFlagServer.loadMaterialFlags(message);
            IndustrialTech.configSync.fluidStateServer  .loadFluidStates(message);
            IndustrialTech.configSync.oreTypeServer     .loadOreTypes(message);
            IndustrialTech.configSync.toolPartServer    .loadToolParts(message);
            IndustrialTech.configSync.toolTypeServer    .loadToolTypes(message);
            IndustrialTech.configSync.textureIconServer .loadTextureIcons(message);
            IndustrialTech.configSync.stoneLayerServer  .loadStoneLayers(message);

            IndustrialTech.configSync.materialServer.loadMaterials(message);

            IndustrialTech.configSync.oreVeinServer.loadOreVeins(message);
            IndustrialTech.configSync.smallOreVeinServer.loadSmallOreVeins(message);
            IndustrialTech.configSync.blocksInWaterServer.loadBlocksInWaters(message);
            IndustrialTech.configSync.randomSurfaceServer.loadRandomSurfaces(message);


            if(!message.isSinglePlayer()){
                IndustrialTech.configSync.isSinglePlayer = false;
                IndustrialTech.configSync.singlePlayerWorldName = "";
                IndustrialTech.configSync.materialFlagServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.fluidStateServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.oreTypeServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.toolPartServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.toolTypeServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.textureIconServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.stoneLayerServer.isClientAndServerConfigsSynced(message);

                IndustrialTech.configSync.materialServer.isClientAndServerConfigsSynced(message);

                IndustrialTech.configSync.oreVeinServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.smallOreVeinServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.blocksInWaterServer.isClientAndServerConfigsSynced(message);
                IndustrialTech.configSync.randomSurfaceServer.isClientAndServerConfigsSynced(message);
            }else{
                IndustrialTech.configSync.isSinglePlayer = true;
                IndustrialTech.configSync.singlePlayerWorldName = message.getSinglePlayerWorldName();

                IndustrialTech.configSync.materialFlagServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.fluidStateServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.oreTypeServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.toolPartServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.toolTypeServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.textureIconServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.stoneLayerServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));

                IndustrialTech.configSync.materialServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));

                IndustrialTech.configSync.oreVeinServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.smallOreVeinServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.blocksInWaterServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
                IndustrialTech.configSync.randomSurfaceServer.isClientAndClientWorldConfigsSynced(Paths.get("saves/" + message.singlePlayerWorldName + "/serverconfig/" + Ref.mod_id));
            }
            //

        });

        context.get().setPacketHandled(true);
    }
}