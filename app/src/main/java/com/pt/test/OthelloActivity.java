package com.pt.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * オセロ
 *
 */
public class OthelloActivity extends Activity implements OnClickListener {

    /** 定数(空) */
    private static final int BLANK = 0;
    /** 定数(黒) */
    private static final int BLACK = R.drawable.othello_black;
    /** 定数(白) */
    private static final int WHITE = R.drawable.othello_white;

    /** マス */
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;
    private ImageView imageView13;
    private ImageView imageView14;
    private ImageView imageView15;
    private ImageView imageView16;
    private ImageView imageView17;
    private ImageView imageView18;
    private ImageView imageView19;
    private ImageView imageView20;
    private ImageView imageView21;
    private ImageView imageView22;
    private ImageView imageView23;
    private ImageView imageView24;
    private ImageView imageView25;
    private ImageView imageView26;
    private ImageView imageView27;
    private ImageView imageView28;
    private ImageView imageView29;
    private ImageView imageView30;
    private ImageView imageView31;
    private ImageView imageView32;
    private ImageView imageView33;
    private ImageView imageView34;
    private ImageView imageView35;
    private ImageView imageView36;
    private ImageView imageView37;
    private ImageView imageView38;
    private ImageView imageView39;
    private ImageView imageView40;
    private ImageView imageView41;
    private ImageView imageView42;
    private ImageView imageView43;
    private ImageView imageView44;
    private ImageView imageView45;
    private ImageView imageView46;
    private ImageView imageView47;
    private ImageView imageView48;
    private ImageView imageView49;
    private ImageView imageView50;
    private ImageView imageView51;
    private ImageView imageView52;
    private ImageView imageView53;
    private ImageView imageView54;
    private ImageView imageView55;
    private ImageView imageView56;
    private ImageView imageView57;
    private ImageView imageView58;
    private ImageView imageView59;
    private ImageView imageView60;
    private ImageView imageView61;
    private ImageView imageView62;
    private ImageView imageView63;
    private ImageView imageView64;

    /** ボタン(スタート) */
    private Button startButton;
    
    /** ボタン(OK) */
    private Button notTurnButton;

    /** レイアウト(スタート) */
    private LinearLayout startLayout;
    
    /** レイアウト(ターン変更) */
    private LinearLayout notTurnLayout;

    /** ターン(黒のターンであればR.drawable.othello_black,白のターンであればR.drawable.othello_white,黒が先攻) */
    private int turn = BLACK;

    /** 隣接した色が裏返す色か */
    private boolean haveToInvertColor;

    /** 置いた回数 */
    private int viewsPut;

    /** 置ける場所があるか */
    private boolean putPlace;

    /** 裏返すImageViewのList */
    private List<Integer> iList = new ArrayList<Integer>();
    private List<Integer> jList = new ArrayList<Integer>();

    /** 裏返す色 */
    private int flipOverColor = WHITE;

    /** 盤面(空が0,黒がR.drawable.othello_black,白がR.drawable.othello_white) */
    private int[][] bord = new int[8][8];

    /** ImageViewのID(石が置かれた場所のチェック用) */
    private ImageView[][] imageViewArray = new ImageView[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello);

        // タイトルのセット
        setTitle(R.string.batting_first);

        // 盤面のセット
        bord[3][3] = WHITE;
        bord[3][4] = BLACK;
        bord[4][3] = BLACK;
        bord[4][4] = WHITE;

        // Viewのセット
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.othello_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.reset:
            // スタートボタン表示
            startLayout.setVisibility(View.VISIBLE);
            // クリック無効化
            listenerChange(false);
            // タイトル変更
            setTitle(R.string.batting_first);

