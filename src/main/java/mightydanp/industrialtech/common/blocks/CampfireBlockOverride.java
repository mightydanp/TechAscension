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
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        VoxelShape horizontal = VoxelShapes.join(VoxelShapes.or(tinder, log1H, log2H, log3H, log4H, ashH), VoxelShapes.empty(), IBooleanFunction.ONLY_FIRST);
        VoxelShape vertical = VoxelShapes.join(VoxelShapes.or(tinder, log1V, log2V, log3V, log4V, ashV), VoxelShapes.empty(), IBooleanFunction.ONLY_FIRST);


        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) iBlockReader.getBlockEntity(blockPos);

        if (tileEntity != null) {
            if(tileEntity.direction == Direction.NORTH || tileEntity.direction == Direction.SOUTH){
                return horizontal;
            }

            if(tileEntity.direction == Direction.EAST || tileEntity.direction == Direction.WEST){
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
                return isLitCampfire(tileEntity.getLevel(), blockPos) ? 15 : 0;
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
            ItemStack fuelSlot = tileEntity.getFuelSlot();

            switch(tileEntity.direction){
                case NORTH: cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot1 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + tileEntity.direction);
            }

            switch(tileEntity.direction){
                case NORTH: cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case SOUTH: cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case EAST:  cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot2 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(tileEntity.direction){
                case NORTH: cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case WEST:  cookingSlot4 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
            }

            switch(tileEntity.direction){
                case NORTH: cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
                case SOUTH: cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case EAST:  cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.1875, blockPos.getZ() + 0.0, blockPos.getX() + 0.3125 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 0.375 + 0.0001);
                    break;
                case WEST:  cookingSlot3 = new AxisAlignedBB(blockPos.getX() + 0.6825, blockPos.getY() + 0.1875, blockPos.getZ() + 0.625, blockPos.getX() + 0.9375 + 0.0001, blockPos.getY() + 0.4375 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(tileEntity.direction){
                case NORTH:
                case SOUTH:
                    ashSlot = new AxisAlignedBB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    ashSlot = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            switch(tileEntity.direction){
                case NORTH:
                case SOUTH:
                    tinderSlot = new AxisAlignedBB(blockPos.getX() + 0.0, blockPos.getY() + 0.0, blockPos.getZ() + 0.0625, blockPos.getX() + 1.0 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 0.75 + 0.0001);
                    break;
                case EAST:
                case WEST:
                    tinderSlot = new AxisAlignedBB(blockPos.getX() + 0.0625, blockPos.getY() + 0.0, blockPos.getZ() + 0.0, blockPos.getX() + 0.75 + 0.0001, blockPos.getY() + 0.0625 + 0.0001, blockPos.getZ() + 1.0 + 0.0001);
                    break;
            }

            if(fuelSlot.getCount() <= 16 && ItemTags.getAllTags().getTagOrEmpty(log).contains(itemstack.getItem())){
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                if(!fuelSlot.isEmpty()){
                    fuelSlot.setCount(fuelSlot.getCount() + 1);
                    tileEntity.getInventory().set(tileEntity.fuelSlotNumber, fuelSlot);
                }else{
                    ItemStack copy = itemstack.copy();
                    copy.setCount(1);
                    tileEntity.getInventory().set(tileEntity.fuelSlotNumber, copy);
                }
            }

            if(!fuelSlot.isEmpty() && itemstack.getItem() == Items.FLINT_AND_STEEL){
                tileEntity.isLit = true;
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }

            if(!fuelSlot.isEmpty()) {
                if (cookingSlot1.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot1().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot1Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.isCrouching() && !tileEntity.isLit) {
                            playerEntity.inventory.placeItemBackInInventory(world, tileEntity.getCookSlot1());
                            tileEntity.getInventory().set(tileEntity.cookSlot1Number, ItemStack.EMPTY);
                        }
                    }
                }

                if (cookingSlot2.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot2().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot2Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.isCrouching()) {
                            playerEntity.inventory.placeItemBackInInventory(world, itemstack);
                            tileEntity.getInventory().set(tileEntity.cookSlot2Number, ItemStack.EMPTY);
                        }
                    }
                }

                if (cookingSlot3.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot3().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot3Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.isCrouching()) {
                            playerEntity.inventory.placeItemBackInInventory(world, itemstack);
                            tileEntity.getInventory().set(tileEntity.cookSlot3Number, ItemStack.EMPTY);
                        }
                    }
                }

                if (cookingSlot4.contains(click(playerEntity, world))) {
                    if (tileEntity.getCookSlot4().isEmpty() && optional.isPresent()) {
                        tileEntity.placeFood(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.cookSlot4Number, optional.get().getCookingTime());
                        itemstack.shrink(1);
                        playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
                    } else {
                        if (playerEntity.isCrouching()) {
                            playerEntity.inventory.placeItemBackInInventory(world, itemstack);
                            tileEntity.getInventory().set(tileEntity.cookSlot4Number, ItemStack.EMPTY);
                        }
                    }
                }
            }

            if(ashSlot.contains(click(playerEntity, world))){
                tileEntity.placeItemStack(playerEntity.abilities.instabuild ? itemstack.copy() : itemstack, tileEntity.fuelSlotNumber);
                itemstack.shrink(1);
                playerEntity.setItemInHand(Hand.MAIN_HAND, itemstack);
            }else{
                if(playerEntity.isCrouching()){
                    playerEntity.inventory.placeItemBackInInventory(world, itemstack);
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

    @Override
    public void setPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity placer, ItemStack itemStack) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (tileEntity != null) {
            if (placer != null) {
                tileEntity.direction = placer.getDirection();
            }
        }
    }

    public void entityInside(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (!entity.fireImmune() && isLitCampfire(world, blockPos) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float)1);
        }

        super.entityInside(blockState, world, blockPos, entity);
    }

    private boolean isSmokeSource(BlockState p_220099_1_) {
        return p_220099_1_.is(Blocks.HAY_BLOCK);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState p_196271_3_, IWorld world, BlockPos blockPos, BlockPos p_196271_6_) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if(direction == Direction.DOWN){
            if(tileEntity != null){
                tileEntity.signalFire = isSmokeSource(p_196271_3_);
            }
        }

        return super.updateShape(blockState, direction, p_196271_3_, world, blockPos, p_196271_6_);
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    public static boolean canLight(World world, BlockPos blockPos) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        return !isLitCampfire(world, blockPos) && !blockState.getValue(BlockStateProperties.WATERLOGGED);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
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
        CampfireTileEntity tileEntity = (CampfireTileEntity)iWorld.getBlockEntity(blockPos);
        if (iWorld.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles((World)iWorld, blockPos);
            }
        }
        if (tileEntity != null) {
            tileEntity.dowse();
        }

    }

    public void onProjectileHit(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride)world.getBlockEntity(blockRayTraceResult.getBlockPos());

        if (!world.isClientSide && projectileEntity.isOnFire()) {
            Entity entity = projectileEntity.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, entity);
            if (flag && !isLitCampfire(world, blockRayTraceResult.getBlockPos()) && !blockState.getValue(WATERLOGGED)) {
                BlockPos blockpos = blockRayTraceResult.getBlockPos();
                if(tileEntity != null) {
                    tileEntity.isLit = true;
                }

            }
        }

    }

    public boolean placeLiquid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) iWorld.getBlockEntity(blockPos);
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
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        Random random = world.getRandom();

        if(tileEntity != null) {
            BasicParticleType basicparticletype = tileEntity.signalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
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
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if(tileEntity != null) {
            if(tileEntity.isLit) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void setDirection(World world, BlockPos blockPos, Direction direction) {
        CampfireTileEntityOverride tileEntity = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);
        if(tileEntity != null) {
            tileEntity.direction = direction;
        }
    }

    @Override
    public int getLightBlock(BlockState blockState, IBlockReader world, BlockPos blockPos) {
        CampfireTileEntityOverride tile = (CampfireTileEntityOverride) world.getBlockEntity(blockPos);

        if(tile != null && tile.isLit){
            return 16;
        }else{
            return 0;
        }
    }
}
