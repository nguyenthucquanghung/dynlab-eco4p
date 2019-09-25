package unicorn.hust.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.model.RegisterResponse;
import unicorn.hust.myapplication.utils.Constant;

public class VerifyActivity extends BaseActivity {
    private static final String TAG = "VerifyActivity";
    EditText etCode;
    Button btnVerify;
    TextView tvResendCode;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify;
    }

    @Override
    protected void setupUI() {
        findViewById();

        tvResendCode.setText(Html.fromHtml("<u>Send code again</u>"));

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().toString().equals("")) {
                    Toast.makeText(VerifyActivity.this,
                            "Please enter your verification code!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerifyActivity.this,
                            "Verifying...", Toast.LENGTH_LONG).show();
                    final String json =
                            "{\"username\":\"" + getIntent().getExtras().getString("username") +
                                    "\",\"password\":\"" + getIntent().getExtras().getString("password") +
                                    "\",\"key\":\"" + etCode.getText().toString() +
                                    "\"}";

                    sendRequest(json);
                }
            }
        });

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String json =
                        "{\"username\":\"" + getIntent().getExtras().getString("username") +
                                "\",\"password\":\"" + getIntent().getExtras().getString("password") +
                                "\",\"email\":\"" + getIntent().getExtras().getString("email") +
                                "\"}";
                Toast.makeText(VerifyActivity.this,
                        "Sending code to your email ...", Toast.LENGTH_LONG).show();
                resendCode(json);
            }
        });
    }

    private void resendCode(String json) {
        RequestBody body = RequestBody.create(json, Constant.JSON);

        final Request request = new Request.Builder()
                .url(Constant.URL_RESEND)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                VerifyActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please send code again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final RegisterResponse registerResponse = gson.fromJson(response.body().charStream(),
                        RegisterResponse.class);

                VerifyActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                registerResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void sendRequest(String json) {
        RequestBody body = RequestBody.create(json, Constant.JSON);

        final Request request = new Request.Builder()
                .url(Constant.URL_VERIFY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                VerifyActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please send code again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final RegisterResponse registerResponse = gson.fromJson(response.body().charStream(),
                        RegisterResponse.class);

                VerifyActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                registerResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                if (registerResponse.getType().equals("Done")) {
                    SharedPreferences sharedPreferences = VerifyActivity.this
                            .getSharedPreferences(Constant.USER, VerifyActivity.this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constant.LOGIN, true);
                    editor.putString(Constant.USERNAME, getIntent().getExtras().getString("username"));
                    editor.putString(Constant.NAME, getIntent().getExtras().getString("name"));
                    editor.putString(Constant.DOB, getIntent().getExtras().getString("age"));
                    editor.putString(Constant.PASSWORD, getIntent().getExtras().getString("password"));
                    editor.apply();

                    Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
                    VerifyActivity.this.finish();
                    VerifyActivity.this.startActivity(intent);
                }
            }
        });
    }

    private void findViewById() {
        etCode = findViewById(R.id.et_code);
        btnVerify = findViewById(R.id.btn_verify);
        tvResendCode = findViewById(R.id.tv_resend_code);
    }

}
