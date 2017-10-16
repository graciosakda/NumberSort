package com.graciosakda.numbersort;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText numberEditText;
    private Button addNumberBtn, ascendingOrderBtn, descendingOrderBtn;
    private TextView numberTextView;
    private ArrayList<Integer> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberEditText = (EditText) findViewById(R.id.numberEditTxt);
        numberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    View view = getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
        });

        addNumberBtn = (Button) findViewById(R.id.addBtn);
        addNumberBtn.setOnClickListener(this);
        ascendingOrderBtn = (Button) findViewById(R.id.ascendingBtn);
        ascendingOrderBtn.setOnClickListener(this);
        descendingOrderBtn = (Button) findViewById(R.id.descendingBtn);
        descendingOrderBtn.setOnClickListener(this);
        numberTextView = (TextView) findViewById(R.id.numberTextView);
        numberTextView.setMovementMethod(new ScrollingMovementMethod());

        numbers = new ArrayList<>();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:
                if(numberEditText.getText().length()>0) {
                    addNumberToTextView(Integer.valueOf(numberEditText.getText().toString()));
                }
                break;

            case R.id.ascendingBtn:
                ascendingOrderBtn.setVisibility(View.GONE);
                descendingOrderBtn.setVisibility(View.VISIBLE);
                ascendingOrder();
                break;

            case R.id.descendingBtn:
                ascendingOrderBtn.setVisibility(View.VISIBLE);
                descendingOrderBtn.setVisibility(View.GONE);
                descendingOrder();
                break;
        }
    }

    private void addNumberToTextView(int i){
        if(numbers.contains(i)){
            numberEditText.setError("Number exists!");
        }else{
            numbers.add(i);
            if(ascendingOrderBtn.getVisibility() == View.VISIBLE){
                descendingOrder();
            }else{
                ascendingOrder();
            }
        }
        numberEditText.setText("");
    }

    private void ascendingOrder(){
        Collections.sort(this.numbers);
        printNumbers("ASCENDING ORDER");
    }

    private void descendingOrder(){
        Collections.sort(numbers, Collections.<Integer>reverseOrder());
        printNumbers("DESCENDING ORDER");
    }

    private void printNumbers(String label){
        String text = label+"\n";
        for(Integer num: numbers){
            text+="\n"+num;
        }
        numberTextView.setText(text);
    }
}