            // 盤面リセット
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    imageViewArray[i][j].setImageResource(0);
                }
            }
            imageViewArray[3][3].setImageResource(WHITE);
            imageViewArray[3][4].setImageResource(BLACK);
            imageViewArray[4][3].setImageResource(BLACK);
            imageViewArray[4][4].setImageResource(WHITE);

            bord = new int[8][8];
            bord[3][3] = WHITE;
            bord[3][4] = BLACK;
            bord[4][3] = BLACK;
            bord[4][4] = WHITE;

            turn = BLACK;
            flipOverColor = WHITE;
            viewsPut = 0;

            // 盤面チェック
            for (int m = 0; m < 8; m++) {
                for (int n = 0; n < 8; n++) {
                    bordCheck(m, n);
                }
            }

            result = true;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            // スタートボタン非表示
            startLayout.setVisibility(View.GONE);
            // クリック有効化
            listenerChange(true);
        } else if (v.getId() == R.id.not_turn_button) {
            // ターン変更ボタン非表示
            notTurnLayout.setVisibility(View.GONE);
            // クリック有効化
            listenerChange(true);
            // ターン変更処理
            turnProcess();
        } else if (viewsPut < 60) {
            // 位置を特定
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (imageViewArray[i][j].equals(v)) {
                        stornCheck(i, j);
                    }
                }
            }
        }
    }

    /**
     * 石を置いた際に裏返る石をチェック
     *
     */
    private void stornCheck(int i, int j) {
        // 石があるかどうか
        if (bord[i][j] == BLANK) {
            int copyi = i, copyj = j;
            iList.clear();
            jList.clear();
            // 左ななめ上
            while (placeTheStone(copyi - 1, copyj - 1)) {
                copyi--;
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 上
            while (placeTheStone(copyi - 1, copyj)) {
                copyi--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右ななめ上
            while (placeTheStone(copyi - 1, copyj + 1)) {
                copyi--;
                copyj++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 左
            while (placeTheStone(copyi, copyj - 1)) {
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右
            while (placeTheStone(copyi, copyj + 1)) {
                copyj++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 左ななめ下
            while (placeTheStone(copyi + 1, copyj - 1)) {
                copyi++;
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 下
            while (placeTheStone(copyi + 1, copyj)) {
                copyi++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右ななめ下
            while (placeTheStone(copyi + 1, copyj + 1)) {
                copyi++;
                copyj++;
            }
            if (haveToInvertColor) {
                // UI上に石を置く
                imageViewArray[i][j].setImageResource(turn);

                // 計算用配列に入力
                bord[i][j] = turn;

                // 石を置いた回数を数える
                viewsPut++;

                haveToInvertColor = false;

                // 盤面チェック
                turnProcess();

                // 石を置く場所が無ければ相手のターンになる
                if (!putPlace && viewsPut != 60) {
                    // スタートボタン表示
                    notTurnLayout.setVisibility(View.VISIBLE);
                    listenerChange(false);
                }
                if (viewsPut == 60) {
                    // 得点計算
                    int blackScore = 0, whiteScore = 0;
                    for (int m = 0; m < 8; m++) {
                        for (int n = 0; n < 8; n++) {
                            if (bord[m][n] == BLACK) {
                                blackScore++;
                            } else {
                                whiteScore++;
                            }
                        }
                    }
                    String win;
                    if (blackScore == whiteScore) {
                        win = "引き分け";
                    } else if (blackScore > whiteScore) {
                        win = "黒の勝ち";
                    } else {
                        win = "白の勝ち";
                    }
                    // 結果を表示
                    setTitle("黒 " + blackScore + "枚、白 " + whiteScore + "枚で" + win + "です！！");
                }
            } else {
                // タイトルに石が置けないことを表示
                setTitle(R.string.not_put);
            }
        } else {
            // タイトルに石が置けないことを表示
            setTitle(R.string.not_put);
        }
    }

    /**
     * ターン処理
     *
     */
    private void turnProcess() {
        turn = flipOverColor;
        if (turn == BLACK) {
            flipOverColor = WHITE;
            setTitle(R.string.black_turn);
        } else {
            flipOverColor = BLACK;
            setTitle(R.string.white_turn);
        }
        // 盤面チェック
        putPlace = false;
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                bordCheck(m, n);
            }
        }
    }

    /**
     * 裏返り処理
     *
     */
    private boolean placeTheStone(int i, int j) {
        if (i < 0 || j < 0 || i > 7 || j > 7) {
            return false;
        }
        if (bord[i][j] == flipOverColor) {
            iList.add(i);
            jList.add(j);
            return true;
        } else if (bord[i][j] == turn) {
            for (int l = 0; l < iList.size(); l++) {
                imageViewArray[iList.get(l)][jList.get(l)].setImageResource(turn);
                bord[iList.get(l)][jList.get(l)] = turn;
                haveToInvertColor = true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 石が置ける場所を探して背景を変える
     *
     */
    private void bordCheck(int i, int j) {
        // 石があるかどうか
        if (bord[i][j] == BLANK) {
            int copyi = i, copyj = j;
            iList.clear();
            jList.clear();
            // 左ななめ上
            while (placeTheBord(copyi - 1, copyj - 1)) {
                copyi--;
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 上
            while (placeTheBord(copyi - 1, copyj)) {
                copyi--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右ななめ上
            while (placeTheBord(copyi - 1, copyj + 1)) {
                copyi--;
                copyj++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 左
            while (placeTheBord(copyi, copyj - 1)) {
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右
            while (placeTheBord(copyi, copyj + 1)) {
                copyj++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 左ななめ下
            while (placeTheBord(copyi + 1, copyj - 1)) {
                copyi++;
                copyj--;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 下
            while (placeTheBord(copyi + 1, copyj)) {
                copyi++;
            }
            copyi = i;
            copyj = j;
            iList.clear();
            jList.clear();
            // 右ななめ下
            while (placeTheBord(copyi + 1, copyj + 1)) {
                copyi++;
                copyj++;
            }
        }

        // 背景を変える
        if (haveToInvertColor) {
            imageViewArray[i][j].setBackgroundResource(R.drawable.othello_background_place);
            putPlace = true;
            haveToInvertColor = false;
        } else {
            imageViewArray[i][j].setBackgroundResource(R.drawable.othello_background);
        }
    }

    /**
     * 盤面のチェック
     *
     */
    private boolean placeTheBord(int i, int j) {
        if (i < 0 || j < 0 || i > 7 || j > 7) {
            return false;
        }
        if (bord[i][j] == flipOverColor) {
            iList.add(i);
            return true;
        } else if (bord[i][j] == turn) {
            if (iList.size() != 0) {
                haveToInvertColor = true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * View設定
     *
     */
    private void setView() {
        // レイアウトとJavaの結合
        imageView1 = (ImageView) findViewById(R.id.image_view1);
        imageView2 = (ImageView) findViewById(R.id.image_view2);
        imageView3 = (ImageView) findViewById(R.id.image_view3);
        imageView4 = (ImageView) findViewById(R.id.image_view4);
        imageView5 = (ImageView) findViewById(R.id.image_view5);
        imageView6 = (ImageView) findViewById(R.id.image_view6);
        imageView7 = (ImageView) findViewById(R.id.image_view7);
        imageView8 = (ImageView) findViewById(R.id.image_view8);
        imageView9 = (ImageView) findViewById(R.id.image_view9);
        imageView10 = (ImageView) findViewById(R.id.image_view10);
        imageView11 = (ImageView) findViewById(R.id.image_view11);
        imageView12 = (ImageView) findViewById(R.id.image_view12);
        imageView13 = (ImageView) findViewById(R.id.image_view13);
        imageView14 = (ImageView) findViewById(R.id.image_view14);
        imageView15 = (ImageView) findViewById(R.id.image_view15);
        imageView16 = (ImageView) findViewById(R.id.image_view16);
        imageView17 = (ImageView) findViewById(R.id.image_view17);
        imageView18 = (ImageView) findViewById(R.id.image_view18);
        imageView19 = (ImageView) findViewById(R.id.image_view19);
        imageView20 = (ImageView) findViewById(R.id.image_view20);
        imageView21 = (ImageView) findViewById(R.id.image_view21);
        imageView22 = (ImageView) findViewById(R.id.image_view22);
        imageView23 = (ImageView) findViewById(R.id.image_view23);
        imageView24 = (ImageView) findViewById(R.id.image_view24);
        imageView25 = (ImageView) findViewById(R.id.image_view25);
        imageView26 = (ImageView) findViewById(R.id.image_view26);
        imageView27 = (ImageView) findViewById(R.id.image_view27);
        imageView28 = (ImageView) findViewById(R.id.image_view28);
        imageView29 = (ImageView) findViewById(R.id.image_view29);
        imageView30 = (ImageView) findViewById(R.id.image_view30);
        imageView31 = (ImageView) findViewById(R.id.image_view31);
        imageView32 = (ImageView) findViewById(R.id.image_view32);
        imageView33 = (ImageView) findViewById(R.id.image_view33);
        imageView34 = (ImageView) findViewById(R.id.image_view34);
        imageView35 = (ImageView) findViewById(R.id.image_view35);
        imageView36 = (ImageView) findViewById(R.id.image_view36);
        imageView37 = (ImageView) findViewById(R.id.image_view37);
        imageView38 = (ImageView) findViewById(R.id.image_view38);
        imageView39 = (ImageView) findViewById(R.id.image_view39);
        imageView40 = (ImageView) findViewById(R.id.image_view40);
        imageView41 = (ImageView) findViewById(R.id.image_view41);
        imageView42 = (ImageView) findViewById(R.id.image_view42);
        imageView43 = (ImageView) findViewById(R.id.image_view43);
        imageView44 = (ImageView) findViewById(R.id.image_view44);
        imageView45 = (ImageView) findViewById(R.id.image_view45);
        imageView46 = (ImageView) findViewById(R.id.image_view46);
        imageView47 = (ImageView) findViewById(R.id.image_view47);
        imageView48 = (ImageView) findViewById(R.id.image_view48);
        imageView49 = (ImageView) findViewById(R.id.image_view49);
        imageView50 = (ImageView) findViewById(R.id.image_view50);
        imageView51 = (ImageView) findViewById(R.id.image_view51);
        imageView52 = (ImageView) findViewById(R.id.image_view52);
        imageView53 = (ImageView) findViewById(R.id.image_view53);
        imageView54 = (ImageView) findViewById(R.id.image_view54);
        imageView55 = (ImageView) findViewById(R.id.image_view55);
        imageView56 = (ImageView) findViewById(R.id.image_view56);
        imageView57 = (ImageView) findViewById(R.id.image_view57);
        imageView58 = (ImageView) findViewById(R.id.image_view58);
        imageView59 = (ImageView) findViewById(R.id.image_view59);
        imageView60 = (ImageView) findViewById(R.id.image_view60);
        imageView61 = (ImageView) findViewById(R.id.image_view61);
        imageView62 = (ImageView) findViewById(R.id.image_view62);
        imageView63 = (ImageView) findViewById(R.id.image_view63);
        imageView64 = (ImageView) findViewById(R.id.image_view64);

        startButton = (Button) findViewById(R.id.start_button);
        notTurnButton = (Button) findViewById(R.id.not_turn_button);
        startLayout = (LinearLayout) findViewById(R.id.start_layout);
        notTurnLayout = (LinearLayout) findViewById(R.id.not_turn_layout);

        startButton.setOnClickListener(this);
        notTurnButton.setOnClickListener(this);
    }

    /**
     * リスナー有効化/無効化
     *
     */
    private void listenerChange(boolean on) {
        OnClickListener listener;
        if (on) {
            listener = this;
        } else {
            listener = null;
        }
        // クリックリスナー設定
        imageView1.setOnClickListener(listener);
        imageView2.setOnClickListener(listener);
        imageView3.setOnClickListener(listener);
        imageView4.setOnClickListener(listener);
        imageView5.setOnClickListener(listener);
        imageView6.setOnClickListener(listener);
        imageView7.setOnClickListener(listener);
        imageView8.setOnClickListener(listener);
        imageView9.setOnClickListener(listener);
        imageView10.setOnClickListener(listener);
        imageView11.setOnClickListener(listener);
        imageView12.setOnClickListener(listener);
        imageView13.setOnClickListener(listener);
        imageView14.setOnClickListener(listener);
        imageView15.setOnClickListener(listener);
        imageView16.setOnClickListener(listener);
        imageView17.setOnClickListener(listener);
        imageView18.setOnClickListener(listener);
        imageView19.setOnClickListener(listener);
        imageView20.setOnClickListener(listener);
        imageView21.setOnClickListener(listener);
        imageView22.setOnClickListener(listener);
        imageView23.setOnClickListener(listener);
        imageView24.setOnClickListener(listener);
        imageView25.setOnClickListener(listener);
        imageView26.setOnClickListener(listener);
        imageView27.setOnClickListener(listener);
        imageView28.setOnClickListener(listener);
        imageView29.setOnClickListener(listener);
        imageView30.setOnClickListener(listener);
        imageView31.setOnClickListener(listener);
        imageView32.setOnClickListener(listener);
        imageView33.setOnClickListener(listener);
        imageView34.setOnClickListener(listener);
        imageView35.setOnClickListener(listener);
        imageView36.setOnClickListener(listener);
        imageView37.setOnClickListener(listener);
        imageView38.setOnClickListener(listener);
        imageView39.setOnClickListener(listener);
        imageView40.setOnClickListener(listener);
        imageView41.setOnClickListener(listener);
        imageView42.setOnClickListener(listener);
        imageView43.setOnClickListener(listener);
        imageView44.setOnClickListener(listener);
        imageView45.setOnClickListener(listener);
        imageView46.setOnClickListener(listener);
        imageView47.setOnClickListener(listener);
        imageView48.setOnClickListener(listener);
        imageView49.setOnClickListener(listener);
        imageView50.setOnClickListener(listener);
        imageView51.setOnClickListener(listener);
        imageView52.setOnClickListener(listener);
        imageView53.setOnClickListener(listener);
        imageView54.setOnClickListener(listener);
        imageView55.setOnClickListener(listener);
        imageView56.setOnClickListener(listener);
        imageView57.setOnClickListener(listener);
        imageView58.setOnClickListener(listener);
        imageView59.setOnClickListener(listener);
        imageView60.setOnClickListener(listener);
        imageView61.setOnClickListener(listener);
        imageView62.setOnClickListener(listener);
        imageView63.setOnClickListener(listener);
        imageView64.setOnClickListener(listener);

        // ボタンを格納
        ImageView[][] imageViewArray = {
                { imageView1, imageView2, imageView3, imageView4, imageView5,
                        imageView6, imageView7, imageView8 },
                { imageView9, imageView10, imageView11, imageView12, imageView13,
                        imageView14, imageView15, imageView16 },
                { imageView17, imageView18, imageView19, imageView20, imageView21,
                        imageView22, imageView23, imageView24 },
                { imageView25, imageView26, imageView27, imageView28, imageView29,
                        imageView30, imageView31, imageView32 },
                { imageView33, imageView34, imageView35, imageView36, imageView37,
                        imageView38, imageView39, imageView40 },
                { imageView41, imageView42, imageView43, imageView44, imageView45,
                        imageView46, imageView47, imageView48 },
                { imageView49, imageView50, imageView51, imageView52, imageView53,
                        imageView54, imageView55, imageView56 },
                { imageView57, imageView58, imageView59, imageView60, imageView61,
                        imageView62, imageView63, imageView64 } };
        this.imageViewArray = imageViewArray;
    }
}