/*
 * Copyright (c) 2016 Appolica Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ksw.googlemapview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 这个类用来接收所有的触碰事件。
 */
public class WrapperLayout extends ConstraintLayout {

    private GestureDetector detector;

    public WrapperLayout(Context context) {
        super(context);
    }

    public WrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (detector != null) {
            detector.onTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void layoutView(View view, Point point) {
        view.layout(point.x, point.y, point.x + view.getWidth(), point.y + view.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (detector != null) {
            detector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public void setDetector(GestureDetector detector) {
        this.detector = detector;
    }
}
