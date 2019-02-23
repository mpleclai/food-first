package com.callisto.foodfirst;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRecipeActivity extends AppCompatActivity {

    EditText name;
    EditText description;

    Button newIngredient;
    Button submit;

    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        newIngredient = (Button) findViewById(R.id.button2);
        newIngredient.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( AddRecipeActivity.this, AddIngredientActivity.class));

            }
        });

    }

}
