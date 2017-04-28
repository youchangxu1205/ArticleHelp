package cn.studyjams.s2.sj107.articlehelp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.studyjams.s2.sj107.articlehelp.model.Article;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private LinearLayout mainContent;
    private NavigationView navigationView;
    private Toolbar appbar;
    private ListView listView;

    private FirebaseDatabase database;
    private DatabaseReference articlesReference;

    private ArticlesAdapter articlesAdapter;
    private List<Article> articles;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainContent = (LinearLayout) findViewById(R.id.main_content);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        appbar = (Toolbar) findViewById(R.id.appbar);
        listView = (ListView) findViewById(R.id.list_view);

        database = FirebaseDatabase.getInstance();
        articlesReference = database.getReference("articles");
        appbar.setTitle("首页");
        setSupportActionBar(appbar);
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
        attachDatabaseReadListener();
//        articlesReference.push().setValue(new Article("测试标题","测试内容","测试作者",new Date().getTime()));
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Article article = dataSnapshot.getValue(Article.class);
                    articlesAdapter.add(article);
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
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
