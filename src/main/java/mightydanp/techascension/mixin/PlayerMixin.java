package mightydanp.techascension.mixin;


import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract Inventory getInventory();

    @Inject(method = "touch", at = @At("HEAD"))
    public void touch(Entity p_36278_, CallbackInfo ci) {
            if(!this.getInventory().getItem(41).isEmpty()){
                //p_36278_.restoreFrom(new );
            }
    }
}
