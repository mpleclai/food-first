package com.callisto.foodfirst;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientActivity extends AppCompatActivity {

    int position;

    boolean gluten = false;
    boolean treeNut = false;
    boolean dairy = false;
    boolean soy = false;
    boolean shellfish = false;
    boolean vegetarian = false;
    boolean vegan = false;
    boolean kosher = false;
    boolean halal = false;

    EditText ingredientText;
    EditText amountText;
    EditText caloriesText;

    CheckBox GlutenCheckBox;
    CheckBox TreeNutCheckBox;
    CheckBox DairyCheckBox;
    CheckBox SoyCheckBox;
    CheckBox ShellfishCheckBox;
    CheckBox VegetarianCheckBox;
    CheckBox VeganCheckBox;
    CheckBox KosherCheckBox;
    CheckBox HalalCheckBox;

    Spinner spinner;


    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingreedient);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ingredientText = (EditText) findViewById(R.id.ingreedientText);
        amountText = (EditText) findViewById(R.id.amountText);
        caloriesText = (EditText) findViewById(R.id.caloriesText);

        GlutenCheckBox = (CheckBox) findViewById(R.id.GlutenCheckBox);
        TreeNutCheckBox = (CheckBox) findViewById(R.id.TreeNutCheckBox);
        DairyCheckBox = (CheckBox) findViewById(R.id.DairyCheckBox);
        SoyCheckBox = (CheckBox) findViewById(R.id.SoyCheckBox);
        ShellfishCheckBox = (CheckBox) findViewById(R.id.ShelfishCheckBox);
        VegetarianCheckBox = (CheckBox) findViewById(R.id.VegitarianCheckBox);
        VeganCheckBox = (CheckBox) findViewById(R.id.VeganCheckBox);
        KosherCheckBox = (CheckBox) findViewById(R.id.KosherCheckBox);
        HalalCheckBox = (CheckBox) findViewById(R.id.HalalCheckBox);

        submit = (Button) findViewById(R.id.submit);
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> categories = new ArrayList<String>();
        categories.add("Teaspoon");
        categories.add("Tablespoon");
        categories.add("Dry Cup");
        categories.add("Liquid Cup");
        categories.add("Ounce");
        categories.add("Fluid Ounce");
        categories.add("Liter");
        categories.add("Other");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        submit.setOnClickListener( new View.OnClickListener() {
            // add send receive data here
            @Override
            public void onClick( View v ) {
//                addInformationToDatabse( v );
                startActivity( new Intent( AddIngredientActivity.this, AddRecipeActivity.class));
            }
        });
    }

    public void addInformationToDatabse( View v) {

        MongoClientURI uri = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> coll = db.getCollection("newDB");

        position = spinner.getSelectedItemPosition();

        if (GlutenCheckBox.isChecked()) {
            gluten = true;
        }
        if (TreeNutCheckBox.isChecked()) {
            treeNut = true;
        }
        if (DairyCheckBox.isChecked()) {
            dairy = true;
        }
        if (SoyCheckBox.isChecked()) {
            soy = true;
        }
        if (ShellfishCheckBox.isChecked()) {
            shellfish = true;
        }
        if (VegetarianCheckBox.isChecked()) {
            vegetarian = true;
        }
        if (VeganCheckBox.isChecked()) {
            vegan = true;
        }
        if (KosherCheckBox.isChecked()) {
            kosher = true;
        }
        if (HalalCheckBox.isChecked()) {
            halal = true;
        }

        Document doc = new Document("item", "ingredient")
                .append("ingredient", ingredientText.getText().toString())
                .append("amount", Double.valueOf(amountText.getText().toString()))
                .append("measurement", String.valueOf(spinner.getSelectedItem()))
                .append("calories", Integer.valueOf(caloriesText.getText().toString()))
                .append("gluten", gluten)
                .append("treeNut", treeNut)
                .append("dairy", dairy)
                .append("soy", soy)
                .append("shellfish", shellfish)
                .append("vegetarian", vegetarian)
                .append("vegan", vegan)
                .append("kosher", kosher)
                .append("halal", halal);

        coll.insertOne(doc);
        client.close();
    }
}
