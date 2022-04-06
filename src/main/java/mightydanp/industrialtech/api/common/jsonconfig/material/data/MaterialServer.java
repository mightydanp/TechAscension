package mightydanp.industrialtech.api.common.jsonconfig.material.data;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
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

import static java.util.stream.Collectors.toList;

/**
 * Created by MightyDanp on 1/3/2022.
 */
public class MaterialServer extends JsonConfigServer<ITMaterial> {

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<ITMaterial> list = message.getConfig(IndustrialTech.configSync.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast)
                .collect(toList());
        
        if(list.size() != ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getAllValues().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("material", sync.get());
            return false;
        }

        ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getAllValues().forEach(itMaterial -> {
            sync.set(list.stream().anyMatch(o -> o.name.equals(itMaterial.name)));

            if(sync.get()) {
                Optional<ITMaterial> optional = list.stream().filter(o -> o.name.equals(itMaterial.name)).findFirst();

                if(optional.isPresent()) {
                    ITMaterial material = optional.get();
                    JsonObject jsonMaterial = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);

        Map<String, ITMaterial> materials = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getRegistryMapFromList(((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getAllValues());
        Map<String, ITMaterial> worldMaterials = new HashMap<>();

        Path materialConfigs = Paths.get(singlePlayerConfigs + "/material");

        if(materialConfigs.toFile().listFiles() != null){
            if(materials.size() != materialConfigs.toFile().listFiles().length){
                sync.set(false);
                IndustrialTech.configSync.syncedJson.put("material", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("material", sync.get());
            return false;
        }

        if(materialConfigs.toFile().listFiles() != null){
            for(File file : materialConfigs.toFile().listFiles()){
                worldMaterials.put(((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getFromJsonObject(((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(file.getName())).name, ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getFromJsonObject(IndustrialTech.configSync.material.getFirst().getJsonObject(file.getName())));
            }

            materials.values().forEach(itMaterial -> {
                sync.set(worldMaterials.containsKey(itMaterial.name));

                if(sync.get()) {
                    ITMaterial material = materials.get(itMaterial.name);
                    JsonObject jsonMaterial = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("material", sync.get());

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
            JsonObject jsonObject = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getJsonObject(material);
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
                    JsonObject jsonObject = IndustrialTech.configSync.material.getFirst().getJsonObject(file.getName());
                    ITMaterial material = ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getFromJsonObject(jsonObject);

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
        List<ITMaterial> list = message.getConfig(IndustrialTech.configSync.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast)
                .collect(toList());

        Map<String, ITMaterial> materials = list.stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(materials);

        IndustrialTech.LOGGER.info("Loaded {} materials from the server", materials.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, ITMaterial material) {//friendlybotbuff
        buffer.writeUtf(material.name);
        buffer.writeInt(material.color);
        String textureIconString = material.textureIcon.getFirst() + ":" + material.textureIcon.getSecond().getName();

        buffer.writeUtf(textureIconString);

        if(material.symbol != null){
            buffer.writeUtf(material.symbol);
        }else {
            buffer.writeUtf("");
        }

        if(material.meltingPoint != null){
            buffer.writeInt(material.meltingPoint);
        } else {
            buffer.writeInt(-1);
        }

        if(material.boilingPoint != null){
            buffer.writeInt(material.boilingPoint);
        } else {
            buffer.writeInt(-1);
        }

        if(material.isStoneLayer != null){
            buffer.writeBoolean(material.isStoneLayer);
        } else {
            buffer.writeBoolean(false);
        }

        if(material.stoneLayerBlock != null){
            buffer.writeUtf(material.stoneLayerBlock);
        } else {
            buffer.writeUtf("");
        }

        if(material.stoneLayerTextureLocation != null){
            buffer.writeUtf(material.stoneLayerTextureLocation);
        } else {
            buffer.writeUtf("");
        }

        if(material.miningLevel != null){
            buffer.writeInt(material.miningLevel);
        } else {
            buffer.writeInt(-1);
        }

        if(material.oreType != null){
            buffer.writeUtf(material.oreType.getName());
        }else {
            buffer.writeUtf("");
        }

        if(material.denseOreDensity != null){
            buffer.writeInt(material.denseOreDensity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidState != null){
            buffer.writeUtf(material.fluidState.getName());
        }else {
            buffer.writeUtf("");
        }

        if(material.fluidAcceleration != null){
            buffer.writeFloat(material.fluidAcceleration);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.fluidDensity != null){
            buffer.writeInt(material.fluidDensity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidLuminosity != null){
            buffer.writeInt(material.fluidLuminosity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidViscosity != null){
            buffer.writeInt(material.fluidViscosity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.attackSpeed != null){
            buffer.writeInt(material.attackSpeed);
        } else {
            buffer.writeInt(-1);
        }

        if(material.durability != null){
            buffer.writeInt(material.durability);
        } else {
            buffer.writeInt(-1);
        }

        if(material.attackDamage != null){
            buffer.writeFloat(material.attackDamage);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.weight != null){
            buffer.writeFloat(material.weight);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.toolTypes != null && material.toolTypes.size() > 0){
            buffer.writeInt(material.toolTypes.size());

            material.toolTypes.forEach(((iToolType, integer) -> {
                buffer.writeUtf(iToolType.getName());
                buffer.writeInt(integer);
            }));
        } else {
            buffer.writeInt(0);
        }

        if(material.toolParts != null && material.toolParts.size() > 0){
            buffer.writeInt(material.toolParts.size());
            for (IToolPart toolPart : material.toolParts) {
                buffer.writeUtf(fixesToName(toolPart.getPrefix(), toolPart.getSuffix()));
            }
        } else {
            buffer.writeInt(0);
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<ITMaterial> list = message.getConfig(IndustrialTech.configSync.materialID).stream()
                .filter(ITMaterial.class::isInstance)
                .map(ITMaterial.class::cast)
                .collect(toList());

        buffer.writeVarInt(list.size());

        list.forEach((material) -> singleToBuffer(buffer, material));
    }

    @Override
    public ITMaterial singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        int color = buffer.readInt();
        String textureIconString = buffer.readUtf();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconString.split(":")[0], ((TextureIconRegistry)IndustrialTech.configSync.textureIcon.getFirst()).getTextureIconByName(textureIconString.split(":")[1]));
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
            IOreType oreType =  ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).getByName(oreTypeString);
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
            IFluidState fluidState = ((FluidStateRegistry)IndustrialTech.configSync.fluidState.getFirst()).getFluidStateByName(fluidStateString);
            material.setFluidProperties(fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity);
        }

        int attackSpeed = buffer.readInt();
        int durability = buffer.readInt();
        float attackDamage  = buffer.readFloat();
        float weight  = buffer.readFloat();

        Map<IToolType, Integer> toolTypes = new HashMap<>();
        int toolTypesSize = buffer.readInt();

        if(toolTypesSize > 0) {
            for (int i = 0; i < toolTypesSize; i++) {
                String toolType = buffer.readUtf();
                Integer level = buffer.readInt();
                toolTypes.put((IToolType)IndustrialTech.configSync.toolType.getFirst().registryMap.get(toolType), level);
            }
        }

        List<IToolPart> toolParts = new ArrayList<>();
        int toolPartsSize = buffer.readInt();

        if(toolPartsSize > 0) {
            for (int i = 0; i < toolPartsSize; i++) {
                String toolPart = buffer.readUtf();
                toolParts.add( ((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).getByName(toolPart));
            }
        }

        if(attackSpeed != -1 && durability != -1 && attackDamage != -1 && weight != -1 && toolTypes.size() != 0 && toolPartsSize != 0){
            material.setToolProperties(attackSpeed, durability, attackDamage, weight, toolTypes, toolParts);
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
