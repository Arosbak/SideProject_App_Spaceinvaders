package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class SpaceInvadersBullet
{
    private int xPos;
    private int yPos;
    private byte height = 10;
    private byte width = 35;
    private Bitmap bullet;
    private Paint bulletPaint;
    private byte speed = 30;
    private boolean hasHit = false;

    public SpaceInvadersBullet(int startX, int startY)
    {
        xPos = startX;
        yPos = startY;
        bulletPaint = new Paint();
    }

    public void draw (Canvas canvas, Resources res)
    {
        bullet = BitmapFactory.decodeResource(res,R.drawable.space_invaders_enemy);
        bullet = Bitmap.createScaledBitmap(bullet,height,width,false);

        canvas.drawBitmap(bullet,xPos,yPos,bulletPaint);

    }

    private void move ()
    {
        yPos -= speed;
    }

    public void update(SpaceInvadersEnemyCollection enemies)
    {
        checkIfHit(enemies);
        move();

    }

    public int getYPos()
    {
        return yPos;
    }


    private void checkIfHit (SpaceInvadersEnemyCollection enemies)
    {

        SpaceInvadersEnemy[] collection = enemies.getEnemies();
        for (SpaceInvadersEnemy enemy : collection )
        {
            if ((yPos+height) > enemy.getYPos() && yPos < (enemy.getYPos() + enemy.getHeight()) && enemy.getAlive())
            {
                if ((xPos + width) > enemy.getXPos() && xPos < (enemy.getXPos() + enemy.getWidth()))
                {
                    enemy.setAlive(false);
                    hasHit = true;
                    enemies.updateEdges(enemy);
                }
            }
        }

    }

    public boolean getHasHit()
    {
        return hasHit;
    }

}
