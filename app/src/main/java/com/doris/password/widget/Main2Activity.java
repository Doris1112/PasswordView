package com.doris.password.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.doris.password.widget.library.PasswordNumberView;
import com.doris.password.widget.library.PasswordViewListener;

/**
 * @author Doris
 * @date 2018/11/3
 */
public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Doris";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        PasswordNumberView passwordNumberView = findViewById(R.id.password_number);
        passwordNumberView.setListener(new PasswordViewListener() {

            @Override
            public void onFail(String wrongNumber) {
                Log.d(TAG, "onFail: " + wrongNumber);
            }

            @Override
            public void onSuccess(String number) {
                Log.d(TAG, "onSuccess: " + number);
            }
        });
    }
}
