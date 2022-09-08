/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.ktxaddress.here.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HereGeoCodeResult {

    @SerializedName("response")
    @Expose
    private GeoCode response;

    public GeoCode getResponse() {
        return response;
    }

    public void setResponse(GeoCode response) {
        this.response = response;
    }

}
