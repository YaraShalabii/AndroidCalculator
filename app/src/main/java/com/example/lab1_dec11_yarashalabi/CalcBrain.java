package com.example.lab1_dec11_yarashalabi;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CalcBrain {

    List<String> history = new ArrayList<>();
    List<String> expressionList = new ArrayList<>();

    // Method to reset the calculator values
    public void reset() {
        expressionList.clear();
    }

    public void saveHistory(String element) {
        history.add(element);
    }

    public String getHistory(){
        StringBuilder formattedString = new StringBuilder();

        for (int i = 0; i < history.size(); i++) {
            if (i == history.size() - 1) {
                // Append the result with "="
                formattedString.append("= ").append(history.get(i));
            } else {
                // Append other elements
                formattedString.append(history.get(i)).append(" ");
            }
        }
        return formattedString.toString();
    }

    public void resetHistory(){
        history.clear();
    }

    public void push(String value) {
        expressionList.add(value);
    }

    public int calculate() {
        if (expressionList.isEmpty()) return 0;
        Log.d("CalcBrain", "expressionList: " + expressionList.toString());

        List<String> tempList = new ArrayList<>(expressionList);

        // First pass: handle multiplication and division
        for (int i = 0; i < tempList.size(); i++) {
            String current = tempList.get(i);
            if (current.equals("*") || current.equals("/")) {
                int operand1 = Integer.parseInt(tempList.get(i - 1));
                int operand2 = Integer.parseInt(tempList.get(i + 1));
                int result = current.equals("*") ? operand1 * operand2 : operand1 / operand2;

                // Replace the operation with its result
                tempList.set(i - 1, String.valueOf(result));
                tempList.remove(i); // Remove operator
                tempList.remove(i); // Remove the next operand (now at index i)
                i--; // Recheck the new element at this index
            }
        }

        // Second pass: handle addition and subtraction
        int result = Integer.parseInt(tempList.get(0));
        for (int i = 1; i < tempList.size(); i += 2) {
            String operator = tempList.get(i);
            int operand = Integer.parseInt(tempList.get(i + 1));
            if (operator.equals("+")) {
                result += operand;
            } else if (operator.equals("-")) {
                result -= operand;
            }
        }
        reset();
        return result;
    }

}
