package unicorn.hust.myapplication.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import unicorn.hust.myapplication.base.BaseActivity;
import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.fragment.FeedFragment;
import unicorn.hust.myapplication.fragment.LocationFragment;
import unicorn.hust.myapplication.fragment.MessagesFragment;
import unicorn.hust.myapplication.fragment.NotificationsFragment;
import unicorn.hust.myapplication.fragment.ProfileFragment;

public class HomeActivity extends BaseActivity {
    final Fragment feedFragment = new FeedFragment();
    final Fragment profileFragment = new ProfileFragment();
    final Fragment locationFragment = new LocationFragment();
    final Fragment messagesFragment = new MessagesFragment();
    final Fragment notificationsFragment = new NotificationsFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment activeFragment = feedFragment;
    Toolbar mToolbar;
    ActionBar mActionBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void setupUI() {
        findViewById();
        setUpBottomNavigation();
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notifications:
                if (activeFragment == notificationsFragment){
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(feedFragment)
                            .commit();
                    activeFragment = feedFragment;
                } else {
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(notificationsFragment)
                            .commit();
                    activeFragment = notificationsFragment;
                }
                break;
            case R.id.menu_messenger:
                if (activeFragment == messagesFragment) {
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(feedFragment)
                            .commit();
                    activeFragment = feedFragment;
                } else {
                    fragmentManager
                            .beginTransaction()
                            .hide(activeFragment)
                            .show(messagesFragment)
                            .commit();
                    activeFragment = messagesFragment;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViewById() {
        mToolbar = findViewById(R.id.toolbar);
    }

    private void setUpBottomNavigation(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav_view_home);

        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, feedFragment, "1")
                .commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, profileFragment, "3")
                .hide(profileFragment)
                .commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, locationFragment, "2")
                .hide(locationFragment)
                .commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, notificationsFragment, "4")
                .hide(notificationsFragment)
                .commit();
        fragmentManager
                .beginTransaction()
                .add(R.id.home_container, messagesFragment, "5")
                .hide(messagesFragment)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_location:
                        mActionBar.hide();
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(locationFragment)
                                .commit();
                        activeFragment = locationFragment;
                        break;
                    case R.id.action_feed:
                        mActionBar.show();
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(feedFragment)
                                .commit();
                        activeFragment = feedFragment;
                        break;
                    case R.id.action_user:
                        mActionBar.hide();
                        fragmentManager
                                .beginTransaction()
                                .hide(activeFragment)
                                .show(profileFragment)
                                .commit();
                        activeFragment = profileFragment;
                        break;
                }
                return true;
            }
        });
    }
}
