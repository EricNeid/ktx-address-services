/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.here.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapView {

    @SerializedName("topLeft")
    @Expose
    private TopLeft topLeft;
    @SerializedName("bottomRight")
    @Expose
    private BottomRight bottomRight;

    public TopLeft getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(TopLeft topLeft) {
        this.topLeft = topLeft;
    }

    public BottomRight getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(BottomRight bottomRight) {
        this.bottomRight = bottomRight;
    }

}
