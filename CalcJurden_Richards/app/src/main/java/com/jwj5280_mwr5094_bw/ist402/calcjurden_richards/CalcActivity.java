package com.jwj5280_mwr5094_bw.ist402.calcjurden_richards;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CalcActivity extends ActionBarActivity {

    TextView txtResult;
    String result;
    char operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        result = "0";

        txtResult = (TextView) findViewById(R.id.txtResult);

        BtnListener listener = new BtnListener();

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


    private class BtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button thisButton = (Button) findViewById(view.getId());

            switch (view.getId()) {
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

                    String currentText = txtResult.getText().toString();

                    if (currentText.equals("0")) {
                        currentText = thisButton.getText().toString();
                    } else {
                        currentText += thisButton.getText();
                    }

                    txtResult.setText(currentText);

                    break;
                case R.id.btnPlus:
                case R.id.btnMinus:
                case R.id.btnTimes:
                case R.id.btnDivide:

                    operation = thisButton.getText().charAt(0);
                    result = txtResult.getText().toString();

                    break;
                case R.id.btnEquals:

                    switch (operation){
                        case '+':
                            float val1 = Float.parseFloat(result);
                            float val2 = Float.parseFloat(txtResult.getText().toString());

                            float answer = val1+val2;

                            result = String.valueOf(answer);

                            txtResult.setText(result);

                            break;
                        case '-':
                            break;
                        case '*':
                            break;
                        case '/':
                            break;
                        default:
                    }

                    break;
                case R.id.btnClear:
                    txtResult.setText("0");
                    break;
            }
        }
    }
}
