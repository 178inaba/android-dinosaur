package com.pt.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 25個のボタンに1～25までの数字をランダムに設定し1～25まで順番に押して時間を計測するプログラム
 *
 */
public class NumberTouchActivity extends Activity implements OnClickListener {

    // ボタン(数字)
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;
    private Button button21;
    private Button button22;
    private Button button23;
    private Button button24;
    private Button button25;

    // ボタン(スタート)
    private Button startButton;

    // レイアウト(スタート)
    private LinearLayout startLayout;

    // リスト(ボタン)
    private List<Button> buttonList = new ArrayList<Button>();

    // リスト(押した数字)
    private List<String> pushNumberList = new ArrayList<String>();

    // 開始時間
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_touch);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.number_touch_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.reset:
            for (Button b : buttonList) {
                b.setText("");
            }
            // スタートボタン表示
            startLayout.setVisibility(View.VISIBLE);
            result = true;
        }
        return result;
    }

    private void setNumber() {
        // 数字リストの消去
        pushNumberList.clear();

        // スタートボタン表示
        startLayout.setVisibility(View.VISIBLE);

        // ボタンに番号を割り振る
        List<Integer> numberList = new ArrayList<Integer>();
        for (Button b : buttonList) {
            int randomNumber;
            do {
                randomNumber = new Random().nextInt(25) + 1;
            } while (numberList.contains(randomNumber));
            b.setEnabled(true);
            b.setText(String.valueOf(randomNumber));
            numberList.add(randomNumber);
        }
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {
        Button clickButton = (Button) v;

        if (clickButton.getId() == R.id.start_button) {
            setNumber();
            // スタートボタン非表示
            startLayout.setVisibility(View.GONE);
        } else {
            String pushNumber = clickButton.getText().toString();
            if (pushNumberList.isEmpty()) {
                if ("1".equals(pushNumber)) {
                    clickButton.setEnabled(false);
                    pushNumberList.add(pushNumber);
                }
            } else {
                int lastNumber = Integer.parseInt(pushNumberList
                        .get(pushNumberList.size() - 1));
                if (lastNumber + 1 == Integer.parseInt(pushNumber)) {
                    clickButton.setEnabled(false);
                    pushNumberList.add(pushNumber);
                }
            }
            if (Integer.parseInt(pushNumber) == 25) {
                long clearTimeMil = System.currentTimeMillis() - startTime;
                long clearTimeSec = clearTimeMil / 1000;
                StringBuilder toastMessage = new StringBuilder();
                toastMessage.append("クリア時間\n");
                toastMessage.append(clearTimeSec);
                toastMessage.append("秒");
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setView() {
        // レイアウトとJavaの結合
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button13 = (Button) findViewById(R.id.button13);
        button14 = (Button) findViewById(R.id.button14);
        button15 = (Button) findViewById(R.id.button15);
        button16 = (Button) findViewById(R.id.button16);
        button17 = (Button) findViewById(R.id.button17);
        button18 = (Button) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);
        button20 = (Button) findViewById(R.id.button20);
        button21 = (Button) findViewById(R.id.button21);
        button22 = (Button) findViewById(R.id.button22);
        button23 = (Button) findViewById(R.id.button23);
        button24 = (Button) findViewById(R.id.button24);
        button25 = (Button) findViewById(R.id.button25);
        startButton = (Button) findViewById(R.id.start_button);
        startLayout = (LinearLayout) findViewById(R.id.start_layout);

        // ボタンを格納
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);
        buttonList.add(button6);
        buttonList.add(button7);
        buttonList.add(button8);
        buttonList.add(button9);
        buttonList.add(button10);
        buttonList.add(button11);
        buttonList.add(button12);
        buttonList.add(button13);
        buttonList.add(button14);
        buttonList.add(button15);
        buttonList.add(button16);
        buttonList.add(button17);
        buttonList.add(button18);
        buttonList.add(button19);
        buttonList.add(button20);
        buttonList.add(button21);
        buttonList.add(button22);
        buttonList.add(button23);
        buttonList.add(button24);
        buttonList.add(button25);

        // クリックリスナー設定
        for (Button b : buttonList) {
            b.setOnClickListener(this);
        }
        startButton.setOnClickListener(this);
    }
}
