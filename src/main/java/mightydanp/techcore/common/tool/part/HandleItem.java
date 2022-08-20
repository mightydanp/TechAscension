package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class HandleItem extends ToolPart {
    public HandleItem(Properties propertiesIn) {
        super(propertiesIn);
    }
}
