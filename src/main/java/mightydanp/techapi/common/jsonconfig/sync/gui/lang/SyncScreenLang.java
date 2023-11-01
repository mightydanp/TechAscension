package mightydanp.techapi.common.jsonconfig.sync.gui.lang;

import mightydanp.techapi.common.jsonconfig.sync.gui.lib.SyncScreenButtonRef;
import mightydanp.techapi.common.jsonconfig.sync.gui.lib.SyncScreenRef;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.LangData;

public class SyncScreenLang {

    public static void init(){
        LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());

       enLang.addTranslation(SyncScreenRef.syncWarningLine1, SyncScreenRef.syncWarningLine1Text);
       enLang.addTranslation(SyncScreenRef.syncWarningLine2, SyncScreenRef.syncWarningLine2Text);
       enLang.addTranslation(SyncScreenRef.syncWarningLine3, SyncScreenRef.syncWarningLine3Text);

       enLang.addTranslation(SyncScreenRef.clientWorldWarningLine1, SyncScreenRef.clientWorldWarningLine1Text);
       enLang.addTranslation(SyncScreenRef.clientWorldWarningLine2, SyncScreenRef.clientWorldWarningLine2Text);
       enLang.addTranslation(SyncScreenRef.clientWorldWarningLine3, SyncScreenRef.clientWorldWarningLine3Text);


       enLang.addTranslation(SyncScreenButtonRef.Continue, SyncScreenButtonRef.continueText);
       enLang.addTranslation(SyncScreenButtonRef.override, SyncScreenButtonRef.overrideText);
       enLang.addTranslation(SyncScreenButtonRef.stopToSync, SyncScreenButtonRef.stopToSyncText);
       enLang.addTranslation(SyncScreenButtonRef.syncClientConfigsWithServers, SyncScreenButtonRef.syncClientConfigsWithServersText);
       enLang.addTranslation(SyncScreenButtonRef.returnToMainMenuScreen, SyncScreenButtonRef.returnToMainMenuScreenText);

        AssetPackRegistry.langDataMap.put("en_us", enLang);
    }
}
