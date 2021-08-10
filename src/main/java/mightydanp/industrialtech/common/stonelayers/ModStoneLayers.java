package mightydanp.industrialtech.common.stonelayers;

import mightydanp.industrialtech.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.common.libs.StoneLayerFlagsEnum;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 7/6/2021.
 */
public class ModStoneLayers {
    public static List<StoneLayerHandler> stoneLayerList = new ArrayList<>();

    public StoneLayerHandler stone;

    public void init(){
        stoneLayerList.add(stone = new StoneLayerHandler(Blocks.STONE.getBlock(), StoneLayerFlagsEnum.thinSlab, StoneLayerFlagsEnum.leg));
    }

    public static void clientInit(){
        for(StoneLayerHandler stoneLayer : stoneLayerList){
            stoneLayer.clientInit();
        }
    }


}