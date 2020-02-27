package cordova.plugin.check.fake.location;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.provider.Settings;
import android.Manifest;

import android.provider.Settings.Secure;

import android.location.Location;
import android.location.LocationManager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Build;
import android.app.Activity;

// import android.support.v4.content.ContextCompat;

import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;
import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class DetectFakeLocationPlugin extends CordovaPlugin {

    private PluginResult.Status status = PluginResult.Status.OK;
    private String result = "";
    private Context mContext = null;
    private String appPackageName = "";
    private FusedLocationProviderClient f = null;
    private String[] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION };
    private boolean isSuccess = true;
    private boolean mockStatus = false;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        mContext = this.cordova.getActivity().getApplicationContext();

        if (action.equals("checkIsMockLocation")) {
            Log.i("xxx", "Init");
            checkIsMockLocation(action, callbackContext);
            return true;
        }
        return false;
    }

    private void checkIsMockLocation(String action, CallbackContext callbackContext) {
        JSONObject rs = new JSONObject();
        isSuccess = true;
        mockStatus = false;
        try {
            Log.i("xxx", "Start");
            if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, mContext)
                    && checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, mContext)) {
                Log.i("xxx", "Perm OK");
                getLocation(callbackContext);
            } else {
                Log.i("xxx", "Perm fail");
                // requesrLocationPermission();
                isSuccess = false;
            }

            // Wait for listener location
            while (isSuccess) {
                Log.i("xxx", "End proccess2");
            }
            rs.put("status", mockStatus);
        } catch (JSONException err) {
            status = PluginResult.Status.ERROR;
            result = "" + err.toString();
            Log.i("xxx", "End proccess1");
        } catch (Exception err) {
            status = PluginResult.Status.ERROR;
            result = "" + err.toString();
        }
        Log.i("xxx", "End");
        callbackContext.sendPluginResult(new PluginResult(status, rs));

    }

    private void requesrLocationPermission() {
        requestAllPermission(permissions, 111);
    }

    private Boolean isMockLocation(Location location) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && location != null
                && location.isFromMockProvider();
    }

    private void getLocation(CallbackContext callbackContext) {
        Log.i("xxx", "Get Location");
        f = LocationServices.getFusedLocationProviderClient(mContext);
        f.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                JSONObject rs = new JSONObject();
                try {
                    mockStatus = isMockLocation(location);
                    Log.i("xxx", "get lo OK2 = " + mockStatus);
                    rs.put("status", mockStatus);
                } catch (JSONException err) {
                    Log.i("xxx", err.toString());
                    status = PluginResult.Status.ERROR;
                    result = "" + err.toString();
                } catch (Exception err) {
                    Log.i("xxx", err.toString());
                    status = PluginResult.Status.ERROR;
                    result = "" + err.toString();
                }
                isSuccess = false;
                // callbackContext.sendPluginResult(new PluginResult(status, rs));
            }
        });
    }

    public boolean checkPermission(String permission, Context context) {
        return cordova.hasPermission(permission);
    }

    public void requestAllPermission(String[] permissionArray, int requestCode) {
        cordova.requestPermissions(this, requestCode, permissionArray);
    }

}
