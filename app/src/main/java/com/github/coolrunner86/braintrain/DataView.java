package com.github.coolrunner86.braintrain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flex on 04.08.16.
 */
public class DataView extends View {
    public static final int DIGITS_IN_ROW = 9;
    private static final String TAG = "DataView";

    private List<Byte> data;

    private int totalRowsCount;

    private int totalHeight;
    private int cellHeight;
    private int cellWidth;
    private int rowsShown;

    private Paint paint = new Paint();
    private DrawPosition pos = new DrawPosition();

    private float motionBeginY;
    private boolean isMovePerformed;

    private OnCellClickListener onCellClickListener;

    private float[] linePoints;
    Point markPoint = new Point(-1, -1);

    public interface OnCellClickListener {
        boolean onCellClick(int cellIndex);
    }

    private class DrawPosition {
        public int x;
        public int y;

        public int posIndex;

        DrawPosition() {
            setCoords(getLeft(), getTop());
        }

        DrawPosition(int _x, int _y) {
            setCoords(_x, _y);
        }

        public void setCoords(int _x, int _y) {
            x = _x + cellWidth / 2;
            y = _y + cellHeight - (int) paint.descent() / 2;
//            y = _y + cellHeight / 2 - (int)((paint.descent() + paint.ascent()) / 2);

            posIndex = 0;
        }

        public void next()
        {
            posIndex++;

            if((posIndex % DIGITS_IN_ROW) == 0) {
                y += cellHeight;
                x = getLeft() + cellWidth / 2;
            }
            else
                x += cellWidth;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }


    public DataView(Context context) {
        super(context);

        Log.d(TAG, "DataView(Context)");

        paint.setColor(Color.GREEN);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public DataView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.d(TAG, "DataView(Context, AttributeSet)");

        paint.setColor(Color.GREEN);
        paint.setTextAlign(Paint.Align.CENTER);

        data = new ArrayList<Byte>();

        setClickable(true);
        setKeepScreenOn(true);
    }

    public void setNewData(List<Byte> newData) {
        Log.d(TAG, "setNewData: data.size()" + data.size());

        data = newData;
        totalRowsCount = data.size() / DIGITS_IN_ROW;

        if(data.size() % DIGITS_IN_ROW != 0)
            totalRowsCount++;

        totalHeight = totalRowsCount * cellHeight;

        if(totalHeight < getHeight())
            totalHeight = getHeight();

        invalidate();
    }

    public void setOnCellClickListener(@Nullable OnCellClickListener l) {
        onCellClickListener = l;
    }

    public void setMarkOnDigit(int index, boolean mark)
    {
        if(mark) {
            markPoint.x = getLeft() + index % DIGITS_IN_ROW * cellWidth;
            markPoint.y = getTop() + index / DIGITS_IN_ROW * cellHeight;
        }
        else {
            markPoint.x = -1;
            markPoint.y = -1;
        }
    }

//    public void test() {
//        Log.d(TAG, "test");
//        totalRowsCount = 20;//rowsShown * 2;
//        totalHeight = totalRowsCount * cellHeight;
//
//        for(int i = 0; i < totalRowsCount; i++) {
//            for(int j = 0; j < DIGITS_IN_ROW; j++)
//                data.add((byte) (i % 10));
//        }
//
//        invalidate();
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        drawEmptyRows(canvas);

        pos.setCoords(getLeft(), getFloorY());

        int index = (getScrollY() - getTop()) / cellHeight * DIGITS_IN_ROW;
        int endIndex;

        Log.d(TAG, "onDraw: getScrollY() = " + getScrollY() + ", getTop() = " + getTop());

        if(getFloorY() == getScrollY())
            endIndex = index + (rowsShown + 1) * DIGITS_IN_ROW;
        else
            endIndex = index + (rowsShown + 2) * DIGITS_IN_ROW;

        Log.d(TAG, "onDraw: index = " + index + ", endIndex = " + endIndex + ", data.size() = " + data.size());

        if(endIndex > data.size())
            endIndex = data.size();

        for(; index < endIndex; index++) {
            drawDigit(canvas, data.get(index), pos);
            pos.next();

            //Log.d(TAG, "onDraw: next pos is " + pos);
        }

        if(markPoint.x != -1)
            markDigit(canvas, markPoint);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "OnLayout");
        Log.d(TAG, "OnLayout: getScrollY() = " + getScrollY() + ", getTop() = " + getTop());

        super.onLayout(changed, left, top, right, bottom);
    }

