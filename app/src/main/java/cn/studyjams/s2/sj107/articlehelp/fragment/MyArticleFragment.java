package cn.studyjams.s2.sj107.articlehelp.fragment;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by dtkj_android on 2017/5/5.
 */

public class MyArticleFragment extends ArticleListFragment {
    private static MyArticleFragment myArticleFragment;

    public static MyArticleFragment getInstance() {
        if (myArticleFragment == null) {
            myArticleFragment = new MyArticleFragment();
        }
        return myArticleFragment;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Log.e(getUid()+"",getUid()+"");
        return databaseReference.child("user_articles").child(getUid()+"");
    }
}
