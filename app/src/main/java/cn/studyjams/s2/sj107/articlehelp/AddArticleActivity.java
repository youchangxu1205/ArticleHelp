package cn.studyjams.s2.sj107.articlehelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s2.sj107.articlehelp.model.Article;


/**
 * Created by dtkj_android on 2017/5/2.
 */

public class AddArticleActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_article_title)
    TextInputEditText etArticleTitle;
    @BindView(R.id.et_article_content)
    TextInputEditText etArticleContent;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference articlesReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        articlesReference = database.getReference("articles");

        toolBar.setTitle("添加文章");
        setSupportActionBar(toolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_article) {
            addArticle();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addArticle() {
        String articleTitle = etArticleTitle.getText().toString().trim();
        String articleContent = etArticleContent.getText().toString().trim();

        if (TextUtils.isEmpty(articleTitle)) {

            return;
        }

        if (TextUtils.isEmpty(articleContent)) {

            return;
        }

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        articlesReference.push().setValue(new Article(articleTitle, articleContent, currentUser.getProviderId(), new Date().getTime(), currentUser.getDisplayName()));


    }
}
