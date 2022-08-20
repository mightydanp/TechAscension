package mightydanp.techcore.common.handler;

import mightydanp.techcore.common.libs.Ref;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindingHandler {

    private static final List<KeyMapping> keyBindingList = new ArrayList<>();

    public static KeyMapping addKeyBinding(String buttonName, int buttonNumber){
        KeyMapping keyBinding = new KeyMapping("key." + buttonName , buttonNumber , "key.categories." + Ref.mod_id);
        keyBindingList.add(keyBinding);
        return keyBinding;
    }

    public static void clientInit(){
        for(KeyMapping keyBinding : keyBindingList) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
