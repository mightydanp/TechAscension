package mightydanp.techascension.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {
    //weight
    protected Float pounds;
    protected Float kilograms;

    //tool properties
    protected int color;
    protected int durability;

    public void setPounds(Float pounds) {
        this.pounds = pounds;
    }

    public void setKilograms(Float kilograms) {
        this.kilograms = kilograms;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_, CallbackInfo ci) {

    }

}
