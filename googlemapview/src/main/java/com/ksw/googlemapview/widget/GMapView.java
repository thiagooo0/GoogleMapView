package com.ksw.googlemapview.widget;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.Marker;
import com.ksw.googlemapview.R;
import com.ksw.googlemapview.been.InfoWindow;

import java.util.ArrayList;


/**
 * use GMapView as google MapView{@link MapView}.but the infoWindow in GMapView is living.
 * <p>
 * this view have some methods to help u add or remove infoWindow:{@link #showInfoWindow(InfoWindow)},
 * {@link #dismissInfoWindow(InfoWindow)}.
 * </p>
 *
 * @author KwokSiuWang
 * @date 2018/5/18
 */
public class GMapView extends FrameLayout {
    private static final String TAG = "GMapView";
    private WrapperLayout wrapperLayout;
    private GoogleMap googleMap;
    private MapView mapView;
    private Projection projection;
    private ArrayList<InfoWindow> infoWindows = new ArrayList<>();
    private boolean flag = false;

    private Point point;
    private WrapperLayout.LayoutParams layoutParams;

    public GMapView(Context context) {
        this(context, null);
    }

    public GMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GMapView(Context context, AttributeSet attributeSet, final int i) {
        super(context, attributeSet, i);
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.layout_map_view, null);
        mapView = constraintLayout.findViewById(R.id.map_view);
        wrapperLayout = constraintLayout.findViewById(R.id.wrapper_layout);
        addView(constraintLayout);

        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    GMapView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            //every 16 seconds,update the position of all the infoWindow.
                            if (infoWindows.size() != 0) {
                                projection = googleMap.getProjection();
                                for (InfoWindow infoWindow : infoWindows) {
                                    point = projection.toScreenLocation(infoWindow.getMarker().getPosition());
                                    if (point.x < 0 || point.y < 0) {
                                        //when the marker out of the screen, infoWindow invisible too.
                                        infoWindow.getInfoWindow().setVisibility(INVISIBLE);
                                    } else {
                                        infoWindow.getInfoWindow().setVisibility(VISIBLE);
                                        layoutParams = (WrapperLayout.LayoutParams) infoWindow.getInfoWindow().getLayoutParams();
                                        layoutParams.leftMargin = point.x + infoWindow.getOffsetX();
                                        layoutParams.topMargin = point.y + infoWindow.getOffsetY();
                                        infoWindow.getInfoWindow().setLayoutParams(layoutParams);
                                    }
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void getMapAsync(final OnMapReadyCallback onMapReadyCallback) {
        //GMapView need a googleMap too.
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GMapView.this.googleMap = googleMap;
                if (onMapReadyCallback != null) {
                    onMapReadyCallback.onMapReady(googleMap);
                }
            }
        });
    }

    /**
     * show a infoWindow.yes,u can show many infoWindow on a same time.
     *
     * @param infoWindow the infoWindow u want to show, {@link InfoWindow}
     */
    public void showInfoWindow(InfoWindow infoWindow) {
        if (infoWindow.isEmpty()) {
            Log.d(TAG, "info window is null");
            return;
        }
        if (!infoWindows.contains(infoWindow)) {
            if (infoWindow.getInfoWindow() != null) {
                if (infoWindow.getInfoWindow().getLayoutParams() == null) {
                    WrapperLayout.LayoutParams layoutParams = new WrapperLayout.LayoutParams(
                            WrapperLayout.LayoutParams.WRAP_CONTENT, WrapperLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.topToTop = WrapperLayout.LayoutParams.PARENT_ID;
                    layoutParams.leftToLeft = WrapperLayout.LayoutParams.PARENT_ID;
                    infoWindow.getInfoWindow().setLayoutParams(layoutParams);
                }
                wrapperLayout.addView(infoWindow.getInfoWindow());
            } else {
                //init the view
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(infoWindow.getInfoWindowId(), wrapperLayout);
                //get the view
                infoWindow.setInfoWindow(viewGroup.getChildAt(viewGroup.getChildCount() - 1));
                //set params
                WrapperLayout.LayoutParams layoutParams = (WrapperLayout.LayoutParams) infoWindow.getInfoWindow().getLayoutParams();
                layoutParams.topToTop = WrapperLayout.LayoutParams.PARENT_ID;
                layoutParams.leftToLeft = WrapperLayout.LayoutParams.PARENT_ID;
                infoWindow.getInfoWindow().setVisibility(INVISIBLE);
                infoWindow.getInfoWindow().setLayoutParams(layoutParams);
            }
            infoWindows.add(infoWindow);
        }
    }

    /**
     * show a infoWindow.yes,u can show many infoWindow on a same time.
     */
    public InfoWindow showInfoWindow(View view, Marker marker) {
        InfoWindow infoWindow = new InfoWindow(view, marker);
        showInfoWindow(infoWindow);
        return infoWindow;
    }

    /**
     * show a infoWindow.yes,u can show many infoWindow on a same time.
     */
    public InfoWindow showInfoWindow(View view, Marker marker, int offsetX, int offsetY) {
        InfoWindow infoWindow = new InfoWindow(view, marker, offsetX, offsetY);
        showInfoWindow(infoWindow);
        return infoWindow;
    }

    /**
     * show a infoWindow.yes,u can show many infoWindow on a same time.
     */
    public InfoWindow showInfoWindow(int layoutId, Marker marker) {
        InfoWindow infoWindow = new InfoWindow(layoutId, marker);
        showInfoWindow(infoWindow);
        return infoWindow;
    }

    /**
     * show a infoWindow.yes,u can show many infoWindow on a same time.
     */
    public InfoWindow showInfoWindow(int layoutId, Marker marker, int offsetX, int offsetY) {
        InfoWindow infoWindow = new InfoWindow(layoutId, marker, offsetX, offsetY);
        showInfoWindow(infoWindow);
        return infoWindow;
    }

    /**
     * dismiss a infoWindow.
     */
    public void dismissInfoWindow(InfoWindow infoWindow) {
        infoWindows.remove(infoWindow);
        wrapperLayout.removeView(infoWindow.getInfoWindow());
    }


    public void onCreate(Bundle bundle) {
        mapView.onCreate(bundle);
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onStart() {
        mapView.onStart();
    }

    public void onStop() {
        mapView.onStop();
    }

    public void onDestroy() {
        flag = false;
        mapView.onDestroy();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        mapView.onSaveInstanceState(bundle);
    }
}
