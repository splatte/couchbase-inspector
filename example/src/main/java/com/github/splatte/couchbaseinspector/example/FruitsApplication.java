package com.github.splatte.couchbaseinspector.example;

import android.app.Application;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FruitsApplication extends Application {
    private static final String TAG = "FruitsApplication";
    public static final String DATABASE_NAME = "fruits_db";

    private Manager manager;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            final DatabaseOptions options = new DatabaseOptions();
            options.setCreate(true);
            database = manager.openDatabase(DATABASE_NAME, options);
        } catch (IOException | CouchbaseLiteException e) {
            e.printStackTrace();
        }

        if (database.getDocumentCount() == 0) {
            Log.d(TAG, "Database is empty. Creating some fruits.");
            fruitSalat();
        }

        Log.d(TAG, String.format("Database has %d documents.", database.getDocumentCount()));
    }

    private void fruitSalat() {
        final Map<String, String> fruits = new HashMap<>();
        fruits.put("Tomato", "red");
        fruits.put("Cherry", "red");
        fruits.put("Apple", "red");
        fruits.put("Cucumber", "green");
        fruits.put("Lime", "green");

        for (Map.Entry<String, String> fruitEntry : fruits.entrySet()) {
            // fill document with data
            final Map<String, Object> docData = new HashMap<>();
            docData.put("name", fruitEntry.getKey());
            docData.put("color", fruitEntry.getValue());

            // store document
            try {
                final Document doc = database.createDocument();
                doc.putProperties(docData);
                Log.d(TAG, String.format("Created document: %s with %s", doc.getId(), doc.getUserProperties()));
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
    }
}
