package cn.studyjams.s2.sj107.articlehelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户详情页
 * Created by dtkj_android on 2017/5/4.
 */

public class UserDetailActivity extends AppCompatActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_display_name)
    TextView tvDisplayName;
    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        String displayName = currentUser.getDisplayName();

        toolBar.setTitle("用户详情");
        setSupportActionBar(toolBar);

        //进入该页面肯定已经登录


        currentUser.getUid();
        currentUser.getEmail();
        currentUser.getPhotoUrl();
        storage = FirebaseStorage.getInstance();

    }
}
