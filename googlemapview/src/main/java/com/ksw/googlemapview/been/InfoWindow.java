package com.ksw.googlemapview.been;

import android.view.View;

import com.google.android.gms.maps.model.Marker;

/**
 * @author KwokSiuWang
 * @date 2018/5/22
 */
public class InfoWindow {
    /**
     * the infoWindow u want to add into the map.
     */
    private View infoWindow;

    private int infoWindowId;
    /**
     * offset in x axis(px).
     */
    private int offsetX = 0;
    /**
     * offset in y axis(px).
     */
    private int offsetY = 0;

    /**
     * the marker which this infoWindow belong to.
     */
    private Marker marker;

    public InfoWindow() {
    }

    public InfoWindow(int layoutId, Marker marker) {
        setInfoWindowId(layoutId);
        setMarker(marker);
    }

    public InfoWindow(int layoutId, Marker marker, int offsetX, int offsetY) {
        setInfoWindowId(layoutId);
        setMarker(marker);
        setOffsetX(offsetX);
        setOffsetY(offsetY);
    }

    public InfoWindow(View view, Marker marker) {
        setInfoWindow(view);
        setMarker(marker);
    }

    public InfoWindow(View view, Marker marker, int offsetX, int offsetY) {
        setInfoWindow(view);
        setMarker(marker);
        setOffsetX(offsetX);
        setOffsetY(offsetY);
    }

    /**
     * we need (infoWindow||infoWindowId) && marker to finish the job.
     *
     * @return is this infoWindow have infoWindow{@link #infoWindow} and marker.
     */
    public boolean isEmpty() {
        return (infoWindowId == 0 && infoWindow == null) || marker == null;
    }

    public View getInfoWindow() {
        return infoWindow;
    }

    public void setInfoWindow(View infoWindow) {
        this.infoWindow = infoWindow;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public int getInfoWindowId() {
        return infoWindowId;
    }

    public void setInfoWindowId(int infoWindowId) {
        this.infoWindowId = infoWindowId;
    }
}
