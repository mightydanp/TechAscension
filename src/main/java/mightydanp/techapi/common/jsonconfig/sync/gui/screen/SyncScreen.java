package mightydanp.techapi.common.jsonconfig.sync.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.RealmsMainScreen;
import mightydanp.techapi.common.jsonconfig.IJsonConfig;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techcore.common.libs.TCButtonRef;
import mightydanp.techcore.common.libs.TCScreenRef;
import mightydanp.techcore.common.libs.TCScreenTextRef;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by MightyDanp on 1/4/2022.
 */

public class SyncScreen extends Screen {
    public SyncScreen() {
        super(new TranslatableComponent(TCScreenRef.syncScreen));
    }

    public boolean isPauseScreen() {
        return true;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        if(minecraft != null) {
            if(ConfigSync.summary != null) {
                this.addRenderableWidget(new Button((this.width / 2) - 150, this.height - 102, 300, 20, new TranslatableComponent(TCButtonRef.loadIntoWorldWithoutSyncing), (button) -> {
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    if (this.minecraft.getLevelSource().levelExists(ConfigSync.summary.getLevelId())) {
                        this.minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
                        this.minecraft.loadLevel(ConfigSync.summary.getLevelId());
                        ConfigSync.summary = null;
                    }
                }));

                this.addRenderableWidget(new Button((this.width / 2) - 150, this.height - 82, 300, 20, new TranslatableComponent(TCButtonRef.syncClientConfigsWithWorld), (button) -> {
                    TechAscension.mainJsonConfig.setFolderLocation("saves/" + ConfigSync.summary.getLevelId() + "/serverconfig/" + Ref.mod_id);
                    TechAscension.mainJsonConfig.reloadConfigJson();
                    this.minecraft.stop();
                }));
            } else {
                this.addRenderableWidget(new Button((this.width / 2) - 150, this.height - 82, 300, 20, new TranslatableComponent(TCButtonRef.syncClientConfigsWithServers), (button) -> {
                    for(int i = 0; i < ConfigSync.configs.size(); i++){
                        Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> config = ConfigSync.configs.get(i);
                        try {
                            config.getSecond().syncClientWithServer("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    TechAscension.mainJsonConfig.setFolderLocation("config/" + Ref.mod_id + "/server");
                    TechAscension.mainJsonConfig.reloadConfigJson();

                    this.minecraft.stop();
                }));
            }

            this.addRenderableWidget(new Button((this.width / 2) - 150, this.height - 62, 300, 20, new TranslatableComponent(TCButtonRef.returnToMainMenuScreen), (button) -> {
                button.active = false;

                this.minecraft.setScreen(new TitleScreen());

                ConfigSync.summary = null;
            }));
        }
    }

    public void render(@NotNull PoseStack poseStack, int x, int y, float p_230430_4_) {
        this.renderBackground(poseStack);

        if(ConfigSync.summary == null){
            drawCenteredString(poseStack, this.font, I18n.get(TCScreenTextRef.syncWarningLine1), this.width / 2, 20, 0xFF5555);

            //poseStack.scale(0.5F, 0.5F, 1F);
            drawCenteredString(poseStack, this.font, I18n.get(TCScreenTextRef.syncWarningLine2), this.width / 2, 40 * 2, 16777215);
            drawCenteredString(poseStack, this.font, I18n.get(TCScreenTextRef.syncWarningLine3), this.width / 2, 60 * 2, 16777215);
        }else{
            drawCenteredString(poseStack, this.font, I18n.get(TCScreenTextRef.clientWorldWarningLine1), this.width / 2, 20, 0xFF5555);

            //poseStack.scale(0.5F, 0.5F, 1F);
            drawCenteredString(poseStack, this.font, I18n.get(TCScreenTextRef.clientWorldWarningLine2), this.width / 2, 40 * 2, 16777215);
        }

        super.render(poseStack, x, y, p_230430_4_);
    }
}
