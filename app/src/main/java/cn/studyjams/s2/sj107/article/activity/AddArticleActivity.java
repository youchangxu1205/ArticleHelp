package cn.studyjams.s2.sj107.article.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.studyjams.s2.sj107.article.R;
import cn.studyjams.s2.sj107.article.model.Article;


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
    private DatabaseReference databaseReference;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        toolBar.setTitle("添加文章");
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
            Toast.makeText(this, "文章标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(articleContent)) {
            Toast.makeText(this, "文章内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog.setMessage("正在保存");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        String key = databaseReference.push().getKey();
        Article article = new Article(articleTitle, articleContent, currentUser.getUid(), new Date().getTime(), currentUser.getDisplayName(),key);
        Map<String, Object> articleMap = article.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/articles/" + key, articleMap);
        childUpdates.put("/user_articles/" + currentUser.getUid() + "/" + key, articleMap);
        databaseReference.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                } else {
                    Toast.makeText(AddArticleActivity.this, "文章保存失败", Toast.LENGTH_SHORT).show();
                    //TODO 保存到草稿中.保存到数据库中
                }
            }
        });

    }
}
