package unicorn.hust.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.model.User;
import unicorn.hust.myapplication.utils.Constant;
import unicorn.hust.myapplication.R;

public class LoginActivity extends BaseActivity {

    private OkHttpClient client = new OkHttpClient();
    Button btnLogin;
    TextView tvRegister;
    EditText edtUsername;
    EditText edtPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupUI() {
        findViewById();

        tvRegister.setText(Html.fromHtml("<u>Create an account</u>"));
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.finish();
                LoginActivity.this.startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void findViewById() {
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        edtUsername = findViewById(R.id.et_email);
        edtPassword = findViewById(R.id.et_password);
    }

    private void validate(String username, String password) {
        final String json = "{\"username\":\"" + username +
                "\",\"password\":\"" + password + "\"}";

        RequestBody body = RequestBody.create(json, Constant.JSON);

        final Request request = new Request.Builder()
                .url(Constant.URL_LOGIN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please login again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                checkResponse(response);
            }

        });
    }

    private void checkResponse(Response response) {
        final Gson gson = new Gson();
        final User user = gson.fromJson(response.body().charStream(), User.class);

        if (user.getType().equals("member")) {
            SharedPreferences sharedPreferences = LoginActivity.this
                    .getSharedPreferences(Constant.USER, LoginActivity.this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.USERNAME, user.getUsername());
            editor.putString(Constant.NAME, user.getName());
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            LoginActivity.this.finish();
            LoginActivity.this.startActivity(intent);
        } else {
            LoginActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(),
                            user.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
