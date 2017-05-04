package cn.studyjams.s2.sj107.articlehelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s2.sj107.articlehelp.model.Article;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;


    //    private ArticlesAdapter articlesAdapter;
    private ArticlesRecycleAdapter articlesRecycleAdapter;
    private List<Article> articles;


    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference articlesReference;
    private ChildEventListener mChildEventListener;

    public static final int TO_ADD_ARTICLE = 1;
    public static final int TO_USER_DETAIL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        articlesReference = FirebaseDatabase.getInstance().getReference("articles");
        //获取最新的50条数据
//        query = articlesReference.limitToLast(50);
        toolBar.setTitle("首页");
        setSupportActionBar(toolBar);

        articles = new ArrayList<>();
        articlesRecycleAdapter = new ArticlesRecycleAdapter(this, articles);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(articlesRecycleAdapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //TODO 切换内容
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
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
        attachDatabaseReadListener();


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


    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        articles.clear();
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Article article = dataSnapshot.getValue(Article.class);
                    articles.add(0, article);
                    articlesRecycleAdapter.notifyDataSetChanged();
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildChanged", dataSnapshot.getKey());
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.i("onChildRemoved", dataSnapshot.getKey());
                    articles.remove(dataSnapshot.getValue(Article.class));
                    articlesRecycleAdapter.notifyDataSetChanged();
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildMoved", dataSnapshot.getKey());
                }

                public void onCancelled(DatabaseError databaseError) {
                    Log.i("onCancelled", databaseError.getDetails());
                }
            };
            articlesReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            articlesReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

}
