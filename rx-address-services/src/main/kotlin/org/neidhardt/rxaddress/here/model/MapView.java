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
    private Position topLeft;
    @SerializedName("bottomRight")
    @Expose
    private Position bottomRight;

    public Position getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Position topLeft) {
        this.topLeft = topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Position bottomRight) {
        this.bottomRight = bottomRight;
    }

}