//    @Override
//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//    }

    @Override
    protected int computeVerticalScrollExtent() {
        Log.d(TAG, "computeVerticalScrollExtent");

        if(totalRowsCount < rowsShown)
            return getHeight();

        return totalHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged");
        Log.d(TAG, "OnSizeChanged: getScrollY() = " + getScrollY() + ", getTop() = " + getTop());

        cellWidth = w / DIGITS_IN_ROW;
        rowsShown = h / cellWidth;
        cellHeight = h / rowsShown;

        Log.d(TAG, "onSizeChanged: w = " + w + ", h = " + h + ", cellWidth = " + cellWidth + ", cellHeight = " + cellHeight + ", rowsShown = " + rowsShown);

        totalHeight = totalRowsCount * cellHeight;

        if(totalHeight < getHeight())
            totalHeight = getHeight();

        paint.setTextSize(cellHeight);

        linePoints = new float[(rowsShown + 1 + DIGITS_IN_ROW + 1) * 4];

        setScrollY(getTop());

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                performMove(event);
                isMovePerformed = true;
                break;

            case MotionEvent.ACTION_DOWN:
                motionBeginY = event.getY();
                isMovePerformed = false;
                break;

            case MotionEvent.ACTION_UP:
                performTap(event);
                break;
        }

        return super.onTouchEvent(event);
    }

    private void performTap(MotionEvent event) {
        if(isMovePerformed)
            return;

        int y = (int)event.getY();
        Log.d(TAG, "performTap:1) y = " + y);
        y += getScrollY() - getTop();
        Log.d(TAG, "performTap:2) y = " + y);

        int rowIndex = y / cellHeight;
        int colIndex = ((int)event.getX() - getLeft()) / cellWidth;

        Log.d(TAG, "performTap: rowIndex = " + rowIndex + ", colIndex = " + colIndex);

        if(onCellClickListener != null) {
            if(onCellClickListener.onCellClick(rowIndex * DIGITS_IN_ROW + colIndex))
                invalidate();
        }
    }

    private void performMove(MotionEvent event) {
        Log.d(TAG, "performMove");

        int historySize = event.getHistorySize();
        int scrollValue;

//            if(historySize > 0)
//            {
//                int scrollValue = (int)(event.getHistoricalY(historySize - 1) - event.getY());
//
//                Log.d(TAG, "onTouchEvent, scrollValue before " + scrollValue + ", beginY " + event.getHistoricalY(historySize - 1) + ", endY " + event.getY());
//
//                if(scrollValue < 0)
//                {
//                    if(Math.abs(scrollValue) > (getScrollY() - getTop()))
//                        scrollValue = getTop() - getScrollY();
//                }
//                else if(scrollValue > totalHeight - (getScrollY() + getHeight()))//scrollValue > 0
//                    scrollValue = totalHeight - (getScrollY() + getHeight());
//
//                Log.d(TAG, "onTouchEvent, totalHeight " + totalHeight);
//                Log.d(TAG, "onTouchEvent, getScrollY() " + getScrollY());
//                Log.d(TAG, "onTouchEvent, getHeight() " + getHeight());
//
//                Log.d(TAG, "onTouchEvent, scrollValue after " + scrollValue);
//
//                scrollBy(0, scrollValue);
//            }
//            else
//                Log.d(TAG, "History size == 0 !");

        if(historySize > 0) {
            scrollValue = (int)(event.getHistoricalY(historySize - 1) - event.getY());
            Log.d(TAG, "performMove: scrollValue = " + scrollValue);

            scrollBy(0, scrollValue);
        }
        else {
            Log.d(TAG, "performMove: History size == 0 !");

            scrollValue = (int) (motionBeginY - event.getY());
            Log.d(TAG, "performMove: scrollValue = " + scrollValue);

            scrollBy(0, scrollValue);
            motionBeginY = event.getY();
        }

        if((totalHeight - getScrollY()) < getHeight())
        {
            Log.d(TAG, "performMove: totalHeight = " + totalHeight + ", getHeight() = " + getHeight());
            setScrollY(totalHeight + getTop() - getHeight());
        }
        else if(getScrollY() < getTop())
        {
            Log.d(TAG, "performMove: setScrollY(" + getTop() + ")");
            setScrollY(getTop());
        }

    }

    @Override
    public void scrollTo(int x, int y) {
        Log.d(TAG, "scrollTo (" + x + "," + y + ")");
        super.scrollTo(x, y);
    }

    private void drawEmptyRows(Canvas canvas) {
        Log.d(TAG, "drawEmptyRows");

        canvas.drawRGB(0, 0, 0);

        int horizontalLinesCount = rowsShown + 1;
        int verticalLinesCount = DIGITS_IN_ROW + 1;

//        float[] points = new float[(horizontalLinesCount + verticalLinesCount) * 4];
        int pointIndex = 0;

        int x = getLeft();
        int y = getFloorY();

        Log.d(TAG, "drawEmptyRows:1) y = " + y);

        if(getScrollY() != y)
            y = getCeilY();

        Log.d(TAG, "drawEmptyRows:2) y = " + y);

        for(int i = 0; i < horizontalLinesCount; i++) {
            linePoints[pointIndex] = getLeft();
            linePoints[pointIndex + 1] = y;
            linePoints[pointIndex + 2] = getRight();
            linePoints[pointIndex + 3] = y;

            y += cellHeight;
            pointIndex += 4;
        }

        for(int i = 0; i < verticalLinesCount; i++) {
            linePoints[pointIndex] = x;
            linePoints[pointIndex + 1] = getTop();
            linePoints[pointIndex + 2] = x;
            linePoints[pointIndex + 3] = y;

            x += cellWidth;
            pointIndex += 4;
        }

        canvas.drawLines(linePoints, paint);
    }

    private void drawDigit(Canvas canvas, byte digit, DrawPosition pos) {
        if(digit == 0)
            return;

        canvas.drawText(Byte.toString(digit), pos.x, pos.y, paint);
    }

    private void markDigit(Canvas canvas, Point pos) {
        linePoints[0] = pos.x;
        linePoints[1] = pos.y;
        linePoints[2] = pos.x + cellWidth;
        linePoints[3] = pos.y + cellHeight;

        linePoints[4] = pos.x + cellWidth;
        linePoints[5] = pos.y;
        linePoints[6] = pos.x;
        linePoints[7] = pos.y + cellHeight;

        canvas.drawLines(linePoints, 0, 8, paint);
    }

    private int getFloorY() {
        return (getScrollY() - getTop()) / cellHeight * cellHeight + getTop();
    }

    private int getCeilY() {
        return ((getScrollY() - getTop()) / cellHeight + 1) * cellHeight + getTop();
    }

}
