package unicorn.hust.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.utils.Constant;
import unicorn.hust.myapplication.R;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupUI() {
        //check if this device has been logged in
        SharedPreferences sharedPref = this.getSharedPreferences(Constant.USER, MainActivity.this.MODE_PRIVATE);
        boolean loginState = sharedPref.getBoolean(Constant.LOGIN, false);
        if (!loginState) {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            this.finish();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }
}
