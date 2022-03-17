package mightydanp.industrialtech.api.common.jsonconfig.sync.network.message;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
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
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.OreTypeServer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.IStoneLayer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
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
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 12/27/2021.
 */
public class SyncMessage {
    private Map<Integer, List<?>> configs = new HashMap<>();

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

        ConfigSync configSync = IndustrialTech.configSync;

        message.setMaterialFlags(((MaterialFlagServer)configSync.materialFlag.getSecond()).multipleFromBuffer(buffer));
        message.setFluidStates(((FluidStateServer)configSync.fluidState.getSecond()).multipleFromBuffer(buffer));
        message.setTextureIcons(((TextureIconServer)configSync.textureIcon.getSecond()).multipleFromBuffer(buffer));
        message.setOreTypes(((OreTypeServer)configSync.oreType.getSecond()).multipleFromBuffer(buffer));
        message.setToolParts(((ToolPartServer)configSync.toolPart.getSecond()).multipleFromBuffer(buffer));
        message.setToolTypes(((ToolTypeServer)configSync.toolType.getSecond()).multipleFromBuffer(buffer));
        message.setStoneLayers(((StoneLayerServer)configSync.stoneLayer.getSecond()).multipleFromBuffer(buffer));

        message.setMaterials(((MaterialServer)configSync.material.getSecond()).multipleFromBuffer(buffer));

        message.setOreVeins(((OreVeinServer)configSync.oreVein.getSecond()).multipleFromBuffer(buffer));
        message.setSmallOreVeins(((SmallOreVeinServer)configSync.smallOre.getSecond()).multipleFromBuffer(buffer));
        message.setBlocksInWater(((BlocksInWaterServer)configSync.blocksInWater.getSecond()).multipleFromBuffer(buffer));
        message.setRandomSurface(((RandomSurfaceServer)configSync.randomSurface.getSecond()).multipleFromBuffer(buffer));

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