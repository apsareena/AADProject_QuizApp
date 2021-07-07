package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView question, qCount, timer;
    private Button option1, option2, option3, option4;
    private List<Question> questionList;
    private int quesNum;
    private CountDownTimer countDown;
    private int score;
    private int setNo;
    private Dialog loadingDialog;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = findViewById(R.id.question);
        qCount = findViewById(R.id.quest_num);
        timer = findViewById(R.id.countDown);

        option1 = findViewById(R.id.button);
        option2 = findViewById(R.id.button2);
        option3 = findViewById(R.id.button3);
        option4 = findViewById(R.id.button4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        setNo = getIntent().getIntExtra("SETNO", 1);
        loadingDialog = new Dialog(QuestionsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        firestore = FirebaseFirestore.getInstance();
        getQuestionsList();
        score=0;
    }

    private void getQuestionsList() {
        questionList = new ArrayList<>();
        firestore.collection("Quiz").document("CAT"+SetsActivity.category_id)
                .collection("SET"+setNo)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    for(QueryDocumentSnapshot doc:questions){
                        questionList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))));
                    }
                    setQuestion();
                }else{
                    Toast.makeText(QuestionsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.cancel();

            }
        });

    }

    private void setQuestion() {
        timer.setText(String.valueOf(10));

        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        qCount.setText(String.valueOf(1)+"/"+String.valueOf(questionList.size()));
        startTimer();

        quesNum=0;
    }

    private void startTimer() {
        countDown = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<10000){
                timer.setText(String.valueOf(millisUntilFinished/1000));}
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }



    private void playAnim(View view, final int value, int viewNum) {
                            switch (viewNum) {
                                case 0:
                                    ((TextView) view).setText(questionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionD());
                                    break;
                            }
                    }



    @Override
    public void onClick(View v) {
        int selectedOption = 0;
        switch (v.getId()){
            case R.id.button:
                selectedOption=1;
                break;
            case R.id.button2:
                selectedOption=1;
                break;
            case R.id.button3:
                selectedOption=1;
                break;
            case R.id.button4:
                selectedOption=1;
                break;
            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, v);
    }
    private void checkAnswer(int selectedOption, View view){
        if(selectedOption==questionList.get(quesNum).getCorrectAns()){
            // Right Answer

            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score=score+1;
        }
        else{
            // Wrong Answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (questionList.get(quesNum).getCorrectAns()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                switch (selectedOption){
                    case 1:
                        option1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                    case 2:
                        option2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                    case 3:
                        option3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                    case 4:
                        option4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));
                }
                changeQuestion();
            }
        }, 2000);

    }


    private void changeQuestion() {
        if(quesNum<questionList.size()-1){
            quesNum++;
            playAnim(question, 0, 0);
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            qCount.setText(String.valueOf(quesNum+1)+"/"+String.valueOf(questionList.size()));
            timer.setText(String.valueOf(10));
            startTimer();

        }else{
            // Go to Score Activity
            Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", score +"/"+ questionList.size());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();
    }
}