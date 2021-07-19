package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private GridView catGrid;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.aboutus:
                Toast.makeText(this, "Should go to about us page", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.login:
                Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.signup:
                Intent intent1 = new Intent(CategoryActivity.this, RegisterActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(CategoryActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        catGrid = findViewById(R.id.catGridView);

//

        CatGridAdapter adapter = new CatGridAdapter(MainActivity.catList);
        catGrid.setAdapter(adapter);



    }

}