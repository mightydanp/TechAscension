package mightydanp.techascension.mixin;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

//This mixin is for core
@Mixin(WorldSelectionList.WorldListEntry.class)
public abstract class WorldListEntryMixin {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Final
    @Shadow
    LevelSummary summary;

    @Shadow
    private void queueLoadScreen(){};

    @Inject(method = "loadWorld", at = @At("HEAD"), cancellable = true)
    public void loadWorld(CallbackInfo ci){
        this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (this.minecraft.getLevelSource().levelExists(this.summary.getLevelId())) {
            if(new File("saves/" + this.summary.getLevelId() + "/serverconfig/" + Ref.mod_id).exists()) {
                if (!TechAscension.mainJsonConfig.getFolderLocation().equals("saves/" + this.summary.getLevelId() + "/serverconfig/" + Ref.mod_id)) {
                    SyncScreen screen = new SyncScreen();

                    ConfigSync.summary = summary;

                    this.minecraft.forceSetScreen(screen);

                    ci.cancel();
                    return;


                    //TechAscension.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                    //TechAscension.mainJsonConfig.reloadConfigJson();
                }
            }

            //this.queueLoadScreen();
            //this.minecraft.loadLevel(this.summary.getLevelId());
        }
    }
}
