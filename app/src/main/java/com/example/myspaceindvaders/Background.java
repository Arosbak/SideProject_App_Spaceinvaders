package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Background {

    private int x =0,y=0;
    public Bitmap background;
    private Paint paint;

    public Background(int screenWidth, int screenHeight, Resources res)
    {
        background = BitmapFactory.decodeResource(res, R.drawable.space_invaders_background);
        background = Bitmap.createScaledBitmap(background,screenWidth,screenHeight,false);
        paint = new Paint();
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(background,0,0, paint);
    }

}
