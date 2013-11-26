package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private EditText user_name_et, password_et;
    private Button login_bt;
    private Context context;
    private TextView login_err_tv;
    private String user_name, user_password;
    private Intent intent;
    Bundle user_data;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        login_bt = (Button) findViewById(R.id.login_bt);
        user_name_et = (EditText) findViewById(R.id.userName_eT);
        password_et = (EditText) findViewById(R.id.password_eT);
        login_err_tv = (TextView) findViewById(R.id.login_err_tv);

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = user_name_et.getText().toString();
                user_password = password_et.getText().toString();
                if (user_name.equals("") || user_password.equals(""))  {
                    login_err_tv.setVisibility(View.VISIBLE);
                }
                else  {
                    login_err_tv.setVisibility(View.INVISIBLE);
                    intent = new Intent(context, ChatActivity.class);
                    user_data = new Bundle();
                    user_data.putString("user_name", user_name);
                    user_data.putString("user_password", user_password);
                    intent.putExtras(user_data);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
