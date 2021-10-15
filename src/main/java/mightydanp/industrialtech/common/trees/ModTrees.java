package mightydanp.industrialtech.common.trees;

import mightydanp.industrialtech.api.common.handler.TreeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 7/31/2021.
 */
public class ModTrees {
    public static List<TreeHandler> trees = new ArrayList<>();
    public static TreeHandler nulled;

    public static void commonInit() {

    }

    public static void clientInit() {
        for(TreeHandler tree : trees) {
            tree.registerColorHandlerForBlock();
            tree.registerColorForItem();
        }
    }
}
