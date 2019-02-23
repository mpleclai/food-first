package com.callisto.foodfirst;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class AddRecipeActivity extends AppCompatActivity {

    private static String nameSave;
    private static String desSave;

    EditText name;
    EditText description;
    EditText instructions;

    Button newIngredient;
    Button submit;


    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        if(savedInstanceState != null) {
            System.out.println("NOT NULL");
            if (savedInstanceState.containsKey("name"))
                System.out.println(savedInstanceState.getString("name"));
        }

        name = (EditText) findViewById(R.id.editText6);
        description = (EditText) findViewById(R.id.editText7);
        instructions = (EditText) findViewById(R.id.editText9);


        if(savedInstanceState != null){
            System.out.println(savedInstanceState.getString("name"));
            description.setText(savedInstanceState.getString("description"));
        }

        name.setText(nameSave);
        description.setText(desSave);


        newIngredient = (Button) findViewById(R.id.button2);
        submit = (Button) findViewById(R.id.button4);

        recycler = (RecyclerView) findViewById(R.id.recyclerView3);

        newIngredient.setOnClickListener( new View.OnClickListener() {
            // how to save information goes here
            @Override
            public void onClick(View v) {
                startActivity( new Intent( AddRecipeActivity.this, AddIngredientActivity.class));

            }
        });
        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View k) {
                //addInformationToDatabse( k );
                openAddIngriedientActivity( k );

            }
        });

    }
    public void openAddIngriedientActivity( View v ) {
        startActivity( new Intent( AddRecipeActivity.this, AddIngredientActivity.class));
    }
    public void addInformationToDatabse( View k) {

        MongoClientURI uri  = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> coll = db.getCollection("newDB");

        Document doc = new Document( "item", "recipe")
                .append( "name", name.getText().toString() )
                .append( "description", description.getText().toString() );


        coll.insertOne(doc);
        client.close();

    }


    @Override
    protected void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putString("name", this.name.getText().toString());
        state.putString("description", this.description.getText().toString());
        nameSave = this.name.getText().toString();
        desSave = this.description.getText().toString();
    }



}
