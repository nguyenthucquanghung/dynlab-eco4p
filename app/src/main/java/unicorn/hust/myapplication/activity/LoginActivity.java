package unicorn.hust.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.utils.Constant;
import unicorn.hust.myapplication.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupUI() {
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvRegister = findViewById(R.id.tv_register);
        final EditText edtUsername = findViewById(R.id.et_email);
        final EditText edtPassword = findViewById(R.id.et_password);

        tvRegister.setText(Html.fromHtml("<u>Create an account</u>"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (validate(username, password)) {
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Constant.USER, LoginActivity.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.LOGIN, username);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.finish();
                    LoginActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! Wrong password or this account doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validate(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) return true;
        else return false;
    }
}
