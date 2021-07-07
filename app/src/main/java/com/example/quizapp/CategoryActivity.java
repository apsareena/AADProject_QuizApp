package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        catGrid = findViewById(R.id.catGridView);

//

        CatGridAdapter adapter = new CatGridAdapter(MainActivity.catList);
        catGrid.setAdapter(adapter);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            CategoryActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}