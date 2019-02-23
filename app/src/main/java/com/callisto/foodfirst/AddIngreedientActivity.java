package com.callisto.foodfirst;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddIngreedientActivity extends AppCompatActivity {

    String ingreedient;
    String measurement;
    double amount;
    int calories;
    boolean gluten;
    boolean treeNut;
    boolean dairy;
    boolean soy;
    boolean shelfish;
    boolean vegitarian;
    boolean vegan;
    boolean kosher;
    boolean halal;

    EditText ingreedientText;
    EditText amountText;
    EditText caloriesText;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingreedient);
    }

}
