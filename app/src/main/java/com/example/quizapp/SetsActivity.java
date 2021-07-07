package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {
    private GridView sets_grid;
    private FirebaseFirestore firestore;
    public static int category_id;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        String title = getIntent().getStringExtra("CATEGORY");
        category_id = getIntent().getIntExtra("CATEGORY_ID", 1);
        getSupportActionBar().setTitle(title);

        sets_grid = findViewById(R.id.sets_grid);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        firestore = FirebaseFirestore.getInstance();
        loadSets();


    }
    public void loadSets(){
        firestore.collection("Quiz").document("CAT"+category_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()){
                                Log.e("document exists", "next i don't know");
                                long sets = (long) doc.get("SETS");
                                Sets_Adapter adapter= new Sets_Adapter((int)sets);
                                sets_grid.setAdapter(adapter);

                            }
                            else{
                                Toast.makeText(SetsActivity.this, "No CAT Document Exists", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(SetsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.cancel();
                    }
                });

    }


}