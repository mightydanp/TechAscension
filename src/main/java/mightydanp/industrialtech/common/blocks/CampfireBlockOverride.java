package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideRecipe;
import mightydanp.industrialtech.common.libs.CampfireEnum;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

/**
 * Created by MightyDanp on 5/3/2021.
 */
public class CampfireBlockOverride extends ContainerBlock implements IWaterLoggable {
    public static final EnumProperty<CampfireEnum> camp_fire = EnumProperty.create("camp_fire", CampfireEnum.class);//, CampfireEnum.---
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty LOG = IntegerProperty.create("log", 0, 4);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    protected static final VoxelShape tinder = Block.box(5, 0, 5, 11, 1, 11);
    private static final VoxelShape log1H = Block.box(1.0D, 0.0D, 0.0D, 5.0D, 4.0D, 16.0D);
    private static final VoxelShape log2H = Block.box(11.0D, 0.0D, 0.0D, 15.0D, 4.0D, 16.0D);
    private static final VoxelShape log3H = Block.box(0.0D, 3.0D, 1.0D, 16.0D, 7.0D, 5.0D);
    private static final VoxelShape log4H = Block.box(0.0D, 3.0D, 11.0D, 16.0D, 7.0D, 15.0D);
    private static final VoxelShape ashH = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 1.0D, 16.0D);

    private static final VoxelShape log1V = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 4.0D, 5.0D);
    private static final VoxelShape log2V = Block.box(0.0D, 0.0D, 11.0D, 16.0D, 4.0D, 15.0D);
    private static final VoxelShape log3V = Block.box(1.0D, 3.0D, 0.0D, 5.0D, 7.0D, 16.0D);
    private static final VoxelShape log4V = Block.box(11.0D, 3.0D, 0.0D, 15.0D, 7.0D, 16.0D);
    private static final VoxelShape ashV = Block.box(0.0D, 0.0D, 5.0D, 16.0D, 1.0D, 11.0D);

    public CampfireBlockOverride() {
        super(AbstractBlock.Properties.of(Material.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(LIT, false).setValue(LOG, 0).setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(WATERLOGGED, LIT, LOG, FACING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CampfireTileEntityOverride();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new CampfireTileEntityOverride();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        VoxelShape horizontal = VoxelShapes.join(VoxelShapes.or(tinder, log1H, log2H, log3H, log4H, ashH), VoxelShapes.empty(), IBooleanFunction.ONLY_FIRST);
        VoxelShape vertical = VoxelShapes.join(VoxelShapes.or(tinder, log1V, log2V, log3V, log4V, ashV), VoxelShapes.empty(), IBooleanFunction.ONLY_FIRST);


        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) iBlockReader.getBlockEntity(blockPos);

        if (tileEntity != null) {
            if(blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH){
                return horizontal;
            }

            if(blockState.getValue(FACING) == Direction.EAST || blockState.getValue(FACING) == Direction.WEST){
                return vertical;
            }
        }

        return horizontal;
    }




    /*
    @Override
    public VoxelShape getInteractionShape(BlockState p_199600_1_, IBlockReader p_199600_2_, BlockPos p_199600_3_) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) iBlockReader.getBlockEntity(blockPos);

        if (tileEntity != null) {

        }

        return INSIDE;
    }
    */

    @Override
    public int getLightValue(BlockState state, IBlockReader iBlockReader, BlockPos blockPos) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride)iBlockReader.getBlockEntity(blockPos);
        if(tileEntity!= null){
            if(tileEntity.getLevel() != null) {
                return state.getValue(CampfireBlockOverride.LIT) ? 15 : 0;
            }else{
                return 0;
            }
        }

        return 0;
    }


    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        AxisAlignedBB cookingSlot1;
        AxisAlignedBB cookingSlot2 = AxisAlignedBB.ofSize(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AxisAlignedBB cookingSlot3 = AxisAlignedBB.ofSize(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AxisAlignedBB cookingSlot4 = AxisAlignedBB.ofSize(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AxisAlignedBB ashSlot    = AxisAlignedBB.ofSize(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AxisAlignedBB tinderSlot   = AxisAlignedBB.ofSize(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);

        if (tileEntity != null) {
            ItemStack itemstack = playerEntity.getItemInHand(hand);
            ResourceLocation log = ItemTags.LOGS.getName();

            Optional<CampfireOverrideRecipe> optional = tileEntity.getCookableRecipe(itemstack);
            int logs = blockState.getValue(LOG);

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + blockState.getValue(FACING));
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH:
                case SOUTH:
                    ashSlot = new AxisAlignedBB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    ashSlot = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH:
                case SOUTH:
                    tinderSlot = new AxisAlignedBB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    tinderSlot = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            if(logs < 4 && ItemTags.getAllTags().getTagOrEmpty(log).contains(itemstack.getItem())){
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);

                BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LOG, blockState.getValue(CampfireBlockOverride.LOG) + 1);
                world.setBlock(blockPos, blockState1, 3);
            }

            if(logs > 0 && itemstack.getItem() == Items.FLINT_AND_STEEL){
                BlockState blockState1 = blockState.setValue(LIT, true);
                world.setBlock(blockPos, blockState1, 3);
                tileEntity.keepLogsFormed = true;
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }

            if(logs > 0) {
                if (cookingSlot1.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot1().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot1Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getCookSlot1());
                            tileEntity.getInventory().set(tileEntity.cookSlot1Number, ItemStack.EMPTY);
                        }else{
                            return ActionResultType.FAIL;
                        }
                    }
                }

                if (cookingSlot2.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot2().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot2Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getCookSlot2());
                            tileEntity.getInventory().set(tileEntity.cookSlot2Number, ItemStack.EMPTY);
                        }else{
                            return ActionResultType.FAIL;
                        }
                    }
                }

                if (cookingSlot3.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot3().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot3Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getCookSlot3());
                            tileEntity.getInventory().set(tileEntity.cookSlot3Number, ItemStack.EMPTY);
                        }else{
                            return ActionResultType.FAIL;
                        }
                    }
                }

                if (cookingSlot4.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot4().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot4Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getCookSlot4());
                            tileEntity.getInventory().set(tileEntity.cookSlot4Number, ItemStack.EMPTY);
                        }else{
                            return ActionResultType.FAIL;
                        }
                    }
                }
            }

            if(ashSlot.contains(click(playerEntity, world))){
                tileEntity.placeItemStack(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.ashSlotNumber);
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
            }else{
                if(playerEntity.isCrouching() && !isLit(blockState)){
                    playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getAshSlot());
                    tileEntity.getInventory().set(tileEntity.ashSlotNumber, ItemStack.EMPTY);
                }
            }

            if(tinderSlot.contains(click(playerEntity, world))){
                tileEntity.placeItemStack(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.tinderSlotNumber);
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
            }else{
                if(playerEntity.isCrouching()){
                    playerEntity.inventory.placeItemBackInInventory(world, itemstack);
                    tileEntity.getInventory().set(tileEntity.tinderSlotNumber, ItemStack.EMPTY);
                }
            }

            if (optional.isPresent()) {
                if(!world.isClientSide){
                    playerEntity.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.CONSUME;
            }
        }

        return ActionResultType.SUCCESS;
    }

    public Vector3d click(PlayerEntity player, World world){
        Double rayLength = new Double(100);
        Vector3d playerRotation = player.getViewVector(0);
        Vector3d rayPath = playerRotation.scale(rayLength);

        //RAY START AND END POINTS
        Vector3d from = player.getEyePosition(0);
        Vector3d to = from.add(rayPath);

        //CREATE THE RAY
        RayTraceContext rayCtx = new RayTraceContext(from, to, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null);
        //CAST THE RAY
        BlockRayTraceResult rayHit = world.clip(rayCtx);

        //CHECK THE RESULTS
        if (rayHit.getType() == RayTraceResult.Type.MISS){
            return null;
        }

        player.sendMessage(ITextComponent.nullToEmpty(rayHit.getLocation().x + " ," + rayHit.getLocation().y + " ," + rayHit.getLocation().z), player.getUUID());

        return rayHit.getLocation();
    }

    private boolean isSmokeSource(BlockState p_220099_1_) {
        return p_220099_1_.is(Blocks.HAY_BLOCK);
    }

    public void entityInside(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        if (!entity.fireImmune() && blockState.getValue(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float)1);
        }

        super.entityInside(blockState, world, blockPos, entity);
    }

    //to-do change to leave ash behind
    public void onRemove(BlockState p_196243_1_, World world, BlockPos blockPos, BlockState blockState, boolean p_196243_5_) {
        if (!p_196243_1_.is(blockState.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(blockPos);
            if (tileentity instanceof CampfireTileEntityOverride) {
                InventoryHelper.dropContents(world, blockPos, ((CampfireTileEntityOverride)tileentity).getInventory());
            }

            super.onRemove(p_196243_1_, world, blockPos, blockState, p_196243_5_);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext useContext) {
        IWorld iworld = useContext.getLevel();
        BlockPos blockpos = useContext.getClickedPos();
        final FluidState fluidState = useContext.getLevel().getFluidState(useContext.getClickedPos());
        boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER && fluidState.getAmount() == 8;

        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, false).setValue(LOG, 0).setValue(FACING, useContext.getHorizontalDirection());
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState underBlock, IWorld world, BlockPos blockPos, BlockPos p_196271_6_) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if(direction == Direction.DOWN){
            if(tileEntity != null){
                tileEntity.signalFire = isSmokeSource(underBlock);
            }
        }

        return super.updateShape(blockState, direction, underBlock, world, blockPos, p_196271_6_);
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        if (blockState.getValue(CampfireBlockOverride.LIT)) {
            if (random.nextInt(10) == 0) {
                world.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (random.nextInt(5) == 0) {
                for(int i = 0; i < random.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, (double)(random.nextFloat() / 2.0F), 5.0E-5D, (double)(random.nextFloat() / 2.0F));
                }
            }

        }
    }

    public static void dowse(IWorld iWorld, BlockPos blockPos) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride)iWorld.getBlockEntity(blockPos);
        if (iWorld.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles((World)iWorld, blockPos, tileEntity.signalFire, true);
            }
        }
        if (tileEntity != null) {
            tileEntity.dowse();
        }

    }

    public boolean placeLiquid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (blockState.getValue(LIT)) {
                if (!iWorld.isClientSide()) {
                    iWorld.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                dowse(iWorld, blockPos);
            }

            iWorld.setBlock(blockPos, blockState.setValue(WATERLOGGED, true).setValue(LIT, false), 3);
            iWorld.getLiquidTicks().scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(iWorld));
            return true;
        } else {
            return false;
        }
    }

    public void onProjectileHit(World world, BlockState blockState, BlockRayTraceResult traceResult, ProjectileEntity projectile) {
        if (!world.isClientSide && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, entity);
            if (flag && !blockState.getValue(LIT) && !blockState.getValue(WATERLOGGED)) {
                BlockPos blockpos = traceResult.getBlockPos();
                world.setBlock(blockpos, blockState.setValue(BlockStateProperties.LIT, true), 11);
            }
        }

    }

    public static void makeParticles(World world, BlockPos blockPos, boolean signalFire, boolean isLit) {
        Random random = world.getRandom();
        BasicParticleType basicparticletype = signalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addAlwaysVisibleParticle(basicparticletype, true, (double)blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockPos.getY() + random.nextDouble() + random.nextDouble(), (double)blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (isLit) {
            world.addParticle(ParticleTypes.SMOKE, (double)blockPos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockPos.getY() + 0.4D, (double)blockPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    public static void makeParticles(World world, BlockPos blockPos) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        Random random = world.getRandom();

        if(tileEntity != null) {
            BasicParticleType basicparticletype = tileEntity.signalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addAlwaysVisibleParticle(basicparticletype, true, (double) blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + random.nextDouble() + random.nextDouble(), (double) blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            if (world.getBlockState(blockPos).getValue(CampfireBlockOverride.LIT)) {
                world.addParticle(ParticleTypes.SMOKE, (double) blockPos.getX() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + 0.4D, (double) blockPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
            }
        }
    }

    public static boolean isSmokeyPos(World world, BlockPos blockPos) {
        for(int i = 1; i <= 5; ++i) {
            BlockPos blockpos = blockPos.below(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (isLit(blockstate)) {
                return true;
            }

            boolean flag = VoxelShapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(world, blockpos, ISelectionContext.empty()), IBooleanFunction.AND);//Forge fix: MC-201374
            if (flag) {
                return blockstate.getValue(CampfireBlockOverride.LIT);
            }
        }

        return false;
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }

    public static boolean isLit(BlockState blockState) {
        return blockState.hasProperty(LIT) && blockState.getValue(LIT);
    }



    @Override
    public int getLightBlock(BlockState blockState, IBlockReader world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);

        if(tile != null && blockState.getValue(CampfireBlockOverride.LIT)){
            return 16;
        }else{
            return 0;
        }
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    public static void shrinkLogs(World world, BlockPos blockPos, BlockState blockState, int amount){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LOG, blockState.getValue(CampfireBlockOverride.LOG) - amount);
        world.setBlock(blockPos, blockState1, 3);
    }

    public static void increaseLogs(World world, BlockPos blockPos, BlockState blockState, int amount){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LOG, blockState.getValue(CampfireBlockOverride.LOG) + amount);
        world.setBlock(blockPos, blockState1, 3);
    }

    public static void setLit(World world, BlockPos blockPos, BlockState blockState, boolean lit){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LIT, lit);
        world.setBlock(blockPos, blockState1, 3);
    }
}
