package com.callisto.foodfirst;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;

public class ChooseRecipeActivity extends AppCompatActivity {

    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddRecipeActivity( view );
            }
        });
    }



    public void openAddRecipeActivity( View v) {
        startActivity( new Intent( ChooseRecipeActivity.this, AddRecipeActivity.class));
    }

}
