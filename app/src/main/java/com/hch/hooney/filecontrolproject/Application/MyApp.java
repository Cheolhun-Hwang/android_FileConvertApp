package com.hch.hooney.filecontrolproject.Application;

import android.Manifest;
import android.app.Application;

public class MyApp extends Application {
    public static final String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public MyApp() {
    }


}
