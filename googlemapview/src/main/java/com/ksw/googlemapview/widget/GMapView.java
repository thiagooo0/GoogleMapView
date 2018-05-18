package com.ksw.googlemapview.widget;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ksw.googlemapview.R;

import java.util.zip.Inflater;

/**
 * @author KwokSiuWang
 * @date 2018/5/18
 */
public class GMapView extends FrameLayout {
    private static final String TAG = "GMapView";
    private WrapperLayout wrapperLayout;
    private GoogleMap googleMap;
    MapView mapView;
    Marker marker;

    public GMapView(Context context) {
        this(context, null);
    }

    public GMapView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.layout_map_view, null);
        mapView = constraintLayout.findViewById(R.id.map_view);
        wrapperLayout = constraintLayout.findViewById(R.id.wrapper_layout);
        wrapperLayout.setDetector(new GestureDetector(getContext(),
                new GestureDetector.OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
//                        Log.d(TAG, "onDown");
                        return false;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
//                        Log.d(TAG, "onShowPress");
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
//                        Log.d(TAG, "onSingleTapUp");
                        return false;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                        Log.d(TAG, "onScroll");
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
//                        Log.d(TAG, "onLongPress");
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                        Log.d(TAG, "onFling");
                        return false;
                    }
                }));
        addView(constraintLayout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    GMapView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            if (marker != null) {
                                Log.d(TAG, "start setInfoWindow");
                                projection = googleMap.getProjection();
                                Point point = projection.toScreenLocation(marker.getPosition());
                                Log.d(TAG, "marker position:" + point.x + "/" + point.y);
                                WrapperLayout.LayoutParams layoutParams = (WrapperLayout.LayoutParams) textView.getLayoutParams();
//                                layoutParams.editorAbsoluteX = point.x;
//                                layoutParams.editorAbsoluteY = point.y;
//                                layoutParams.leftToLeft = WrapperLayout.LayoutParams.PARENT_ID;
//                                layoutParams.topToTop = WrapperLayout.LayoutParams.PARENT_ID;
                                layoutParams.leftMargin = point.x;
                                layoutParams.topMargin = point.y;
                                textView.setLayoutParams(layoutParams);

                                Log.d(TAG, "-----end setInfoWindow");
//                                textView.layout(point.x, point.y, point.x + textView.getWidth(), point.y + textView.getHeight());
                            }
                        }
                    });
                    try {
                        Thread.sleep(13);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    TextView textView;
    Projection projection;

    public void getMapAsync(final OnMapReadyCallback onMapReadyCallback) {
        //拦截一个googeleMap下来，自己用。
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GMapView.this.googleMap = googleMap;
                LatLng positon = new LatLng(23.1471650000, 113.4153150000);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(positon);
                marker = googleMap.addMarker(markerOptions);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(positon));

                textView = new TextView(GMapView.this.getContext());
                textView.setText("我是时间");
                WrapperLayout.LayoutParams layoutParams = new WrapperLayout.LayoutParams(WrapperLayout.LayoutParams.WRAP_CONTENT, WrapperLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.topToTop = WrapperLayout.LayoutParams.PARENT_ID;
//                layoutParams.bottomToBottom = WrapperLayout.LayoutParams.PARENT_ID;
                layoutParams.leftToLeft = WrapperLayout.LayoutParams.PARENT_ID;
//                layoutParams.rightToRight = WrapperLayout.LayoutParams.PARENT_ID;
                textView.setLayoutParams(layoutParams);
                wrapperLayout.addView(textView);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText("时间：" + System.currentTimeMillis());
                    }
                });
                if (onMapReadyCallback != null) {
                    onMapReadyCallback.onMapReady(googleMap);
                }
            }
        });
    }

    private void addInterceptLayout() {
        GMapView.LayoutParams layoutParams = new GMapView.LayoutParams(GMapView.LayoutParams.MATCH_PARENT, GMapView.LayoutParams.MATCH_PARENT);
        wrapperLayout = new WrapperLayout(getContext());
//        wrapperLayout.setLayoutParams(layoutParams);
        wrapperLayout.setBackgroundColor(Color.BLUE);
        addView(wrapperLayout, 0);
        wrapperLayout.setDetector(new GestureDetector(getContext(),
                new GestureDetector.OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        Log.d(TAG, "onDown");
                        return false;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
                        Log.d(TAG, "onShowPress");
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Log.d(TAG, "onSingleTapUp");
                        return false;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        Log.d(TAG, "onScroll");
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        Log.d(TAG, "onLongPress");
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        Log.d(TAG, "onFling");
                        return false;
                    }
                }));

    }

    public void setView(View view) {
        WrapperLayout.LayoutParams layoutParams = new WrapperLayout.LayoutParams(WrapperLayout.LayoutParams.WRAP_CONTENT, WrapperLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(200, 200, 200, 200);
        layoutParams.topToTop = WrapperLayout.LayoutParams.PARENT_ID;
//        layoutParams.bottomToBottom = WrapperLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = WrapperLayout.LayoutParams.PARENT_ID;
//        layoutParams.rightToRight = WrapperLayout.LayoutParams.PARENT_ID;
        view.setLayoutParams(layoutParams);
        wrapperLayout.addView(view, 0);
        wrapperLayout.setBackgroundColor(Color.BLUE);
        invalidate();
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
        mapView.onDestroy();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        mapView.onSaveInstanceState(bundle);
    }
}
