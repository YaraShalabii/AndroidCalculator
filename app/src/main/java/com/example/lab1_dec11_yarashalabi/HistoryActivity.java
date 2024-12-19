package com.example.lab1_dec11_yarashalabi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    Button doneHistoryActivityBtn;

    TextView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        String history = intent.getStringExtra("Calculator_history");

        doneHistoryActivityBtn = findViewById(R.id.doneHistoryActivityBtn);
        historyList = findViewById(R.id.history_list);

        doneHistoryActivityBtn.setOnClickListener(v -> {
            finish();
        });

        historyList.setText(history);

    }
}