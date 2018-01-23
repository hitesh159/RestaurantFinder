package com.example.hitesh.restaurantfinder;

/**
 * Created by hitesh on 1/18/2018.
 */

public class ListModel {
    private String address,locality,name;
    private int id;


    public int getId() {
        return id;
    }

    public ListModel(String address, String locality, String name, int id) {
        this.address = address;
        this.locality = locality;
        this.name = name;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }
    public void setLocality(String locality)
    {
        this.locality=locality;
    }
    public void setCity(String name)
    {
        this.name=name;
    }
    public String getCity()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }
    public String getLocality()
    {
        return locality;
    }

}
