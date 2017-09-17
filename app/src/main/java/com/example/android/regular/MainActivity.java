package com.example.android.regular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userNameView, passWordView;

    Button logInButton, clearButton;

    ProgressBar mProgressBar;

    User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameView = (EditText) findViewById(R.id.user_name_editText);

        passWordView = (EditText) findViewById(R.id.passWord_editText);

        logInButton = (Button) findViewById(R.id.button_log_in);

        clearButton = (Button) findViewById(R.id.button_clear);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


/**
 * the login button will simulate the function when user tries to log in, the sever will take some time
 * to pull the data, and retun if the user log in or not
 *
 * first the progress bar will show in UI thread, then thread sleeps 2 seconds.
 *
 * then it will check if user's login info is correct or not,
 *
 * the toast messgae will pop up and progress bar will be gone(this stpe will be called from the UI Thread)
 */
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (userNameView.getText().toString().equals("sun") && passWordView.getText().toString().equals("123")) {

                            user1 = new User();
                            user1.setPassWord(passWordView.getText().toString());
                            user1.setUser_name(userNameView.getText().toString());
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "Wrong username or passwrod", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                }).start();

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNameView.getText() != null || passWordView.getText() != null) {
                    userNameView.setText("");
                    passWordView.setText("");
                }
            }
        });
    }
}
