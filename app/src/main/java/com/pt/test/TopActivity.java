package com.pt.test;

import twitter4j.TwitterException;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

// TOPActivity
public class TopActivity extends Activity implements OnClickListener {

    // ボタン
    private Button numberTouchButton;
    private Button openGlButton;
    private Button othelloButton;
    private Button twitterButton;

    // Twitter
    public static RequestToken _req = null;
    public static OAuthAuthorization _oauth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // タイトルバー無し
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_top);
        setView();
    }

    @Override
    public void onClick(View v) {
        Button clickButton = (Button) v;

        switch (clickButton.getId()) {
        case R.id.number_touch_button:
            startActivity(new Intent(this, NumberTouchActivity.class));
            break;
        case R.id.opengl_button:
            startActivity(new Intent(this, OpenGlActivity.class));
            break;
        case R.id.othello_button:
            startActivity(new Intent(this, OthelloActivity.class));
            break;
        case R.id.twitter_button:
            AsyncRequest ar = new AsyncRequest();
            ar.execute();
            break;
        default:
            break;
        }
    }

    private void setView() {
        // レイアウトとJavaの結合
        numberTouchButton = (Button) findViewById(R.id.number_touch_button);
        openGlButton = (Button) findViewById(R.id.opengl_button);
        othelloButton = (Button) findViewById(R.id.othello_button);
        twitterButton = (Button) findViewById(R.id.twitter_button);

        // クリックリスナー設定
        numberTouchButton.setOnClickListener(this);
        openGlButton.setOnClickListener(this);
        othelloButton.setOnClickListener(this);
        twitterButton.setOnClickListener(this);
    }

    class AsyncRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            // Twitter4Jの設定を読み込む
            Configuration conf = ConfigurationContext.getInstance();
            // OAuth認証オブジェクト作成
            _oauth = new OAuthAuthorization(conf);
            // OAuth認証オブジェクトにconsumerKeyとconsumerSecretを設定
            _oauth.setOAuthConsumer(getString(R.string.consumer_key), getString(R.string.consumer_secret));
            // アプリの認証オブジェクト作成
            try {
                _req = _oauth.getOAuthRequestToken(getString(R.string.callback_uri));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            String _uri;
            _uri = _req.getAuthorizationURL();
            startActivityForResult(new Intent(Intent.ACTION_VIEW , Uri.parse(_uri)), 0);
            return null;
        }
    }
}
