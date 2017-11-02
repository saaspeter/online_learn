package com.joybeacon.model;

public class City {
    
	private String citycode;
    
	private String name;

	private String zipcode;

    private String telregioncode;


    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelregioncode() {
        return telregioncode;
    }

    public void setTelregioncode(String telregioncode) {
        this.telregioncode = telregioncode;
    }
    
}