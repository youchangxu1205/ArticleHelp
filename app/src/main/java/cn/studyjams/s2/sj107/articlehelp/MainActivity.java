package cn.studyjams.s2.sj107.articlehelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s2.sj107.articlehelp.model.Article;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionBtn;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private FirebaseDatabase database;
    private DatabaseReference articlesReference;

    private ArticlesAdapter articlesAdapter;
    private List<Article> articles;
    private ChildEventListener mChildEventListener;


    private FirebaseAuth mFirebaseAuth;

    public static final int RC_SIGN_IN = 1;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        articlesReference = database.getReference("articles");
        query = articlesReference.orderByChild("createTime");


        toolBar.setTitle("首页");
        setSupportActionBar(toolBar);
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(this, R.layout.item_article, articles);
        listView.setAdapter(articlesAdapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //TODO 切换内容
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        floatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启添加文章界面
                FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    toLoginActivity();
                } else {
                    toAddArticleActivity();
                }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        attachDatabaseReadListener();
    }

    private void toAddArticleActivity() {
        Intent intent = new Intent(this, AddArticleActivity.class);
        startActivity(intent);

    }

    private void toLoginActivity() {
        Toast.makeText(this, "只有登录用户才可以发表文章,请先登录", Toast.LENGTH_SHORT).show();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setProviders(
                                AuthUI.EMAIL_PROVIDER)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "登录被取消了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Article article = dataSnapshot.getValue(Article.class);
                    articlesAdapter.add(article);
                    //TODO 缓存到数据库中
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                }
            };
            query.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    private void detachDatabaseReadListener() {

        if (mChildEventListener != null) {
            query.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        articlesAdapter.clear();
        detachDatabaseReadListener();
    }
}
