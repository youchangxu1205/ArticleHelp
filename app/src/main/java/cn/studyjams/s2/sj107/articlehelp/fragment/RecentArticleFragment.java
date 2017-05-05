package cn.studyjams.s2.sj107.articlehelp.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by dtkj_android on 2017/5/5.
 */

public class RecentArticleFragment extends ArticleListFragment {
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query query = databaseReference.child("articles").limitToLast(100);
        return query;
    }

    private static RecentArticleFragment recentArticleFragment;

    public static RecentArticleFragment getInstance() {
        if (recentArticleFragment == null) {
            recentArticleFragment = new RecentArticleFragment();
        }
        return recentArticleFragment;
    }
}
