package com.doris.password.widget.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Doris
 * @date 2018/11/3
 */
public class PasswordCircleView extends View {

    private Paint mPaint;
    private int color = Color.BLACK;

    public PasswordCircleView(Context context) {
        super(context);
        init();
    }


    public PasswordCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        double width = (getWidth() - getPaddingLeft() - getPaddingRight()) * 0.5;
        double height = (getHeight() - getPaddingTop() - getPaddingBottom()) * 0.5;
        int cx = (int) (getPaddingLeft() + width);
        int cy = (int) (getPaddingTop() + height);
        int radius = (int) Math.min(width,height);
        mPaint.setColor(color);
        canvas.drawCircle(cx,cy,radius,mPaint);
    }
}
