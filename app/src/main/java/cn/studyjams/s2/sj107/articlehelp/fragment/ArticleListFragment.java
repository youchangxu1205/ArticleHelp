package cn.studyjams.s2.sj107.articlehelp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.studyjams.s2.sj107.articlehelp.R;
import cn.studyjams.s2.sj107.articlehelp.model.Article;
import cn.studyjams.s2.sj107.articlehelp.viewholder.ArticleViewHolder;

/**
 * Created by dtkj_android on 2017/5/5.
 */

public abstract class ArticleListFragment extends Fragment {


    @BindView(R.id.article_recycle_view)
    RecyclerView articleRecycleView;
    Unbinder unbinder;
    private DatabaseReference mDatabase;
    private LinearLayoutManager layoutManager;
    private FirebaseRecyclerAdapter<Article, ArticleViewHolder> firebaseRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        articleRecycleView.setHasFixedSize(true);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        articleRecycleView.setLayoutManager(layoutManager);
        final Query articleQuery = getQuery(mDatabase);
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Article, ArticleViewHolder>(Article.class, R.layout.item_article, ArticleViewHolder.class, articleQuery) {

            @Override
            protected void populateViewHolder(ArticleViewHolder viewHolder, Article model, int position) {
                DatabaseReference articleRef = getRef(position);

                String articleRefKey = articleRef.getKey();
                viewHolder.articleTitleTv.setText(model.getArticleTitle());
                viewHolder.articleContentTv.setText(model.getArticleContent());
                viewHolder.articleAuthorTv.setText(model.getAuthorName());
                Date date = new Date(model.getCreateTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                String format = simpleDateFormat.format(date);
                viewHolder.createTimeTv.setText(format);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });

            }
        };
        articleRecycleView.setAdapter(firebaseRecyclerAdapter);
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public String getUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }
}
