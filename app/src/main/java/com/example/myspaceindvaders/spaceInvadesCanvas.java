package com.example.myspaceindvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;


public class spaceInvadesCanvas extends SurfaceView implements Runnable
{
    private Thread gameThread ;
    private SpaceInvadersPlayer player ;

    private int screenX;
    private int screenY;

    private boolean isPlaying;
    private Background BG;
    private Paint paint;
    private Joystick joystick;
    private Canvas canvas;
    private SpaceInvadersEnemyCollection collection;
    private ArrayList <SpaceInvadersBullet> bullets;
    private ArrayList <SpaceInvadersBullet> removeBulletList;
    private Button shootButton;
    private  boolean joyStickIsFirst;
    private boolean shooterIsFirst;


    public spaceInvadesCanvas(Context context, int screenX,int screenY)
    {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        player = new SpaceInvadersPlayer(screenX/2,850,60,100,screenX);
        BG = new Background(screenX,screenY,getResources());
        paint = new Paint();
        joystick = new Joystick(250,750,80,40);
        collection = new SpaceInvadersEnemyCollection(55, screenX/1);
        Thread collectionThread = new Thread(collection);
        collectionThread.start();
        bullets = new ArrayList<SpaceInvadersBullet>();
        removeBulletList = new ArrayList<SpaceInvadersBullet>();
        shootButton = new Button(1500,750,80);

    }

    @Override
    public void run()
    {
        while (isPlaying)
        {
            update();
            draw();
            sleep();

        }
    }

    private void update()
    {
        // UI
        joystick.update();
        shootButton.update();

        // Characters
        if (joystick.direction() != 0)
        {
            player.update(joystick.direction());
        }
        //collection.update();

        // Artifacts
        if (bullets != null)
        {
            for (SpaceInvadersBullet bullet : bullets)
            {
                bullet.update(collection);

                if (bullet.getYPos() < 0 || bullet.getHasHit())
                {
                    removeBulletList.add(bullet);
                }
            }

            bullets.removeAll(removeBulletList);
        }

    }

    private void draw()
    {
        if (getHolder().getSurface().isValid())
        {
            canvas = getHolder().lockCanvas();

            BG.draw(canvas);
            joystick.draw(canvas);
            shootButton.draw(canvas);

            player.draw(canvas,getResources());
            collection.draw(canvas, getResources());

            if (bullets != null)
            {
                for (SpaceInvadersBullet bullet:bullets)
                {
                    bullet.draw(canvas, getResources());
                }
            }


            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep()
    {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume()
    {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void pause ()
    {

        try {
            gameThread.join();
            isPlaying=false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventType = event.getActionMasked();


        switch (eventType)
        {
            case MotionEvent.ACTION_DOWN:

                if (joystick.isPressed(event.getX(), event.getY()))
                {
                    joystick.setIsPressed(true);
                    joyStickIsFirst = true;
                }
                else if (shootButton.isPressed((event.getX()), event.getY()))
                {
                    shootButton.setPressed(true);
                    bullets.add(player.shoot());
                    shooterIsFirst = true;
                }

                return true;

            case MotionEvent.ACTION_POINTER_DOWN:

                if (joystick.isPressed(event.getX(1), event.getY(1)) )
                {
                    joystick.setIsPressed(true);
                    joyStickIsFirst = false;
                }
                else if (shootButton.isPressed((event.getX(1)), event.getY(1)))
                {
                    shootButton.setPressed(true);
                    bullets.add(player.shoot());
                    shooterIsFirst = false;
                }

                return true;

            case MotionEvent.ACTION_MOVE:

                if (joystick.getIsPressed()  && joyStickIsFirst)
                {
                    joystick.setActuator(event.getX(),event.getY());
                }
                else if (joystick.getIsPressed() && !joyStickIsFirst)
                {
                    joystick.setActuator(event.getX(1) , event.getY(1));
                }

                return true;

            case MotionEvent.ACTION_POINTER_UP:

                if (joystick.getIsPressed() && !joyStickIsFirst)
                {
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                else if (shootButton.getIsPressed())
                {
                    shootButton.setPressed(false);
                }
                return true;

            case MotionEvent.ACTION_UP:

                if (joystick.getIsPressed() && joyStickIsFirst)
                {
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                else if (shootButton.getIsPressed())
                {
                    shootButton.setPressed(false);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
}
