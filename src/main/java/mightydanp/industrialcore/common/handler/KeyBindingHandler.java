package mightydanp.industrialcore.common.handler;

import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MightyDanp on 5/12/2021.
 */
public class KeyBindingHandler {

    private static List<KeyMapping> keyBindingList = new ArrayList<>();
    public KeyMapping keyBinding;

    public KeyBindingHandler(String buttonName, int buttonNumber){
        keyBindingList.add(keyBinding = new KeyMapping("key." + buttonName , buttonNumber , "key.categories." + Ref.mod_id));
    }

    public static void clientInit(){
        for(KeyMapping keyBinding : keyBindingList) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
