package unicorn.hust.myapplication.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.model.RegisterResponse;
import unicorn.hust.myapplication.model.User;
import unicorn.hust.myapplication.utils.Constant;

public class RegisterActivity extends BaseActivity {
    EditText etUsername;
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    EditText etPassword2;
    EditText etPassword2Confirm;
    EditText etAge;
    EditText etPhoneNumber;
    Button btnRegister;
    final Calendar myCalendar = Calendar.getInstance();
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupUI() {
        findViewById();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        etAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
        etAge.setText(sdf.format(myCalendar.getTime()));
    }

    private void register() {
        if (
                etUsername.getText().toString().equals("") |
                        etPhoneNumber.getText().toString().equals("") |
                        etName.getText().toString().equals("") |
                        etAge.getText().toString().equals("") |
                        etPassword2Confirm.getText().toString().equals("") |
                        etPassword2.getText().toString().equals("") |
                        etPasswordConfirm.getText().toString().equals("") |
                        etPassword.getText().toString().equals("") |
                        etEmail.getText().toString().equals("")
        ) {
            Toast.makeText(RegisterActivity.this, "Please finish the register form",
                    Toast.LENGTH_SHORT).show();
        } else if (
                !etPassword2Confirm.getText().toString().equals(etPassword2.getText().toString()) |
                        !etPasswordConfirm.getText().toString().equals(etPassword.getText().toString())
        ) {
            Toast.makeText(RegisterActivity.this,
                    "Your confirmed password and your password must be the same",
                    Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RegisterActivity.this,
                    "Creating your account ...", Toast.LENGTH_LONG).show();
            sendRequest(
                    etUsername.getText().toString(),
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    etPassword.getText().toString(),
                    etPassword2.getText().toString(),
                    etAge.getText().toString(),
                    etPhoneNumber.getText().toString()
            );
        }
    }

    private void sendRequest(
            final String username,
            final String name,
            final String email,
            final String password,
            String password2,
            final String dateOfBirth,
            String phoneNumber
    ) {
        final String json =
                "{\"username\":\"" +                            username +
                "\",\"password\":\"" +                          password +
                        "\",\"email\":\"" +                     email +
                        "\",\"name\":\"" +                      name +
                        "\",\"age\":\"" +                       dateOfBirth +
                        "\",\"phoneNumber\":\"" +               phoneNumber +
                        "\",\"imgProfile\":\""  +               "none" +
                        "\",\"lv2password\":\"" +               password2 +
                        "\"}";

        RequestBody body = RequestBody.create(json, Constant.JSON);

        final Request request = new Request.Builder()
                .url(Constant.URL_REGISTER)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please register again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final RegisterResponse registerResponse = gson.fromJson(response.body().charStream(),
                        RegisterResponse.class);
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                registerResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                if (registerResponse.getType().equals("done")) {
                    Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("age", dateOfBirth);
                    RegisterActivity.this.finish();
                    RegisterActivity.this.startActivity(intent);
                }
            }

        });
    }

    private void findViewById() {
        etUsername = findViewById(R.id.et_username);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPassword2 = findViewById(R.id.et_password_2);
        etPasswordConfirm = findViewById(R.id.et_password_confirm);
        etPassword2Confirm = findViewById(R.id.et_password_confirm_2);
        etAge = findViewById(R.id.et_age);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnRegister = findViewById(R.id.btn_register);
    }
}
