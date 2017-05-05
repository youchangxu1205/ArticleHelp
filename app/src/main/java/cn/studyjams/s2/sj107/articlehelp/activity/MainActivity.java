package cn.studyjams.s2.sj107.articlehelp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s2.sj107.articlehelp.R;
import cn.studyjams.s2.sj107.articlehelp.fragment.MyArticleFragment;
import cn.studyjams.s2.sj107.articlehelp.fragment.RecentArticleFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String RECENT_ARTICLE_FRAGMENT = "RECENT_ARTICLE_FRAGMENT";
    private static final String MY_ARTICLE_FRAGMENT = "MY_ARTICLE_FRAGMENT";
    private static final String SELECT_FRAGMENT = "SELECT_FRAGMENT";
    private String CURRENT_FRAGMENT = RECENT_ARTICLE_FRAGMENT;

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionBtn;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private FirebaseAuth mFirebaseAuth;

    public static final int TO_ADD_ARTICLE = 1;
    public static final int TO_USER_DETAIL = 2;
    private FragmentManager supportFragmentManager;
    private String currentFragmentTag;
    private RecentArticleFragment recentArticleFragment;
    private MyArticleFragment myArticleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolBar.setTitle("首页");
        setSupportActionBar(toolBar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close);

        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        supportFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString(SELECT_FRAGMENT);
            recentArticleFragment = (RecentArticleFragment) supportFragmentManager.findFragmentByTag(RECENT_ARTICLE_FRAGMENT);
            myArticleFragment = (MyArticleFragment) supportFragmentManager.findFragmentByTag(MY_ARTICLE_FRAGMENT);
        } else {
            recentArticleFragment = RecentArticleFragment.getInstance();
            myArticleFragment = MyArticleFragment.getInstance();
        }


        supportFragmentManager.beginTransaction().add(R.id.fragment_container, recentArticleFragment, RECENT_ARTICLE_FRAGMENT)
                .add(R.id.fragment_container, myArticleFragment, MY_ARTICLE_FRAGMENT)
                .show(recentArticleFragment).commit();


        final ActionBar supportActionBar = getSupportActionBar();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawers();

                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_item_articles:
                        fragmentTransaction.hide( myArticleFragment).show(recentArticleFragment);
                        supportActionBar.setTitle("首页");
                        CURRENT_FRAGMENT = RECENT_ARTICLE_FRAGMENT;
                        break;
                    case R.id.navigation_item_my_articles:
                        fragmentTransaction.hide(recentArticleFragment).show( myArticleFragment);
                        supportActionBar.setTitle("我的文章");
                        CURRENT_FRAGMENT = MY_ARTICLE_FRAGMENT;
                        break;
                }
                fragmentTransaction.commit();
                return false;
            }
        });


        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.iv_avatar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启用户详情页面
                FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    toLoginActivity(TO_USER_DETAIL);
                } else {
                    toUserDetailActivity();
                }
            }
        });

        floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启添加文章界面
                FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    toLoginActivity(TO_ADD_ARTICLE);
                } else {
                    toAddArticleActivity();
                }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECT_FRAGMENT, CURRENT_FRAGMENT);
    }

    private void toUserDetailActivity() {
        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
    }

    private void toAddArticleActivity() {
        Intent intent = new Intent(this, AddArticleActivity.class);
        startActivity(intent);
    }

    private void toLoginActivity(int code) {
        Toast.makeText(this, "只有登录用户才可以发表文章,请先登录", Toast.LENGTH_SHORT).show();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setProviders(
                                AuthUI.EMAIL_PROVIDER)
                        .build(),
                code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Sign-in succeeded, set up the UI
            Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
            if (requestCode == TO_ADD_ARTICLE) {
                toAddArticleActivity();
            } else if (requestCode == TO_USER_DETAIL) {
                toUserDetailActivity();
            }
        } else if (resultCode == RESULT_CANCELED) {
            // Sign in was canceled by the user, finish the activity
            Toast.makeText(this, "登录被取消了", Toast.LENGTH_SHORT).show();
        }
    }

}
