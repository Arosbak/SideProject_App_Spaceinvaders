package com.example.myspaceindvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button goToSpaceInvadersBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToSpaceInvadersBTN = (Button) findViewById(R.id.SpaceIndvarsGoToBTN);
        goToSpaceInvadersBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSpaceInvaders();
            }
        });
        }
    public void goToSpaceInvaders()
    {
        Intent intent = new Intent(this, spaceInvaders.class);
        startActivity(intent);
    }

}

