package com.group2.androidbankingapp.bottomnav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CurvedLineView extends View {
    Paint paint = new Paint();;

    public CurvedLineView(Context context) {
        super(context);
    }

    public CurvedLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CurvedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CurvedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);

        setBackground(null);
        int lineStartHeight = (int) (viewHeight * 0.2);
        Point lineStart = new Point(0, lineStartHeight);
        Point lineEnd = new Point(viewWidth, lineStartHeight);

        int horizontalCenter = viewWidth / 2;
        int buttonRadius = 120;

        Path curvedBorder = new Path();
        curvedBorder.moveTo(lineStart.x, lineStart.y);
        curvedBorder.lineTo(horizontalCenter - (int) (buttonRadius / 1.4), lineStartHeight);

        Point controlPoint1 = new Point(horizontalCenter - (int) (buttonRadius / 3.5), (int) 24);
        Point controlPoint2 = new Point(horizontalCenter + (int) (buttonRadius / 3.5), (int) 24);

        curvedBorder.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
                horizontalCenter + (int) (buttonRadius / 1.4),
                lineStartHeight);
        curvedBorder.lineTo(viewWidth, lineStartHeight);

        curvedBorder.lineTo(viewWidth, viewHeight);
        curvedBorder.lineTo(0, viewHeight);
        curvedBorder.close();

        canvas.drawPath(curvedBorder, paint);

        //Gray border on top
        Path topCurvedBorder = new Path();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#E5E5E5"));
        curvedBorder.moveTo(lineStart.x, lineStart.y);
        curvedBorder.lineTo(horizontalCenter - (int) (buttonRadius / 1.4), lineStartHeight);

        curvedBorder.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
                horizontalCenter + (int) (buttonRadius / 1.4),
                lineStartHeight);
        curvedBorder.lineTo(viewWidth, lineStartHeight);
        canvas.drawPath(curvedBorder, paint);
    }
}
