package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Joystick {

    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;

    private int outerCircleRadius;
    private int innerCircleRadius;

    private Paint outerCirclePaint;
    private Paint innerCirclePaint;

    private boolean isPressed;

    private double joystickCenterToTouchDist;

    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPosX,int centerPosY, int outerCircleRadius, int innerCircleRadius)
    {
        outerCircleCenterPositionX = centerPosX;
        outerCircleCenterPositionY = centerPosY;
        innerCircleCenterPositionX = centerPosX;
        innerCircleCenterPositionY = centerPosY;

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.WHITE);

    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint);

        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint);
    }

    public void update()
    {
        updateInnerCirclePos();
    }

    private void updateInnerCirclePos()
    {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);
    }

    public boolean isPressed(double touchPosX, double touchPosY)
    {
        joystickCenterToTouchDist = getJoystickCenterToTouchDistCal(touchPosX,touchPosY);

        if (joystickCenterToTouchDist < outerCircleRadius)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setIsPressed (boolean pressed)
    {
        isPressed = pressed;
    }

    public boolean getIsPressed ()
    {
        return isPressed;
    }

    public void setActuator(double posX,double posY)
    {
        double deltaX = posX - outerCircleCenterPositionX;
        double deltaY = posY - outerCircleCenterPositionY;
        double deltaDist = getJoystickCenterToTouchDistCal(posX, posY);

        if (deltaDist < outerCircleRadius)
        {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else
        {
            actuatorX = deltaX/deltaDist;
            actuatorY = deltaY/deltaDist;
        }

    }

    private double getJoystickCenterToTouchDistCal(double x, double y)
    {
        double calDist = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - x,2) +
                Math.pow( outerCircleCenterPositionY - y,2));
        return calDist;
    }

    public void resetActuator()
    {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public int direction ()
    {
        if (innerCircleCenterPositionX < outerCircleCenterPositionX)
        {
            return -1;
        }
        else if (innerCircleCenterPositionX > outerCircleCenterPositionX)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
