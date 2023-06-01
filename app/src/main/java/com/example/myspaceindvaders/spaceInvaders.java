package com.example.myspaceindvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class spaceInvaders extends AppCompatActivity {

    spaceInvadesCanvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point pointer = new Point();
        getWindowManager().getDefaultDisplay().getSize(pointer);
        canvas = new spaceInvadesCanvas(this, pointer.x , pointer.y);
        setContentView(canvas);
    }

    @Override
    protected void onPause() {
        super.onPause();
        canvas.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        canvas.resume();
    }
}