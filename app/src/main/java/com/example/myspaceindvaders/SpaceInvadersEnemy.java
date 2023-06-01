package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Calendar;

public class SpaceInvadersEnemy
{
    private int xPos = 0;
    private int yPos = 0;
    private int height = 40;
    private int width =  20;
    private Bitmap enemy;
    private Paint enemyPaint;
    private int speed = 20 ;
    private boolean moveOneDown = false;
    private boolean alive = true;
    private byte id;

    public SpaceInvadersEnemy()
    {
        enemyPaint = new Paint();

    }

    public void draw (Canvas canvas, Resources res)
    {
        if (alive)
        {
            enemy = BitmapFactory.decodeResource(res, R.drawable.space_invaders_enemy);
            enemy = Bitmap.createScaledBitmap(enemy, height, width, false);

            canvas.drawBitmap(enemy, xPos, yPos, enemyPaint);
        }
    }

    public void update(int dir)
    {
        move (dir);
    }

    private void move (int dir)
    {
        xPos += speed*dir;
    }

    public int getWidth ()
    {
        return width;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos ()
    {
        return yPos;
    }

    public int getHeight()
    {
        return height;
    }

    public void setXPos(int x)
    {
        xPos = x;
    }

    public void setYPos(int y)
    {
        yPos = y;
    }

    public int getSpeed ()
    {
        return speed;
    }

    public boolean getAlive()
    {
        return alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
