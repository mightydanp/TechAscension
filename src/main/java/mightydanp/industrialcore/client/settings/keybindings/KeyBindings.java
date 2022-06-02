package mightydanp.industrialcore.client.settings.keybindings;

import mightydanp.industrialcore.common.handler.KeyBindingHandler;
import mightydanp.industrialtech.common.libs.KeyBindingsRef;

/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindings {
    public static KeyBindingHandler activateBlockPreViewer;

    public static void init(){
        activateBlockPreViewer = new KeyBindingHandler(KeyBindingsRef.activate_block_previewer, 67);
    }
}
