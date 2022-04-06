package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindingHandler {

    public Keyboard keyboard;
    private static List<KeyMapping> keyBindingList = new ArrayList<>();
    public KeyMapping keyBinding;
    public Component.Identifier.Key key;

    public KeyBindingHandler(String buttonName, int buttonNumber){
        keyBindingList.add(keyBinding = new KeyMapping("key." + buttonName , buttonNumber , "key.categories." + Ref.mod_id));
    }

    public static void clientInit(){
        for(KeyMapping keyBinding : keyBindingList) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
