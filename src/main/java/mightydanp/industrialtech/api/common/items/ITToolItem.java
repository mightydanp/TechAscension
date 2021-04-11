package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;
import javafx.util.Pair;
import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainerProvider;
import mightydanp.industrialtech.api.common.items.capabilities.ITToolItemCapabilityProvider;
import mightydanp.industrialtech.api.common.items.handler.ITToolItemItemStackHandler;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class ITToolItem extends Item {


    private final Set<Block> effectiveBlocks;
    private final java.util.Map<ToolType, Integer> toolClasses = Maps.newHashMap();
    private final Item.Properties properties;
    private List<Pair<ITToolType, Integer>> toolType;

    public ITToolItem(Set<Block> effectiveBlocksIn, Properties propertiesIn) {
        super(propertiesIn.stacksTo(1));
        this.effectiveBlocks = effectiveBlocksIn;
        properties = propertiesIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStackIn, BlockState state) {
        if (getToolTypes(itemStackIn).stream().anyMatch(e -> state.isToolEffective(e))) {
            return getEfficiency(itemStackIn);
        }

        return this.effectiveBlocks.contains(state.getBlock()) ? getEfficiency(itemStackIn) : 1.0F;
    }

    @Override
    public java.util.Set<net.minecraftforge.common.ToolType> getToolTypes(ItemStack itemStackIn) {
        return toolClasses.keySet();
    }

    @Override
    public boolean isEnchantable(ItemStack itemStackIn) {
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack itemStackIn, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStackIn, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();

        if(nbt.contains("material_name") && !nbt.getString("material_name").equals("")){
            tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + "material:" + "\u00A7f" + " " + "\u00A7a" + getMaterialName(itemStackIn) + "\u00A7a"));
        }

        if(nbt.contains("harvest_levels") && nbt.contains("it_tool_types")) {
            StringBuilder levelString = new StringBuilder();
            int i = 0;
            for(int level : getHarvestLevel(itemStackIn)){
                if(i == 0){
                    i++;
                    levelString.append(level);
                }else{
                    levelString.append(", ").append(level);
                }
            }
            tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + nbt.getString("it_tool_types") + " level:" + "\u00A7f" + " " + "\u00A7a" + levelString + "\u00A7a"));
        }

        if(nbt.contains("efficiency")){
            tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + "efficiency:" + "\u00A7f" + " " + "\u00A7a" + getEfficiency(itemStackIn) + "\u00A7a"));
        }
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerEntityIn, Hand handIn) {
        ItemStack itemStackIn = playerEntityIn.getItemInHand(handIn);
        //worldIn.playSound((PlayerEntity)null, playerEntityIn.getX(), playerEntityIn.getY(), playerEntityIn.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        //playerEntityIn.getCooldowns().addCooldown(this, 20);
        if (!worldIn.isClientSide) {
            INamedContainerProvider itToolItemContainerProvider = new ITToolItemContainerProvider(this, itemStackIn);
            NetworkHooks.openGui((ServerPlayerEntity) playerEntityIn, itToolItemContainerProvider,
                    (packetBuffer)->{packetBuffer.writeInt(3);});
        }
        return ActionResult.sidedSuccess(itemStackIn, worldIn.isClientSide());
    }

    public void setHarvestLevel(ItemStack itemStackIn, List<Pair<ITToolType, Integer>> toolTypeIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        List<Integer> intList = new ArrayList<>();
        int[] intListNew = Ints.toArray(intList);

        StringBuilder itToolTypeString = new StringBuilder();
        int i = 0;
        for(Pair<ITToolType, Integer> toolType :toolTypeIn){
            ToolType newToolType = ToolType.get(toolType.getKey().getName());
            properties.addToolType(newToolType, toolType.getValue());
            intList.add(toolType.getValue());
            if(i == 0) {
                itToolTypeString.append(newToolType);
            }else{
                itToolTypeString.append(", ").append(newToolType);
            }
            i++;
        }
        nbt.putString("it_tool_types", itToolTypeString.toString());
        nbt.putIntArray("harvest_levels", intListNew);
        
    }

    public int[] getHarvestLevel(ItemStack itemStackIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if(nbt.contains("harvest_levels")){
            return nbt.getIntArray("harvest_levels");
        }else{
            return null;
        }
    }

    public void setAttackDamage(ItemStack itemStackIn, float attackDamageIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_damage", attackDamageIn);
    }

    public float getAttackDamage(ItemStack itemStackIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if(nbt.contains("attack_damage")){
            return nbt.getFloat("attack_damage");
        }else{
            return 0.00F;
        }
    }

    public void setAttackSpeed(ItemStack itemStackIn, float attackSpeedIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_speed", attackSpeedIn);
    }

    public float getAttackSpeed(ItemStack itemStackIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if(nbt.contains("attack_speed")){
            return nbt.getFloat("attack_speed");
        }else{
            return 0.00F;
        }

    }

    public void setEfficiency(ItemStack itemStackIn, float efficiencyIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("efficiency", efficiencyIn);
    }

    public float getEfficiency(ItemStack itemStackIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if(nbt.contains("efficiency")){
            return nbt.getFloat("efficiency");
        }else{
            return 0.00F;
        }
    }

    public void setMaterialName(ItemStack itemStackIn, String materialNameIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putString("material_name", materialNameIn);
    }

    public String getMaterialName(ItemStack itemStackIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if(nbt.contains("material_name")){
            return nbt.getString("material_name");
        }else{
            return "";
        }
    }

    public void setHeadColor(ItemStack itemStackIn, int colorIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("head_color", colorIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getHeadColor(ItemStack itemStackIn){
        if(itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("head_color")) {
                return nbt.getInt("head_color");
            } else {
                return 0xFFFFFFFF;
            }
        }else{
            return 0;
        }
    }

    public void setBindingColor(ItemStack itemStackIn, int colorIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("binding_color", colorIn);;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBindingColor(ItemStack itemStackIn){
        if(itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("binding_color")) {
                return nbt.getInt("binding_color");
            } else {
                return 0xFFFFFFFF;
            }
        }else{
            return 0;
        }
    }

    public void setHandleColor(ItemStack itemStackIn, int colorIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("handle_color", colorIn);
    }

    public int getHandleColor(ItemStack itemStackIn){
        if(itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("handle_color")) {
                return nbt.getInt("handle_color");
            } else {
                return 0xFFFFFFFF;
            }
        }else{
            return 0;
        }
    }

    public CompoundNBT getCompoundNBT(ItemStack itemStackIn) {
        return itemStackIn.getOrCreateTag();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            return ImmutableMultimap.of();
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlotType.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getIsHoldingShift() {
        long minecraftWindow = Minecraft.getInstance().getWindow().getWindow();
        return InputMappings.isKeyDown(minecraftWindow, GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {
        return new ITToolItemCapabilityProvider();
    }

    public ITToolItemItemStackHandler getItemStackHandler(ItemStack itemStack) {
        IItemHandler flowerBag = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (!(flowerBag instanceof ITToolItemItemStackHandler)) {
            IndustrialTech.LOGGER.error("ITToolItem did not have the expected ITEM_HANDLER_CAPABILITY");
            return new ITToolItemItemStackHandler(3);
        }
        return (ITToolItemItemStackHandler)flowerBag;
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        ITToolItemItemStackHandler itemStackHandlerFlowerBag = getItemStackHandler(stack);
        CompoundNBT capabilityTag = itemStackHandlerFlowerBag.serializeNBT();
        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) {
            combinedTag.put("base", baseTag);
        }
        if (capabilityTag != null) {
            combinedTag.put("capability", capabilityTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }
        CompoundNBT baseTag = nbt.getCompound("base");              // empty if not found
        CompoundNBT capabilityTag = nbt.getCompound("capability"); // empty if not found
        stack.setTag(baseTag);
        ITToolItemItemStackHandler itemStackHandlerFlowerBag = getItemStackHandler(stack);
        itemStackHandlerFlowerBag.deserializeNBT(capabilityTag);
    }
}