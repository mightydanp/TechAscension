package mightydanp.techascension.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Inventory.class)
public class InventoryMixin{

    @Final
    @Shadow
    public NonNullList<ItemStack> items;

    @Final
    @Shadow
    public NonNullList<ItemStack> armor;
    @Final
    @Shadow
    public NonNullList<ItemStack> offhand;

    public NonNullList<ItemStack> back = NonNullList.withSize(1, ItemStack.EMPTY);

    @Mutable
    @Final
    @Shadow
    private List<NonNullList<ItemStack>> compartments;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void Inventory(Player p_35983_, CallbackInfo ci){
        compartments = ImmutableList.of(this.items, this.armor, this.offhand, back);
    }

    @Inject(method = "save", at = @At("TAIL"), cancellable = true)
    public void save(ListTag p_36027_, CallbackInfoReturnable<ListTag> cir){
        for(int k = 0; k < this.back.size(); ++k) {
            if (!this.back.get(k).isEmpty()) {
                CompoundTag compoundtag2 = new CompoundTag();
                compoundtag2.putByte("Slot", (byte)(k + 200));
                this.back.get(k).save(compoundtag2);
                p_36027_.add(compoundtag2);
            }
        }
        cir.setReturnValue(p_36027_);
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void load(ListTag p_36036_, CallbackInfo ci) {
        this.back.clear();

        for(int i = 0; i < p_36036_.size(); ++i) {
            CompoundTag compoundtag = p_36036_.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.of(compoundtag);
            if (!itemstack.isEmpty()) {
                if (j >= 200 && j < this.back.size() + 200) {
                    this.back.set(j - 200, itemstack);
                }
            }
        }

    }

    @Inject(method = "getContainerSize", at = @At("TAIL"), cancellable = true)
    public void getContainerSize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() + back.size());
    }

    @Inject(method = "isEmpty", at = @At("TAIL"), cancellable = true)
    public void isEmpty(CallbackInfoReturnable<Boolean> cir) {
        for(ItemStack itemstack2 : this.back) {
            if (!itemstack2.isEmpty()) {
                cir.setReturnValue(false);
            }
        }
    }

}
