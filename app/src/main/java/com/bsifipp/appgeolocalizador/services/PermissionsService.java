package com.bsifipp.appgeolocalizador.services;

import android.Manifest;
import android.content.Context;

import com.bsifipp.appgeolocalizador.utils.Permissions;

public class PermissionsService {
    private Permissions permissions;
    private String[] PermissonsList = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET};
    public PermissionsService(Context context) {
        this.permissions = new Permissions(context);
    }
    public void CheckPermissionGranted()
    {
        if (permissions.hasPermissions(PermissonsList)) {
            //  permission granted
        } else {
            permissions.requestPermissions(PermissonsList, 1);
        }
    }
}
