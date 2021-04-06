package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class ITToolItem extends Item {
    private final Set<Block> effectiveBlocks;
    private final java.util.Map<ToolType, Integer> toolClasses = Maps.newHashMap();
    private final Item.Properties properties;

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

    public void setHarvestLevel(net.minecraftforge.common.ToolType tool, int levelIn) {
        properties.addToolType(tool, levelIn);
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
        super.appendHoverText(itemStackIn, worldIn, tooltip, flagIn);
        CompoundNBT nbt = itemStackIn.getOrCreateTag();

        if(nbt.contains("material_name")){
            tooltip.add(ITextComponent.nullToEmpty("material_name:" + nbt.getString("material_name")));
        }

        if(nbt.contains("attack_damage")){
            tooltip.add(ITextComponent.nullToEmpty("attack_damage:" + nbt.getFloat("attack_damage")));
        }else{
            tooltip.add(ITextComponent.nullToEmpty("attack_damage:" + "null"));
        }

        if(nbt.contains("attack_speed")){
            tooltip.add(ITextComponent.nullToEmpty("attack_speed:" + nbt.getFloat("attack_speed")));
        }else{
            tooltip.add(ITextComponent.nullToEmpty("attack_speed:" + "null"));
        }

        if(nbt.contains("efficiency")){
            tooltip.add(ITextComponent.nullToEmpty("efficiency:" + nbt.getFloat("efficiency")));
        }else{
            tooltip.add(ITextComponent.nullToEmpty("efficiency:" + "null"));
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
            return 0;
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
            return 0;
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
            return 0;
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
                return 0;
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
                return 0;
            }
        }else{
            return 0;
        }
    }

    public void setColorHandleLayer(ItemStack itemStackIn, int colorIn){
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("handle_Color", colorIn);
    }

    public int getHandleColor(ItemStack itemStackIn){
        if(itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("handle_color")) {
                return nbt.getInt("handle_color");
            } else {
                return 0;
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
}