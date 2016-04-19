package com.github.splatte.couchbaseinspector.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.splatte.couchbaseinspector.CouchbaseInspectorActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnInspector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CouchbaseInspectorActivity.intentTo(MainActivity.this, FruitsApplication.DATABASE_NAME));
            }
        });
    }
}
