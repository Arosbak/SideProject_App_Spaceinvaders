
package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Button
{
    private int centerPosX;
    private int centerPosY;
    private int radius;
    private Paint buttonPaint;
    private boolean isPressed = false;




    public Button (int centerPosX,int centerPosY, int radius)
    {
        this.centerPosX = centerPosX;
        this.centerPosY = centerPosY;
        this.radius = radius;
        buttonPaint = new Paint();
        buttonPaint.setColor(Color.WHITE);
    }

    public void draw (Canvas canvas)
    {
        canvas.drawCircle(
                centerPosX,
                centerPosY,
                radius,
                buttonPaint );

    }

    public void update ()
    {
        if (isPressed)
        {
            buttonPaint.setColor(Color.GRAY);
        }
        else
        {
            buttonPaint.setColor(Color.WHITE);
        }
    }

    public boolean isPressed (float touchPosX, float touchPosY)
    {
        double dist = Math.sqrt(
                Math.pow(centerPosX - touchPosX, 2) +
                Math.pow(centerPosY - touchPosY, 2));
        if (radius > dist)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public boolean getIsPressed ()
    {
        return isPressed;
    }
}
