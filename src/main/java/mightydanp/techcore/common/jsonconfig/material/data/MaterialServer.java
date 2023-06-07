package mightydanp.techcore.common.jsonconfig.material.data;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateCodec;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.techcore.common.jsonconfig.material.ore.IOreType;
import mightydanp.techcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.TCMaterial;
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
public class MaterialServer extends JsonConfigServer<TCMaterial> {

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<TCMaterial> list = message.getConfig(TCJsonConfigs.materialID).stream()
                .filter(TCMaterial.class::isInstance)
                .map(TCMaterial.class::cast).toList();
        
        if(list.size() != ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("material", sync.get());
                return false;
            }
        }

        ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().forEach(itMaterial -> {
            sync.set(list.stream().anyMatch(o -> o.name.equals(itMaterial.name)));

            if(sync.get()) {
                Optional<TCMaterial> optional = list.stream().filter(o -> o.name.equals(itMaterial.name)).findFirst();

                if(optional.isPresent()) {
                    TCMaterial material = optional.get();
                    JsonObject jsonMaterial = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).toJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).toJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        ConfigSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);

        Map<String, TCMaterial> materials = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getRegistryMapFromList(((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues());
        Map<String, TCMaterial> worldMaterials = new HashMap<>();

        Path materialConfigs = Paths.get(singlePlayerConfigs + "/material");
        File[] files = materialConfigs.toFile().listFiles();

        if(files != null){
            if(materials.size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("material", sync.get());
                return false;
            }

            for(File file : files){
                worldMaterials.put(((MaterialRegistry) TCJsonConfigs.material.getFirst()).fromJsonObject(((MaterialRegistry) TCJsonConfigs.material.getFirst()).getJsonObject(file.getName())).name, ((MaterialRegistry) TCJsonConfigs.material.getFirst()).fromJsonObject(TCJsonConfigs.material.getFirst().getJsonObject(file.getName())));
            }

            materials.values().forEach(itMaterial -> {
                sync.set(worldMaterials.containsKey(itMaterial.name));

                if(sync.get()) {
                    TCMaterial material = materials.get(itMaterial.name);
                    JsonObject jsonMaterial = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).toJsonObject(itMaterial);
                    JsonObject materialJson = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).toJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("material", sync.get());
                return false;
            }
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

        for (TCMaterial material : serverMap.values()) {
            Path materialFile = Paths.get(serverConfigFolder + "/" + material.name + ".json");
            JsonObject jsonObject = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).toJsonObject(material);
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
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/material");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.material.getFirst().getJsonObject(file.getName());
                    TCMaterial material = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).fromJsonObject(jsonObject);

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
        List<TCMaterial> list = message.getConfig(TCJsonConfigs.materialID).stream()
                .filter(TCMaterial.class::isInstance)
                .map(TCMaterial.class::cast).toList();

        Map<String, TCMaterial> materials = list.stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(materials);

        TechAscension.LOGGER.info("Loaded {} materials from the server", materials.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, TCMaterial material) {
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
            buffer.writeUtf(material.fluidState.name());
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

        if(material.toolPartWhiteList != null && material.toolPartWhiteList.size() > 0){
            buffer.writeInt(material.toolPartWhiteList.size());
            for (Pair<String, String> toolPart : material.toolPartWhiteList) {
                buffer.writeUtf(toolPart.getFirst());
                buffer.writeUtf(toolPart.getSecond());
            }
        } else {
            buffer.writeInt(0);
        }

        if(material.toolPartBlackList != null && material.toolPartBlackList.size() > 0){
            buffer.writeInt(material.toolPartBlackList.size());
            for (Pair<String, String> toolPart : material.toolPartBlackList) {
                buffer.writeUtf(toolPart.getFirst());
                buffer.writeUtf(toolPart.getSecond());
            }
        } else {
            buffer.writeInt(0);
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<TCMaterial> list = message.getConfig(TCJsonConfigs.materialID).stream()
                .filter(TCMaterial.class::isInstance)
                .map(TCMaterial.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((material) -> singleToBuffer(buffer, material));
    }

    @Override
    public TCMaterial singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();
        int color = buffer.readInt();
        String textureIconString = buffer.readUtf();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconString.split(":")[0], ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).getTextureIconByName(textureIconString.split(":")[1]));
        TCMaterial material = new TCMaterial(name, color, textureIcon);

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
            IOreType oreType =  ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).getByName(oreTypeString);
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
            FluidStateCodec fluidState = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).getByName(fluidStateString);
            material.setFluidProperties(fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity);
        }

        int attackSpeed = buffer.readInt();
        int durability = buffer.readInt();
        float attackDamage  = buffer.readFloat();
        float weight  = buffer.readFloat();
        float efficiency  = buffer.readFloat();
        int toolLevel = buffer.readInt();

        List<Pair<String, String>> toolPartWhiteList = new ArrayList<>();
        int toolPartWhiteListSize = buffer.readInt();

        if(toolPartWhiteListSize > 0) {
            for (int i = 0; i < toolPartWhiteListSize; i++) {
                String toolPartPrefix = buffer.readUtf();
                String toolPartSuffix = buffer.readUtf();
                toolPartWhiteList.add(new Pair<>(toolPartPrefix, toolPartSuffix));
            }
        }

        List<Pair<String, String>> toolPartBlackList = new ArrayList<>();
        int toolPartBlackListSize = buffer.readInt();

        if(toolPartBlackListSize > 0) {
            for (int i = 0; i < toolPartBlackListSize; i++) {
                String toolPartPrefix = buffer.readUtf();
                String toolPartSuffix = buffer.readUtf();
                toolPartWhiteList.add(new Pair<>(toolPartPrefix, toolPartSuffix));
            }
        }

        if(attackSpeed != -1 && durability != -1 && attackDamage  != -1 && weight != -1 && efficiency  != -1 && toolLevel != -1) {
            material.setToolProperties(attackSpeed, durability, attackDamage, weight, efficiency, toolLevel, toolPartWhiteList, toolPartBlackList);
        }

        return material;
    }

    @Override
    public List<TCMaterial> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<TCMaterial> materials = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            TCMaterial material = singleFromBuffer(buffer);

            materials.add(material);
        }

        return materials;
    }
}
