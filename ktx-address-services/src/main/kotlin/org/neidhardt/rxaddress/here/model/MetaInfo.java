/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.here.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaInfo {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
