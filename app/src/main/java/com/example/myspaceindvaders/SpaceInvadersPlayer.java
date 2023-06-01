package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SpaceInvadersPlayer {
    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private Bitmap player;
    private Paint colorPlayer;
    private int speed = 8;
    private int screenWidth;

    public SpaceInvadersPlayer(int startX, int startY, int width,int height, int screenWidth)
    {
        this.screenWidth = screenWidth;
        xPos = startX;
        yPos = startY;
        this.width = width;
        this.height = height;
        colorPlayer = new Paint();
    }

    public void draw(Canvas canvas, Resources res)
    {
        player = BitmapFactory.decodeResource(res,R.drawable.space_invaders_player);
        player = Bitmap.createScaledBitmap(player,height,width,false);

        canvas.drawBitmap(player,xPos,yPos,colorPlayer);
    }

    private void move (int dir)
    {
        xPos += speed*dir;
        if (xPos > screenWidth - height)
        {
            xPos = screenWidth - height;
        }
        if(xPos < 0)
        {
            xPos = 0;
        }
    }

    public SpaceInvadersBullet shoot()
    {
        SpaceInvadersBullet bullet = new SpaceInvadersBullet(xPos+(width/2),yPos/1);
        return bullet;
    }

    public void update (int dir)
    {
        move(dir);
    }

    public void setXPos(int x)
    {
        xPos = x;
    }

    public void setYPos(int y)
    {
        yPos = y;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }
}
