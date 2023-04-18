package mightydanp.techcore.common.jsonconfig.definedstructure;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class DefinedStructureRegistry extends JsonConfigMultiFile<IDefinedStructure> {

    @Override
    public void initiate() {
        setJsonFolderName("defined_structure");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IDefinedStructure definedStructureIn) {
        String name = definedStructureIn.getName();
        if (registryMap.containsKey(definedStructureIn.getName()))
            throw new IllegalArgumentException("defined structure with name(" + name + "), already exists.");
        registryMap.put(name, definedStructureIn);
    }

    public IDefinedStructure getByName(String definedStructure) {
        return registryMap.get(definedStructure);
    }

    public void buildJson(){
        for(IDefinedStructure definedStructure : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(definedStructure.getName());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(definedStructure.getName(), toJsonObject(definedStructure));
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        IDefinedStructure definedStructure = fromJsonObject(jsonObject);

                        registryMap.put(definedStructure.getName(), definedStructure);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to defined structure list because a defined structure already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("defined structure json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public static UnboundedMapCodec<BlockPos, BlockState> codec = Codec.unboundedMap(BlockPos.CODEC.fieldOf("block_pos").codec(), BlockState.CODEC.fieldOf("block_state").codec());

    @Override
    public IDefinedStructure fromJsonObject(JsonObject jsonObjectIn){
        return new IDefinedStructure() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }

            @Override
            public Map<BlockPos, BlockState> getBlockMap() {
                return codec.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false, TechAscension.LOGGER::error).getFirst();
            }
        };

    }

    public JsonObject toJsonObject(IDefinedStructure definedStructure) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", definedStructure.getName());

        codec.encodeStart(JsonOps.INSTANCE, definedStructure.getBlockMap());

        return jsonObject;
    }
}