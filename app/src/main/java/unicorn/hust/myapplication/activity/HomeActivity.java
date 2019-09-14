package unicorn.hust.myapplication.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.fragment.FeedFragment;
import unicorn.hust.myapplication.fragment.LocationFragment;
import unicorn.hust.myapplication.fragment.ProfileFragment;

public class HomeActivity extends BaseActivity {
    final Fragment feedFragment = new FeedFragment();
    final Fragment profileFragment = new ProfileFragment();
    final Fragment locationFragment = new LocationFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment activeFragment = locationFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void setupUI() {
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav_view_home);

        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, feedFragment, "2")
                .hide(feedFragment)
                .commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, profileFragment, "3")
                .hide(profileFragment).commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, locationFragment, "1")
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_location:
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(locationFragment)
                                .commit();
                        activeFragment = locationFragment;
                        break;
                    case R.id.action_feed:
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(feedFragment).commit();
                        activeFragment = feedFragment;
                        break;
                    case R.id.action_user:
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(profileFragment).commit();
                        activeFragment = profileFragment;
                        break;
                }
                return true;
            }
        });
    }
}
