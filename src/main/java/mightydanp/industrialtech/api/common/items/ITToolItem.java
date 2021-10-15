package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.capabilities.ITToolItemCapabilityProvider;
import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemItemStackHandler;
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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class ITToolItem extends Item {


    private final Set<Block> effectiveBlocks;
    private final Item.Properties properties;
    public final int partsToWork;
    public boolean preformAction;
    public List<Pair<Item, Integer>> craftingToolsNeeded;
    public List<Pair<Item, Integer>> toolParts;
    public List<Item> toolsNeededForDisassemble;

    public ITToolItem(Set<Block> effectiveBlocksIn, Properties propertiesIn, List<Pair<Item, Integer>> craftingToolsNeededIn, List<Pair<Item, Integer>> partsNeededToBuildIn, List<Item> toolsNeededForDisassembleIn) {
        super(propertiesIn.stacksTo(1));
        craftingToolsNeeded = craftingToolsNeededIn;
        toolParts = partsNeededToBuildIn;
        effectiveBlocks = effectiveBlocksIn;
        properties = propertiesIn;
        partsToWork = partsNeededToBuildIn.size();
        toolsNeededForDisassemble = toolsNeededForDisassembleIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStackIn) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStackIn, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        List<ITextComponent> tooltipList = tooltip;
        CompoundNBT nbt = itemStackIn.getOrCreateTag();

        tooltip.add(ITextComponent.nullToEmpty(""));

        if (!getPartsDamage(itemStackIn).isEmpty()) {
            for (Pair<String, String> pair : getPartsDamage(itemStackIn)) {
                tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + pair.getFirst() + ":" + "\u00A7f" + " " + "\u00A7a" + pair.getSecond() + "\u00A7a"));
            }
        }

        tooltip.add(ITextComponent.nullToEmpty(""));

        if (nbt.contains("harvest_levels") && nbt.contains("it_tool_types")) {
            Map<ToolType, Integer> toolTypeList = getHarvestLevelsList(itemStackIn);
            for (int i = 0; i < toolTypeList.size(); i++) {
                String toolTypeName = ((ToolType) (toolTypeList.keySet().toArray()[i])).getName();
                int toolTypeLevel = (int) toolTypeList.values().toArray()[i];
                tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + toolTypeName + " level:" + "\u00A7f" + " " + "\u00A7a" + toolTypeLevel + "\u00A7a"));
            }
        }

        if (nbt.contains("efficiency")) {
            tooltip.add(ITextComponent.nullToEmpty("\u00A7f" + "efficiency:" + "\u00A7f" + " " + "\u00A7a" + getEfficiency(itemStackIn) + "\u00A7a"));
        }
    }

    @Override
    public boolean mineBlock(ItemStack itemStackIn, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.requiresCorrectToolForDrops()) {
            if (canWork(itemStackIn)) {
                for (ToolType toolType : itemStackIn.getToolTypes()) {
                    if (state.isToolEffective(toolType)) {
                        damageToolParts(entityLiving.getMainHandItem(), (PlayerEntity) entityLiving, worldIn, 1);
                    } else {
                        damageToolParts(entityLiving.getMainHandItem(), (PlayerEntity) entityLiving, worldIn, 2);
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(ItemStack itemStackIn, BlockState blockStateIn) {
        if (canWork(itemStackIn)) {
            super.canHarvestBlock(itemStackIn, blockStateIn);
        } else {
            return false;
        }
        return super.canHarvestBlock(itemStackIn, blockStateIn);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStackIn, BlockState state) {
        boolean canWork = canWork(itemStackIn);

        if (canWork) {
            if (getToolTypes(itemStackIn).stream().anyMatch(e -> state.isToolEffective(e))) {
                return getEfficiency(itemStackIn);
            } else {
                return 1.0F;
            }
        }else{
            return 1.0F;
        }
    }

    @Override
    public java.util.Set<net.minecraftforge.common.ToolType> getToolTypes(ItemStack itemStackIn) {
        return getHarvestLevelsList(itemStackIn).keySet();
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerEntityIn, Hand handIn) {
        ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
        ItemStack offHandItemStack = playerEntityIn.getOffhandItem();
        ItemStack toolHeadOnTool = getItemStackHandler(mainHandItemStack).getToolHead();
        ItemStack toolBindingOnTool = getItemStackHandler(mainHandItemStack).getToolBinding();
        ItemStack toolHandleOnTool = getItemStackHandler(mainHandItemStack).getToolHandle();

        if (canWork(mainHandItemStack) && preformAction) {
            if(preformAction) {
                damageToolParts(playerEntityIn.getMainHandItem(), playerEntityIn, worldIn, 1);
            }
        }

        if (!canWork(mainHandItemStack)){
            disassembleTool(playerEntityIn.getMainHandItem(), playerEntityIn, worldIn, 1, toolsNeededForDisassemble);
            playerEntityIn.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }

        //playerEntityIn.getCooldowns().addCooldown(this, 20);
        /*
        if (!worldIn.isClientSide) {
            INamedContainerProvider itToolItemContainerProvider = new ITToolItemContainerProvider(this, itemStackIn);
            NetworkHooks.openGui((ServerPlayerEntity) playerEntityIn, itToolItemContainerProvider,
                    (packetBuffer)->{packetBuffer.writeInt(40);});
        }
        */
        return ActionResult.sidedSuccess(mainHandItemStack, worldIn.isClientSide());
    }

    @Override
    public void onUseTick(World worldIn, LivingEntity playerEntityIn, ItemStack itemStack, int p_219972_4_) {
        /*if(canWork(itemStack) && preformAction) {
            damageItemstack(playerEntityIn.getMainHandItem(), (PlayerEntity) playerEntityIn, worldIn, null, 1);
        }*/
    }

    public void setHarvestLevel(ItemStack itemStackIn, List<Pair<ToolType, Integer>> toolTypeIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        int[] harvestArray = new int[toolTypeIn.size()];
        StringBuilder itToolTypeString = new StringBuilder();

        for (Pair<ToolType, Integer> toolType : toolTypeIn) {
            for (int i = 0; i < toolTypeIn.size(); i++) {
                if (i == 0) {
                    harvestArray[i] = toolType.getSecond();
                    itToolTypeString.append(toolType.getFirst().getName());
                } else {
                    harvestArray[i] = toolType.getSecond();
                    itToolTypeString.append("_").append(toolType.getFirst().getName());
                }
            }
        }
        nbt.putString("it_tool_types", String.valueOf(itToolTypeString));
        nbt.putIntArray("harvest_levels", harvestArray);
    }

    public Map<ToolType, Integer> getHarvestLevelsList(ItemStack itemStackIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        Map<ToolType, Integer> toolTypes = new HashMap<>();
        int[] intArray = nbt.getIntArray("harvest_levels");
        String[] stringArray = nbt.getString("it_tool_types").split("_");
        for (int i = 0; i < intArray.length; i++) {
            toolTypes.putIfAbsent(ToolType.get(stringArray[i]), intArray[i]);
        }
        return toolTypes;
    }

    @Override
    public int getHarvestLevel(ItemStack itemStackIn, net.minecraftforge.common.ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return canWork(itemStackIn) ? getHarvestLevelsList(itemStackIn).getOrDefault(tool, -1) : - 1;
    }

    public void setAttackDamage(ItemStack itemStackIn, float attackDamageIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_damage", attackDamageIn);
    }

    public float getAttackDamage(ItemStack itemStackIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("attack_damage")) {
            return nbt.getFloat("attack_damage");
        } else {
            return 0.00F;
        }
    }

    public void setAttackSpeed(ItemStack itemStackIn, float attackSpeedIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_speed", attackSpeedIn);
    }

    public float getAttackSpeed(ItemStack itemStackIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("attack_speed")) {
            return nbt.getFloat("attack_speed");
        } else {
            return 0.00F;
        }

    }

    public void setEfficiency(ItemStack itemStackIn, float efficiencyIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("efficiency", efficiencyIn);
    }

    public float getEfficiency(ItemStack itemStackIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("efficiency")) {
            return nbt.getFloat("efficiency");
        } else {
            return 0.00F;
        }
    }

    public void setMaterialName(ItemStack itemStackIn, String materialNameIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putString("material_name", materialNameIn);
    }

    public String getMaterialName(ItemStack itemStackIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("material_name")) {
            return nbt.getString("material_name");
        } else {
            return "";
        }
    }

    public void setHeadColor(ItemStack itemStackIn, int colorIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("head_color", colorIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getHeadColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("head_color")) {
                return nbt.getInt("head_color");
            } else {
                return 0xFFFFFFFF;
            }
        } else {
            return 0;
        }
    }

    public void setBindingColor(ItemStack itemStackIn, int colorIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("binding_color", colorIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBindingColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("binding_color")) {
                return nbt.getInt("binding_color");
            } else {
                return 0xFFFFFFFF;
            }
        } else {
            return 0;
        }
    }

    public void setHandleColor(ItemStack itemStackIn, int colorIn) {
        CompoundNBT nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("handle_color", colorIn);
    }

    public int getHandleColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundNBT nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("handle_color")) {
                return nbt.getInt("handle_color");
            } else {
                return 0xFFFFFFFF;
            }
        } else {
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
        return (ITToolItemItemStackHandler) flowerBag;
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

    public boolean canWork(ItemStack itemStackIn){
        ITToolItemItemStackHandler handler = getItemStackHandler(itemStackIn);
        ITToolItem toolItem = (ITToolItem) itemStackIn.getItem();
        if ((toolItem.partsToWork == 1 || toolItem.partsToWork == 2 || toolItem.partsToWork == 3) & handler.getToolHead() != null) {
            if(handler.getToolHead().getDamageValue() < handler.getToolHead().getMaxDamage()){
                return true;
            }
        }

        if ((toolItem.partsToWork == 2 || toolItem.partsToWork == 3) && handler.getToolHandle() != null && handler.getToolHead() != null) {
            if(handler.getToolHandle().getDamageValue() < handler.getToolHandle().getMaxDamage() && handler.getToolHead().getDamageValue() < handler.getToolHead().getMaxDamage()){
                return true;
            }
        }

        if (toolItem.partsToWork == 3 && handler.getToolHandle() != null && handler.getToolHead() != null && handler.getToolBinding() != null) {
            if(handler.getToolHandle().getDamageValue() < handler.getToolHandle().getMaxDamage() && handler.getToolHead().getDamageValue() < handler.getToolHead().getMaxDamage() && handler.getToolBinding().getDamageValue() < handler.getToolBinding().getMaxDamage()){
                return true;
            }
        }

        return false;
    }

    public Boolean damageToolParts(ItemStack itemStackIn, PlayerEntity playerIn, World worldIn, int amountIn) {
        ITToolItemItemStackHandler itemStackHandler = getItemStackHandler(itemStackIn);
        ITToolItem toolItem = (ITToolItem) itemStackIn.getItem();
        ItemStack toolHeadOnTool = itemStackHandler.getToolHead();
        ItemStack toolBindingOnTool = itemStackHandler.getToolBinding();
        ItemStack toolHandleOnTool = itemStackHandler.getToolHandle();

        if (toolHeadOnTool != null && (toolItem.partsToWork == 3 || toolItem.partsToWork == 2 || toolItem.partsToWork == 1)) {
            int headDamage = toolHeadOnTool.getDamageValue();
            int headMaxDamage = toolHeadOnTool.getMaxDamage();

            if (headDamage != headMaxDamage) {
                toolHeadOnTool.setDamageValue(headDamage + amountIn);
                return true;
            } else {
                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                return false;
            }
        }

        if (toolHandleOnTool != null && (toolItem.partsToWork == 3 || toolItem.partsToWork == 2)){
            int handleDamage = toolHandleOnTool.getDamageValue();
            int handleMaxDamage = toolHandleOnTool.getMaxDamage();

            if (handleDamage != handleMaxDamage) {
                toolHandleOnTool.setDamageValue(handleDamage + amountIn);
                return true;
            } else {
                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                return false;
            }
        }

        if (toolBindingOnTool != null && toolItem.partsToWork == 3){
            int bindingDamage = toolBindingOnTool.getDamageValue();
            int bindingMaxDamage = toolBindingOnTool.getMaxDamage();
            if (bindingDamage != bindingMaxDamage) {
                toolBindingOnTool.setDamageValue(bindingDamage + amountIn);
                return true;
            } else {
                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                return false;
            }
        }

        if(!canWork(itemStackIn)){
            return false;
        }

        return true;
    }

    public void disassembleTool(ItemStack itemStackIn, PlayerEntity playerIn, World worldIn, int toolInDamage, List<Item> toolNeededIn) {
        ITToolItemItemStackHandler itemStackHandler = getItemStackHandler(itemStackIn);
        ItemStack toolHeadItemStack = itemStackHandler.getToolHead();
        ItemStack toolBindingItemStack = itemStackHandler.getToolBinding();
        ItemStack toolHandleItemStack = itemStackHandler.getToolHandle();

        damageToolsNeededInPlayerInventory(playerIn, worldIn, toolInDamage, toolNeededIn);

        if (toolHandleItemStack != null){
            if (playerIn.inventory.canPlaceItem(1, toolHandleItemStack)) {
                playerIn.inventory.add(toolHandleItemStack);
                itemStackHandler.setToolHandle(ItemStack.EMPTY);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            } else {
                worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolHandleItemStack));
            }

        }

        if (toolHeadItemStack != null){
            if (playerIn.inventory.canPlaceItem(1, toolHeadItemStack)) {
                playerIn.inventory.add(toolHeadItemStack);
                itemStackHandler.setToolHead(ItemStack.EMPTY);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            } else {
                worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolHeadItemStack));
            }
        }

        if (toolBindingItemStack != null){
            if (playerIn.inventory.canPlaceItem(1, toolBindingItemStack)) {
                playerIn.inventory.add(toolBindingItemStack);
                itemStackHandler.setToolBinding(ItemStack.EMPTY);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            } else {
                worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolBindingItemStack));
            }
        }

        if (toolHandleItemStack == null && toolHeadItemStack == null && toolBindingItemStack == null) {

        }
    }

    public void damageToolsNeededInPlayerInventory(PlayerEntity playerIn, World worldIn, int Damage, List<Item> toolNeededIn){
        if(inventoryToolCheck(playerIn, toolNeededIn) && toolNeededIn.size() != 0){
            for(Item toolThatIsNeeded : toolNeededIn){
                for(int i = 9; i <= 45; i++){
                    if (toolThatIsNeeded == playerIn.inventory.getItem(i).getItem()){
                        toolNeededIn.remove(playerIn.inventory.getItem(i).getItem());
                        ItemStack toolItem = playerIn.inventory.getItem(i);
                        if(toolThatIsNeeded instanceof ITToolItem) {
                            damageToolParts(toolItem, playerIn, worldIn, 1);
                        }else{
                            if(toolItem.getMaxDamage() > 0) {
                                toolItem.setDamageValue(toolItem.getDamageValue() + Damage);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean inventoryToolCheck(PlayerEntity playerIn, List<Item> toolNeededIn){
        for(int i = 9; i <= 45; i++){
            ItemStack toolNeeded = playerIn.inventory.getItem(i);
            if(toolNeededIn.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof ITToolItem){
                    ITToolItem toolNeededItem = (ITToolItem)playerIn.inventory.getItem(i).getItem();
                    ITToolItemItemStackHandler itemStackHandler = getItemStackHandler(toolNeeded);

                    if((toolNeededItem.partsToWork == 1 || toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHead() != null){
                        if(itemStackHandler.getToolHead().getDamageValue() < itemStackHandler.getToolHead().getMaxDamage()) {
                            toolNeededIn.remove(toolNeeded.getItem());
                        }
                    }

                    if((toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHandle() != null){
                       if(itemStackHandler.getToolHandle().getDamageValue() < itemStackHandler.getToolHandle().getDamageValue()){
                           toolNeededIn.remove(toolNeeded.getItem());
                       }
                    }

                    if(toolNeededItem.partsToWork == 3 & itemStackHandler.getToolBinding() != null){
                       if(itemStackHandler.getToolBinding().getDamageValue() < itemStackHandler.getToolBinding().getDamageValue()){
                           toolNeededIn.remove(toolNeeded.getItem());
                       }
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage()) {
                        toolNeededIn.remove(toolNeeded.getItem());
                    }
                }
            }
        }
        return toolNeededIn.size() == 0;
    }

    public List<Pair<String, String>> getPartsDamage(ItemStack itemstackIn) {
        ITToolItemItemStackHandler itemStackHandler = getItemStackHandler(itemstackIn);
        ItemStack toolHeadOnTool = itemStackHandler.getToolHead();
        ItemStack toolBindingOnTool = itemStackHandler.getToolBinding();
        ItemStack toolHandleOnTool = itemStackHandler.getToolHandle();

        List<Pair<String, String>> list = new ArrayList<>();

        if (toolHandleOnTool != null) {
            list.add(new Pair<>(toolHandleOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolHandleOnTool.getMaxDamage() - toolHandleOnTool.getDamageValue() + "/" + toolHandleOnTool.getMaxDamage()));
        }

        if (toolHeadOnTool != null) {
            list.add(new Pair<>(toolHeadOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolHeadOnTool.getMaxDamage() - toolHeadOnTool.getDamageValue() + "/" + toolHeadOnTool.getMaxDamage()));
        }

        if (toolBindingOnTool != null) {
            list.add(new Pair<>(toolBindingOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolBindingOnTool.getMaxDamage() - toolBindingOnTool.getDamageValue() + "/" + toolBindingOnTool.getMaxDamage()));
        }

        return list;
    }

    public int getPartsToWork() {
        return partsToWork;
    }
}