package com.callisto.foodfirst;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.BasicDBObject;



import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class AddRecipeActivity extends AppCompatActivity {

    int numberOfIngreedients = 0;
    int numberOfItems = 0;

    private static String nameSave;
    private static String desSave;
    ObjectId id;                     // recipe id

    ObjectId myLocalID;  // ingredient id

    EditText name;
    EditText description;
    EditText instructions;

    Button newIngredient;
    Button submit;


    RecyclerView recycler;
    String[] items;
    ObjectId[] ingredientIDs;
    Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        if(savedInstanceState != null) {
            System.out.println("NOT NULL");
            if (savedInstanceState.containsKey("name"))
                System.out.println(savedInstanceState.getString("name"));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        items = new String[15];
        ingredientIDs = new ObjectId[15];

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
        submit = (Button) findViewById(R.id.button3);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter( this, items);
        recycler.setAdapter(mAdapter);

        newIngredient.setOnClickListener( new View.OnClickListener() {
            // how to save information goes here
            @Override
            public void onClick(View v) {
                MongoClientURI uri  = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
                MongoClient mongoClient = new MongoClient(uri);
                MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());

                MongoCollection<Document> ingredients = db.getCollection("ingredients");

                startActivity( new Intent(  AddRecipeActivity.this, AddIngredientActivity.class));

                Bundle extras = getIntent().getExtras();
                if ( extras != null ) {
                    String hexStr = extras.getString("ID");
                    myLocalID = new ObjectId(hexStr);
                    Log.d( "id1", id.toString());
                }

                ingredientIDs[numberOfIngreedients] = myLocalID;
                numberOfIngreedients++;

                MongoCollection<Document> myCollection = db.getCollection("myCollection");
                Document document = myCollection.find(eq("_id", myLocalID)).first();
                if (document == null) {
                    //Document does not exist
                } else {
                    items[numberOfItems] = document.get("ingredient").toString();
                }
                mAdapter.notifyItemInserted(numberOfItems);
                mongoClient.close();

            }
        });
        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View k) {
                addInformationToDatabse( k );
                startActivity( new Intent( AddRecipeActivity.this, ChooseRecipeActivity.class));

            }
        });

    }

    public void addInformationToDatabse( View k) {

        MongoClientURI uri = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
        MongoClient mongoClient = new MongoClient(uri);

        MongoDatabase db = mongoClient.getDatabase("FoodFirst");
        MongoCollection<Document> recipes = db.getCollection("recipes");
        MongoCollection<Document> contains = db.getCollection("contains");

        Document recipe = new Document()
                .append("name", name.getText().toString())
                .append("description", description.getText().toString())
                .append("instructions", instructions.getText().toString());

        recipes.insertOne(recipe);

        id = (ObjectId) recipe.get("_id");


        for (int i = 0; i <= numberOfIngreedients; i++) {
            Document contain = new Document();
            contain.append("recipeId", id)
                    .append("ingredientId", ingredientIDs[i]);
            contains.insertOne(contain);
        }

        mongoClient.close();

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
