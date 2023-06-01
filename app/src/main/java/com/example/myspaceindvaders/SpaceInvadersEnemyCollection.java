package com.example.myspaceindvaders;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class SpaceInvadersEnemyCollection implements Runnable
{
    private int numOfEnemies;
    private byte spaceBetweenEnemiesY = 5;
    private byte spaceBetweenEnemiesX = 75;
    private byte numOfEnemiesPerRow = 11;
    private byte enemiesPerRow = numOfEnemiesPerRow;
    private int yPos = 0;
    private int xPos = 0;
    private SpaceInvadersEnemy [] enemies;
    private boolean needsToBeGenerated = true;
    private int screenWidth;
    private byte direction = 1;
    private int enemiesSpeed;
    private boolean moveOneDown;
    private byte upDateCircles = 10;
    private ArrayList <SpaceInvadersEnemy> leftEdge;
    private ArrayList <SpaceInvadersEnemy> rightEdge;
    private ArrayList <SpaceInvadersEnemy> removeFromEdge;
    private ArrayList <SpaceInvadersEnemy> addToEdge;

    public SpaceInvadersEnemyCollection (int numOfEnemies, int screenWidth)
    {
        this.numOfEnemies = numOfEnemies;
        enemies = new SpaceInvadersEnemy[numOfEnemies];
        this.screenWidth = screenWidth;
        leftEdge = new ArrayList<SpaceInvadersEnemy>();
        rightEdge = new ArrayList<SpaceInvadersEnemy>();
        removeFromEdge = new ArrayList<SpaceInvadersEnemy>();
        addToEdge = new ArrayList<SpaceInvadersEnemy>();
    }

    public void generateEnemies ()
    {
        for (int i = 0; i < numOfEnemies; i++)
        {
            enemies[i] = new SpaceInvadersEnemy();
            if (i == 0)
            {
                enemies[i].setXPos(xPos + spaceBetweenEnemiesX);
                enemies[i].setYPos(yPos + spaceBetweenEnemiesY);
                enemiesPerRow --;
                xPos = enemies[i].getXPos() + enemies[i].getWidth();
                enemiesSpeed = enemies[i].getSpeed();
                leftEdge.add(enemies[i]);
                enemies[i].setId((byte)i);
            }
            else
            {
                if (enemiesPerRow >= 0)
                {
                    enemies[i].setXPos(xPos + spaceBetweenEnemiesX);
                    enemies[i].setYPos(yPos + spaceBetweenEnemiesY);

                    if (enemiesPerRow == numOfEnemiesPerRow)
                    {
                        leftEdge.add(enemies[i]);
                    }

                    enemiesPerRow --;
                    xPos = enemies[i].getXPos() + enemies[i].getWidth() ;
                    enemies[i].setId((byte)i);
                }
            }
            if (enemiesPerRow == 0)
            {
                yPos += spaceBetweenEnemiesY + enemies[i].getHeight();
                xPos =  0;
                enemiesPerRow = numOfEnemiesPerRow;
                rightEdge.add(enemies[i]);
            }
        }
    }

    @Override
    public void run()
    {
        if (needsToBeGenerated)
        {
            generateEnemies();
            needsToBeGenerated = false;
        }

        // Calculates the direction of the enemies
        if (direction > 0)
        {
            for (SpaceInvadersEnemy enemy : rightEdge)
            {
                if (enemy.getXPos() + enemiesSpeed >= screenWidth - enemy.getWidth())
                {
                    Log.d("Direction","I have changed direction: GO RIGHT");
                    direction *= -1;
                    moveOneDown = true;
                    break;
                }
            }
        } else if (direction < 0)
        {
            for (SpaceInvadersEnemy enemy : leftEdge)
            {
                if (enemy.getXPos() - enemiesSpeed <= enemy.getWidth())
                {
                    Log.d("Direction","I have changed direction: GO LEFT");
                    direction *= -1;
                    moveOneDown = true;
                    break;
                }
            }
        }
        // Updates the positions of the enemies
        for (int i = 0; i < enemies.length; i++)
        {
            enemies[i].update(direction);
            if (moveOneDown)
            {
                enemies[i].setYPos(enemies[i].getYPos() + enemiesSpeed);
            }

        }
        moveOneDown = false;

    }

    public void sleep ()
    {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw(Canvas canvas, Resources res)
    {
        for (int i = 0; i < enemies.length; i ++ )
        {
            enemies[i].draw(canvas, res);
        }
    }


    public void update()
    {
        run();
    }

    public SpaceInvadersEnemy [] getEnemies()
    {
        return enemies;
    }


    public void setNeedsToBeGenerated(boolean needsToBeGenerated)
    {
        this.needsToBeGenerated = needsToBeGenerated;
    }

    public void updateEdges (SpaceInvadersEnemy enemyHit)
    {
        boolean foundEnemyIsInTheRightSide = false;
        // checking right side
        for (SpaceInvadersEnemy enemy : rightEdge)
        {
            if (enemyHit.getId() == enemy.getId())
            {
                if (!enemy.getAlive())
                {
                    removeFromEdge.add(enemy);
                    for (int i = 1; i <  enemiesPerRow ; i++)
                    {
                        if (enemy.getYPos() == enemies[enemy.getId() - i].getYPos() && enemies[enemy.getId() - i].getAlive())
                        {
                            addToEdge.add(enemies[enemy.getId() - i]);
                            foundEnemyIsInTheRightSide = true;
                            break;
                        }
                    }
                }
            }
        }

        //Checking the left side
        if (!foundEnemyIsInTheRightSide)
        {
            for (SpaceInvadersEnemy enemy : leftEdge) {
                if (enemy.getId() == enemyHit.getId()) {
                    if (!enemy.getAlive()) {
                        removeFromEdge.add(enemy);
                        for (int i = 1; i < enemiesPerRow ; i++) {
                            if(enemy.getId()+i < numOfEnemies) {
                                if (enemy.getYPos() == enemies[enemy.getId() + i].getYPos() && enemies[enemy.getId() + i].getAlive()) {
                                    addToEdge.add(enemies[enemy.getId() + i]);
                                    Log.d("UPDATEEDGE", "i = " + i);
                                    break;
                                }
                            } else
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (foundEnemyIsInTheRightSide)
        {
            if ( !removeFromEdge.isEmpty())
            {
                Log.d("UPDATEEDGE", "");
                for (SpaceInvadersEnemy enemy : rightEdge)
                {
                    Log.d("UPDATEEDGE", "The IDs of the enemies in Right edge: " + enemy.getId());
                }
                Log.d("UPDATEEDGE", "");
                rightEdge.removeAll(removeFromEdge);
                removeFromEdge.clear();

            }

            if (!addToEdge.isEmpty())
            {
                rightEdge.addAll(addToEdge);
                addToEdge.clear();

                for (SpaceInvadersEnemy enemy : rightEdge)
                {
                    Log.d("UPDATEEDGE", "The IDs of the enemies in Right edge: " + enemy.getId());
                }

            }
        } else
        {
            if (!removeFromEdge.isEmpty())
            {
                Log.d("UPDATEEDGE", "");
                for (SpaceInvadersEnemy enemy : leftEdge)
                {
                    Log.d("UPDATEEDGE", "The IDs of the enemies in Left edge: " + enemy.getId());
                }
                leftEdge.removeAll(removeFromEdge);
                removeFromEdge.clear();
                Log.d("UPDATEEDGE", "");
            }

            if (!addToEdge.isEmpty())
            {
                leftEdge.addAll(addToEdge);
                addToEdge.clear();

                for (SpaceInvadersEnemy enemy : leftEdge)
                {
                    Log.d("UPDATEEDGE", "The IDs of the enemies in Left edge: " + enemy.getId());
                }
            }
        }
    }
}
