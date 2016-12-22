package com.ab.cordovafloatingactivityPack;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class cordovafloatingactivity extends CordovaPlugin {
    private PermissionChecker mPermissionChecker;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        PackageManager pm = cordova.getActivity().getPackageManager();
        Context context = cordova.getActivity().getApplicationContext();
        String packageName = "test";
        Boolean result = true;

        if (action.equals("startFloatingActivity")) {

            mPermissionChecker = new PermissionChecker(cordova.getActivity());
            if (!mPermissionChecker.isRequiredPermissionGranted()) {
                Intent intent = mPermissionChecker.createRequiredPermissionIntent();
                cordova.getActivity().startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
            } else {
                result = launchService(pm, context, packageName, context);

                if (result) {
                    callbackContext.success("success");
                } else {
                    callbackContext.success("false");
                }
                return true;
            }

        } else if (action.equals("stopFloatingActivity")) {
            result = stopService(pm, context, packageName, context);
            return result;
        } else {
            return false;
        }

        return true;

    }

    public boolean launchService(PackageManager pm, Context c, String packname, final Context con) {
        cordova.getActivity().startService(new Intent(cordova.getActivity().getApplication(), ChatHeadService.class));
        return true;

    }

    public boolean stopService(PackageManager pm, Context c, String packname, final Context con) {
        cordova.getActivity().stopService(new Intent(cordova.getActivity().getApplication(), ChatHeadService.class));
        return true;

    }

}
