package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideRecipe;
import mightydanp.industrialtech.common.tileentities.CampfireBlockEntityOverride;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Created by MightyDanp on 5/3/2021.
 */
public class CampfireBlockOverride extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty LOG = IntegerProperty.create("log", 0, 5);
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
        super(BlockBehaviour.Properties.of(Material.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(LIT, false).setValue(LOG, 0).setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(WATERLOGGED, LIT, LOG, FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CampfireBlockEntityOverride(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter iBlockReader, BlockPos blockPos, CollisionContext iSelectionContext) {
        VoxelShape horizontal = Shapes.join(Shapes.or(tinder, log1H, log2H, log3H, log4H, ashH), Shapes.empty(), BooleanOp.ONLY_FIRST);
        VoxelShape vertical = Shapes.join(Shapes.or(tinder, log1V, log2V, log3V, log4V, ashV), Shapes.empty(), BooleanOp.ONLY_FIRST);


        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride) iBlockReader.getBlockEntity(blockPos);

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
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride)world.getBlockEntity(pos);
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
    public InteractionResult use(BlockState blockState, Level world, BlockPos blockPos, Player playerEntity, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride) world.getBlockEntity(blockPos);
        AABB cookingSlot1;
        AABB cookingSlot2 = AABB.ofSize(blockRayTraceResult.getLocation(), blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AABB cookingSlot3 = AABB.ofSize(blockRayTraceResult.getLocation(), blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AABB cookingSlot4 = AABB.ofSize(blockRayTraceResult.getLocation(), blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AABB ashSlot      = AABB.ofSize(blockRayTraceResult.getLocation(), blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);
        AABB tinderSlot   = AABB.ofSize(blockRayTraceResult.getLocation(), blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0);

        if (tileEntity != null) {
            ItemStack itemstack = playerEntity.getItemInHand(hand);
            ResourceLocation log = ItemTags.LOGS.getName();

            Optional<CampfireOverrideRecipe> optional = tileEntity.getCookableRecipe(itemstack);
            int logs = blockState.getValue(LOG);

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot1 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot1 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot1 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot1 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + blockState.getValue(FACING));
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot2 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot2 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot2 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot2 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot4 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot4 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot4 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot4 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH: cookingSlot3 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot3 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot3 = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot3 = new AABB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH:
                case SOUTH:
                    ashSlot = new AABB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    ashSlot = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(blockState.getValue(FACING)){
                case NORTH:
                case SOUTH:
                    tinderSlot = new AABB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    tinderSlot = new AABB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            if(logs < 5 && ItemTags.getAllTags().getTagOrEmpty(log).contains(itemstack.getItem())){
                itemstack.shrink(1);
                playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                increaseLogs(world, blockPos, blockState, 1);
            }

            if(logs > 0 && itemstack.getItem() == Items.FLINT_AND_STEEL){
                CampfireBlockOverride.setLit(world, blockPos, blockState, true);
                tileEntity.keepLogsFormed = true;
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }

            if(logs > 0) {
                if (cookingSlot1.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot1().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot1Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.getInventory().placeItemBackInInventory(tileEntity.getCookSlot1());
                            tileEntity.getInventory().set(tileEntity.cookSlot1Number, ItemStack.EMPTY);
                        }else{
                            return InteractionResult.FAIL;
                        }
                    }
                }

                if (cookingSlot2.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot2().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot2Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.getInventory().placeItemBackInInventory(tileEntity.getCookSlot2());
                            tileEntity.getInventory().set(tileEntity.cookSlot2Number, ItemStack.EMPTY);
                        }else{
                            return InteractionResult.FAIL;
                        }
                    }
                }

                if (cookingSlot3.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot3().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot3Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.getInventory().placeItemBackInInventory(tileEntity.getCookSlot3());
                            tileEntity.getInventory().set(tileEntity.cookSlot3Number, ItemStack.EMPTY);
                        }else{
                            return InteractionResult.FAIL;
                        }
                    }
                }

                if (cookingSlot4.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot4().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot4Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.getMainHandItem().getItem() == Items.STICK) {
                            playerEntity.getInventory().placeItemBackInInventory(tileEntity.getCookSlot4());
                            tileEntity.getInventory().set(tileEntity.cookSlot4Number, ItemStack.EMPTY);
                        }else{
                            return InteractionResult.FAIL;
                        }
                    }
                }
            }

            if(ashSlot.contains(click(playerEntity, world))){
                tileEntity.placeItemStack(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.ashSlotNumber);
                itemstack.shrink(1);
                playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
            }else{
                if(playerEntity.isCrouching() && !isLit(blockState)){
                    playerEntity.getInventory().placeItemBackInInventory(tileEntity.getAshSlot());
                    tileEntity.getInventory().set(tileEntity.ashSlotNumber, ItemStack.EMPTY);
                }
            }

            if(tinderSlot.contains(click(playerEntity, world))){
                tileEntity.placeItemStack(playerEntity.getAbilities().instabuild ? itemstack.copy() : itemstack, tileEntity.tinderSlotNumber);
                itemstack.shrink(1);
                playerEntity.setItemInHand(InteractionHand.MAIN_HAND, itemstack);
            }else{
                if(playerEntity.isCrouching()){
                    playerEntity.getInventory().placeItemBackInInventory(itemstack);
                    tileEntity.getInventory().set(tileEntity.tinderSlotNumber, ItemStack.EMPTY);
                }
            }

            if (optional.isPresent()) {
                if(!world.isClientSide){
                    playerEntity.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.SUCCESS ;
    }

    public Vec3 click(Player player, Level world){
        Double rayLength = 100D;
        Vec3 playerRotation = player.getViewVector(0);
        Vec3 rayPath = playerRotation.scale(rayLength);

        //RAY START AND END POINTS
        Vec3 from = player.getEyePosition(0);
        Vec3 to = from.add(rayPath);

        //CREATE THE RAY
        ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
        //CAST THE RAY
        BlockHitResult rayHit = world.clip(rayCtx);

        //CHECK THE RESULTS
        if (rayHit.getType() == HitResult.Type.MISS){
            return null;
        }

        //player.sendMessage(ITextComponent.nullToEmpty(rayHit.getLocation().x + " ," + rayHit.getLocation().y + " ," + rayHit.getLocation().z), player.getUUID());

        return rayHit.getLocation();
    }

    private boolean isSmokeSource(BlockState p_220099_1_) {
        return p_220099_1_.is(Blocks.HAY_BLOCK);
    }

    public void entityInside(BlockState blockState, Level world, BlockPos blockPos, Entity entity) {
        if (!entity.fireImmune() && blockState.getValue(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float)1);
        }

        super.entityInside(blockState, world, blockPos, entity);
    }

    //to-do change to leave ash behind
    public void onRemove(BlockState p_196243_1_, Level world, BlockPos blockPos, BlockState blockState, boolean p_196243_5_) {
        if (!p_196243_1_.is(blockState.getBlock())) {
            BlockEntity tileentity = world.getBlockEntity(blockPos);
            if (tileentity instanceof CampfireBlockEntityOverride) {
                Containers.dropContents(world, blockPos, ((CampfireBlockEntityOverride)tileentity).getInventory());
            }

            super.onRemove(p_196243_1_, world, blockPos, blockState, p_196243_5_);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext useContext) {
        LevelAccessor iworld = useContext.getLevel();
        BlockPos blockpos = useContext.getClickedPos();
        final FluidState fluidState = useContext.getLevel().getFluidState(useContext.getClickedPos());
        boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER && fluidState.getAmount() == 8;

        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, false).setValue(LOG, 0).setValue(FACING, useContext.getHorizontalDirection());
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState underBlock, LevelAccessor world, BlockPos blockPos, BlockPos p_196271_6_) {
        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride) world.getBlockEntity(blockPos);
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

    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, Level world, BlockPos blockPos, Random random) {
        if (blockState.getValue(CampfireBlockOverride.LIT)) {
            if (random.nextInt(10) == 0) {
                world.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (random.nextInt(5) == 0) {
                for(int i = 0; i < random.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, (double)(random.nextFloat() / 2.0F), 5.0E-5D, (double)(random.nextFloat() / 2.0F));
                }
            }

        }
    }

    public static void dowse(LevelAccessor iWorld, BlockPos blockPos) {
        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride)iWorld.getBlockEntity(blockPos);
        if (iWorld.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles((Level)iWorld, blockPos, tileEntity.signalFire, true);
            }
        }
        if (tileEntity != null) {
            tileEntity.dowse();
        }

    }

    public boolean placeLiquid(LevelAccessor iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (blockState.getValue(LIT)) {
                if (!iWorld.isClientSide()) {
                    iWorld.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
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

    public void onProjectileHit(Level world, BlockState blockState, BlockHitResult traceResult, Projectile projectile) {
        if (!world.isClientSide && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, entity);
            if (flag && !blockState.getValue(LIT) && !blockState.getValue(WATERLOGGED)) {
                BlockPos blockpos = traceResult.getBlockPos();
                world.setBlock(blockpos, blockState.setValue(BlockStateProperties.LIT, true), 11);
            }
        }

    }

    public static void makeParticles(Level world, BlockPos blockPos, boolean signalFire, boolean isLit) {
        Random random = world.getRandom();
        SimpleParticleType basicparticletype = signalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addAlwaysVisibleParticle(basicparticletype, true, (double)blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockPos.getY() + random.nextDouble() + random.nextDouble(), (double)blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (isLit) {
            world.addParticle(ParticleTypes.SMOKE, (double)blockPos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockPos.getY() + 0.4D, (double)blockPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    public static void makeParticles(Level world, BlockPos blockPos) {
        CampfireBlockEntityOverride tileEntity = (CampfireBlockEntityOverride) world.getBlockEntity(blockPos);
        Random random = world.getRandom();

        if(tileEntity != null) {
            SimpleParticleType basicparticletype = tileEntity.signalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addAlwaysVisibleParticle(basicparticletype, true, (double) blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + random.nextDouble() + random.nextDouble(), (double) blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            if (world.getBlockState(blockPos).getValue(CampfireBlockOverride.LIT)) {
                world.addParticle(ParticleTypes.SMOKE, (double) blockPos.getX() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + 0.4D, (double) blockPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
            }
        }
    }

    public static boolean isSmokeyPos(Level world, BlockPos blockPos) {
        for(int i = 1; i <= 5; ++i) {
            BlockPos blockpos = blockPos.below(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (isLit(blockstate)) {
                return true;
            }

            boolean flag = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(world, blockpos, CollisionContext.empty()), BooleanOp.AND);//Forge fix: MC-201374
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
        return blockState.getValue(LIT);
    }



    @Override
    public int getLightBlock(BlockState blockState, BlockGetter world, BlockPos blockPos) {
        CampfireBlockEntityOverride tile = (CampfireBlockEntityOverride) world.getBlockEntity(blockPos);

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

    public static void shrinkLogs(Level world, BlockPos blockPos, BlockState blockState, int amount){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LOG, blockState.getValue(CampfireBlockOverride.LOG) - amount);
        world.setBlock(blockPos, blockState1, 3);
    }

    public static void increaseLogs(Level world, BlockPos blockPos, BlockState blockState, int amount){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LOG, blockState.getValue(CampfireBlockOverride.LOG) + amount);
        world.setBlock(blockPos, blockState1, 3);
    }

    public static void setLit(Level world, BlockPos blockPos, BlockState blockState, boolean lit){
        BlockState blockState1 = blockState.setValue(CampfireBlockOverride.LIT, lit);
        world.setBlock(blockPos, blockState1, 3);
    }
}
