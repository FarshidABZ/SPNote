package com.farshidabz.spnote.util.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.farshidabz.spnote.util.ScreenUtils;

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

    int colorId;

    private int brushSize;
    private boolean canDraw;
    private boolean somethingDrawn;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setupDrawing();
    }

    private void setupDrawing() {
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        somethingDrawn = false;

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
        setBrushSize(4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
        } else {
            bitmap = Bitmap.createBitmap(bitmap);
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            canvas = new Canvas(bitmap);
        }
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

    public void setBrushSize(int size) {
        brushSize = size;
        paint.setStrokeWidth(ScreenUtils.dpToPx(context, brushSize));
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

        somethingDrawn = true;
    }

    private void touch_up() {
        path.lineTo(X, Y);
        canvas.drawPath(path, paint);
        path.reset();
    }

    /**
     * Enable or disable drawing functionality
     *
     * @param canDraw the can draw
     */
    public void canDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canDraw)
            return false;

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

    /**
     * Sets erase mode.
     *
     * @param isErase the is erase
     */
    public void setErase(boolean isErase) {
        if (isErase) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.setStrokeWidth(ScreenUtils.dpToPx(context, 12));
        } else {
            setBrushSize(brushSize);
            paint.setXfermode(null);
        }
    }

    public void setPaintColor(int paintColor) {
        this.colorId = paintColor;
        paint.setColor(context.getResources().getColor(paintColor));
    }

    /**
     * Gets drawn image as bitmap.
     *
     * @return the canvas bitmap
     */
    public Bitmap getCanvasBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bmp) {
        bitmap = bmp;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public int getColorId() {
        return colorId;
    }

    public boolean isSomethingDrawn() {
        return somethingDrawn;
    }
}