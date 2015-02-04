/**
 * CalcActivity.java
 * Jesse Jurden, Mike Richards
 * February 9, 2015
 *
 * This handles the main activity and logic for the calculator app.
 */
package com.jwj5280_mwr5094_bw.ist402.calcjurden_richards;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CalcActivity extends ActionBarActivity {

    private TextView txtResult; // Keeps a reference to the TextView element that will display the result.
    private String inStr = "0"; // Saves the previous value of what the user has put in
    private char lastOperator = ' '; // Keeps track of what operation the user wants to perform
    private float result = 0; // The float result for calculation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        // Initialize the TextView
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText("0");

        // Create our button listener in order to apply it to the buttons
        BtnListener listener = new BtnListener();

        // Set the button listener to all our buttons.
        findViewById(R.id.btn0).setOnClickListener(listener);
        findViewById(R.id.btn1).setOnClickListener(listener);
        findViewById(R.id.btn2).setOnClickListener(listener);
        findViewById(R.id.btn3).setOnClickListener(listener);
        findViewById(R.id.btn4).setOnClickListener(listener);
        findViewById(R.id.btn5).setOnClickListener(listener);
        findViewById(R.id.btn6).setOnClickListener(listener);
        findViewById(R.id.btn7).setOnClickListener(listener);
        findViewById(R.id.btn8).setOnClickListener(listener);
        findViewById(R.id.btn9).setOnClickListener(listener);
        findViewById(R.id.btnPlus).setOnClickListener(listener);
        findViewById(R.id.btnMinus).setOnClickListener(listener);
        findViewById(R.id.btnTimes).setOnClickListener(listener);
        findViewById(R.id.btnDivide).setOnClickListener(listener);
        findViewById(R.id.btnDot).setOnClickListener(listener);
        findViewById(R.id.btnEquals).setOnClickListener(listener);
        findViewById(R.id.btnClear).setOnClickListener(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void compute() {
        // convert inStr to float value
        float inNum = Float.parseFloat(inStr);
        inStr = "0";

        if (lastOperator == ' ') {
            result = inNum;
        } else if (lastOperator == '+') {
            result += inNum;
        } else if (lastOperator == '-') {
            result -= inNum;
        } else if (lastOperator == '*') {
            result *= inNum;
        } else if (lastOperator == '/') {
            if (inNum == 0) {
                // Handle the divided by zero exception
                Toast.makeText(this, "Silly goose! You cannot divide by zero!", Toast.LENGTH_SHORT).show();
                result = 0;
                txtResult.setText(String.valueOf(result));
            } else {
                result /= inNum;
            }

        } else if (lastOperator == '=') {
            // Keep the result for the next operation
            result = inNum;
        }
        // Convert numeric result to String and then display
        txtResult.setText(String.valueOf(result));
    }

    /**
     * Inner class used as an OnClickListener for all the buttons
     */
    private class BtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Numeric buttons, including decimal
                case R.id.btn0:
                case R.id.btn1:
                case R.id.btn2:
                case R.id.btn3:
                case R.id.btn4:
                case R.id.btn5:
                case R.id.btn6:
                case R.id.btn7:
                case R.id.btn8:
                case R.id.btn9:
                case R.id.btnDot:
                    // Assigns or appends the digit to the display
                    String inDigit = ((Button) view).getText().toString();

                    if (inStr.equals("0")) {
                        // If the user enters a dot first, then it should say 0.
                        // It could cause an error otherwise.
                        if (inDigit.equals(".")) {
                            inStr = "0" + inDigit;
                        } else {
                            inStr = inDigit;
                        }
                    } else {
                        // DON'T add any more decimals if one already exists
                        if (inDigit.equals(".") && inStr.contains(".")) {
                            // Do nothing
                        } else {
                            inStr += inDigit;
                        }
                    }
                    // Display the new text
                    txtResult.setText(inStr);
                    // Clear buffer if last operator is '='
                    if (lastOperator == '='){
                        // Result
                        result = 0;
                        lastOperator = ' ';
                    }
                    break;
                // Operator buttons
                case R.id.btnPlus:
                case R.id.btnMinus:
                case R.id.btnTimes:
                case R.id.btnDivide:
                case R.id.btnEquals:
                    compute();
                    lastOperator = ((Button) view).getText().charAt(0);

                    break;
                // Clear button
                case R.id.btnClear:
                    inStr = "0";
                    txtResult.setText(inStr);
                    result = 0;
                    lastOperator = ' ';
                    break;
            }
        }
    }
}
