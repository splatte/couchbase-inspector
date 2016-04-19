package com.github.splatte.couchbaseinspector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;


public class CouchbaseInspectorActivity extends AppCompatActivity implements Query.QueryCompleteListener {
    private static final String ARG_DATABASE_NAME = "ci_database_name";

    private RecyclerView mList;
    private CouchbaseAdapter mAdapter;

    private Manager manager;
    private Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector);

        mAdapter = new CouchbaseAdapter();
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);

        final String databaseName = getIntent().getStringExtra(ARG_DATABASE_NAME);
        startCouchbase(databaseName);

        database.createAllDocumentsQuery().runAsync(this);
    }

    @Override
    protected void onDestroy() {
        stopCouchbase();
        super.onDestroy();
    }

    private void startCouchbase(String databaseName) {
        try {
            manager = new Manager(new AndroidContext(this.getApplicationContext()), Manager.DEFAULT_OPTIONS);
            final DatabaseOptions options = new DatabaseOptions();
            options.setCreate(false);
            database = manager.openDatabase(databaseName, options);
        } catch (IOException e) {
            errorDialog("Couldn't open database.");
        } catch (CouchbaseLiteException e) {
            errorDialog("Couldn't open database: " + e.getMessage());
        }
    }

    private void stopCouchbase() {
        if (manager != null) {
            manager.close();
        }
    }

    private void errorDialog(final String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .show();
    }

    public static Intent intentTo(final Context context, final String databaseName) {
        final Intent intent = new Intent(context, CouchbaseInspectorActivity.class);
        intent.putExtra(ARG_DATABASE_NAME, databaseName);
        return intent;
    }

    @Override
    public void completed(QueryEnumerator rows, Throwable error) {
        while (rows.hasNext()) {
            final QueryRow row = rows.next();
            mAdapter.add(row);
        }
    }
}
