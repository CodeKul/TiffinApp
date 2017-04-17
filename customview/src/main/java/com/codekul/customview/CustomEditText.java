package com.codekul.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by aniruddha on 17/4/17.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    private Paint paint;

    public CustomEditText(Context context) {
        super(context);

        // create view via code

        initPaint();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // create view via xml

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(2f);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.RED);
        canvas.drawLine(50, 0, 50, getHeight(), paint);

        paint.setColor(Color.BLACK);
        for(int n = 1; n < 100 ; n++) {
            canvas.drawLine(0, n * 50, getWidth(), n*50 , paint);
        }

        /*canvas.drawLine(0, 20 , getWidth(), 20 , paint);
        canvas.drawLine(0, 40 , getWidth(), 40 , paint);
        canvas.drawLine(0, 60 , getWidth(), 60 , paint);*/
    }
}
