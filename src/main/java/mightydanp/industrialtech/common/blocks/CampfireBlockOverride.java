package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.common.blocks.state.CampfireStateController;
import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideRecipe;
import mightydanp.industrialtech.common.libs.CampfireEnum;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
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
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 15.9D, 7.0D, 15.9D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public CampfireBlockOverride() {
        super(AbstractBlock.Properties.of(Material.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader iBlockReader, BlockPos blockPos) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride)iBlockReader.getBlockEntity(blockPos);
        return tileEntity != null && tileEntity.getLevel() != null ?(isLitCampfire(tileEntity.getLevel(), blockPos) ? 15 : 0) : 0;
    }

    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        TileEntity tileentity = world.getBlockEntity(blockPos);

        if (tileentity instanceof CampfireTileEntityOverride) {
            CampfireTileEntityOverride campfiretileentity = (CampfireTileEntityOverride)tileentity;
            ItemStack itemstack = playerEntity.getItemInHand(hand);
            ResourceLocation log = ItemTags.LOGS.getName();

            Optional<CampfireOverrideRecipe> optional = campfiretileentity.getCookableRecipe(itemstack);

            if(ItemTags.getAllTags().getTagOrEmpty(log).contains(itemstack.getItem())){
                ItemStack slot = campfiretileentity.getLogItemSlots().get(0);
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                if(!campfiretileentity.getLogItemSlots().get(0).isEmpty()){
                    slot.setCount(slot.getCount() + 1);
                    campfiretileentity.getLogItemSlots().set(0, slot);
                }else{
                    ItemStack copy = itemstack.copy();
                    copy.setCount(1);
                    campfiretileentity.getLogItemSlots().set(0, copy);
                }
            }

            if(itemstack.getItem() == Items.FLINT_AND_STEEL){
                campfiretileentity.getCampfireNBT().putIsLit(true);
            }

            if (optional.isPresent()) {
                if (!world.isClientSide && campfiretileentity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, optional.get().getCookingTime())) {
                    playerEntity.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.CONSUME;
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride)world.getBlockEntity(blockPos);

        if(tileEntity != null && placer != null) {
            CampfireStateController nbt = tileEntity.getCampfireNBT();
            nbt.putDirection(placer.getDirection().getName().toLowerCase());
        }
    }

    public void entityInside(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (!entity.fireImmune() && isLitCampfire(world, blockPos) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float)1);
        }

        super.entityInside(blockState, world, blockPos, entity);
    }

    private boolean isSmokeSource(BlockState p_220099_1_) {
        return p_220099_1_.is(Blocks.HAY_BLOCK);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState p_196271_3_, IWorld world, BlockPos blockPos, BlockPos p_196271_6_) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if(direction == Direction.DOWN){
            if(tile != null && tile.getCampfireNBT() != null){
                tile.getCampfireNBT().putSignalFire(isSmokeSource(p_196271_3_));
            }
        }

        return super.updateShape(blockState, direction, p_196271_3_, world, blockPos, p_196271_6_);
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    public static boolean canLight(World world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        return !isLitCampfire(world, blockPos) && !blockState.getValue(BlockStateProperties.WATERLOGGED);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (isLitCampfire(world, blockPos)) {
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
        if (iWorld.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles((World)iWorld, blockPos);
            }
        }

        TileEntity tileentity = iWorld.getBlockEntity(blockPos);
        if (tileentity instanceof CampfireTileEntity) {
            ((CampfireTileEntity)tileentity).dowse();
        }

    }

    public void onProjectileHit(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockRayTraceResult.getBlockPos());

        if (!world.isClientSide && projectileEntity.isOnFire()) {
            Entity entity = projectileEntity.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, entity);
            if (flag && !isLitCampfire(world, blockRayTraceResult.getBlockPos()) && !blockState.getValue(WATERLOGGED)) {
                BlockPos blockpos = blockRayTraceResult.getBlockPos();
                if(tile != null && tile.getCampfireNBT() != null) {
                    tile.getCampfireNBT().putIsLit(true);
                }

            }
        }

    }

    public boolean placeLiquid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) iWorld.getBlockEntity(blockPos);
        if (!blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            if (isLitCampfire((World)iWorld, blockPos)) {
                if (!iWorld.isClientSide()) {
                    iWorld.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                dowse(iWorld, blockPos);
            }

            iWorld.setBlock(blockPos, blockState.setValue(WATERLOGGED, true), 3);
            iWorld.getLiquidTicks().scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(iWorld));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
         return new CampfireTileEntityOverride();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new CampfireTileEntityOverride();
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    public static void makeParticles(World world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        Random random = world.getRandom();

        if(tile != null && tile.getCampfireNBT() != null) {
            BasicParticleType basicparticletype = tile.getCampfireNBT().getSignalFire() ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addAlwaysVisibleParticle(basicparticletype, true, (double) blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + random.nextDouble() + random.nextDouble(), (double) blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            if (isLitCampfire(world, blockPos)) {
                world.addParticle(ParticleTypes.SMOKE, (double) blockPos.getX() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + 0.4D, (double) blockPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
            }
        }
    }

    public static boolean isSmokeyPos(World world, BlockPos blockPos) {
        for(int i = 1; i <= 5; ++i) {
            BlockPos blockpos = blockPos.below(i);
            BlockState blockstate = world.getBlockState(blockpos);
            if (isLitCampfire(world, blockPos)) {
                return true;
            }

            boolean flag = VoxelShapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(world, blockpos, ISelectionContext.empty()), IBooleanFunction.AND);//Forge fix: MC-201374
            if (flag) {
                return isLitCampfire(world, blockPos);
            }
        }

        return false;
    }

    public static boolean isLitCampfire(World world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        return tile != null && tile.getCampfireNBT().getIsLit();
    }

    public void setDirection(World world, BlockPos blockPos, Direction direction) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if(tile != null) {
            tile.getCampfireNBT().putDirection(direction.getName().toLowerCase());
        }
    }

    @Override
    public int getLightBlock(BlockState blockState, IBlockReader world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);

        if(tile != null && tile.getCampfireNBT().getIsLit()){
            return 16;
        }else{
            return 0;
        }
    }
}
