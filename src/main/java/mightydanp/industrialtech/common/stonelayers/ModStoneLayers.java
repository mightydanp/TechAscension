package mightydanp.industrialtech.common.stonelayers;

import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.common.libs.StoneLayerFlagsEnum;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 7/6/2021.
 */
public class ModStoneLayers {
    public static List<StoneLayerHandler> stoneLayerList = new ArrayList<>();

    public static StoneLayerHandler stone, andesite, granite, diorite;

    public static void init(){
        stoneLayerList.add(stone = new StoneLayerHandler(Blocks.STONE, StoneLayerFlagsEnum.thinSlab));
        stoneLayerList.add(andesite = new StoneLayerHandler(Blocks.ANDESITE, StoneLayerFlagsEnum.thinSlab));
        stoneLayerList.add(granite = new StoneLayerHandler(Blocks.GRANITE, StoneLayerFlagsEnum.thinSlab));
        stoneLayerList.add(diorite = new StoneLayerHandler(Blocks.DIORITE, StoneLayerFlagsEnum.thinSlab));
    }

    public static void clientInit(){
        for(StoneLayerHandler stoneLayer : stoneLayerList){
            stoneLayer.clientInit();
        }
    }


}