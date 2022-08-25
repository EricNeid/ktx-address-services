/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.here.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("locationId")
    @Expose
    private String locationId;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("displayPosition")
    @Expose
    private Position displayPosition;
    @SerializedName("navigationPosition")
    @Expose
    private List<Position> navigationPosition = null;
    @SerializedName("mapView")
    @Expose
    private MapView mapView;
    @SerializedName("address")
    @Expose
    private Address address;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Position getDisplayPosition() {
        return displayPosition;
    }

    public void setDisplayPosition(Position displayPosition) {
        this.displayPosition = displayPosition;
    }

    public List<Position> getNavigationPosition() {
        return navigationPosition;
    }

    public void setNavigationPosition(List<Position> navigationPosition) {
        this.navigationPosition = navigationPosition;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
