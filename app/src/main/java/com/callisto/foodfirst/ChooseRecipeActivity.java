package com.callisto.foodfirst;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.os.StrictMode;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.w3c.dom.Document;

import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class ChooseRecipeActivity extends AppCompatActivity {

    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        MongoClientURI uri = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<BasicDBObject> recipe = db.getCollection("recipes", BasicDBObject.class);

        Block<BasicDBObject> block = new Block<BasicDBObject>(){
            public void apply(final BasicDBObject document) {
                System.out.println(document.toString());
            }
        };

        recipe.find(eq("selected", true)).forEach(block);

        //recipe.find(eq("selected",true));

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
        client.close();
    }



    public void openAddRecipeActivity( View v) {
        startActivity( new Intent( ChooseRecipeActivity.this, AddRecipeActivity.class));
    }

}
