package mightydanp.techapi.common.resources.asset.data;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Map;

public class ModelData {
    public String modelName;

    public static final String BLOCK_FOLDER = "block";
    public static final String ITEM_FOLDER = "item";
    private TAModelBuilder model;

    private final String modelFolder;
    private final String parentFolder;

    public ModelData(String modelName, String modelFolder, String parentFolder) {
        this.modelName = modelName;
        this.modelFolder = modelFolder;
        this.parentFolder = parentFolder;
        this.model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "models/" + modelFolder + "/" + (parentFolder == null ? "" : parentFolder + "/")  + modelName + ".json"));
    }

    public TAModelBuilder getModel() {
        return model;
    }

    public ModelData overrideModel(TAModelBuilder model) {
        this.model = model;
        return this;
    }

    public String getModelFolder() {
        return modelFolder;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public JsonObject createJson() {
        return model.toJson();
    }

    public ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }

    private ResourceLocation extendWithFolder(ResourceLocation rl) {
        if (rl.getPath().contains("/")) {
            return rl;
        }
        return new ResourceLocation(rl.getNamespace(), modelFolder + "/" + rl.getPath());
    }

    public ModelFile.UncheckedModelFile getFile(ResourceLocation path) {
        ModelFile.UncheckedModelFile ret = new ModelFile.UncheckedModelFile(extendWithFolder(path));
        ret.assertExistence();
        return ret;
    }


    public TAModelBuilder withExistingParent(String parent) {
        return withExistingParent(mcLoc(parent));
    }

    public TAModelBuilder withExistingParent(ResourceLocation parent) {
        return model.parent(getFile(parent));
    }

    public TAModelBuilder cube(ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
        return withExistingParent("cube")
                .texture("down", down)
                .texture("up", up)
                .texture("north", north)
                .texture("south", south)
                .texture("east", east)
                .texture("west", west);
    }

    private TAModelBuilder singleTexture(String parent, ResourceLocation texture) {
        return singleTexture(mcLoc(parent), texture);
    }

    public TAModelBuilder singleTexture(ResourceLocation parent, ResourceLocation texture) {
        return singleTexture(parent, "texture", texture);
    }

    private TAModelBuilder singleTexture(String parent, String textureKey, ResourceLocation texture) {
        return singleTexture(mcLoc(parent), textureKey, texture);
    }

    public TAModelBuilder singleTexture(ResourceLocation parent, String textureKey, ResourceLocation texture) {
        return withExistingParent(parent)
                .texture(textureKey, texture);
    }

    public TAModelBuilder cubeAll(ResourceLocation texture) {
        return singleTexture(modelFolder + "/cube_all", "all", texture);
    }

    public TAModelBuilder cubeTop(ResourceLocation side, ResourceLocation top) {
        return withExistingParent(modelFolder + "/cube_top")
                .texture("side", side)
                .texture("top", top);
    }

    private TAModelBuilder sideBottomTop(String parent, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return withExistingParent(parent)
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    public TAModelBuilder cubeBottomTop(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/cube_bottom_top", side, bottom, top);
    }

    public TAModelBuilder cubeColumn(ResourceLocation side, ResourceLocation end) {
        return withExistingParent(modelFolder + "/cube_column")
                .texture("side", side)
                .texture("end", end);
    }

    public TAModelBuilder cubeColumnHorizontal(ResourceLocation side, ResourceLocation end) {
        return withExistingParent(modelFolder + "/cube_column_horizontal")
                .texture("side", side)
                .texture("end", end);
    }

    public TAModelBuilder orientableVertical(ResourceLocation side, ResourceLocation front) {
        return withExistingParent(modelFolder + "/orientable_vertical")
                .texture("side", side)
                .texture("front", front);
    }

    public TAModelBuilder orientableWithBottom(ResourceLocation side, ResourceLocation front, ResourceLocation bottom, ResourceLocation top) {
        return withExistingParent(modelFolder + "/orientable_with_bottom")
                .texture("side", side)
                .texture("front", front)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    public TAModelBuilder orientable(ResourceLocation side, ResourceLocation front, ResourceLocation top) {
        return withExistingParent(modelFolder + "/orientable")
                .texture("side", side)
                .texture("front", front)
                .texture("top", top);
    }

    public TAModelBuilder crop(ResourceLocation crop) {
        return singleTexture(modelFolder + "/crop", "crop", crop);
    }

    public TAModelBuilder cross(ResourceLocation cross) {
        return singleTexture(modelFolder + "/cross", "cross", cross);
    }

    public TAModelBuilder stairs(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/stairs", side, bottom, top);
    }

    public TAModelBuilder stairsOuter(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/outer_stairs", side, bottom, top);
    }

    public TAModelBuilder stairsInner(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/inner_stairs", side, bottom, top);
    }

    public TAModelBuilder slab(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/slab", side, bottom, top);
    }

    public TAModelBuilder slabTop(ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return sideBottomTop(modelFolder + "/slab_top", side, bottom, top);
    }

    public TAModelBuilder button(ResourceLocation texture) {
        return singleTexture(modelFolder + "/button", texture);
    }

    public TAModelBuilder buttonPressed(ResourceLocation texture) {
        return singleTexture(modelFolder + "/button_pressed", texture);
    }

    public TAModelBuilder buttonInventory(ResourceLocation texture) {
        return singleTexture(modelFolder + "/button_inventory", texture);
    }

    public TAModelBuilder pressurePlate(ResourceLocation texture) {
        return singleTexture(modelFolder + "/pressure_plate_up", texture);
    }

    public TAModelBuilder pressurePlateDown(ResourceLocation texture) {
        return singleTexture(modelFolder + "/pressure_plate_down", texture);
    }

    public TAModelBuilder sign(ResourceLocation texture) {
        return model.texture("particle", texture);
    }

    public TAModelBuilder fencePost(ResourceLocation texture) {
        return singleTexture(modelFolder + "/fence_post", texture);
    }

    public TAModelBuilder fenceSide(ResourceLocation texture) {
        return singleTexture(modelFolder + "/fence_side", texture);
    }

    public TAModelBuilder fenceInventory(ResourceLocation texture) {
        return singleTexture(modelFolder + "/fence_inventory", texture);
    }

    public TAModelBuilder fenceGate(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_fence_gate", texture);
    }

    public TAModelBuilder fenceGateOpen(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_fence_gate_open", texture);
    }

    public TAModelBuilder fenceGateWall(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_fence_gate_wall", texture);
    }

    public TAModelBuilder fenceGateWallOpen(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_fence_gate_wall_open", texture);
    }

    public TAModelBuilder wallPost(ResourceLocation wall) {
        return singleTexture(modelFolder + "/template_wall_post", "wall", wall);
    }

    public TAModelBuilder wallSide(ResourceLocation wall) {
        return singleTexture(modelFolder + "/template_wall_side", "wall", wall);
    }

    public TAModelBuilder wallSideTall(ResourceLocation wall) {
        return singleTexture(modelFolder + "/template_wall_side_tall", "wall", wall);
    }

    public TAModelBuilder wallInventory(ResourceLocation wall) {
        return singleTexture(modelFolder + "/wall_inventory", "wall", wall);
    }

    private TAModelBuilder pane(String parent, ResourceLocation pane, ResourceLocation edge) {
        return withExistingParent(modelFolder + "/" + parent)
                .texture("pane", pane)
                .texture("edge", edge);
    }

    public TAModelBuilder panePost(ResourceLocation pane, ResourceLocation edge) {
        return pane("template_glass_pane_post", pane, edge);
    }

    public TAModelBuilder paneSide(ResourceLocation pane, ResourceLocation edge) {
        return pane("template_glass_pane_side", pane, edge);
    }

    public TAModelBuilder paneSideAlt(ResourceLocation pane, ResourceLocation edge) {
        return pane("template_glass_pane_side_alt", pane, edge);
    }

    public TAModelBuilder paneNoSide(ResourceLocation pane) {
        return singleTexture(modelFolder + "/template_glass_pane_noside", "pane", pane);
    }

    public TAModelBuilder paneNoSideAlt(ResourceLocation pane) {
        return singleTexture(modelFolder + "/template_glass_pane_noside_alt", "pane", pane);
    }

    private TAModelBuilder door(String model, ResourceLocation bottom, ResourceLocation top) {
        return withExistingParent(modelFolder + "/" + model)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    public TAModelBuilder doorBottomLeft(ResourceLocation bottom, ResourceLocation top) {
        return door("door_bottom", bottom, top);
    }

    public TAModelBuilder doorBottomRight(ResourceLocation bottom, ResourceLocation top) {
        return door("door_bottom_rh", bottom, top);
    }

    public TAModelBuilder doorTopLeft(ResourceLocation bottom, ResourceLocation top) {
        return door("door_top", bottom, top);
    }

    public TAModelBuilder doorTopRight(ResourceLocation bottom, ResourceLocation top) {
        return door("door_top_rh", bottom, top);
    }

    public TAModelBuilder trapdoorBottom(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_trapdoor_bottom", texture);
    }

    public TAModelBuilder trapdoorTop(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_trapdoor_top", texture);
    }

    public TAModelBuilder trapdoorOpen(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_trapdoor_open", texture);
    }

    public TAModelBuilder trapdoorOrientableBottom(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_orientable_trapdoor_bottom", texture);
    }

    public TAModelBuilder trapdoorOrientableTop(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_orientable_trapdoor_top", texture);
    }

    public TAModelBuilder trapdoorOrientableOpen(ResourceLocation texture) {
        return singleTexture(modelFolder + "/template_orientable_trapdoor_open", texture);
    }

    public TAModelBuilder torch(ResourceLocation torch) {
        return singleTexture(modelFolder + "/template_torch", "torch", torch);
    }

    public TAModelBuilder torchWall(ResourceLocation torch) {
        return singleTexture(modelFolder + "/template_torch_wall", "torch", torch);
    }

    public TAModelBuilder carpet(ResourceLocation wool) {
        return singleTexture(modelFolder + "/carpet", "wool", wool);

    }

    public TAModelBuilder singleTextureMap(ResourceLocation parent, Map<String, String> map) {
        TAModelBuilder model = withExistingParent(parent);

        map.forEach(model::texture);

        return model;
    }

    public TAModelBuilder resourceTextureMap(ResourceLocation parent, Map<String, ResourceLocation> map) {
        TAModelBuilder model = withExistingParent(parent);

        map.forEach(model::texture);

        return model;
    }

    public TAModelBuilder tintCube(String modelName, String parentFolder, int numberOfTints){
        TAModelBuilder model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "models/" + ModelData.BLOCK_FOLDER + "/" + (parentFolder == null ? "" : parentFolder + "/")  + modelName + ".json"));
        model.setParent(TAModelBuilder.ExistingBlockModels.block.model);

        for(int i = 0; i < numberOfTints; i ++) {
            model.element()
                    .from(0F, 0F, 0F)
                    .to(16F, 16F, 16F)
                    .face(Direction.DOWN).texture("#down_" + i).cullface(Direction.DOWN).tintindex(i).end()
                    .face(Direction.UP).texture("#up_" + i).cullface(Direction.UP).tintindex(i).end()
                    .face(Direction.NORTH).texture("#north_" + i).cullface(Direction.NORTH).tintindex(i).end()
                    .face(Direction.SOUTH).texture("#south_" + i).cullface(Direction.SOUTH).tintindex(i).end()
                    .face(Direction.WEST).texture("#west_" + i).cullface(Direction.WEST).tintindex(i).end()
                    .face(Direction.EAST).texture("#east_" + i).cullface(Direction.EAST).tintindex(i).end();
        }
        return model;
    }

    public ModelData taLogTintCube(ModelData model, int numberOfTints, boolean Horizontal){
        model.getModel().setParent(TAModelBuilder.ExistingBlockModels.block.model);

        for(int i = 0; i < numberOfTints; i ++) {
            model.getModel().element()
                    .from(0F, 0F, 0F)
                    .to(16F, 16F, 16F)
                    .face(Direction.DOWN).texture("#down_" + i).cullface(Direction.DOWN).tintindex(i).end()
                    .face(Direction.UP).texture("#up_" + i).cullface(Direction.UP).rotation(Horizontal ? TAModelBuilder.FaceRotation.UPSIDE_DOWN : TAModelBuilder.FaceRotation.ZERO).tintindex(i).end()
                    .face(Direction.NORTH).texture("#north").cullface(Direction.NORTH).tintindex(0).end()
                    .face(Direction.SOUTH).texture("#south").cullface(Direction.SOUTH).tintindex(0).end()
                    .face(Direction.WEST).texture("#west").cullface(Direction.WEST).tintindex(0).end()
                    .face(Direction.EAST).texture("#east").cullface(Direction.EAST).tintindex(0).end();
        }

        return model;
    }

    public ModelData taLogCubeColumn(String modelName, String parentFolder, boolean Horizontal){
        ModelData taCubeLogColumn = new ModelData(modelName, BLOCK_FOLDER, parentFolder);

        String logTintCubeName = modelName + "_cube";

        ModelData tintLogCube = taLogTintCube(new ModelData(logTintCubeName, BLOCK_FOLDER, "tree_icons/"), 2, Horizontal);

        AssetPackRegistry.blockModelDataMap.put(logTintCubeName, tintLogCube);

        return taCubeLogColumn;

    }

    public ModelData taLog(ModelData modelData, boolean Horizontal, ResourceLocation side, ResourceLocation end_0, ResourceLocation end_1){
        String tintLogCubeName = modelName + "_cube_column" + (Horizontal ? "_horizontal" : "");

        ModelData taCubeLogColumn = taLogCubeColumn(tintLogCubeName, "tree_icons/", Horizontal);

        AssetPackRegistry.blockModelDataMap.put(tintLogCubeName, taCubeLogColumn);

        modelData.resourceTextureMap(taCubeLogColumn.model.getUncheckedLocation(), Map.of(
                "side", side,
                "#end_0", end_0,
                "#end_1", end_1
        ));

        return modelData;

    }

    public static ModelData taSaplingCross(ModelData modelData){
        TAModelBuilder model =  modelData.model;

        model.ambientOcclusion(false);
        //model.texture("particle", )
        model.element()
                .from(0.8F, 0F, 8F)
                .to(15.2F, 16F, 8F)
                .shade(false)
                .face(Direction.NORTH).uvs( 0, 0, 16, 16).texture("#overlay_0").tintindex(0).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#overlay_0").tintindex(0).end()
                .rotation().origin(8, 8, 8)
                .axis(Direction.Axis.Y).angle(45F).rescale(true);
        model.element()
                .from(0.8F, 0F, 8F)
                .to(15.2F, 16F, 8F)
                .shade(false)
                .face(Direction.NORTH).uvs( 0, 0, 16, 16).texture("#overlay_1").tintindex(1).end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(1).end()
                .rotation().origin(8, 8, 8)
                .axis(Direction.Axis.Y).angle(45F).rescale(true);
        model.element()
                .from(8F, 0F, 0.8F)
                .to(8F, 16F, 15.2F)
                .shade(false)
                .face(Direction.WEST).uvs( 0, 0, 16, 16).texture("#overlay_0").tintindex(0).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#overlay_0").tintindex(0).end()
                .rotation().origin(8, 8, 8)
                .axis(Direction.Axis.Y).angle(45F).rescale(true);
        model.element()
                .from(8F, 0F, 0.8F)
                .to(8F, 16F, 15.2F)
                .shade(false)
                .face(Direction.WEST).uvs( 0, 0, 16, 16).texture("#overlay_1").tintindex(1).end()
                .face(Direction.EAST).uvs(0, 0, 16, 16).texture("#overlay_1").tintindex(1).end()
                .rotation().origin(8, 8, 8)
                .axis(Direction.Axis.Y).angle(45F).rescale(true);

        return modelData.overrideModel(model);
    }

    public static ModelData tintSlab(ModelData modelData){
        TAModelBuilder model =  modelData.model;

        model.texture("particle", "#side");

        model.element()
                .from(0F, 0F, 0F)
                .to(16F, 8F, 16F)
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").tintindex(0).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#toptop").cullface(Direction.UP).tintindex(0).end()
                .face(Direction.NORTH).uvs(0, 8, 16, 16).texture("#side").cullface(Direction.NORTH).tintindex(0).end()
                .face(Direction.SOUTH).uvs(0, 8, 16, 16).texture("#side").cullface(Direction.SOUTH).tintindex(0).end()
                .face(Direction.WEST).uvs(0, 8, 16, 16).texture("#side").cullface(Direction.WEST).tintindex(0).end()
                .face(Direction.EAST).uvs(0, 8, 16, 16).texture("#side").cullface(Direction.EAST).tintindex(0).end();

        return modelData.overrideModel(model);
    }

    public static ModelData getTintSlab(){
        String slabModelName = "tint_slab";
        ModelData slabModelData = new ModelData("tint_slab", BLOCK_FOLDER, "tree_icons/");
        taTopTintSlab(slabModelData);

        AssetPackRegistry.saveBlockModelDataMap(slabModelName, slabModelData, true);

        return slabModelData;
    }

    public TAModelBuilder taTintSlab(ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
        return resourceTextureMap(getTintSlab().getModel().getUncheckedLocation(), Map.of("bottom", bottom, "top", top, "side", side));
    }


    public static ModelData taTopTintSlab(ModelData modelData){
        TAModelBuilder model =  modelData.model;

        model.texture("particle", "#side");

        model.element()
                .from(0F, 8F, 0F)
                .to(16F, 16F, 16F)
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").tintindex(0).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#toptop").cullface(Direction.UP).tintindex(0).end()
                .face(Direction.NORTH).uvs(0, 0, 16,  8).texture("#side").cullface(Direction.NORTH).tintindex(0).end()
                .face(Direction.SOUTH).uvs(0, 0, 16,  8).texture("#side").cullface(Direction.SOUTH).tintindex(0).end()
                .face(Direction.WEST).uvs(0, 0, 16,  8).texture("#side").cullface(Direction.WEST).tintindex(0).end()
                .face(Direction.EAST).uvs(0, 0, 16,  8).texture("#side").cullface(Direction.EAST).tintindex(0).end();

        return modelData.overrideModel(model);
    }

    public static ModelData getTopTintSlab(){
        String slabModelName = "top_tint_slab";
        ModelData slabModelData = new ModelData("tint_slab", BLOCK_FOLDER, "tree_icons/");
        taTopTintSlab(slabModelData);

        AssetPackRegistry.saveBlockModelDataMap(slabModelName, slabModelData, true);

        return slabModelData;
    }

    public TAModelBuilder taTopTintSlab(ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
        return resourceTextureMap(getTintSlab().getModel().getUncheckedLocation(), Map.of("bottom", bottom, "top", top, "side", side));
    }

    public TAModelBuilder taSaplingCross(ResourceLocation overlay_0, ResourceLocation overlay_1) {
        return resourceTextureMap(getTASaplingCross().getModel().getUncheckedLocation(), Map.of("overlay_0", overlay_0, "overlay_1", overlay_1));

    }
    //to-do
    //save somehow before resources starts to save.
    public ModelData getTASaplingCross(){
        String modelName = "ta_" + "sapling" + "_cross";
        String parentFolder = "tree_icons/";

        ModelData modelData = ModelData.taSaplingCross(new ModelData(modelName, BLOCK_FOLDER, parentFolder));

        AssetPackRegistry.saveBlockModelDataMap("ta_" + "sapling" + "_cross", modelData, true);

        return modelData;
    }
}