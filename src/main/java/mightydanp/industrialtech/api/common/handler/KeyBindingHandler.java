package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.ToggleableKeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindingHandler {

    public Keyboard keyboard;
    private static List<KeyBinding> keyBindingList = new ArrayList<>();
    public KeyBinding keyBinding;
    public Component.Identifier.Key key;

    public KeyBindingHandler(String buttonName, int buttonNumber){
        keyBindingList.add(keyBinding = new KeyBinding("key." + buttonName , buttonNumber , "key.categories." + Ref.mod_id));
    }

    public static void clientInit(){
        for(KeyBinding keyBinding : keyBindingList) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
