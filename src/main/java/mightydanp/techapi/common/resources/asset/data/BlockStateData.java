package mightydanp.techapi.common.resources.asset.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class BlockStateData {
    private IGeneratedBlockstate blockState;

    public VariantBlockStateBuilder getVariantBuilder(Block b) throws Exception {
        Constructor<VariantBlockStateBuilder> constructor = VariantBlockStateBuilder.class.getDeclaredConstructor(Block.class);
        constructor.setAccessible(true);

        return constructor.newInstance(b);
    }

    public MultiPartBlockStateBuilder getMultipartBuilder(Block b) throws Exception {
        Constructor<MultiPartBlockStateBuilder> constructor = MultiPartBlockStateBuilder.class.getDeclaredConstructor(Block.class);
        constructor.setAccessible(true);

        return constructor.newInstance(b);
    }

    public BlockStateData setBlockState(IGeneratedBlockstate generatedBlockState){
        blockState = generatedBlockState;
        return this;
    }

    public JsonObject createJson(){
        return blockState.toJson();
    }

    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = block.getRegistryName();

        if(name != null) {
            return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
        } else {
            return null;
        }
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    public TAModelBuilder cubeAll(Block block) {
        String blockName = name(block);

        ModelData modelData = new ModelData(blockName, ModelData.BLOCK_FOLDER, "");
        modelData.cubeAll(blockTexture(block));

        return modelData.getModel();
    }

    //to-do simpleBlock

    public void simpleBlock(Block block) throws Exception {
        simpleBlock(block, cubeAll(block));
    }

    /*
    public void simpleBlock(Block block, Function<ModelFile, ConfiguredModel[]> expander) throws Exception {
        simpleBlock(block, expander.apply(cubeAll(block)));
    }
     */

    public void simpleBlock(Block block, TAModelBuilder model) throws Exception {
        simpleBlockWithItem(block, model);
        simpleBlock(block, new ConfiguredModel(model));
    }

    public void simpleBlock(Block block, ConfiguredModel... models) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block).partialState().setModels(models);

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void simpleBlockWithItem(Block block, TAModelBuilder model) {
        if(block.getRegistryName() != null) {
            String blockName = block.getRegistryName().getPath();

            ModelData blockModelData = new ModelData(blockName, ModelData.BLOCK_FOLDER, "").overrideModel(model);
            AssetPackRegistry.blockModelDataMap.put(blockName, blockModelData);

            ModelData itemModelData = new ModelData(blockName, ModelData.ITEM_FOLDER, "");
            itemModelData.overrideModel(itemModelData.getModel().parent(blockModelData.getModel()));

            AssetPackRegistry.itemModelDataHashMap.put(blockName, itemModelData);
        }
    }

    public void simpleBlockItem(Block block, ModelData model) {
        if(block.getRegistryName() != null) {
            String blockName = block.getRegistryName().getPath();

            ModelData itemModelData = new ModelData(blockName, ModelData.ITEM_FOLDER, "");
            itemModelData.overrideModel(itemModelData.getModel().parent(model.getModel()));

            AssetPackRegistry.itemModelDataHashMap.put(blockName, itemModelData);
        }
    }

    public void axisBlock(RotatedPillarBlock block) throws Exception {
        axisBlock(block, blockTexture(block));
    }

    public void logBlock(RotatedPillarBlock block) throws Exception {
        axisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"));
    }

    public void axisBlock(RotatedPillarBlock block, ResourceLocation baseName) throws Exception {
        axisBlock(block, extend(baseName, "_side"), extend(baseName, "_end"));
    }

    public void axisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) throws Exception {
        String blockName = name(block);
        ModelData modelData = new ModelData(blockName, ModelData.BLOCK_FOLDER, "");

        simpleBlockItem(block, modelData);

        axisBlock(block, modelData.cubeColumn(side, end), modelData.cubeColumnHorizontal(side, end));
    }

    public void axisBlock(RotatedPillarBlock block, ModelFile vertical, ModelFile horizontal) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public void horizontalBlock(Block block, ResourceLocation side, ResourceLocation front, ResourceLocation top) throws Exception {
        String name = name(block);

        ModelData modelData = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        simpleBlockItem(block, modelData);

        horizontalBlock(block, modelData.orientable(side, front, top));
    }

    public void horizontalBlock(Block block, ModelFile model) throws Exception {
        horizontalBlock(block, model, DEFAULT_ANGLE_OFFSET);
    }

    public void horizontalBlock(Block block, ModelFile model, int angleOffset) throws Exception {
        horizontalBlock(block, $ -> model, angleOffset);
    }

    public void horizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc) throws Exception {
        horizontalBlock(block, modelFunc, DEFAULT_ANGLE_OFFSET);
    }

    public void horizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) % 360)
                        .build()
                );

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void horizontalFaceBlock(Block block, ModelFile model) throws Exception {
        horizontalFaceBlock(block, model, DEFAULT_ANGLE_OFFSET);
    }

    public void horizontalFaceBlock(Block block, ModelFile model, int angleOffset) throws Exception {
        horizontalFaceBlock(block, $ -> model, angleOffset);
    }

    public void horizontalFaceBlock(Block block, Function<BlockState, ModelFile> modelFunc) throws Exception {
        horizontalFaceBlock(block, modelFunc, DEFAULT_ANGLE_OFFSET);
    }

    public void horizontalFaceBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) throws Exception {
        String blockName = name(block);
        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationX(state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                        .rotationY((((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) + (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                        .build()
                );

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void directionalBlock(Block block, ModelFile model) throws Exception {
        directionalBlock(block, model, DEFAULT_ANGLE_OFFSET);
    }

    public void directionalBlock(Block block, ModelFile model, int angleOffset) throws Exception {
        directionalBlock(block, $ -> model, angleOffset);
    }

    public void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc) throws Exception {
        directionalBlock(block, modelFunc, DEFAULT_ANGLE_OFFSET);
    }

    public void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + angleOffset) % 360)
                            .build();
                });

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void stairsBlock(StairBlock block, ResourceLocation texture) throws Exception {
        stairsBlock(block, texture, texture, texture);
    }

    public void stairsBlock(StairBlock block, String name, ResourceLocation texture) throws Exception {
        stairsBlock(block, name, texture, texture, texture);
    }

    public void stairsBlock(StairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) throws Exception {
        String name = name(block);

        stairsBlockInternal(block, name, side, bottom, top);
    }

    public void stairsBlock(StairBlock block, String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) throws Exception {
        stairsBlockInternal(block, name + "_stairs", side, bottom, top);
    }

    private void stairsBlockInternal(StairBlock block, String baseName, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) throws Exception {
        ModelData stairsModel = new ModelData(baseName, ModelData.BLOCK_FOLDER, "");
        ModelFile stairs = stairsModel.stairs(side, bottom, top);

        simpleBlockWithItem(block, stairsModel.getModel());

        ModelData stairsInnerModel = new ModelData(baseName + "_inner", ModelData.BLOCK_FOLDER, "");
        ModelFile stairsInner = stairsInnerModel.stairsInner(side, bottom, top);

        simpleBlockWithItem(block, stairsInnerModel.getModel());

        ModelData stairsOuterModel = new ModelData(baseName + "_outer", ModelData.BLOCK_FOLDER, "");
        ModelFile stairsOuter = stairsOuterModel.stairsOuter(side, bottom, top);

        simpleBlockWithItem(block, stairsOuterModel.getModel());


        stairsBlock(block, stairs, stairsInner, stairsOuter);
    }

    public void stairsBlock(StairBlock block, ModelFile stairs, ModelFile stairsInner, ModelFile stairsOuter) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(StairBlock.FACING);
                    Half half = state.getValue(StairBlock.HALF);
                    StairsShape shape = state.getValue(StairBlock.SHAPE);
                    int yRot = (int) facing.getClockWise().toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
                    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                        yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
                    }
                    if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                        yRot += 90; // Top stairs are rotated 90 degrees clockwise
                    }
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation
                    return ConfiguredModel.builder()
                            .modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
                            .rotationX(half == Half.BOTTOM ? 0 : 180)
                            .rotationY(yRot)
                            .uvLock(uvlock)
                            .build();
                }, StairBlock.WATERLOGGED);

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void slabBlock(SlabBlock block, ResourceLocation doubleSlab, ResourceLocation texture) throws Exception {
        slabBlock(block, doubleSlab, texture, texture, texture);
    }

    public void slabBlock(SlabBlock block, ResourceLocation doubleSlab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) throws Exception {
        String name = name(block);
        ModelData slabModel = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        TAModelBuilder slab = slabModel.slab(side, bottom, top);
        slabModel.overrideModel(slab);

        simpleBlockWithItem(block, slabModel.getModel());

        ModelData slabTopModel = new ModelData(name + "_top", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder slabTop = slabTopModel.slab(side, bottom, top);
        slabTopModel.overrideModel(slabTop);

        simpleBlockWithItem(block, slabTopModel.getModel());

        slabBlock(block, slab, slabTop, new ModelFile.UncheckedModelFile(doubleSlab));
    }

    public void slabBlock(SlabBlock block, ModelFile bottom, ModelFile top, ModelFile doubleSlab) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block);

        variantBlockStateBuilder.partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(bottom))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(top))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(doubleSlab));

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void buttonBlock(ButtonBlock block, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData buttonModel = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        TAModelBuilder button = buttonModel.button(texture);
        buttonModel.overrideModel(button);

        simpleBlockWithItem(block, buttonModel.getModel());

        ModelData buttonPressedModel = new ModelData(name + "_pressed", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder buttonPressed = buttonPressedModel.buttonPressed(texture);
        buttonPressedModel.overrideModel(buttonPressed);

        simpleBlockWithItem(block, buttonPressedModel.getModel());

        buttonBlock(block, button, buttonPressed);
    }

    public void buttonBlock(ButtonBlock block, ModelFile button, ModelFile buttonPressed) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(ButtonBlock.FACING);
            AttachFace face = state.getValue(ButtonBlock.FACE);
            boolean powered = state.getValue(ButtonBlock.POWERED);

            return ConfiguredModel.builder()
                    .modelFile(powered ? buttonPressed : button)
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .uvLock(face == AttachFace.WALL)
                    .build();
        });

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData pressurePlateModel = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        TAModelBuilder pressurePlate = pressurePlateModel.button(texture);
        pressurePlateModel.overrideModel(pressurePlate);

        simpleBlockWithItem(block, pressurePlateModel.getModel());

        ModelData pressurePlateDownModel = new ModelData(name + "_down", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder pressurePlateDown = pressurePlateDownModel.button(texture);
        pressurePlateDownModel.overrideModel(pressurePlateDown);

        simpleBlockWithItem(block, pressurePlateDownModel.getModel());

        pressurePlateBlock(block, pressurePlate, pressurePlateDown);
    }

    public void pressurePlateBlock(PressurePlateBlock block, ModelFile pressurePlate, ModelFile pressurePlateDown) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block);

        variantBlockStateBuilder.partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown))
                .partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void signBlock(StandingSignBlock block, WallSignBlock wallSignBlock, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData model = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        TAModelBuilder builder = model.sign(texture);
        model.overrideModel(builder);

        simpleBlockWithItem(block, model.getModel());

        signBlock(block, wallSignBlock, builder);
    }

    public void signBlock(StandingSignBlock signBlock, WallSignBlock wallSignBlock, TAModelBuilder sign) throws Exception {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public void fourWayBlock(CrossCollisionBlock block, ModelFile post, ModelFile side) throws Exception {
        String blockName = name(block);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel().end();
        fourWayMultipart(block, builder, side);
    }

    public void fourWayMultipart(CrossCollisionBlock block, MultiPartBlockStateBuilder builder, ModelFile side) {
        String blockName = name(block);

        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                builder.part().modelFile(side).rotationY((((int) dir.toYRot()) + 180) % 360).uvLock(true).addModel()
                        .condition(value, true);
            }
        });

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(builder));
    }

    public void fenceBlock(FenceBlock block, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData fencePostModel = new ModelData(name + "_post", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fencePostBuilder = fencePostModel.fencePost(texture);
        fencePostModel.overrideModel(fencePostBuilder);

        simpleBlockWithItem(block, fencePostModel.getModel());

        ModelData fenceSideModel = new ModelData(name + "_side", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceSideBuilder = fenceSideModel.fenceSide(texture);
        fenceSideModel.overrideModel(fenceSideBuilder);

        simpleBlockWithItem(block, fenceSideModel.getModel());

        fourWayBlock(block, fencePostBuilder, fenceSideBuilder);
    }

    public void fenceBlock(FenceBlock block, String name, ResourceLocation texture) throws Exception {
        ModelData fencePostModel = new ModelData(name + "_fence_post", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fencePostBuilder = fencePostModel.fencePost(texture);
        fencePostModel.overrideModel(fencePostBuilder);

        simpleBlockWithItem(block, fencePostModel.getModel());

        ModelData fenceSideModel = new ModelData(name + "_fence_side", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceSideBuilder = fenceSideModel.fenceSide(texture);
        fenceSideModel.overrideModel(fenceSideBuilder);

        simpleBlockWithItem(block, fenceSideModel.getModel());

        fourWayBlock(block, fencePostBuilder, fenceSideBuilder);
    }

    public void fenceGateBlock(FenceGateBlock block, ResourceLocation texture) throws Exception {
        String name = name(block);
        fenceGateBlockInternal(block, name, texture);
    }

    public void fenceGateBlock(FenceGateBlock block, String name, ResourceLocation texture) throws Exception {
        fenceGateBlockInternal(block, name + "_fence_gate", texture);
    }

    private void fenceGateBlockInternal(FenceGateBlock block, String baseName, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData fenceGateModel = new ModelData(name, ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceGateBuilder = fenceGateModel.fenceGate(texture);
        fenceGateModel.overrideModel(fenceGateBuilder);

        simpleBlockWithItem(block, fenceGateModel.getModel());

        ModelData fenceGateOpenModel = new ModelData(name + "_open", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceGateOpenBuilder = fenceGateOpenModel.fenceGateOpen(texture);
        fenceGateOpenModel.overrideModel(fenceGateOpenBuilder);

        simpleBlockWithItem(block, fenceGateOpenModel.getModel());

        ModelData fenceGateWallModel = new ModelData(name + "_wall", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceGateWallBuilder = fenceGateWallModel.fenceGateWall(texture);
        fenceGateWallModel.overrideModel(fenceGateWallBuilder);

        simpleBlockWithItem(block, fenceGateWallModel.getModel());

        ModelData fenceGateWallOpenModel = new ModelData(name + "_wall_open", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder fenceGateWallOpenBuilder = fenceGateWallOpenModel.fenceGateWallOpen(texture);
        fenceGateWallOpenModel.overrideModel(fenceGateWallOpenBuilder);

        simpleBlockWithItem(block, fenceGateWallOpenModel.getModel());

        fenceGateBlock(block, fenceGateBuilder, fenceGateOpenBuilder, fenceGateWallBuilder, fenceGateWallOpenBuilder);
    }

    public void fenceGateBlock(FenceGateBlock block, ModelFile gate, ModelFile gateOpen, ModelFile gateWall, ModelFile gateWallOpen) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block).forAllStatesExcept(state -> {
            ModelFile model = gate;
            if (state.getValue(FenceGateBlock.IN_WALL)) {
                model = gateWall;
            }
            if (state.getValue(FenceGateBlock.OPEN)) {
                model = model == gateWall ? gateWallOpen : gateOpen;
            }
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) state.getValue(FenceGateBlock.FACING).toYRot())
                    .uvLock(true)
                    .build();
        }, FenceGateBlock.POWERED);

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void wallBlock(WallBlock block, ResourceLocation texture) throws Exception {
        String name = name(block);

        wallBlockInternal(block, name, texture);
    }

    public void wallBlock(WallBlock block, String name, ResourceLocation texture) throws Exception {
        wallBlockInternal(block, name + "_wall", texture);
    }

    private void wallBlockInternal(WallBlock block, String baseName, ResourceLocation texture) throws Exception {
        String name = name(block);

        ModelData wallPostModel = new ModelData(name + "_post", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder wallPostBuilder = wallPostModel.wallPost(texture);
        wallPostModel.overrideModel(wallPostBuilder);

        simpleBlockWithItem(block, wallPostModel.getModel());

        ModelData wallSideModel = new ModelData(name + "_side", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder wallSideBuilder = wallSideModel.wallSide(texture);
        wallSideModel.overrideModel(wallSideBuilder);

        simpleBlockWithItem(block, wallSideModel.getModel());

        ModelData wallSideTallModel = new ModelData(name + "_side_tall", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder wallSideTallBuilder = wallSideTallModel.wallSideTall(texture);
        wallSideTallModel.overrideModel(wallSideTallBuilder);

        simpleBlockWithItem(block, wallSideTallModel.getModel());

        wallBlock(block, wallPostBuilder, wallSideBuilder, wallSideTallBuilder);
    }

    public static final ImmutableMap<Direction, Property<WallSide>> WALL_PROPS = ImmutableMap.<Direction, Property<WallSide>>builder()
            .put(Direction.EAST,  BlockStateProperties.EAST_WALL)
            .put(Direction.NORTH, BlockStateProperties.NORTH_WALL)
            .put(Direction.SOUTH, BlockStateProperties.SOUTH_WALL)
            .put(Direction.WEST,  BlockStateProperties.WEST_WALL)
            .build();

    public void wallBlock(WallBlock block, ModelFile post, ModelFile side, ModelFile sideTall) throws Exception {
        String blockName = name(block);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel()
                .condition(WallBlock.UP, true).end();
        WALL_PROPS.entrySet().stream()
                .filter(e -> e.getKey().getAxis().isHorizontal())
                .forEach(e -> {
                    wallSidePart(builder, side, e, WallSide.LOW);
                    wallSidePart(builder, sideTall, e, WallSide.TALL);
                });

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(builder));
    }

    private void wallSidePart(MultiPartBlockStateBuilder builder, ModelFile model, Map.Entry<Direction, Property<WallSide>> entry, WallSide height) {

        builder.part()
                .modelFile(model)
                .rotationY((((int) entry.getKey().toYRot()) + 180) % 360)
                .uvLock(true)
                .addModel()
                .condition(entry.getValue(), height);
    }

    public void paneBlock(IronBarsBlock block, ResourceLocation pane, ResourceLocation edge) throws Exception {
        String name = name(block);

        paneBlockInternal(block, name, pane, edge);
    }

    public void paneBlock(IronBarsBlock block, String name, ResourceLocation pane, ResourceLocation edge) throws Exception {
        paneBlockInternal(block, name + "_pane", pane, edge);
    }

    private void paneBlockInternal(IronBarsBlock block, String baseName, ResourceLocation pane, ResourceLocation edge) throws Exception {
        String name = name(block);

        ModelData panePostModel = new ModelData(name + "_post", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder panePostBuilder = panePostModel.panePost(pane, edge);
        panePostModel.overrideModel(panePostBuilder);

        simpleBlockWithItem(block, panePostModel.getModel());

        ModelData paneSideModel = new ModelData(name + "_side", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder paneSideBuilder = paneSideModel.paneSide(pane, edge);
        paneSideModel.overrideModel(paneSideBuilder);

        simpleBlockWithItem(block, paneSideModel.getModel());

        ModelData paneSideAltModel = new ModelData(name + "_side_alt", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder paneSideAltBuilder = paneSideAltModel.paneSideAlt(pane, edge);
        paneSideAltModel.overrideModel(paneSideAltBuilder);

        simpleBlockWithItem(block, paneSideAltModel.getModel());

        ModelData paneNoSideModel = new ModelData(name + "_noside", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder paneNoSideBuilder = paneNoSideModel.paneNoSide(pane);
        paneNoSideModel.overrideModel(paneNoSideBuilder);

        simpleBlockWithItem(block, paneNoSideModel.getModel());

        ModelData paneNoSideAltModel = new ModelData(name + "_noside_alt", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder paneNoSideAltBuilder = paneNoSideAltModel.paneNoSideAlt(pane);
        paneNoSideAltModel.overrideModel(paneNoSideAltBuilder);

        simpleBlockWithItem(block, paneNoSideAltModel.getModel());


        paneBlock(block, panePostBuilder, paneSideBuilder, paneSideAltBuilder, paneNoSideBuilder, paneNoSideAltBuilder);
    }

    public void paneBlock(IronBarsBlock block, ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt) throws Exception {
        String blockName = name(block);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel().end();
        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                boolean alt = dir == Direction.SOUTH;
                builder.part().modelFile(alt || dir == Direction.WEST ? sideAlt : side).rotationY(dir.getAxis() == Direction.Axis.X ? 90 : 0).addModel()
                        .condition(value, true).end()
                        .part().modelFile(alt || dir == Direction.EAST ? noSideAlt : noSide).rotationY(dir == Direction.WEST ? 270 : dir == Direction.SOUTH ? 90 : 0).addModel()
                        .condition(value, false);
            }
        });

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(builder));
    }

    public void doorBlock(DoorBlock block, ResourceLocation bottom, ResourceLocation top) throws Exception {
        String name = name(block);

        doorBlockInternal(block, name, bottom, top);
    }

    public void doorBlock(DoorBlock block, String name, ResourceLocation bottom, ResourceLocation top) throws Exception {
        doorBlockInternal(block, name + "_door", bottom, top);
    }

    private void doorBlockInternal(DoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation top) throws Exception {
        String name = name(block);

        ModelData doorBottomLeftModel = new ModelData(name + "_bottom", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder doorBottomLeftBuilder = doorBottomLeftModel.doorBottomLeft(bottom, top);
        doorBottomLeftModel.overrideModel(doorBottomLeftBuilder);

        simpleBlockWithItem(block, doorBottomLeftModel.getModel());

        ModelData doorBottomRightModel = new ModelData(name + "_bottom_hinge", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder doorBottomRightBuilder = doorBottomRightModel.doorBottomRight(bottom, top);
        doorBottomRightModel.overrideModel(doorBottomRightBuilder);

        simpleBlockWithItem(block, doorBottomRightModel.getModel());

        ModelData doorTopLeftModel = new ModelData(name + "_top", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder doorTopLeftBuilder = doorTopLeftModel.doorTopLeft(bottom, top);
        doorTopLeftModel.overrideModel(doorTopLeftBuilder);

        simpleBlockWithItem(block, doorTopLeftModel.getModel());

        ModelData doorTopRightModel = new ModelData(name + "_top_hinge", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder doorTopRightBuilder = doorTopRightModel.doorTopRight(bottom, top);
        doorTopRightModel.overrideModel(doorTopRightBuilder);

        simpleBlockWithItem(block, doorTopRightModel.getModel());

        doorBlock(block, doorBottomLeftBuilder, doorBottomRightBuilder, doorTopLeftBuilder, doorTopRightBuilder);
    }

    public void doorBlock(DoorBlock block, ModelFile bottomLeft, ModelFile bottomRight, ModelFile topLeft, ModelFile topRight) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
            boolean rh = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(DoorBlock.OPEN);
            boolean right = rh ^ open;
            if (open) {
                yRot += 90;
            }
            if (rh && open) {
                yRot += 180;
            }
            yRot %= 360;
            return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? bottomRight : bottomLeft) : (right ? topRight : topLeft))
                    .rotationY(yRot)
                    .build();
        }, DoorBlock.POWERED);

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }

    public void trapdoorBlock(TrapDoorBlock block, ResourceLocation texture, boolean orientable) throws Exception {
        String name = name(block);

        trapdoorBlockInternal(block, name, texture, orientable);
    }

    public void trapdoorBlock(TrapDoorBlock block, String name, ResourceLocation texture, boolean orientable) throws Exception {
        trapdoorBlockInternal(block, name + "_trapdoor", texture, orientable);
    }

    private void trapdoorBlockInternal(TrapDoorBlock block, String baseName, ResourceLocation texture, boolean orientable) throws Exception {
        String name = name(block);

        ModelData trapdoorOrientableBottomModel = new ModelData(name + "_bottom", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorOrientableBottomBuilder = trapdoorOrientableBottomModel.trapdoorOrientableBottom(texture);
        trapdoorOrientableBottomModel.overrideModel(trapdoorOrientableBottomBuilder);

        simpleBlockWithItem(block, trapdoorOrientableBottomModel.getModel());

        ModelData trapdoorBottomModel = new ModelData(name + "_bottom", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorBottomBuilder = trapdoorBottomModel.trapdoorBottom(texture);
        trapdoorBottomModel.overrideModel(trapdoorBottomBuilder);

        simpleBlockWithItem(block, trapdoorBottomModel.getModel());

        ModelData trapdoorOrientableTopModel = new ModelData(name + "_top", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorOrientableTopBuilder = trapdoorOrientableTopModel.trapdoorOrientableTop(texture);
        trapdoorOrientableTopModel.overrideModel(trapdoorOrientableTopBuilder);

        simpleBlockWithItem(block, trapdoorOrientableTopModel.getModel());

        ModelData trapdoorTopModel = new ModelData(name + "_top", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorTopBuilder = trapdoorTopModel.trapdoorTop(texture);
        trapdoorTopModel.overrideModel(trapdoorTopBuilder);

        simpleBlockWithItem(block, trapdoorTopModel.getModel());

        ModelData trapdoorOrientableOpenModel = new ModelData(name + "_open", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorOrientableOpenBuilder = trapdoorOrientableOpenModel.trapdoorOrientableOpen(texture);
        trapdoorOrientableOpenModel.overrideModel(trapdoorOrientableOpenBuilder);

        simpleBlockWithItem(block, trapdoorOrientableOpenModel.getModel());

        ModelData trapdoorOpenModel = new ModelData(name + "_open", ModelData.BLOCK_FOLDER, "");
        TAModelBuilder trapdoorOpenBuilder = trapdoorOpenModel.trapdoorOpen(texture);
        trapdoorOpenModel.overrideModel(trapdoorOpenBuilder);

        simpleBlockWithItem(block, trapdoorOpenModel.getModel());

        ModelFile bottom = orientable ? trapdoorOrientableBottomBuilder : trapdoorBottomBuilder;
        ModelFile top = orientable ? trapdoorOrientableTopBuilder : trapdoorTopBuilder;
        ModelFile open = orientable ? trapdoorOrientableOpenBuilder : trapdoorOpenBuilder;

        trapdoorBlock(block, bottom, top, open, orientable);
    }

    public void trapdoorBlock(TrapDoorBlock block, ModelFile bottom, ModelFile top, ModelFile open, boolean orientable) throws Exception {
        String blockName = name(block);

        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block).forAllStatesExcept(state -> {
            int xRot = 0;
            int yRot = ((int) state.getValue(TrapDoorBlock.FACING).toYRot()) + 180;
            boolean isOpen = state.getValue(TrapDoorBlock.OPEN);
            if (orientable && isOpen && state.getValue(TrapDoorBlock.HALF) == Half.TOP) {
                xRot += 180;
                yRot += 180;
            }
            if (!orientable && !isOpen) {
                yRot = 0;
            }
            yRot %= 360;
            return ConfiguredModel.builder().modelFile(isOpen ? open : state.getValue(TrapDoorBlock.HALF) == Half.TOP ? top : bottom)
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
        }, TrapDoorBlock.POWERED, TrapDoorBlock.WATERLOGGED);

        AssetPackRegistry.blockStateDataMap.put(blockName, new BlockStateData().setBlockState(variantBlockStateBuilder));
    }



}
