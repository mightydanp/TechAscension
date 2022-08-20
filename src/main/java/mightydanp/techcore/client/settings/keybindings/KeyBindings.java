package mightydanp.techcore.client.settings.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import mightydanp.techcore.common.handler.KeyBindingHandler;
import mightydanp.techascension.common.libs.KeyBindingsRef;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.KeyboardInput;

/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindings {
    //public static KeyBindingHandler activateBlockPreViewer;
    public static KeyMapping handCrafting;

    public static void init(){
        //activateBlockPreViewer = new KeyBindingHandler(KeyBindingsRef.activate_block_previewer, 67);
        handCrafting = KeyBindingHandler.addKeyBinding(KeyBindingsRef.hand_crafting, InputConstants.KEY_C);
    }
}
