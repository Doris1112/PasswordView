package com.doris.password.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.doris.password.widget.library.PasswordBlendView;
import com.doris.password.widget.library.PasswordViewListener;

/**
 * @author Doris
 * @date 2018/11/3
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Doris";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PasswordBlendView passwordNumberView = findViewById(R.id.password_number);
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

    public void onViewClicked(View view){
        startActivity(new Intent(this, Main2Activity.class));
    }
}
