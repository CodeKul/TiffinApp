package com.codekul.externalizingresources;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aniruddha on 19/4/17.
 */

public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private View.OnClickListener clickListener;

    public MyImageView(Context context) {
        super(context);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.clickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickListener.onClick(this);
        return super.onTouchEvent(event);
    }
}
