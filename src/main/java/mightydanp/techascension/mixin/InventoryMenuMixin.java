package mightydanp.techascension.mixin;


import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends RecipeBookMenu<CraftingContainer> {

    public InventoryMenuMixin(MenuType<?> p_40115_, int p_40116_) {
        super(p_40115_, p_40116_);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void InventoryMenu(Inventory p_39706_, boolean p_39707_, Player p_39708_, CallbackInfo ci){
        this.addSlot(new Slot(p_39706_, 41, 134, 62));
    }
}
