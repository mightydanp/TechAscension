package mightydanp.techcore.common.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Either;
import mightydanp.techcore.client.settings.keybindings.KeyBindings;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.tool.ToolCodec;
import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
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
import org.jetbrains.annotations.NotNull;
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
    private final Map<String, ItemStack> handles = new HashMap<>();
    private final Map<String, ItemStack> dullHeads = new HashMap<>();
    private final Map<String, ItemStack> heads = new HashMap<>();
    private final Map<String, ItemStack> bindings = new HashMap<>();

    public Integer parts = 0;
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

    public void addHandle(String reference, ItemStack itemStack) {
        handles.put(reference, itemStack);
    }

    public void addDullHead(String reference, ItemStack itemStack) {
        dullHeads.put(reference, itemStack);
    }

    public void addHead(String reference, ItemStack itemStack) {
        heads.put(reference, itemStack);
    }

    public void addBinding(String reference, ItemStack itemStack) {
        bindings.put(reference, itemStack);
    }

    public Map<String, ItemStack> getHandles() {
        Map<String, ItemStack> map = handles;
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            ToolCodec tool = ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name));

            for (Either<String, ItemStack> either : tool.handleItems()) {
                List<ItemStack> handles = ToolCodec.giveItemStacks(either);

                handles.forEach(itemStack -> {
                    String registryName = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
                    map.put(Objects.requireNonNull(registryName), itemStack);
                });
            }
        }

        return map;
    }

    public Map<String, ItemStack> getDullHeads() {
        return dullHeads;
    }

    public Map<String, ItemStack> getHeads() {
        Map<String, ItemStack> map = heads;
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            ToolCodec tool = ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name));

            for (Either<String, ItemStack> either : tool.headItems()) {
                List<ItemStack> heads = ToolCodec.giveItemStacks(either);

                heads.forEach(itemStack -> {
                    String registryName = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
                    map.put(Objects.requireNonNull(registryName), itemStack);
                });
            }
        }
        return map;
    }

    public Map<String, ItemStack> getBindings() {
        Map<String, ItemStack> map = bindings;
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            ToolCodec tool = ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name));

            for (Either<String, ItemStack> either : tool.bindingItems()) {
                List<ItemStack> bindings = ToolCodec.giveItemStacks(either);

                bindings.forEach(itemStack -> {
                    String registryName = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
                    map.put(Objects.requireNonNull(registryName), itemStack);
                });
            }
        }

        return map;
    }

    public List<BlockState> getEffectiveBlocks() {
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            return ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name)).effectiveOn();
        }
        return null;
    }

    public Map<Integer, List<List<Either<String, ItemStack>>>> getAssembleItems() {
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            return ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name)).assembleStepsItems();
        }
        return null;
    }

    public List<List<Either<String, ItemStack>>> getDisassembleItems() {
        if(TCJsonConfigs.tool.getFirst().registryMap.containsKey(name)){
            return ((ToolCodec)TCJsonConfigs.tool.getFirst().registryMap.get(name)).disassembleItems();
        }
        return null;
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStackIn, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
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
                    if (isCorrectToolForDrops(itemStack, blockState)) {
                        damageToolParts(entityLiving.getMainHandItem(), (Player) entityLiving, world, 1);
                        return true;
                    } else {
                        damageToolParts(entityLiving.getMainHandItem(), (Player) entityLiving, world, 2);
                        return false;
                    }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity itemEntity) {
        super.onDestroyed(itemEntity);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStackIn, @NotNull BlockState state) {
        if (canWork(itemStackIn)) {
            if (state.requiresCorrectToolForDrops()) {
                return getEfficiency(itemStackIn, state);
            } else {
                return 1F;
            }
        }
        return 1F;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerEntityIn, @NotNull InteractionHand handIn) {
        if(playerEntityIn.isShiftKeyDown() && KeyBindings.handCrafting.isDown()){
            handToolDisassemble(worldIn, playerEntityIn, handIn);
        }

        return InteractionResultHolder.success(playerEntityIn.getItemInHand(handIn));
    }

    public void handToolDisassemble(Level worldIn, Player playerEntityIn, InteractionHand handIn){
        List<ItemStack> disassembleItems = containsDisassembleItems(getDisassembleItems(), playerEntityIn.getInventory());

        if (disassembleItems.size() > 0) {
            Set<ItemStack> set = new HashSet<>(disassembleItems);

            disassembleTool(playerEntityIn.getMainHandItem(), playerEntityIn, worldIn, 2, set);
            playerEntityIn.setItemInHand(handIn, ItemStack.EMPTY);
        }
    }

    public List<ItemStack> containsDisassembleItems(List<List<Either<String, ItemStack>>> disassembleItems, Inventory playerInventory){
        List<ItemStack> items = new ArrayList<>();

        for(List<Either<String, ItemStack>> map : disassembleItems) {
            List<List<ItemStack>> stack = new ArrayList<>();
            map.forEach((either) -> {
                //List<ItemStack> ing = new ArrayList<>();

                //to-do make it work with tags it adds all items to list instead of just one
                //either.ifLeft(s -> ing.addAll(Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(s))).stream().map(ItemStack::new).toList()));

                //either.ifRight(ing::add);

                stack.add(ToolCodec.giveItemStacks(either));

            });

            for (int i = 9; i <= 45; i++) {
                for (List<ItemStack> itemStacks : stack) {
                    for (ItemStack itemStack : itemStacks) {
                        if (playerInventory.getItem(i).equals(itemStack)) {
                            items.add(itemStack);
                            break;
                        }
                    }
                }

                if (map.size() == stack.size()) {
                    break;
                }
            }

            if (items.size() == disassembleItems.size()) {
                break;
            }
        }

        return items;
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

        if (state.requiresCorrectToolForDrops()){

            if(canWork(stack)){
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
                    return getEffectiveBlocks().contains(state);
                }
            } else {
                isCorrect.set(false);
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

    public float getEfficiency(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains("efficiency")) {
            return nbt.getFloat("efficiency");
        }

        return 0.00F;
    }

    public float getEfficiency(ItemStack itemStack, BlockState blockState) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains("efficiency")) {
            if (isCorrectToolForDrops(itemStack, blockState)) {
                return nbt.getFloat("efficiency");
            }
        }

        return 0.00F;
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
        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);

            if (toolItem.parts  >= 3) {
                if (inventory.getToolBinding(itemStackIn) != null) {
                    ItemStack part = inventory.getToolBinding(itemStackIn);

                    if(!part.isEmpty()) {
                        if (part.isDamageableItem()) {
                            if (part.getDamageValue() == part.getMaxDamage()) return false;
                        } else {
                            if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                                if (part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                    int damage = part.getTag().getInt("damage");
                                    int maxDamage = part.getTag().getInt("max_damage");

                                    if (damage == maxDamage) return false;
                                } else return false;
                            } else return false;
                        }
                    } else return false;
                } else return false;
            }

            if (toolItem.parts >= 2) {
                if (inventory.getToolHandle(itemStackIn) != null) {
                    ItemStack part = inventory.getToolHandle(itemStackIn);

                    if(!part.isEmpty()) {
                        if (part.isDamageableItem()) {
                            if (part.getDamageValue() == part.getMaxDamage()) return false;
                        } else {
                            if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                                if (part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                    int damage = part.getTag().getInt("damage");
                                    int maxDamage = part.getTag().getInt("max_damage");

                                    if (damage == maxDamage) return false;
                                } else return false;
                            } else return false;
                        }
                    } else return false;
                } else return false;
            }

            if (toolItem.parts >= 1) {
                if (inventory.getToolHead(itemStackIn) != null) {
                    ItemStack part = inventory.getToolHead(itemStackIn);

                    if(!part.isEmpty()) {
                        if (part.isDamageableItem()) {
                            return part.getDamageValue() != part.getMaxDamage();
                        } else {
                            if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                                if (part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                    int damage = part.getTag().getInt("damage");
                                    int maxDamage = part.getTag().getInt("max_damage");

                                    return damage != maxDamage;
                                } else return false;
                            } else return false;
                        }
                    } else return false;
                } else return false;
            }
        }

        return true;
    }

    public void damageToolParts(ItemStack itemStackIn, Player playerIn, Level worldIn, int amountIn) {
        Random random = new Random();


        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);

            ItemStack headPart = inventory.getToolHead(itemStackIn);
            ItemStack bindingPart = inventory.getToolBinding(itemStackIn);
            ItemStack handlePart = inventory.getToolHandle(itemStackIn);

            if (headPart != null && toolItem.parts  >= 1) {
                if(headPart.isDamageableItem()) {
                    int damage = headPart.getDamageValue();
                    int maxDamage = headPart.getMaxDamage();

                    if (damage != maxDamage) {
                        headPart.setDamageValue(damage + amountIn);
                        inventory.setToolHead(itemStackIn, headPart);
                    }

                    if(damage == maxDamage - 1){
                        playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    }
                } else {
                    if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(headPart.getItem().getRegistryName()).toString())) {
                        CompoundTag tag = headPart.getTag();
                        if(tag != null && tag.contains("damage") && tag.contains("max_damage")) {
                            int damage = tag.getInt("damage");
                            int maxDamage = tag.getInt("max_damage");

                            if (damage != maxDamage) {
                                tag.putInt("damage", damage + amountIn);
                                inventory.setToolHead(itemStackIn, headPart);
                            }

                            if(damage == maxDamage - 1){
                                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                            }
                        }
                    }
                }
            }

            if (handlePart != null && toolItem.parts  >= 2) {
                if(handlePart.isDamageableItem()) {
                    int damage = handlePart.getDamageValue();
                    int maxDamage = handlePart.getMaxDamage();

                    if (damage != maxDamage) {
                        handlePart.setDamageValue(damage + amountIn);
                        inventory.setToolHandle(itemStackIn, handlePart);
                    }

                    if (damage == maxDamage - 1) {
                        playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    }
                } else {
                    if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(handlePart.getItem().getRegistryName()).toString())) {
                        CompoundTag tag = handlePart.getTag();
                        if(tag != null && tag.contains("damage") && tag.contains("max_damage")) {
                            int damage = tag.getInt("damage");
                            int maxDamage = tag.getInt("max_damage");

                            if (damage != maxDamage) {
                                tag.putInt("damage", damage + amountIn);
                                inventory.setToolHandle(itemStackIn, handlePart);
                            }

                            if(damage == maxDamage - 1){
                                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                            }
                        }
                    }
                }
            }

            if (bindingPart != null && toolItem.parts >= 3) {
                if(bindingPart.isDamageableItem()) {
                    int damage = bindingPart.getDamageValue();
                    int maxDamage = bindingPart.getMaxDamage();
                    if (damage != maxDamage) {
                        bindingPart.setDamageValue(damage + amountIn);
                        inventory.setToolBinding(itemStackIn, bindingPart);
                    }

                    if (damage == maxDamage - 1) {
                        playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    }
                } else {
                    if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(bindingPart.getItem().getRegistryName()).toString())) {
                        CompoundTag tag = bindingPart.getTag();
                        if(tag != null && tag.contains("damage") && tag.contains("max_damage")) {
                            int damage = tag.getInt("damage");
                            int maxDamage = tag.getInt("max_damage");

                            if (damage != maxDamage) {
                                tag.putInt("damage", damage + amountIn);
                                inventory.setToolBinding(itemStackIn, bindingPart);
                            }

                            if(damage == maxDamage - 1){
                                playerIn.broadcastBreakEvent(playerIn.getUsedItemHand());
                                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                            }
                        }
                    }
                }
            }
        }

    }

    public void disassembleTool(ItemStack itemStackIn, Player playerIn, Level worldIn, int toolInDamage, Set<ItemStack> toolNeededIn) {
        Random random = new Random();

        if(itemStackIn.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(itemStackIn);
            ItemStack headHandle = inventory.getToolHandle(itemStackIn);
            ItemStack headPart = inventory.getToolHead(itemStackIn);
            ItemStack bindingPart = inventory.getToolBinding(itemStackIn);

            damageToolsNeededInPlayerInventory(playerIn, worldIn, toolInDamage, toolNeededIn);

            if (headHandle != null) {
                if(headHandle.isDamageableItem()) {
                    if (headHandle.getDamageValue() == headHandle.getMaxDamage()) {
                        if (headHandle.getItem() instanceof HandleItem part) {
                            part.setBroken(true);
                        }
                    }

                    if (playerIn.getInventory().canPlaceItem(1, headHandle)) {
                        playerIn.getInventory().add(headHandle);
                        inventory.setToolHandle(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), headHandle));
                    }
                }

            }

            if (headPart != null) {
                if(headPart.isDamageableItem()) {
                    if (headPart.getDamageValue() == headPart.getMaxDamage()) {
                        if (headPart.getItem() instanceof HeadItem part) {
                            part.setBroken(true);
                        }
                    }

                    if (playerIn.getInventory().canPlaceItem(1, headPart)) {
                        playerIn.getInventory().add(headPart);
                        inventory.setToolHead(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), headPart));
                    }
                }
            }

            if (bindingPart != null) {
                if(bindingPart.isDamageableItem()) {
                    if (bindingPart.getDamageValue() == bindingPart.getMaxDamage()) {
                        if (bindingPart.getItem() instanceof BindingItem part) {
                            part.setBroken(true);
                        }
                    }else{
                        if (bindingPart.getItem() instanceof BindingItem part) {
                            part.setBroken(true);
                        }
                        bindingPart.setDamageValue(bindingPart.getMaxDamage());
                    }
                    if (playerIn.getInventory().canPlaceItem(1, bindingPart)) {
                        playerIn.getInventory().add(bindingPart);
                        inventory.setToolBinding(itemStackIn, ItemStack.EMPTY);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    } else {
                        worldIn.addFreshEntity(new ItemEntity(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), bindingPart));
                    }
                }


            }
        }
    }

    public void damageToolsNeededInPlayerInventory(Player playerIn, Level worldIn, int Damage, Set<ItemStack> toolNeededIn){
        if(inventoryToolCheck(playerIn, toolNeededIn) && toolNeededIn.size() != 0){
            for(ItemStack toolThatIsNeeded : toolNeededIn){
                for(int i = 9; i <= 45; i++){
                    if (toolThatIsNeeded == playerIn.getInventory().getItem(i)){
                        toolNeededIn.remove(playerIn.getInventory().getItem(i));
                        ItemStack toolItem = playerIn.getInventory().getItem(i);
                        if(toolThatIsNeeded.getItem() instanceof TCToolItem) {
                            damageToolParts(toolItem, playerIn, worldIn, 1);
                        }else{
                            if(toolItem.isDamageableItem()) {
                                if (toolItem.getMaxDamage() > 0) {
                                    toolItem.setDamageValue(toolItem.getDamageValue() + Damage);
                                } else {
                                    if (toolItem.getTag() != null && toolItem.getTag().contains("damage")) {
                                        int damage = toolItem.getTag().getInt("damage");
                                        if (damage > 0) {
                                            toolItem.getTag().putInt("damage", damage - 1);
                                        }
                                    }
                                }
                            } else {
                                playerIn.getInventory().getItem(i).setCount(playerIn.getInventory().getItem(i).getCount() - 1);
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

    public boolean inventoryToolCheck(Player playerIn, Set<ItemStack> toolNeededIn){
        for(int i = 9; i <= 45; i++){
            ItemStack toolNeeded = playerIn.getInventory().getItem(i);
            if(toolNeededIn.contains(toolNeeded)){
                if(toolNeeded.getItem() instanceof TCToolItem toolItem){
                    TCToolItemInventoryHelper inventory = toolItem.getInventory(toolNeeded);

                    TCToolItem toolNeededItem = (TCToolItem)playerIn.getInventory().getItem(i).getItem();

                    if((toolNeededItem.parts  >= 1) & inventory.getToolHead(toolNeeded) != null){
                        ItemStack part = inventory.getToolHead(toolNeeded);

                        if(part.isDamageableItem()) {
                            if (part.getDamageValue() < part.getMaxDamage()) {
                                toolNeededIn.remove(toolNeeded);
                            }
                        }else if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                            if(part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                int damage = part.getTag().getInt("damage");
                                int maxDamage = part.getTag().getInt("max_damage");

                                if (damage < maxDamage) {
                                    toolNeededIn.remove(toolNeeded);
                                }
                            }
                        }
                    }

                    if((toolNeededItem.parts  >= 2) & inventory.getToolHandle(toolNeeded) != null){
                        ItemStack part = inventory.getToolHandle(toolNeeded);

                        if(part.isDamageableItem()) {
                            if (part.getDamageValue() < part.getDamageValue()) {
                                toolNeededIn.remove(toolNeeded);
                            }else if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                                if(part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                    int damage = part.getTag().getInt("damage");
                                    int maxDamage = part.getTag().getInt("max_damage");

                                    if (damage < maxDamage) {
                                        toolNeededIn.remove(toolNeeded);
                                    }
                                }
                            }
                        }
                    }

                    if(toolNeededItem.parts  >= 3 & inventory.getToolBinding(toolNeeded) != null){
                        ItemStack part = inventory.getToolBinding(toolNeeded);
                        if(part.isDamageableItem()) {
                            if (part.getDamageValue() < part.getDamageValue()) {
                                toolNeededIn.remove(toolNeeded);
                            }
                        } else if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(part.getItem().getRegistryName()).toString())) {
                            if(part.getTag() != null && part.getTag().contains("damage") && part.getTag().contains("max_damage")) {
                                int damage = part.getTag().getInt("damage");
                                int maxDamage = part.getTag().getInt("max_damage");

                                if (damage < maxDamage) {
                                    toolNeededIn.remove(toolNeeded);
                                }
                            }
                        }
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage()) {
                        toolNeededIn.remove(toolNeeded);
                    }
                }
            }

            if(toolNeededIn.size() == 0){
                break;
            }
        }

        return toolNeededIn.size() == 0;
    }

    public Map<String, String> getPartsDamage(ItemStack tool) {
        if(tool.getItem() instanceof TCToolItem toolItem) {
            TCToolItemInventoryHelper inventory = toolItem.getInventory(tool);

            ItemStack head = inventory.getToolHead(tool);
            ItemStack binding = inventory.getToolBinding(tool);
            ItemStack handle = inventory.getToolHandle(tool);

            Map<String, String> map = new HashMap<>();

            if (handle != null) {
                if(handle.isDamageableItem()) {
                    map.put(handle.getDisplayName().getString().replace("[", "").replace("]", "") + " ", handle.getMaxDamage() - handle.getDamageValue() + "/" + handle.getMaxDamage());
                } else {
                    if(handle.getTag() != null && handle.getTag().contains("damage") && handle.getTag().contains("max_damage")) {
                        if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(handle.getItem().getRegistryName()).toString())) {
                            int damage = handle.getTag().getInt("damage");
                            int maxDamage = handle.getTag().getInt("max_damage");
                            map.put(handle.getDisplayName().getString().replace("[", "").replace("]", "") + " ", maxDamage - damage + "/" + maxDamage);
                        }
                    }
                }
            }

            if (head != null) {
                if(head.isDamageableItem()) {
                    map.put(head.getDisplayName().getString().replace("[", "").replace("]", "") + " ", head.getMaxDamage() - head.getDamageValue() + "/" + head.getMaxDamage());
                } else {
                    if(head.getTag() != null && head.getTag().contains("damage") && head.getTag().contains("max_damage")) {
                        if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(head.getItem().getRegistryName()).toString())) {
                            int damage = head.getTag().getInt("damage");
                            int maxDamage = head.getTag().getInt("max_damage");
                            map.put(head.getDisplayName().getString().replace("[", "").replace("]", "") + " ", maxDamage - damage + "/" + maxDamage);
                        }
                    }
                }
            }

            if (binding != null) {
                if(binding.isDamageableItem()) {
                    map.put(binding.getDisplayName().getString().replace("[", "").replace("]", "") + " ", binding.getMaxDamage() - binding.getDamageValue() + "/" + binding.getMaxDamage());
                } else {
                    if(binding.getTag() != null && binding.getTag().contains("damage") && binding.getTag().contains("max_damage")) {
                        if(TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(binding.getItem().getRegistryName()).getPath())) {
                            int damage = binding.getTag().getInt("damage");
                            int maxDamage = binding.getTag().getInt("max_damage");
                            map.put(binding.getDisplayName().getString().replace("[", "").replace("]", "") + " ", maxDamage - damage + "/" + maxDamage);
                        }
                    }
                }
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