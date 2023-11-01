package mightydanp.techcore.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techascension.common.blocks.ModBlocks;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateCodec;
import mightydanp.techcore.common.jsonconfig.tool.ToolCodec;
import mightydanp.techcore.common.world.gen.feature.BlocksInWaterGenFeatureCodec;
import net.minecraft.CrashReport;
import net.minecraft.world.level.Level;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlocksInWaterRegistry extends JsonConfigMultiFile<BlocksInWaterGenFeatureCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(BlocksInWaterGenFeatureCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        super.initiate();
    }
////////////////////////////////////////////////////////////////////////////find way to build jsons that exist instead of overriding pre existing jsons with new one.///////////////////////////////////
    @Override
    public List<BlocksInWaterGenFeatureCodec> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    @Override
    public void register(BlocksInWaterGenFeatureCodec codec) {
        if (registryMap.containsKey(codec.name())) {
            throw new IllegalArgumentException(BlocksInWaterGenFeatureCodec.codecName + " with name (" + codec.name() + "), already exists.");
        } else {
            registryMap.put(codec.name(), codec);
            PlantGenerationHandler.addRegistryBlockInWaterGenerate(codec);
        }
    }


    public void buildJson() {
        for (BlocksInWaterGenFeatureCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
            }
        }
    }

    public void buildAndRegister(BlocksInWaterGenFeatureCodec codec){
        if (!registryMap.containsKey(codec.name())) {
            this.register(codec);
            this.saveJsonObject(codec.name(), toJsonObject(codec));
        }
    }

    @Override
    public void loadExistingJsons() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        BlocksInWaterGenFeatureCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + BlocksInWaterGenFeatureCodec.codecName + " list because it already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(BlocksInWaterGenFeatureCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public BlocksInWaterGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return BlocksInWaterGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + BlocksInWaterGenFeatureCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(BlocksInWaterGenFeatureCodec codec) {
        return BlocksInWaterGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + BlocksInWaterGenFeatureCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}