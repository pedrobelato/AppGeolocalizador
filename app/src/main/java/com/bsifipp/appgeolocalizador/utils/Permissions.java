package com.bsifipp.appgeolocalizador.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class Permissions {
    private Context mContext;

    public Permissions(Context context) {
        mContext = context;
    }
    public  boolean hasPermissions(String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mContext != null
                && permissions != null)
        {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mContext, permission) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public  void  requestPermissions( String[] PERMISSIONS,int PERMISSION_ALL) {
        ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, PERMISSION_ALL);
    }

}