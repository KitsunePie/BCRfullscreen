package com.ketal.bcrfullscreen;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(lpparam.processName))
            return;
        if (!lpparam.packageName.equals("com.bilibili.priconne"))
            return;
        XposedBridge.hookMethod(Activity.class.getDeclaredMethod("onResume"), new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Activity act = (Activity) param.thisObject;
                WindowManager.LayoutParams attributes = act.getWindow().getAttributes();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    attributes.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    act.getWindow().setAttributes(attributes);
                }
            }
        });
    }
}
