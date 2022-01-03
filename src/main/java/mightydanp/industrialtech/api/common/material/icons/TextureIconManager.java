package mightydanp.industrialtech.api.common.material.icons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 12/29/2021.
 */
public class TextureIconManager {
    private static Map<String, ITextureIcon> textureIconById = new HashMap<>();

    static {
        for (DefaultTextureIcon textureIcon : DefaultTextureIcon.values())
            register(textureIcon);
    }

    public static void register(ITextureIcon textureIconIn) {
        String name = textureIconIn.getName();
        if (textureIconById.containsKey(textureIconIn.getName()))
            throw new IllegalArgumentException("Texture Icon with name(" + name + "), already exists.");
        textureIconById.put(name, textureIconIn);
    }

    public static ITextureIcon getTextureIconByName(String nameIn) {
        return textureIconById.get(nameIn);
    }

    public static Set<ITextureIcon> getAllTextureIcon() {
        return new HashSet<ITextureIcon>(textureIconById.values());
    }
}