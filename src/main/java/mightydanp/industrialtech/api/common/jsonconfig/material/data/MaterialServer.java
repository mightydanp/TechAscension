package mightydanp.industrialtech.api.common.jsonconfig.material.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ToolType;

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
public class MaterialServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, ITMaterial> serverMaterialList = new LinkedHashMap<>();
    public ConfigSync configSync = IndustrialTech.configSync;
    public MaterialRegistry materialRegistry = IndustrialTech.materialRegistryInstance;

    public static String fixesToName(Pair<String, String> fixes){
        String prefix = fixes.getFirst().replace("_", "");
        String suffix = fixes.getSecond().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getMaterials().size() != MaterialRegistry.getMaterials().size()){
            sync.set(false);
            configSync.syncedJson.put("material", sync.get());
            return false;
        }

        MaterialRegistry.getMaterials().forEach(itMaterial -> {
            sync.set(message.getMaterials().stream().anyMatch(o -> o.name.equals(itMaterial.name)));

            if(sync.get()) {
                Optional<ITMaterial> optional = message.getMaterials().stream().filter(o -> o.name.equals(itMaterial.name)).findFirst();

                if(optional.isPresent()) {
                    ITMaterial material = optional.get();
                    JsonObject jsonMaterial = MaterialRegistry.getJsonObject(itMaterial);
                    JsonObject materialJson = MaterialRegistry.getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);

        Map<String, ITMaterial> materials = MaterialRegistry.getMaterialsMap(MaterialRegistry.getMaterials());
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

                worldMaterials.put(MaterialRegistry.getMaterial(materialRegistry.getJsonObject(file.getName())).name, MaterialRegistry.getMaterial(materialRegistry.getJsonObject(file.getName())));
            }

            materials.values().forEach(itMaterial -> {
                sync.set(worldMaterials.containsKey(itMaterial.name));

                if(sync.get()) {
                    ITMaterial material = materials.get(itMaterial.name);
                    JsonObject jsonMaterial = MaterialRegistry.getJsonObject(itMaterial);
                    JsonObject materialJson = MaterialRegistry.getJsonObject(material);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("material", sync.get());

        return sync.get();
    }

    public void syncClientMaterialConfigsWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/material");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (ITMaterial material : serverMaterialList.values()) {
            Path materialFile = Paths.get(serverConfigFolder + "/" + material.name + ".json");
            JsonObject jsonObject = MaterialRegistry.getJsonObject(material);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientMaterialConfigsWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/material");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/material");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = materialRegistry.getJsonObject(file.getName());
                    ITMaterial material = MaterialRegistry.getMaterial(jsonObject);

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

    public boolean serverHasMaterials(){
        return serverMaterialList.size() > 0;
    }

    public void loadMaterials(SyncMessage message) {
        Map<String, ITMaterial> materials = message.getMaterials().stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMaterialList.clear();
        serverMaterialList.putAll(materials);

        IndustrialTech.LOGGER.info("Loaded {} materials from the server", materials.size());
    }

    public static Map<String, ITMaterial> getServerMaterialList(){
        return serverMaterialList;
    }

    public static void singleToBuffer(PacketBuffer buffer, ITMaterial material) {//friendlybotbuff
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

        if(material.oreType != null){
            buffer.writeUtf(material.oreType.getName());
        }else {
            buffer.writeUtf("");
        }

        if(material.isStoneLayer != null){
            buffer.writeBoolean(material.isStoneLayer);
        } else {
            buffer.writeBoolean(false);
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
            for (Pair<ToolType, Integer> toolType : material.toolTypes) {
                buffer.writeUtf(toolType.getFirst().getName());
                buffer.writeInt(toolType.getSecond());
            }
        } else {
            buffer.writeInt(0);
        }

        if(material.toolParts != null && material.toolParts.size() > 0){
            buffer.writeInt(material.toolParts.size());
            for (IToolPart toolPart : material.toolParts) {
                buffer.writeUtf(toolPart.getPrefix());
                buffer.writeUtf(toolPart.getSuffix());
            }
        } else {
            buffer.writeInt(0);
        }
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getMaterials().size());

        message.getMaterials().forEach((material) -> {

            singleToBuffer(buffer, material);
        });
    }

    public static ITMaterial singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        int color = buffer.readInt();
        String textureIconString = buffer.readUtf();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconString.split(":")[0], IndustrialTech.textureIconRegistry.getTextureIconByName(textureIconString.split(":")[1]));
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
        String stoneLayerTextureLocation = buffer.readUtf();

        if(isStoneLayer && !stoneLayerTextureLocation.equals("")){
            material.setStoneLayerProperties(isStoneLayer, stoneLayerTextureLocation);
        }

        int miningLevel = buffer.readInt();

        if(miningLevel > -1){
            material.setBlockProperties(miningLevel);
        }

        String oreTypeString = buffer.readUtf();

        if(!oreTypeString.equals("")){
            IOreType oreType = OreTypeRegistry.getOreTypeByName(oreTypeString);
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
            IFluidState fluidState = IndustrialTech.fluidStateRegistry.getFluidStateByName(fluidStateString);
            material.setFluidProperties(fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity);
        }

        int attackSpeed = buffer.readInt();
        int durability = buffer.readInt();
        float attackDamage  = buffer.readFloat();
        float weight  = buffer.readFloat();

        List<Pair<ToolType, Integer>> toolTypes = new ArrayList<>();
        int toolTypesSize = buffer.readInt();

        if(toolTypesSize > 0) {
            for (int i = 0; i < toolTypesSize; i++) {
                String toolType = buffer.readUtf();
                Integer level = buffer.readInt();
                toolTypes.add(new Pair<>(ToolType.get(toolType), level));
            }
        }

        List<IToolPart> toolParts = new ArrayList<>();
        int toolPartsSize = buffer.readInt();

        if(toolPartsSize > 0) {
            for (int i = 0; i < toolPartsSize; i++) {
                String prefix = buffer.readUtf();
                String suffix = buffer.readUtf();
                toolParts.add(ToolPartRegistry.getToolPartByName(fixesToName(new Pair<>(prefix, suffix))));
            }
        }

        if(attackSpeed != -1 && durability != -1 && attackDamage != -1 && weight != -1 && toolTypes.size() != 0 && toolPartsSize != 0){
            material.setToolProperties(attackSpeed, durability, attackDamage, weight, toolTypes, toolParts);
        }

        return material;
    }

    public static List<ITMaterial> multipleFromBuffer(PacketBuffer buffer) {
        List<ITMaterial> materials = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITMaterial material = singleFromBuffer(buffer);

            materials.add(material);
        }

        return materials;
    }
}
