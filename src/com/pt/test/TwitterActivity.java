package com.pt.test;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Twitterのタイムラインを取得し、ListViewに表示
 * 音声認識機能を使用し、しゃべったことをツイートする
 *
 */
public class TwitterActivity extends Activity implements OnClickListener {

    /** リストビュー */
    private ListView tweetList;

    /** リクエストコード */
    private static final int REQUEST_CODE = 1;

    /** Twitter */
    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        // View設定
        Button voiceTweetButton = (Button) findViewById(R.id.voice_tweet_button);
        voiceTweetButton.setOnClickListener(this);
        tweetList = (ListView) findViewById(R.id.tweet_list_view);

        // アクセストークン取得通信処理
        // 通信部分があるため非同期で実行
        new AsyncGetAccessToken().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.twitter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
        case R.id.timeline_reload:
            // タイムラインを更新
            new AsyncTimeLine().execute();
            result = true;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        try {
            // Intent生成
            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // 音声認識画面に表示する説明を設定
            speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.voice_prompt));

            // 音声認識Intent開始
            startActivityForResult(speechIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            // 端末が対応していない場合にトーストでメッセージを表示する
            Toast.makeText(this, getString(R.string.device_not_supported), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 音声認識の結果を受け取る
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が発行したIntentであれば処理を行う
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            // 音声認識した文字列を取得
            ArrayList<String> tweetStr = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // ツイート処理
            new AsyncTweet().execute(tweetStr.get(0));

            // ツイートした内容を表示
            Toast.makeText(this, "\"" + tweetStr.get(0) + "\"とツイートしました！", Toast.LENGTH_LONG).show();

            // ツイートしたらタイムラインを更新
            new AsyncTimeLine().execute();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * アクセストークン取得通信処理クラス
     *
     */
    private class AsyncGetAccessToken extends AsyncTask<Void, Void, Void> {

        /** アクセストークン */
        private AccessToken token;

        @Override
        protected Void doInBackground(Void... arg0) {
            // OAuth認証情報をブラウザから送られたIntentから取得
            Uri oAuthUri = getIntent().getData();
            if(oAuthUri != null && oAuthUri.toString().startsWith(getString(R.string.callback_uri))){
                try {

                    // アクセストークンを取得
                    token = TopActivity._oauth.getOAuthAccessToken(TopActivity._req,
                            oAuthUri.getQueryParameter(getString(R.string.verifier)));
                } catch (TwitterException e) {
                    Log.d(getString(R.string.log_twitter), "getOAuthAccessTokenでTwitterExceptionが発生");
                    if (e.isCausedByNetworkIssue()) {
                        Log.d(getString(R.string.log_twitter), "ネットワークの問題");
                   }
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Twitterインスタンス取得
            twitter = new TwitterFactory().getInstance();

            // Consumer key & Consumer secretをTwitterインスタンスに設定
            twitter.setOAuthConsumer(getString(R.string.consumer_key), getString(R.string.consumer_secret));

            // AccessTokenをTwitterインスタンスに設定
            twitter.setOAuthAccessToken(token);

            // タイムライン取得処理
            new AsyncTimeLine().execute();
            super.onPostExecute(result);
        }
    }

    /**
     * ツイート処理クラス
     *
     */
    private class AsyncTweet extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                // ツイート
                twitter.updateStatus(arg0[0]);
            } catch (TwitterException e) {
                Log.d(getString(R.string.log_twitter), "updateStatusでTwitterExceptionが発生");
                if (e.isCausedByNetworkIssue()) {
                     Log.d(getString(R.string.log_twitter), "ネットワークの問題");
                }
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * タイムライン取得処理
     *
     */
    private class AsyncTimeLine extends AsyncTask<Void, Void, Void> {

        /** ListViewに使用するアダプター */
        private ArrayAdapter<String> adapter;

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                // タイムラインの取得(自分のデフォルトタイムライン)
                ResponseList<twitter4j.Status> homeTimeline = twitter.getHomeTimeline();
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

                // タイムラインを一件づつ処理
                for (twitter4j.Status status : homeTimeline) {
                    // ID、ツイートをアダプターにセット
                    adapter.add("@" + status.getUser().getScreenName() + "\n" + status.getText());
                }
            } catch (TwitterException e) {
                Log.d(getString(R.string.log_twitter), "getHomeTimelineでTwitterExceptionが発生");
                if (e.isCausedByNetworkIssue()) {
                     Log.d(getString(R.string.log_twitter), "ネットワークの問題");
                }
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // ListViewにアダプターをセット
            tweetList.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
