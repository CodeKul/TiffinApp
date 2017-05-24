package com.codekul.uithread.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codekul.uithread.R;

public class LoginActivity extends AppCompatActivity implements  LoginView{

    EditText etUserName,etUserPassword;
    Button btnLogin;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        presenter = new LoginPresenter();
    }

    private void initViews() {
        etUserName= (EditText) findViewById(R.id.et_user_name);
        etUserPassword= (EditText) findViewById(R.id.et_user_password);
        btnLogin= (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            presenter.login(etUserName.getText().toString(), etUserPassword.getText().toString());
        });
    }

    @Override
    public void onInvalidUerName(String msg) {
        etUserName.setError(msg);
    }

    @Override
    public void onInvalidPassword(String msg) {
        etUserPassword.setError(msg);
    }
}
