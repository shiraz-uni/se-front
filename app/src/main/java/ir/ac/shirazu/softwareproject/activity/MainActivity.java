package ir.ac.shirazu.softwareproject.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.fragment.ListFragment;
import ir.ac.shirazu.softwareproject.fragment.WeeklyFragment;

public class MainActivity extends AppCompatActivity {

    private static final int WEEKLY_INDEX = 0;
    private static final int LIST_INDEX = 1;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ImageView creditIncreasingBtn;

    public static boolean isInDormitory() {
        return isInDormitory;
    }

    private static boolean isInDormitory = true;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onClickListeners();
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        creditIncreasingBtn = findViewById(R.id.credit_increasing_iv);
        setSupportActionBar(mToolbar);
        showCredit();
        setTabLayout();
        setNavigationDrawer();
        updateFragmentContainer(WEEKLY_INDEX);
    }

    private void updateFragmentContainer(int index) {
        Fragment fragment = null;
        switch (index) {
            case WEEKLY_INDEX:
                fragment = new WeeklyFragment();
                break;

            case LIST_INDEX:
                fragment = new ListFragment();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    private void setNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.bank_transactions_report:
                        break;
                    case R.id.purchase_reserve_report:
                        break;
                    case R.id.credits_report:
                        break;
                    case R.id.exit:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    private void showCredit() {
        Resources res = getResources();
        int creditValue = 7000;
        String text = res.getString(R.string.credit, creditValue);
        TextView textView = findViewById(R.id.credit);
        textView.setText(text);
    }

    private void setTabLayout() {
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tablayout_weekly));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tablayout_list));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case LIST_INDEX:
                        updateFragmentContainer(LIST_INDEX);
                        break;

                    case WEEKLY_INDEX:
                        updateFragmentContainer(WEEKLY_INDEX);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void onClickListeners() {
        creditIncreasingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
