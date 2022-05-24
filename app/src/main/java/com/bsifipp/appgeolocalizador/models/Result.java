package com.bsifipp.appgeolocalizador.models;

import java.util.ArrayList;

public class Result{
    public ArrayList<AddressComponent> address_components;
    public String formatted_address;
    public Geometry geometry;
    public String place_id;
    public ArrayList<String> types;
}
