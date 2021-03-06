package com.example.vladimir.remindme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.vladimir.remindme.Interfaces.RemindOnClickListener;
import com.example.vladimir.remindme.adapter.TabsFragmentAdapter;
import com.example.vladimir.remindme.fragment.HistoryFragment;
import com.example.vladimir.remindme.models.Item;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{

    public interface FragmentListener {
        void updateFragmentList();
        void deleteFragmentList();
    }

    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private FragmentListener fragmentListener;

    private RemindOnClickListener remindOnClickListener;

    private Realm realmDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        realmDb = Realm.getDefaultInstance();

        initChannels(this);
        initToolBar();
        initNavigationView();
        initTabs();
        initFab();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_create:
                        createItemAction();
                        break;

                    case R.id.delete_all:
                        deleteAllItemsAction();
                        break;
                }

                return true;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_lyout);

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle my_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(my_toggle);
        my_toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.first_item:
                        showAuthorActivity();
                }

                return true;
            }
        });
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItemAction();
            }
        });
    }

    public void setFragmentListener(FragmentListener listener) {
        fragmentListener = listener;
    }

    private void showAuthorActivity(){
        Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
        startActivity(intent);
    }

    private void createItemAction(){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivityForResult(intent, 13);
    }

    private void deleteAllItemsAction(){
        fragmentListener.deleteFragmentList();
    }


    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "RemindMe",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("RemindMe");
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realmDb.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 13:
                if(resultCode == RESULT_OK){
                    fragmentListener.updateFragmentList();
                }
                break;
        }
    }
}
