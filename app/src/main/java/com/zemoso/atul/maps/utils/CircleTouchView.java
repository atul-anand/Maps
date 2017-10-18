package com.zemoso.atul.maps.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CircleTouchView extends View {

    private static final String TAG = "CircleView";
    private static final double MOVE_SENSITIVITY = 1.25;
    public Circle circle;
    private Paint circlePaint;
    private boolean isPinchMode;
    private int lastCircleX;
    private int lastCircleY;
    private boolean isDoneResizing = true;

    public CircleTouchView(Context context) {
        super(context);
        setCirclePaint(0x220000ff);
    }

    public CircleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCirclePaint(0x220000ff);
    }

    public CircleTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCirclePaint(0x220000ff);
    }

    public void setCircle(Circle circle) {
        circle = circle;
    }

    private void setCirclePaint(int color) {
        circlePaint = new Paint();
        circlePaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circle.centerX, circle.centerY, circle.radius, circlePaint);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        int historySize;
        double lastDistance;
        double oneBeforeLastDistance;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastCircleX = circle.centerX;
                lastCircleY = circle.centerY;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                isPinchMode = true;
                isDoneResizing = false;
                break;

            case MotionEvent.ACTION_MOVE:
                circle.centerX = lastCircleX;
                circle.centerY = lastCircleY;
                ;

                if (getTouchedCircle((int) event.getX(), (int) event.getY()) && !isPinchMode && isDoneResizing) {

                    historySize = event.getHistorySize();
                    if (historySize > 0) {

                        oneBeforeLastDistance = Math.sqrt((event.getX() - event.getHistoricalX(0, historySize - 1)) *
                                (event.getX() - event.getHistoricalX(0, historySize - 1)) +
                                (event.getY() - event.getHistoricalY(0, historySize - 1)) *
                                        (event.getY() - event.getHistoricalY(0, historySize - 1)));


                        if (oneBeforeLastDistance > MOVE_SENSITIVITY) {
                            circle.centerX = (int) event.getX();
                            circle.centerY = (int) event.getY();
                            lastCircleX = circle.centerX;
                            lastCircleY = circle.centerY;

                        }
                    }
                }
                if (isPinchMode) {
                    circle.centerX = lastCircleX;
                    circle.centerY = lastCircleY;

                    historySize = event.getHistorySize();
                    if (historySize > 0) {

                        lastDistance = Math.sqrt((event.getX(0) - event.getX(1)) * (event.getX(0) - event.getX(1)) +
                                (event.getY(0) - event.getY(1)) * (event.getY(0) - event.getY(1)));

                        oneBeforeLastDistance = Math.sqrt((event.getHistoricalX(0, historySize - 1) - event.getHistoricalX(1, historySize - 1)) *
                                (event.getHistoricalX(0, historySize - 1) - event.getHistoricalX(1, historySize - 1)) +
                                (event.getHistoricalY(0, historySize - 1) - event.getHistoricalY(1, historySize - 1)) *
                                        (event.getHistoricalY(0, historySize - 1) - event.getHistoricalY(1, historySize - 1)));


                        if (lastDistance < oneBeforeLastDistance) {
                            circle.radius -= Math.abs(lastDistance - oneBeforeLastDistance);
                        } else {
                            circle.radius += Math.abs(lastDistance - oneBeforeLastDistance);
                        }
                    }
                }
                lastCircleX = circle.centerX;
                lastCircleY = circle.centerY;
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                circle.centerX = lastCircleX;
                circle.centerY = lastCircleY;
                isPinchMode = false;
                break;

            case MotionEvent.ACTION_UP:
                circle.centerX = lastCircleX;
                circle.centerY = lastCircleY;
                isPinchMode = false;
                isDoneResizing = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_HOVER_MOVE:
                break;

            default:
                super.onTouchEvent(event);
                break;
        }
        return true;
    }

    private Boolean getTouchedCircle(final int xTouch, final int yTouch) {
        if ((circle.centerX - xTouch) * (circle.centerX - xTouch) +
                (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius * circle.radius) {
            return true;
        } else {
            return false;
        }

    }

    public class Circle {
        int radius;
        int centerX;
        int centerY;
        Double lat;
        Double lon;
        LatLng coordinates;
        String type;

        Circle(JSONObject jsonObject) {
            String rad = jsonObject.optString("radius");
            radius = Integer.parseInt(rad);
            JSONArray coords = jsonObject.optJSONArray("coordinates");
            lat = coords.optDouble(0);
            lon = coords.optDouble(1);
            centerX = Integer.parseInt(String.valueOf(lat));
            centerY = Integer.parseInt(String.valueOf(lon));
            coordinates = new LatLng(lat, lon);
            type = jsonObject.optString("type");
        }

        @Override
        public String toString() {
            return "{" +
                    "\"type\":\"" + type + '\"' +
                    ", \"coordinates\":" + coordinates +
                    ", \"radius\":\"" + radius + '\"' +
                    '}';
        }

        public JSONObject toJSON() {
            Map<String, Object> map = new HashMap<>();
            map.put("type", type);
            map.put("coordinates", coordinates.toString());
            map.put("radius", radius);
            return new JSONObject(map);
        }
    }
}
