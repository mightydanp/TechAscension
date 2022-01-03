package mightydanp.industrialtech.api.common.stonelayer;

import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 10/31/2021.
 */
public class ITStoneLayers {
    public static List<StoneLayerHandler> stoneLayerList = new ArrayList<>();

    public static void init(){

    }

    public static void clientInit(){
        for(StoneLayerHandler stoneLayer : stoneLayerList){
            stoneLayer.clientInit();
        }
    }
}
