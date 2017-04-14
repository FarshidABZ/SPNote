package com.farshidabz.supernote.util.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by FarshidAbz.
 * Since 4/14/2017.
 */

public class DrawingView extends View {

    Context context;

    public int width;
    public int height;

    private Bitmap bitmap;

    private Canvas canvas;

    private Path path;

    private Paint bitmapPaint;
    private Paint paint;

    private float brushSize;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupDrawing();
    }

    private void setupDrawing() {
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        initPaint();
    }


    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, paint);
    }

    public void startNew() {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setBrushSize(float size) {
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                size, getResources().getDisplayMetrics());
        paint.setStrokeWidth(brushSize);
    }

    private float X, Y;

    private void touch_start(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        X = x;
        Y = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - X);
        float dy = Math.abs(y - Y);
        path.quadTo(X, Y, (x + X) / 2, (y + Y) / 2);
        X = x;
        Y = y;
    }

    private void touch_up() {
        path.lineTo(X, Y);
        canvas.drawPath(path, paint);
        path.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public void setErase(boolean isErase) {
        if (isErase) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.setStrokeWidth(48);
            paint.setAlpha(0);
        } else {
            paint.setXfermode(null);
        }
    }

    public void setPaintColor(int paintColor) {
        paint.setColor(paintColor);
    }

    public Bitmap getCanvasBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bmp) {
        bitmap = bmp;
    }
}