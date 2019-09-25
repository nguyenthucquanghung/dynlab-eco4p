package unicorn.hust.myapplication.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

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
import unicorn.hust.myapplication.model.EditAccountResponse;
import unicorn.hust.myapplication.model.RegisterResponse;
import unicorn.hust.myapplication.utils.Constant;

public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";
    Button btnLogout;
    Button btnChangePassword;
    ImageView ivEditName;
    ImageView ivEditDoB;
    EditText etName;
    EditText etDoB;
    String mPassword;
    final Calendar myCalendar = Calendar.getInstance();
    AlertDialog changeNameDialog;
    AlertDialog changePasswordDialog;
    AlertDialog changeDoBDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void setupUI() {
        findViewById();

        setData();

        setupChangeDoB();

        setupChangeName();

        setupChangePassword();
    }

    private void setupChangePassword() {
        changePasswordDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_change_password, null);

        final EditText etNewPassword = dialogView.findViewById(R.id.et_new_password);
        final EditText etNewPasswordConfirm = dialogView.findViewById(R.id.et_new_password_confirm);
        final EditText etPassword = dialogView.findViewById(R.id.edt_password);
        Button btnSave = dialogView.findViewById(R.id.buttonSubmit);
        Button btnCancel = dialogView.findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (validateChangePassword(
                        etNewPassword.getText().toString(),
                        etNewPasswordConfirm.getText().toString(),
                        etPassword.getText().toString()
                )) {
                    case 0:
                        saveNewPassword(etNewPassword.getText().toString());
                        break;
                    case 1:
                        Toast.makeText(SettingsActivity.this,
                                "Your new confirmed password and your new password must be the same!",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(SettingsActivity.this,
                                "Wrong password!",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        changePasswordDialog.setView(dialogView);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changePasswordDialog.show();
            }
        });
    }

    private void saveNewPassword(final String newPassword) {
        Toast.makeText(SettingsActivity.this, "Saving...", Toast.LENGTH_LONG).show();
        String username = sharedPreferences.getString(Constant.USERNAME, "");
        String json = "{\n" +
                "\t\"type\":\"changePassword\",\n" +
                "\t\"username\":\"" + username + "\",\n" +
                "\t\"password\":\"" + mPassword + "\",\n" +
                "\t\"newPassword\":\"" + newPassword + "\"\n" +
                "}";
        RequestBody body = RequestBody.create(json, Constant.JSON);
        final Request request = new Request.Builder()
                .url(Constant.URL_CHANGE_PASSWORD)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final RegisterResponse registerResponse = gson.fromJson(response.body().charStream(),
                        RegisterResponse.class);
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                registerResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                if (registerResponse.getType().equals("done")) {
                    editor = sharedPreferences.edit();
                    editor.putString(Constant.PASSWORD, newPassword);
                    editor.apply();
                    mPassword = newPassword;
                    changePasswordDialog.dismiss();
                }

            }
        });
    }

    private int validateChangePassword(String newPassword, String newPasswordConfirm, String password) {
        if ((newPassword.equals(newPasswordConfirm)) && password.equals(mPassword))
            return 0;
        else if (!(newPassword.equals(newPasswordConfirm)))
            return 1;
        else
            return 2;

    }

    private void setupChangeName() {
        changeNameDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_with_et, null);

        final EditText etNewName = dialogView.findViewById(R.id.edt_comment);
        final EditText etPassword = dialogView.findViewById(R.id.edt_password);
        Button btnSave = dialogView.findViewById(R.id.buttonSubmit);
        Button btnCancel = dialogView.findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNameDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (validate(
                        etNewName.getText().toString(),
                        etPassword.getText().toString()
                )) {
                    case 0:
                        saveName(etNewName.getText().toString());
                        break;
                    case 1:
                        Toast.makeText(SettingsActivity.this,
                                "New name must not equal to current name",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(SettingsActivity.this,
                                "Wrong password!",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        changeNameDialog.setView(dialogView);
        ivEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNameDialog.show();
            }
        });
    }

    private int validate(String newName, String confirmPassword) {
        if ((!newName.equals(etName.getText().toString())) && confirmPassword.equals(mPassword))
            return 0;
        else if (newName.equals(etName.getText().toString()))
            return 1;
        else
            return 2;
    }

    private void saveName(final String newName) {
        Toast.makeText(SettingsActivity.this, "Saving...", Toast.LENGTH_LONG).show();
        String username = sharedPreferences.getString(Constant.USERNAME, "");
        String dateOfBirth = sharedPreferences.getString(Constant.DOB, "");
        String json = "{\n" +
                "\t\"type\":\"changeInfo\",\n" +
                "\t\"username\":\"" + username + "\",\n" +
                "\t\"password\":\"" + mPassword + "\",\n" +
                "\t\"newName\":\"" + newName + "\",\n" +
                "\t\"newAge\":\"" + dateOfBirth + "\"\n" +
                "}";
        RequestBody body = RequestBody.create(json, Constant.JSON);
        final Request request = new Request.Builder()
                .url(Constant.URL_UPDATE_PROFILE)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                try {
                    final EditAccountResponse accountResponse = gson.fromJson(response.body().charStream(),
                            EditAccountResponse.class);
                    if (accountResponse.getType().equals("done") &&
                            accountResponse.getMessage()[0].equals("Done")) {
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),
                                        "Name changed!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        editor = sharedPreferences.edit();
                        editor.putString(Constant.NAME, newName);
                        editor.apply();
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                etName.setText(newName);
                            }
                        });
                        changeNameDialog.dismiss();
                    } else {
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),
                                        accountResponse.getMessage()[0],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (JsonSyntaxException e) {
                    final RegisterResponse accountResponse = gson.fromJson(response.body().charStream(),
                            RegisterResponse.class);
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(),
                                    accountResponse.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void setData() {
        sharedPreferences = this.getSharedPreferences(Constant.USER, this.MODE_PRIVATE);
        etName.setText(sharedPreferences.getString(Constant.NAME, "Jinny"));
        etDoB.setText(sharedPreferences.getString(Constant.DOB, "09/11/1999"));
        mPassword = sharedPreferences.getString(Constant.PASSWORD, "");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupChangeDoB() {
        changeDoBDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_date, null);

        final EditText etNewDoB = dialogView.findViewById(R.id.edt_comment);
        final EditText etPassword = dialogView.findViewById(R.id.edt_password);
        final ImageView ivEdit = dialogView.findViewById(R.id.iv_edit);

        etNewDoB.setText(sharedPreferences.getString(Constant.DOB, ""));

        Button btnSave = dialogView.findViewById(R.id.buttonSubmit);
        Button btnCancel = dialogView.findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDoBDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (validateDate(
                        etNewDoB.getText().toString(),
                        etPassword.getText().toString()
                )) {
                    case 0:
                        saveDoB(etNewDoB.getText().toString());
                        break;
                    case 1:
                        Toast.makeText(SettingsActivity.this,
                                "New date of birth must not equal to the current one",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(SettingsActivity.this,
                                "Wrong password!",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        changeDoBDialog.setView(dialogView);
        ivEditDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDoBDialog.show();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                String pickedDate = sdf.format(myCalendar.getTime());
                etNewDoB.setText(pickedDate);
            }

        };


        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SettingsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private int validateDate(String date, String password) {
        if ((!date.equals(etDoB.getText().toString())) && password.equals(mPassword))
            return 0;
        else if (date.equals(etDoB.getText().toString()))
            return 1;
        else
            return 2;
    }

    private void findViewById() {
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnLogout = findViewById(R.id.btn_logout);
        ivEditDoB = findViewById(R.id.iv_edit_dob);
        ivEditName = findViewById(R.id.iv_edit_name);
        etName = findViewById(R.id.et_name);
        etDoB = findViewById(R.id.et_dob);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void saveDoB(final String pickedDate) {
        Toast.makeText(SettingsActivity.this, "Saving...", Toast.LENGTH_LONG).show();
        String username = sharedPreferences.getString(Constant.USERNAME, "");
        String name = sharedPreferences.getString(Constant.NAME, "");
        String json = "{\n" +
                "\t\"type\":\"changeInfo\",\n" +
                "\t\"username\":\"" + username + "\",\n" +
                "\t\"password\":\"" + mPassword + "\",\n" +
                "\t\"newName\":\"" + name + "\",\n" +
                "\t\"newAge\":\"" + pickedDate + "\"\n" +
                "}";
        RequestBody body = RequestBody.create(json, Constant.JSON);
        final Request request = new Request.Builder()
                .url(Constant.URL_UPDATE_PROFILE)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(),
                                "Server error! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                try {
                    final Gson gson = new Gson();
                    final EditAccountResponse accountResponse = gson.fromJson(response.body().charStream(),
                            EditAccountResponse.class);

                    if (accountResponse.getType().equals("done")) {
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),
                                        "Date of birth changed!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        editor = sharedPreferences.edit();
                        editor.putString(Constant.DOB, pickedDate);
                        editor.apply();
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                etDoB.setText(pickedDate);
                                changeDoBDialog.dismiss();
                            }
                        });


                    } else {
                        SettingsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(),
                                        accountResponse.getMessage()[0],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (JsonSyntaxException e) {
                    final Gson gson = new Gson();
                    final RegisterResponse accountResponse = gson.fromJson(response.body().charStream(),
                            RegisterResponse.class);
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(),
                                    accountResponse.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
