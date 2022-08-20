package mightydanp.techcore.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mightydanp.techcore.client.settings.keybindings.KeyBindings;
import mightydanp.techcore.common.handler.itemstack.TCToolItemInventoryHelper;
import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.DullHeadItem;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class TCToolItem extends Item {
    public String name;
    public List<String> effectiveBlocks = new ArrayList<>();
    public boolean preformAction;
    public Map<String, Integer> assembleItems = new HashMap<>();
    public Map<String, HandleItem> handles = new HashMap<>();
    public Map<String, DullHeadItem> dullHeads = new HashMap<>();
    public Map<String, HeadItem> heads = new HashMap<>();
    public Map<String, BindingItem> bindings = new HashMap<>();

    public Integer parts = 0;
    public List<String> disassembleItems = new ArrayList<>();
    //public TCToolItemInventoryHelper inventory = new TCToolItemInventoryHelper();

    public TCToolItem(Properties propertiesIn) {
        super(propertiesIn.tab(TCCreativeModeTab.tool_tab).stacksTo(1));
    }

    public TCToolItemInventoryHelper getInventory(ItemStack itemStackIn){
        return new TCToolItemInventoryHelper(itemStackIn);
    }

    public TagKey<Block> getToolTag(String toolName){
        return BlockTags.create(new ResourceLocation("forge", "tool/" + toolName));
    }

    public TCToolItem setName(String name) {
        this.name = name;

        return this;
    }

    public TCToolItem setEffectiveBlocks(Set<String> effectiveBlocksIn) {
        effectiveBlocks.addAll(effectiveBlocksIn);
        return this;
    }

    public TCToolItem setAssembleItems(Map<String, Integer> assembleItemsIn) {
        assembleItems.putAll(assembleItemsIn);
        return this;
    }

    public TCToolItem setParts(Integer parts) {
        this.parts = parts;
        return this;
    }

    public TCToolItem setDisassembleItems(List<String> disassembleItemsIn) {
        disassembleItems.addAll(disassembleItemsIn);
        return this;
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
    public void appendHoverText(ItemStack itemStackIn, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();

        tooltip.add(Component.nullToEmpty(""));

        if (!getPartsDamage(itemStackIn).isEmpty()) {
            getPartsDamage(itemStackIn).forEach(((s1, s2) -> tooltip.add(Component.nullToEmpty("\u00A7f" + s1 + ":" + "\u00A7f" + " " + "\u00A7a" + s2 + "\u00A7a"))));
        }

        tooltip.add(Component.nullToEmpty(""));

        if (nbt.contains("tool_levels") && nbt.contains("tool_names")) {
            Map<String, Integer> toolList = getToolLevelsList(itemStackIn);
            for (int i = 0; i < toolList.size(); i++) {
                String toolName = toolList.keySet().stream().toList().get(i);
                int toolLevel = toolList.values().stream().toList().get(i);
                tooltip.add(Component.nullToEmpty("\u00A7f" + toolName + " level:" + "\u00A7f" + " " + "\u00A7a" + toolLevel + "\u00A7a"));
            }
        }

        if (nbt.contains("efficiency")) {
            tooltip.add(Component.nullToEmpty("\u00A7f" + "efficiency:" + "\u00A7f" + " " + "\u00A7a" + getEfficiency(itemStackIn) + "\u00A7a"));
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean mineBlock(ItemStack itemStack,  Level world, BlockState blockState, BlockPos blockPos, LivingEntity entityLiving) {
        if(blockState.requiresCorrectToolForDrops()) {
            if (canWork(itemStack)) {
                if (blockState.requiresCorrectToolForDrops()) {
                    if (isCorrectToolForDrops(itemStack, blockState)) {
                        damageToolParts(entityLiving.getMainHandItem(), (Player) entityLiving, world, 1);
                        return true;
                    } else {
                        damageToolParts(entityLiving.getMainHandItem(), (Player) entityLiving, world, 2);
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyed(ItemEntity p_150887_) {
        super.onDestroyed(p_150887_);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStackIn, BlockState state) {
        if (canWork(itemStackIn)) {
            if (state.requiresCorrectToolForDrops()) {
                return getEfficiency(itemStackIn);
            } else {
                return 1F;
                
            }
        }
        return 1F;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerEntityIn, InteractionHand handIn) {
        if(playerEntityIn.isShiftKeyDown() && KeyBindings.handCrafting.isDown()){
            handToolDisassemble(worldIn, playerEntityIn, handIn);
        }

        return InteractionResultHolder.success(playerEntityIn.getItemInHand(handIn));
    }

    public void handToolDisassemble(Level worldIn, Player playerEntityIn, InteractionHand handIn){
        ItemStack handItemStack = playerEntityIn.getItemInHand(handIn);

        if (canWork(handItemStack) && preformAction) {
            damageToolParts(handItemStack, playerEntityIn, worldIn, 1);
        }
        HashSet<String> playerInventory = new HashSet<>(playerEntityIn.getInventory().items.stream().map(itemStack -> Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString()).toList());
        if (!canWork(handItemStack) && playerInventory.containsAll(disassembleItems)) {
            disassembleTool(playerEntityIn.getMainHandItem(), playerEntityIn, worldIn, 2, disassembleItems);
            playerEntityIn.setItemInHand(handIn, ItemStack.EMPTY);
        }
    }

    @Override
    public void onUseTick(Level worldIn, LivingEntity playerEntityIn, ItemStack itemStack, int p_219972_4_) {
        /*if(canWork(itemStack) && preformAction) {
            damageItemstack(playerEntityIn.getMainHandItem(), (PlayerEntity) playerEntityIn, worldIn, null, 1);
        }*/
    }

    public void setToolLevel(ItemStack itemStackIn, Map<String, Integer> toolMapIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        int[] harvestArray = new int[toolMapIn.size()];
        StringBuilder itToolTypeString = new StringBuilder();

        toolMapIn.forEach(((toolName, integer) -> {
            for (int i = 0; i < toolMapIn.size(); i++) {
                if (i == 0) {
                    harvestArray[i] = integer;
                    itToolTypeString.append(toolName);
                } else {
                    harvestArray[i] = integer;
                    itToolTypeString.append(", ").append(toolName);
                }
            }
        }));

        nbt.putString("tool_names", String.valueOf(itToolTypeString));
        nbt.putIntArray("tool_levels", harvestArray);
    }

    public Map<String, Integer> getToolLevelsList(ItemStack itemStackIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        Map<String, Integer> toolMap = new HashMap<>();
        String[] toolNames = nbt.getString("tool_names").split(", ");
        int[] toolLevels = nbt.getIntArray("tool_levels");

        for (int i = 0; i < toolLevels.length; i++) {
            toolMap.putIfAbsent(toolNames[i], toolLevels[i]);
            //toolMap.putIfAbsent((IToolType)ICJsonConfigs.toolType.getFirst().registryMap.get(toolNames[i]), BlockTags.create(new ResourceLocation("harvest_levels/" + toolLevels[i])));
        }
        return toolMap;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        AtomicBoolean isCorrect = new AtomicBoolean(true);

        if(canWork(stack)) {

            if (state.requiresCorrectToolForDrops()) {
                getToolLevelsList(stack).forEach((toolName, toolLevel) -> {
                    if (state.is(getToolTag(toolName))) {

                        for (int i = 0; i <= toolLevel; i++) {
                            if (!state.is(BlockTags.create(new ResourceLocation("forge", "tool_level/" + i)))) {
                                isCorrect.set(false);
                            }
                        }
                    } else {
                        isCorrect.set(false);
                    }
                });

                if (!isCorrect.get()) {
                    List<Block> blocks = new ArrayList<>();

                    effectiveBlocks.forEach(string -> {
                        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
                        blocks.add(block);
                    });

                    return blocks.contains(state.getBlock());
                }
            } else {
                isCorrect.set(true);
            }
        } else {
            isCorrect.set(false);
        }

        return isCorrect.get();
    }

    public void setAttackDamage(ItemStack itemStackIn, float attackDamageIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_damage", attackDamageIn);
    }

    public float getAttackDamage(ItemStack itemStackIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("attack_damage")) {
            return nbt.getFloat("attack_damage");
        } else {
            return 0.00F;
        }
    }

    public void setAttackSpeed(ItemStack itemStackIn, float attackSpeedIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("attack_speed", attackSpeedIn);
    }

    public float getAttackSpeed(ItemStack itemStackIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("attack_speed")) {
            return nbt.getFloat("attack_speed");
        } else {
            return 0.00F;
        }

    }

    public void setEfficiency(ItemStack itemStackIn, float efficiencyIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putFloat("efficiency", efficiencyIn);
    }

    public float getEfficiency(ItemStack itemStackIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("efficiency")) {
            return nbt.getFloat("efficiency");
        } else {
            return 0.00F;
        }
    }

    public void setMaterialName(ItemStack itemStackIn, String materialNameIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putString("material_name", materialNameIn);
    }

    public String getMaterialName(ItemStack itemStackIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        if (nbt.contains("material_name")) {
            return nbt.getString("material_name");
        } else {
            return "";
        }
    }

    public void setHeadColor(ItemStack itemStackIn, int colorIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("head_color", colorIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getHeadColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
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
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("binding_color", colorIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBindingColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
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
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("handle_color", colorIn);
    }

    public int getHandleColor(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("handle_color")) {
                return nbt.getInt("handle_color");
            } else {
                return 0xFFFFFFFF;
            }
        } else {
            return 0;
        }
    }

    public CompoundTag getCompoundNBT(ItemStack itemStackIn) {
        return itemStackIn.getOrCreateTag();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            return ImmutableMultimap.of();
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean getIsHoldingShift() {
        long minecraftWindow = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(minecraftWindow, GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        if(stack.getTag() != null) {
            CompoundTag inventory = stack.getTag().getCompound("inventory");
            ContainerHelper.saveAllItems(inventory, getInventory(stack).inventory);
            stack.getTag().put("inventory", inventory);
        }

        return stack.getTag();
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }

        if(stack.getTag() != null) {
            CompoundTag inventory = stack.getTag().getCompound("inventory");

            ContainerHelper.loadAllItems(inventory, getInventory(stack).inventory);

            nbt.put("inventory", inventory);
        }
    }

    public boolean canWork(ItemStack itemStackIn){
        List<Boolean> check = new ArrayList<>();

        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);

            if (toolItem.parts  >= 3 && inventory.getToolHandle(itemStackIn) != null && inventory.getToolHead(itemStackIn) != null && inventory.getToolBinding(itemStackIn) != null) {
                if (inventory.getToolHandle(itemStackIn).getDamageValue() < inventory.getToolHandle(itemStackIn).getMaxDamage() && inventory.getToolHead(itemStackIn).getDamageValue() < inventory.getToolHead(itemStackIn).getMaxDamage() && inventory.getToolBinding(itemStackIn).getDamageValue() < inventory.getToolBinding(itemStackIn).getMaxDamage()) {
                    check.add(true);
                }else{
                    check.add(false);
                }
            }else{
                check.add(false);
            }

            if (toolItem.parts >= 2 && inventory.getToolHandle(itemStackIn) != null && inventory.getToolHead(itemStackIn) != null) {
                if (inventory.getToolHandle(itemStackIn).getDamageValue() < inventory.getToolHandle(itemStackIn).getMaxDamage() && inventory.getToolHead(itemStackIn).getDamageValue() < inventory.getToolHead(itemStackIn).getMaxDamage()) {
                    check.add(true);
                }else{
                    check.add(false);
                }
            }else{
                check.add(false);
            }

            if (toolItem.parts >= 1 & inventory.getToolHead(itemStackIn) != null) {
                if (inventory.getToolHead(itemStackIn).getDamageValue() < inventory.getToolHead(itemStackIn).getMaxDamage()) {
                    check.add(true);
                }else{
                    check.add(false);
                }
            }else{
                check.add(false);
            }
        }

        return !check.contains(false);
    }

    public void damageToolParts(ItemStack itemStackIn, Player playerIn, Level worldIn, int amountIn) {
        Random random = new Random();


        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);

            ItemStack toolHeadOnTool = inventory.getToolHead(itemStackIn);
            ItemStack toolBindingOnTool = inventory.getToolBinding(itemStackIn);
            ItemStack toolHandleOnTool = inventory.getToolHandle(itemStackIn);

            if (toolHeadOnTool != null && toolItem.parts  >= 1) {
                int headDamage = toolHeadOnTool.getDamageValue();
                int headMaxDamage = toolHeadOnTool.getMaxDamage();

                if (headDamage != headMaxDamage) {
                    toolHeadOnTool.setDamageValue(headDamage + amountIn);
                    inventory.setToolHead(itemStackIn, toolHeadOnTool);
                }

                if(headDamage == headMaxDamage - 1){
                    playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                }


            }

            if (toolHandleOnTool != null && toolItem.parts  >= 2) {
                int handleDamage = toolHandleOnTool.getDamageValue();
                int handleMaxDamage = toolHandleOnTool.getMaxDamage();

                if (handleDamage != handleMaxDamage) {
                    toolHandleOnTool.setDamageValue(handleDamage + amountIn);
                    inventory.setToolHandle(itemStackIn, toolHandleOnTool);
                }

                if(handleDamage == handleMaxDamage - 1){
                    playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                }
            }

            if (toolBindingOnTool != null && toolItem.parts  >= 3) {
                int bindingDamage = toolBindingOnTool.getDamageValue();
                int bindingMaxDamage = toolBindingOnTool.getMaxDamage();
                if (bindingDamage != bindingMaxDamage) {
                    toolBindingOnTool.setDamageValue(bindingDamage + amountIn);
                    inventory.setToolBinding(itemStackIn, toolBindingOnTool);
                }

                if(bindingDamage == bindingMaxDamage - 1){
                    playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                }
            }
        }

    }

    public void disassembleTool(ItemStack itemStackIn, Player playerIn, Level worldIn, int toolInDamage, List<String> toolNeededIn) {
        Random random = new Random();

        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);
            ItemStack toolHandleItemStack = inventory.getToolHandle(itemStackIn);
            ItemStack toolHeadItemStack = inventory.getToolHead(itemStackIn);
            ItemStack toolBindingItemStack = inventory.getToolBinding(itemStackIn);

            damageToolsNeededInPlayerInventory(playerIn, worldIn, toolInDamage, toolNeededIn);

            if (toolHandleItemStack != null) {
                if(toolHandleItemStack.isDamageableItem()) {
                    if (toolHandleItemStack.getDamageValue() == toolHandleItemStack.getMaxDamage()) {
                        if (toolHandleItemStack.getItem() instanceof HandleItem part) {
                            part.setBroken(true);
                        }
                    }

                    if (playerIn.getInventory().canPlaceItem(1, toolHandleItemStack)) {
                        playerIn.getInventory().add(toolHandleItemStack);
                        inventory.setToolHandle(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolHandleItemStack));
                    }
                }

            }

            if (toolHeadItemStack != null) {
                if(toolHeadItemStack.isDamageableItem()) {
                    if (toolHeadItemStack.getDamageValue() == toolHeadItemStack.getMaxDamage()) {
                        if (toolHeadItemStack.getItem() instanceof HeadItem part) {
                            part.setBroken(true);
                        }
                    }

                    if (playerIn.getInventory().canPlaceItem(1, toolHeadItemStack)) {
                        playerIn.getInventory().add(toolHeadItemStack);
                        inventory.setToolHead(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolHeadItemStack));
                    }
                }
            }

            if (toolBindingItemStack != null) {
                if(toolBindingItemStack.isDamageableItem()) {
                    if (toolBindingItemStack.getDamageValue() == toolBindingItemStack.getMaxDamage()) {
                        if (toolBindingItemStack.getItem() instanceof BindingItem part) {
                            part.setBroken(true);
                        }
                    }else{
                        if (toolBindingItemStack.getItem() instanceof BindingItem part) {
                            part.setBroken(true);
                        }
                        toolBindingItemStack.setDamageValue(toolBindingItemStack.getMaxDamage());
                    }
                    if (playerIn.getInventory().canPlaceItem(1, toolBindingItemStack)) {
                        playerIn.getInventory().add(toolBindingItemStack);
                        inventory.setToolBinding(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), toolBindingItemStack));
                    }
                }


            }
        }
    }

    public void damageToolsNeededInPlayerInventory(Player playerIn, Level worldIn, int Damage, List<String> toolNeededIn){


        if(inventoryToolCheck(playerIn, getItemsFromForge(toolNeededIn)) && toolNeededIn.size() != 0){
            for(Item toolThatIsNeeded : getItemsFromForge(toolNeededIn)){
                for(int i = 9; i <= 45; i++){
                    if (toolThatIsNeeded == playerIn.getInventory().getItem(i).getItem()){
                        toolNeededIn.remove(String.valueOf(playerIn.getInventory().getItem(i).getItem().getRegistryName()));
                        ItemStack toolItem = playerIn.getInventory().getItem(i);
                        if(toolThatIsNeeded instanceof TCToolItem) {
                            damageToolParts(toolItem, playerIn, worldIn, 1);
                        }else{
                            if(toolItem.getMaxDamage() > 0) {
                                toolItem.setDamageValue(toolItem.getDamageValue() + Damage);
                            }
                        }
                    }

                    if(toolNeededIn.isEmpty()){
                        break;
                    }
                }
            }
        }
    }

    public boolean inventoryToolCheck(Player playerIn, List<Item> toolNeededIn){
        for(int i = 9; i <= 45; i++){
            ItemStack toolNeeded = playerIn.getInventory().getItem(i);
            if(toolNeededIn.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof TCToolItem toolItem){
                    TCToolItemInventoryHelper inventory = toolItem.getInventory(toolNeeded);

                    TCToolItem toolNeededItem = (TCToolItem)playerIn.getInventory().getItem(i).getItem();

                    if((toolNeededItem.parts  == 1 || toolNeededItem.parts  == 2 || toolNeededItem.parts  == 3) & inventory.getToolHead(toolNeeded) != null){
                        if(inventory.getToolHead(toolNeeded).getDamageValue() < inventory.getToolHead(toolNeeded).getMaxDamage()) {
                            toolNeededIn.remove(toolNeeded.getItem());
                        }
                    }

                    if((toolNeededItem.parts  == 2 || toolNeededItem.parts  == 3) & inventory.getToolHandle(toolNeeded) != null){
                       if(inventory.getToolHandle(toolNeeded).getDamageValue() < inventory.getToolHandle(toolNeeded).getDamageValue()){
                           toolNeededIn.remove(toolNeeded.getItem());
                       }
                    }

                    if(toolNeededItem.parts  == 3 & inventory.getToolBinding(toolNeeded) != null){
                       if(inventory.getToolBinding(toolNeeded).getDamageValue() < inventory.getToolBinding(toolNeeded).getDamageValue()){
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

    public Map<String, String> getPartsDamage(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);

            ItemStack toolHeadOnTool = inventory.getToolHead(itemStackIn);
            ItemStack toolBindingOnTool = inventory.getToolBinding(itemStackIn);
            ItemStack toolHandleOnTool = inventory.getToolHandle(itemStackIn);

            Map<String, String> map = new HashMap<>();

            if (toolHandleOnTool != null) {
                map.put(toolHandleOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolHandleOnTool.getMaxDamage() - toolHandleOnTool.getDamageValue() + "/" + toolHandleOnTool.getMaxDamage());
            }

            if (toolHeadOnTool != null) {
                map.put(toolHeadOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolHeadOnTool.getMaxDamage() - toolHeadOnTool.getDamageValue() + "/" + toolHeadOnTool.getMaxDamage());
            }

            if (toolBindingOnTool != null) {
                map.put(toolBindingOnTool.getDisplayName().getString().replace("[", "").replace("]", "") + " ", toolBindingOnTool.getMaxDamage() - toolBindingOnTool.getDamageValue() + "/" + toolBindingOnTool.getMaxDamage());
            }

            return map;
        }

        return new HashMap<>();
    }

    public int getParts() {
        return parts;
    }

    public List<Item> getItemsFromForge(List<String> listIn){
        List<Item> items = new ArrayList<>();

        for(String string : listIn){
            String modID = string.split(":")[0];
            String blockLocalization = string.split(":")[1];
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modID, blockLocalization));

            if(item != null){
                items.add(item);
            }
        }

        return items;
    }
}