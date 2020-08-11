package com.pt.test;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OpenGlSurfaceView extends GLSurfaceView {

    OpenGlRenderer myRenderer;

    public OpenGlSurfaceView(Context context) {
        super(context);
        myRenderer = new OpenGlRenderer();
        setRenderer(myRenderer);
    }

}