package ch.fhnw.edu.emoba.spheroapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomView extends View {

    private Paint linePaint;
    private Paint circlePaint;

    private float currentX;
    private float currentY;

    private ScheduledFuture viewTask;

    private AtomicBoolean isCancelled = new AtomicBoolean();

    public CustomView(Context context) {
        super(context);

        linePaint = new Paint();
        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.YELLOW);

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);
    }

    @Override
    public void onAttachedToWindow(){
        super.onAttachedToWindow();

        currentX = getWidth();
        currentY = getHeight();

        ScheduledExecutorService scheduler;
        // Created scheduler
        scheduler = Executors.newSingleThreadScheduledExecutor();
        // Start View-Task after a delay of 50ms with an interval of 50ms
        viewTask = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 50, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        viewTask.cancel(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 50, circlePaint);
        canvas.drawLine(getWidth()/2, getHeight()/2, currentX, currentY, linePaint);
        canvas.drawCircle(currentX, currentY, 10, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        return true;
    }

}
