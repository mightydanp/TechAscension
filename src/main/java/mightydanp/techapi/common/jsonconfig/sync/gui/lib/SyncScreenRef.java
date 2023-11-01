package mightydanp.techapi.common.jsonconfig.sync.gui.lib;

import mightydanp.techcore.common.libs.Ref;

/**
 * Created by MightyDanp on 1/4/2022.
 */
public class SyncScreenRef {
    public static String syncScreen = Ref.mod_id + ".sync.screen";

    public static String syncWarningLine1 = Ref.mod_id + ".sync_warning_line_1";
    public static String syncWarningLine1Text = "The server you are joining doesn't have the same configs as you.";
    public static String syncWarningLine2 = Ref.mod_id + ".sync_warning_line_2";
    public static String syncWarningLine2Text = "Currently there is no way to sync json configs with a servers configs.";
    //public static String syncWarningLine2Text = "You can either sync with the server's json configs or go back to the server list menu";
    public static String syncWarningLine3 = Ref.mod_id + ".sync_warning_line_3";
    public static String syncWarningLine3Text = "You need to get with the owner of this server to grab a copy of there configs to join.";
    //public static String syncWarningLine3Text = "You will NOT be able to play on this server unless you sync with the server's json configs or contact the owner of this server to get a copy of there json configs!";

    public static String clientWorldWarningLine1 = Ref.mod_id + ".client_world_error_line_1";
    public static String clientWorldWarningLine1Text = "Your current configs don't match with the current selected world!";
    public static String clientWorldWarningLine2 = Ref.mod_id + ".client_world_error_line_2";
    public static String clientWorldWarningLine2Text = "You can either continue, stop to sync, or go back to the main menu.";

    public static String clientWorldWarningLine3 = Ref.mod_id + ".client_world_error_line_3";

    public static String clientWorldWarningLine3Text = "If you continue without stopping to sync there is a chance things will disappear or your game might crash.";
}
