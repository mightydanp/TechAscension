package mightydanp.industrialtech.api.common.jsonconfig.sync.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.RealmsMainScreen;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.libs.ITButtonRef;
import mightydanp.industrialtech.api.common.libs.ITScreenRef;
import mightydanp.industrialtech.api.common.libs.ITScreenTextRef;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.nio.file.Paths;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;

/**
 * Created by MightyDanp on 1/4/2022.
 */

public class SyncScreen extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    //private String serverIP;
    //private String serverName;

    public SyncScreen() {
        super(new TranslatableComponent(ITScreenRef.syncScreen));
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
        boolean debug = false;
        if((minecraft != null) || debug) {
            this.addRenderableWidget(new Button(this.width / 5, this.height - 82, 300, 20, new TranslatableComponent(ITButtonRef.syncClientConfigsWithServers), (button) -> {
                if(!IndustrialTech.configSync.isSinglePlayer){
                    for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                        Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                        try {
                            config.getSecond().syncClientWithServer("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    IndustrialTech.mainJsonConfig.setFolderLocation("config/" + Ref.mod_id + "/server");
                    IndustrialTech.mainJsonConfig.reloadMainConfigJson();

                }else{
                    IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + IndustrialTech.configSync.singlePlayerWorldName + "/serverconfig" + Ref.mod_id);
                    IndustrialTech.mainJsonConfig.reloadMainConfigJson();
                }
                this.minecraft.stop();
            }));

            this.addRenderableWidget(new Button(this.width / 5, this.height - 62, 300, 20, new TranslatableComponent(ITButtonRef.returnToServerScreen), (button) -> {
                boolean flag = this.minecraft.isLocalServer();
                boolean flag1 = this.minecraft.isConnectedToRealms();

                button.active = false;
                if (flag) {
                    this.minecraft.setScreen(new TitleScreen());
                } else if (flag1) {
                    this.minecraft.setScreen(new RealmsMainScreen(this));
                } else {
                    this.minecraft.setScreen(new JoinMultiplayerScreen(new TitleScreen()));
                }

            }));
        }
    }

    public void render(PoseStack poseStack, int x, int y, float p_230430_4_) {
        this.renderBackground(poseStack);

        if(minecraft != null) {
            if (!minecraft.hasSingleplayerServer()) {
                GL11.glPushMatrix();
                drawCenteredString(poseStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine1), this.width / 2, 20, 0xFF5555);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                poseStack.scale(0.5F, 0.5F, 1F);
                drawCenteredString(poseStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine2), this.width, 40 * 2, 16777215);
                drawCenteredString(poseStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine3), this.width, 60 * 2, 16777215);
                GL11.glPopMatrix();
            }else{
                GL11.glPushMatrix();
                drawCenteredString(poseStack, this.font, I18n.get(ITScreenTextRef.clientWorldWarningLine1), this.width / 2, 20, 0xFF5555);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                poseStack.scale(0.5F, 0.5F, 1F);
                drawCenteredString(poseStack, this.font, I18n.get(ITScreenTextRef.clientWorldWarningLine2), this.width, 40 * 2, 16777215);
                GL11.glPopMatrix();
            }
        }

        super.render(poseStack, x, y, p_230430_4_);
    }
}
