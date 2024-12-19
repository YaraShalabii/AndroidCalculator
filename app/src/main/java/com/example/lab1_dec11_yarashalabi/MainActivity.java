package com.example.lab1_dec11_yarashalabi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView result;

    TextView history;
    Button[] numButtons = new Button[10];

    Button divideBtn, multiplyBtn, subtractBtn, addBtn, equalBtn, resetBtn, clearBtn, calculatorStyle, historyMenuBtn;

    CalcBrain calculator = new CalcBrain();  // Instantiate the Calculator class
    final boolean[] isNewNumber = {true};

    int finalResult;

    int operationsClicked = 0;

    List<String> previousHistory = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numButtons[0] = findViewById(R.id.num0);
        numButtons[1] = findViewById(R.id.num1);
        numButtons[2] = findViewById(R.id.num2);
        numButtons[3] = findViewById(R.id.num3);
        numButtons[4] = findViewById(R.id.num4);
        numButtons[5] = findViewById(R.id.num5);
        numButtons[6] = findViewById(R.id.num6);
        numButtons[7] = findViewById(R.id.num7);
        numButtons[8] = findViewById(R.id.num8);
        numButtons[9] = findViewById(R.id.num9);

        divideBtn = findViewById(R.id.divide);
        multiplyBtn = findViewById(R.id.multiply);
        subtractBtn = findViewById(R.id.subtract);
        addBtn = findViewById(R.id.addition);
        equalBtn = findViewById(R.id.equal);

        resetBtn = findViewById(R.id.reset);
        clearBtn = findViewById(R.id.clear);

        result = findViewById(R.id.result);
        history = findViewById(R.id.full_operation);
        historyMenuBtn = findViewById(R.id.history_menu_btn);

        calculatorStyle = findViewById(R.id.calculatorStyle);

        calculatorStyle.setOnClickListener(v -> {
            String currentStyle = calculatorStyle.getText().toString();
            calculator.resetHistory();
            if (currentStyle.equals("Advanced Calculator - with history")) {
                history.setVisibility(View.VISIBLE);
                calculatorStyle.setText("Standard Calculator - no history");
            } else {
                history.setVisibility(View.GONE);
                calculatorStyle.setText("Advanced Calculator - with history");
            }
        });

        // Set up number button listeners
        for (int i = 0; i < numButtons.length; i++) {
            equalBtn.isEnabled();
            int finalI = i;
            numButtons[i].setOnClickListener(v -> {
                Log.d("CalculatorApp", "Clicked number = " + finalI);

                if (isNewNumber[0]) {
                    result.setText(String.valueOf(finalI));
                    isNewNumber[0] = false;
                } else {
                    // Get the current text in the TextView
                    String currentText = result.getText().toString();

                    // Append the clicked number to the current text
                    String updatedText = currentText + finalI;

                    // Set the updated text to the TextView
                    result.setText(updatedText);
                }

            });
        }

        // Set up operation buttons
        divideBtn.setOnClickListener(v -> {
            setOperation("/", divideBtn);
        });

        multiplyBtn.setOnClickListener(v -> {
            setOperation("*", multiplyBtn);
        });

        subtractBtn.setOnClickListener(v -> {
            setOperation("-", subtractBtn);
        });

        addBtn.setOnClickListener(v -> {
            setOperation("+", addBtn);
        });

        equalBtn.setOnClickListener(v -> {

            buttonPressed(equalBtn);
            calculator.push(result.getText().toString());
            calculator.saveHistory(result.getText().toString());
            finalResult = calculator.calculate();
            result.setText(String.valueOf(finalResult));
            calculator.saveHistory(result.getText().toString());

            if (calculatorStyle.getText().toString().equals("Standard Calculator - no history")) {
                history.setText(calculator.getHistory());
                // Append to full history
                previousHistory.add(history.getText().toString() + "\n"); // Get existing history
                calculator.resetHistory();
            }

            isNewNumber[0] = true; // Reset to accept a new number
            operationsClicked = 0;
            calculator.reset();
        });

        resetBtn.setOnClickListener(v -> {
            calculator.reset();
            result.setText("0");
            isNewNumber[0] = true;
            operationsClicked = 0;
            calculator.resetHistory();
        });

        clearBtn.setOnClickListener(v -> {
            String currentText = result.getText().toString();
            if (!currentText.isEmpty()) {
                currentText = currentText.substring(0, currentText.length() - 1);
                result.setText(currentText);
                if (currentText.isEmpty()) {
                    result.setText("0");
                    isNewNumber[0] = true;

                }
            }
        });

        historyMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("Calculator_history", String.join("", previousHistory));
            startActivity(intent);
        });

    }

    private void setOperation(String operation, Button button) {
        operationsClicked++;
        buttonPressed(button);
        calculator.push(result.getText().toString());
        calculator.saveHistory(result.getText().toString());
        calculator.push(operation);
        calculator.saveHistory(operation);
        isNewNumber[0] = true;
    }
    private void buttonPressed(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.white, null));
        button.setTextColor(getResources().getColor(R.color.light_blue, null));

        new Handler().postDelayed(() -> {
            if (button.equals(equalBtn)) {
                button.setBackgroundColor(getResources().getColor(R.color.light_blue, null));
                button.setTextColor(getResources().getColor(R.color.white, null));
            } else {
                button.setBackgroundColor(getResources().getColor(R.color.gray, null));
                button.setTextColor(getResources().getColor(R.color.light_blue, null));
            }
        }, 100);
    }
}