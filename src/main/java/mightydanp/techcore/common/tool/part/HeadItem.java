package mightydanp.techcore.common.tool.part;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class HeadItem extends ToolPart {
    public String suggestedCraftedTool;
    public Float efficiency;

    public Map<String, Integer> tools = new HashMap<>();
    public Float attackDamage;

    public HeadItem(Properties properties) {
        super(properties);
    }
    public HeadItem setSuggestedCraftedTool(String suggestedCraftedTool) {
        this.suggestedCraftedTool = suggestedCraftedTool;
        return this;
    }

    public HeadItem setEfficiency(Float efficiency) {
        this.efficiency = efficiency;
        return this;
    }

    public HeadItem setTools(Map<String, Integer> itToolTypeIn) {
        tools.putAll(itToolTypeIn);
        return this;
    }

    public HeadItem setAttackDamage(Float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(efficiency != null) {
            tooltip.add(Component.nullToEmpty("Efficiency:" + efficiency));
        }

        if(tools != null) {
            tools.forEach(((toolName, toolLevel) -> tooltip.add(
                    Component.nullToEmpty(toolName.substring(0, 1).toUpperCase() + toolName.substring(1) + " Level: " + toolLevel))

            ));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}