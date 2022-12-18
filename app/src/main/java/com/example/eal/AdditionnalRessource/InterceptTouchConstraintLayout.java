package com.example.eal.AdditionnalRessource;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


//Inspired by ywwynm from https://stackoverflow.com/questions/30419898/parent-click-event-not-firing-when-recyclerview-clicked
public class InterceptTouchConstraintLayout extends ConstraintLayout {
    public InterceptTouchConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public InterceptTouchConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
