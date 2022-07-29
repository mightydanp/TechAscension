package mightydanp.industrialcore.common.jsonconfig.material.data;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialcore.common.jsonconfig.ICJsonConfigs;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialcore.common.jsonconfig.material.ore.IOreType;
import mightydanp.industrialcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.material.ITMaterial;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.FriendlyByteBuf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/3/2022.
 */
public class MaterialServer extends JsonConfigServer<ITMaterial> {

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ITMaterial> list = message.getConfig(ICJsonConfigs.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast).toList();
        
        if(list.size() != ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getAllValues().size()){
            sync.set(false);
            ConfigSync.syncedJson.put("material", sync.get());
            return false;
        }

        ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getAllValues().forEach(itMaterial -> {
            sync.set(list.stream().anyMatch(o -> o.name.equals(itMaterial.name)));

            if(sync.get()) {
                Optional<ITMaterial> optional = list.stream().filter(o -> o.name.equals(itMaterial.name)).findFirst();

                if(optional.isPresent()) {
                    ITMaterial material = optional.get();
                    JsonObject jsonMaterial = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        ConfigSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);

        Map<String, ITMaterial> materials = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getRegistryMapFromList(((MaterialRegistry)ICJsonConfigs.material.getFirst()).getAllValues());
        Map<String, ITMaterial> worldMaterials = new HashMap<>();

        Path materialConfigs = Paths.get(singlePlayerConfigs + "/material");

        if(materialConfigs.toFile().listFiles() != null){
            if(materials.size() != materialConfigs.toFile().listFiles().length){
                sync.set(false);
                ConfigSync.syncedJson.put("material", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            ConfigSync.syncedJson.put("material", sync.get());
            return false;
        }

        if(materialConfigs.toFile().listFiles() != null){
            for(File file : materialConfigs.toFile().listFiles()){
                worldMaterials.put(((MaterialRegistry)ICJsonConfigs.material.getFirst()).getFromJsonObject(((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(file.getName())).name, ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getFromJsonObject(ICJsonConfigs.material.getFirst().getJsonObject(file.getName())));
            }

            materials.values().forEach(itMaterial -> {
                sync.set(worldMaterials.containsKey(itMaterial.name));

                if(sync.get()) {
                    ITMaterial material = materials.get(itMaterial.name);
                    JsonObject jsonMaterial = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        ConfigSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/material");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ITMaterial material : serverMap.values()) {
            Path materialFile = Paths.get(serverConfigFolder + "/" + material.name + ".json");
            JsonObject jsonObject = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getJsonObject(material);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/material");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/material");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = ICJsonConfigs.material.getFirst().getJsonObject(file.getName());
                    ITMaterial material = ((MaterialRegistry)ICJsonConfigs.material.getFirst()).getFromJsonObject(jsonObject);

                    Path materialFile = Paths.get(singlePlayerSaveConfigFolder + "/" + material.name + ".json");
                    if (!Files.exists(materialFile)) {
                        Files.createDirectories(materialFile.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                            String s = GSON.toJson(jsonObject);
                            bufferedwriter.write(s);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadFromServer(SyncMessage message) {
        List<ITMaterial> list = message.getConfig(ICJsonConfigs.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast).toList();

        Map<String, ITMaterial> materials = list.stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(materials);

        IndustrialTech.LOGGER.info("Loaded {} materials from the server", materials.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ITMaterial material) {
        buffer.writeUtf(material.name);
        buffer.writeInt(material.color);
        String textureIconString = material.textureIcon.getFirst() + ":" + material.textureIcon.getSecond().getName();

        buffer.writeUtf(textureIconString);
        buffer.writeUtf(Objects.requireNonNullElse(material.symbol, ""));
        buffer.writeInt(Objects.requireNonNullElse(material.meltingPoint, -1));
        buffer.writeInt(Objects.requireNonNullElse(material.boilingPoint, -1));
        buffer.writeBoolean(Objects.requireNonNullElse(material.isStoneLayer, false));
        buffer.writeUtf(Objects.requireNonNullElse(material.stoneLayerBlock, ""));
        buffer.writeUtf(Objects.requireNonNullElse(material.stoneLayerTextureLocation, ""));
        buffer.writeInt(Objects.requireNonNullElse(material.harvestLevel, -1));

        if(material.oreType != null){
            buffer.writeUtf(material.oreType.getName());
        }else {
            buffer.writeUtf("");
        }

        buffer.writeInt(Objects.requireNonNullElse(material.denseOreDensity, -1));

        if(material.fluidState != null){
            buffer.writeUtf(material.fluidState.getName());
        }else {
            buffer.writeUtf("");
        }

        buffer.writeFloat(Objects.requireNonNullElse(material.fluidAcceleration, -1F));
        buffer.writeInt(Objects.requireNonNullElse(material.fluidDensity, -1));
        buffer.writeInt(Objects.requireNonNullElse(material.fluidLuminosity, -1));
        buffer.writeInt(Objects.requireNonNullElse(material.fluidViscosity, -1));
        buffer.writeInt(Objects.requireNonNullElse(material.attackSpeed, -1));
        buffer.writeInt(Objects.requireNonNullElse(material.durability, -1));
        buffer.writeFloat(Objects.requireNonNullElse(material.attackDamage, -1F));
        buffer.writeFloat(Objects.requireNonNullElse(material.weight, -1F));
        buffer.writeFloat(Objects.requireNonNullElse(material.efficiency, -1F));
        buffer.writeInt(Objects.requireNonNullElse(material.toolLevel, -1));

        if(material.toolParts != null && material.toolParts.size() > 0){
            buffer.writeInt(material.toolParts.size());
            for (Pair<String, String> toolPart : material.toolParts) {
                buffer.writeUtf(toolPart.getFirst());
                buffer.writeUtf(toolPart.getSecond());
            }
        } else {
            buffer.writeInt(0);
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ITMaterial> list = message.getConfig(ICJsonConfigs.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((material) -> singleToBuffer(buffer, material));
    }

    @Override
    public ITMaterial singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        int color = buffer.readInt();
        String textureIconString = buffer.readUtf();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconString.split(":")[0], ((TextureIconRegistry)ICJsonConfigs.textureIcon.getFirst()).getTextureIconByName(textureIconString.split(":")[1]));
        ITMaterial material = new ITMaterial(name, color, textureIcon);

        String symbol = buffer.readUtf();
        if(!symbol.equals("")){
            material.setElementalLocalization(symbol);
        }

        int meltingPoint = buffer.readInt();
        int boilingPoint = buffer.readInt();

        if(meltingPoint != -1 && boilingPoint != -1){
            material.setTemperatureProperties(meltingPoint, boilingPoint);
        }

        boolean isStoneLayer = buffer.readBoolean();
        String stoneLayerBlock = buffer.readUtf();
        String stoneLayerTextureLocation = buffer.readUtf();

        if(isStoneLayer && !stoneLayerTextureLocation.equals("")){
            material.setStoneLayerProperties(isStoneLayer, stoneLayerBlock, stoneLayerTextureLocation);
        }

        int miningLevel = buffer.readInt();

        if(miningLevel > -1){
            material.setBlockProperties(miningLevel);
        }

        String oreTypeString = buffer.readUtf();

        if(!oreTypeString.equals("")){
            IOreType oreType =  ((OreTypeRegistry)ICJsonConfigs.oreType.getFirst()).getByName(oreTypeString);
            material.setOreType(oreType);
        }

        int denseOreDensity = buffer.readInt();
        if(denseOreDensity != -1){
            material.setDenseOreDensity(denseOreDensity);
        }

        String fluidStateString = buffer.readUtf();
        float fluidAcceleration = buffer.readFloat();
        int fluidDensity = buffer.readInt();
        int fluidLuminosity = buffer.readInt();
        int fluidViscosity = buffer.readInt();

        if(!fluidStateString.equals("") && fluidAcceleration != -1 && fluidDensity != -1 && fluidLuminosity != -1 && fluidViscosity != -1){
            IFluidState fluidState = ((FluidStateRegistry)ICJsonConfigs.fluidState.getFirst()).getFluidStateByName(fluidStateString);
            material.setFluidProperties(fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity);
        }

        int attackSpeed = buffer.readInt();
        int durability = buffer.readInt();
        float attackDamage  = buffer.readFloat();
        float weight  = buffer.readFloat();
        float efficiency  = buffer.readFloat();
        int toolLevel = buffer.readInt();

        List<Pair<String, String>> toolParts = new ArrayList<>();
        int toolPartsSize = buffer.readInt();

        if(toolPartsSize > 0) {
            for (int i = 0; i < toolPartsSize; i++) {
                String toolPartPrefix = buffer.readUtf();
                String toolPartSuffix = buffer.readUtf();
                toolParts.add(new Pair<>(toolPartPrefix, toolPartSuffix));
            }

            material.setToolProperties(attackSpeed, durability, attackDamage, weight, efficiency, toolLevel, toolParts);
        }

        return material;
    }

    @Override
    public List<ITMaterial> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<ITMaterial> materials = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITMaterial material = singleFromBuffer(buffer);

            materials.add(material);
        }

        return materials;
    }
}
