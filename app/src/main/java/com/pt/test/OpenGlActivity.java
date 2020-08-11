package com.pt.test;

import android.app.Activity;
import android.os.Bundle;

public class OpenGlActivity extends Activity {

    OpenGlSurfaceView myGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLView = new OpenGlSurfaceView(this);
        setContentView(myGLView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }
}
