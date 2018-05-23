package com.ksw.googlemapview.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * i will add all the infoWindow into this layout.ok,now it just a normal constraintLayout....
 */
public class WrapperLayout extends ConstraintLayout {

    public WrapperLayout(Context context) {
        super(context);
    }

    public WrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
