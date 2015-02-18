/**
 * Programmer name: Jesse Jurden
 * Date: February 18, 2015
 *
 * TipCalcActivity - Week 5 Assignment
 *
 * This is the primary logic for handling the TipCalculator app.
 */

package com.jwj5280_bw.ist402.tipcalcjurden;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class TipCalcActivity extends ActionBarActivity {

    // constants used when saving/restoring state
    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal; // bill amount entered by the user
    private int currentCustomPercent; // tip % set with the SeekBar
    private EditText tip10EditText; // displays 10% tip
    private EditText total10EditText; // displays total with 10% tip
    private EditText tip15EditText; // displays 15% tip
    private EditText total15EditText; // displays total with 15% tip
    private EditText billEditText; // accepts user input for bill total
    private EditText tip20EditText; // displays 20% tip
    private EditText total20EditText; // displays total with 20% tip
    private TextView customTipTextView; // displays custom tip percentage
    private EditText tipCustomEditText; // displays custom tip amount
    private EditText totalCustomEditText; // displays total tip amount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc);

        // check if app just started or is being restored from memory
        if (savedInstanceState == null) // the app just started running
        {
            currentBillTotal = 0.0; // initialize the bill amount to zero
            currentCustomPercent = 18; // initialize the custom tip to 18%
        }else // app is being restored from memory, not executed from scratch
        {
            // initialize the bill amount to saved amount
            currentBillTotal = savedInstanceState.getFloat(BILL_TOTAL);
            // initialize the custom tip to saved tip percent
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }

        // get references to the 10%, 15%, 20% tip and total EditTexts
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);

        // get the billEditText
        billEditText = (EditText) findViewById(R.id.billEditText);

        // get the custom tip and total EditTexts
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

        // get the custom tip TextView - the one that displays the SeekBar's amount
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);

        // billEditTextWatcher handles billEditText's onTextChanged event
        billEditText.addTextChangedListener(billEditTextWatcher);

        // get the SeekBar used to set the custom tip amount
        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip_calc, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putDouble(BILL_TOTAL, currentBillTotal);
        outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    }

    /**
     * Updates the 10%, 15%, and 20% EditText values for tip and total
     */
    private void updateStandard()
    {
        // calculate bill total with a ten percent tip
        double tenPercentTip = currentBillTotal * .10;
        double tenPercentTotal = currentBillTotal + tenPercentTip;

        // set tip10EditText's text to tenPercentTip
        tip10EditText.setText(String.format("%.02f",tenPercentTip));
        // set total10EditText's text to tenPercentTotal
        total10EditText.setText(String.format("%.02f",tenPercentTotal));

        // calculate bill total with a fifteen percent tip
        double fifteenPercentTip = currentBillTotal * .15;
        double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

        // set tip15EditText's text to fifteenPercentTip
        tip15EditText.setText(String.format("%.02f",fifteenPercentTip));
        // set total15EditText's text to fifteenPercentTotal
        total15EditText.setText(String.format("%.02f",fifteenPercentTotal));

        // calculate bill total with a twenty percent tip
        double twentyPercentTip = currentBillTotal * .20;
        double twentyPercentTotal = currentBillTotal + twentyPercentTip;

        // set tip20EditText's text to twentyPercentTip
        tip20EditText.setText(String.format("%.02f",twentyPercentTip));
        // set total20EditText's text to twentyPercentTotal
        total20EditText.setText(String.format("%.02f",twentyPercentTotal));
    }

    /**
     * Updates the custom tip percentage EditTexts
     */
    private void updateCustom(){
        // set customTipTextView's text to match the position of the SeekBar
        customTipTextView.setText(currentCustomPercent+"%");

        // calculate the custom tip amount
        double customTipAmount = currentBillTotal * currentCustomPercent * 0.01;

        // calculate the total bill, including the custom tip
        double customTotalAmount = currentBillTotal + customTipAmount;

        // update the EditText values
        tipCustomEditText.setText(String.format("%.02f",customTipAmount));
        totalCustomEditText.setText(String.format("%.02f",customTotalAmount));
    }

    // Creates a SeekBarChange Listener to handle the custom tip percentage
    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        /**
         * Update currentCustomPercent, then call updateCustom
         * @param seekBar the primary SeekBar element
         * @param progress the amount the SeekBar has currently
         * @param fromUser if the user was the one who modified the SeekBar value
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            currentCustomPercent = seekBar.getProgress();
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /**
     * Event-handling object that responds to billEditText's events
     */
    private TextWatcher billEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * Called when the user enters a number
         * @param s the user's input
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                // Convert the value of the input into a double
                currentBillTotal = Double.parseDouble(s.toString());
            }catch (NumberFormatException e){
                // In case the EditText's value is not a double
                currentBillTotal = 0.0;
            }

            // Update the EditTexts to reflect the new tip and total values
            updateStandard(); // updates the 10%, 15%, and 20% EditTexts
            updateCustom(); // update the custom tip EditTexts
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
