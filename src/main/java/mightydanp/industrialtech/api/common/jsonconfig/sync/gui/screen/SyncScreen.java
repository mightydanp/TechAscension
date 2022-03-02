package mightydanp.industrialtech.api.common.jsonconfig.sync.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mightydanp.industrialtech.api.common.libs.ITButtonRef;
import mightydanp.industrialtech.api.common.libs.ITScreenRef;
import mightydanp.industrialtech.api.common.libs.ITScreenTextRef;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

/**
 * Created by MightyDanp on 1/4/2022.
 */

public class SyncScreen extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    //private String serverIP;
    //private String serverName;

    public SyncScreen() {
        super(new TranslationTextComponent(ITScreenRef.syncScreen));
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
            this.addButton(new Button(this.width / 5, this.height - 82, 300, 20, new TranslationTextComponent(ITButtonRef.syncClientConfigsWithServers), (button) -> {
                try {
                    if(!IndustrialTech.configSync.isSinglePlayer){
                        IndustrialTech.configSync.materialFlagServer.syncClientWithServer("");
                        IndustrialTech.configSync.fluidStateServer.syncClientWithServer("");
                        IndustrialTech.configSync.oreTypeServer.syncClientWithServer("");
                        IndustrialTech.configSync.toolTypeServer.syncClientWithServer("");
                        IndustrialTech.configSync.toolPartServer.syncClientWithServer("");
                        IndustrialTech.configSync.stoneLayerServer.syncClientWithServer("");

                        IndustrialTech.configSync.materialServer.syncClientWithServer("");

                        IndustrialTech.configSync.oreVeinServer.syncClientWithServer("");
                        IndustrialTech.configSync.smallOreVeinServer.syncClientWithServers("");

                        IndustrialTech.mainJsonConfig.setFolderLocation("config/" + Ref.mod_id + "/server");
                        IndustrialTech.mainJsonConfig.reloadMainConfigJson();

                    }else{
                        IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + IndustrialTech.configSync.singlePlayerWorldName + "/serverconfig" + Ref.mod_id);
                        IndustrialTech.mainJsonConfig.reloadMainConfigJson();
                    }
                } catch (IOException e) {
                    LOGGER.fatal("You must be on a client world you shouldn't be seeing this message. Please message a developer of this mod please and tell them about this message :)");
                }
                this.minecraft.stop();
            }));

            this.addButton(new Button(this.width / 5, this.height - 62, 300, 20, new TranslationTextComponent(ITButtonRef.returnToServerScreen), (button) -> {
                boolean flag = this.minecraft.isLocalServer();
                boolean flag1 = this.minecraft.isConnectedToRealms();

                button.active = false;
                if (flag) {
                    this.minecraft.setScreen(new MainMenuScreen());
                } else if (flag1) {
                    RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
                    realmsbridgescreen.switchToRealms(new MainMenuScreen());
                } else {
                    this.minecraft.setScreen(new MultiplayerScreen(new MainMenuScreen()));
                }

            }));
        }
    }

    public void render(MatrixStack matrixStack, int x, int y, float p_230430_4_) {
        this.renderBackground(matrixStack);

        if(minecraft != null) {
            if (!minecraft.hasSingleplayerServer()) {
                GL11.glPushMatrix();
                drawCenteredString(matrixStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine1), this.width / 2, 20, 0xFF5555);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GlStateManager._scaled(0.5, 0.5, 1);
                drawCenteredString(matrixStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine2), this.width, 40 * 2, 16777215);
                drawCenteredString(matrixStack, this.font, I18n.get(ITScreenTextRef.syncWarningLine3), this.width, 60 * 2, 16777215);
                GL11.glPopMatrix();
            }else{
                GL11.glPushMatrix();
                drawCenteredString(matrixStack, this.font, I18n.get(ITScreenTextRef.clientWorldWarningLine1), this.width / 2, 20, 0xFF5555);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GlStateManager._scaled(0.5, 0.5, 1);
                drawCenteredString(matrixStack, this.font, I18n.get(ITScreenTextRef.clientWorldWarningLine2), this.width, 40 * 2, 16777215);
                GL11.glPopMatrix();
            }
        }

        super.render(matrixStack, x, y, p_230430_4_);
    }
}
