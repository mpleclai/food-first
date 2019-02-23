package com.callisto.foodfirst;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddIngreedientActivity extends AppCompatActivity {

    String measurement;

    boolean gluten = false;
    boolean treeNut = false;
    boolean dairy = false;
    boolean soy = false;
    boolean shelfish = false;
    boolean vegitarian = false;
    boolean vegan = false;
    boolean kosher = false;
    boolean halal = false;

    EditText ingreedientText;
    EditText amountText;
    EditText caloriesText;

    CheckBox GlutenCheckBox;
    CheckBox TreeNutCheckBox;
    CheckBox DairyCheckBox;
    CheckBox SoyCheckBox;
    CheckBox ShelfishCheckBox;
    CheckBox VegitarianCheckBox;
    CheckBox VeganCheckBox;
    CheckBox KosherCheckBox;
    CheckBox HalalCheckBox;


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

        ingreedientText = (EditText) findViewById(R.id.ingreedientText);
        amountText = (EditText) findViewById(R.id.amountText);
        caloriesText = (EditText) findViewById(R.id.caloriesText);

        GlutenCheckBox = (CheckBox) findViewById(R.id.GlutenCheckBox);
        TreeNutCheckBox = (CheckBox) findViewById(R.id.TreeNutCheckBox);
        DairyCheckBox = (CheckBox) findViewById(R.id.DairyCheckBox);
        SoyCheckBox = (CheckBox) findViewById(R.id.SoyCheckBox);
        ShelfishCheckBox = (CheckBox) findViewById(R.id.ShelfishCheckBox);
        VegitarianCheckBox = (CheckBox) findViewById(R.id.VegitarianCheckBox);
        VeganCheckBox = (CheckBox) findViewById(R.id.VeganCheckBox);
        KosherCheckBox = (CheckBox) findViewById(R.id.KosherCheckBox);
        HalalCheckBox = (CheckBox) findViewById(R.id.HalalCheckBox);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                MongoClientURI uri  = new MongoClientURI("mongodb://ajerdman:FoodFirst10072@foodfirst-shard-00-00-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-01-aarbi.azure.mongodb.net:27017,foodfirst-shard-00-02-aarbi.azure.mongodb.net:27017/test?ssl=true&replicaSet=FoodFirst-shard-0&authSource=admin&retryWrites=true");
                MongoClient client = new MongoClient(uri);
                MongoDatabase db = client.getDatabase(uri.getDatabase());
                MongoCollection<Document> coll = db.getCollection("newDB");

                if( GlutenCheckBox.isChecked()) {
                    gluten = true;
                }
                if( TreeNutCheckBox.isChecked()) {
                    treeNut = true;
                }
                if( DairyCheckBox.isChecked()) {
                    dairy = true;
                }
                if( SoyCheckBox.isChecked()) {
                    soy = true;
                }
                if( ShelfishCheckBox.isChecked()) {
                    shelfish = true;
                }
                if( VegitarianCheckBox.isChecked()) {
                    vegitarian = true;
                }
                if( VeganCheckBox.isChecked()) {
                    vegan = true;
                }
                if( KosherCheckBox.isChecked()) {
                    kosher = true;
                }
                if( HalalCheckBox.isChecked()) {
                    halal = true;
                }

                Document doc = new Document()
                        .append( "ingreedient", ingreedientText.getText().toString() )
                        .append( "amount", Double.valueOf(amountText.getText().toString()) )
                        .append( "calories", Integer.valueOf(caloriesText.getText().toString()) )
                        .append( "gluten", gluten )
                        .append( "treeNut", treeNut)
                        .append( "dairy", dairy )
                        .append( "soy", soy )
                        .append( "shelfish", shelfish )
                        .append( "vegitarian", vegitarian )
                        .append( "vegan", vegan )
                        .append( "kosher", kosher )
                        .append( "halal", halal );

                coll.insertOne(doc);
                client.close();
            }
        });
    }
}
