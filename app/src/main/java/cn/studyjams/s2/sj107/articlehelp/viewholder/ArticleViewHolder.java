package cn.studyjams.s2.sj107.articlehelp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.studyjams.s2.sj107.articlehelp.R;

/**
 * Created by dtkj_android on 2017/5/5.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    public TextView articleTitleTv;
    public TextView articleContentTv;
    public TextView articleAuthorTv;
    public TextView createTimeTv;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        articleTitleTv = (TextView) itemView.findViewById(R.id.tv_article_title);
        articleContentTv = (TextView) itemView.findViewById(R.id.tv_article_content);
        articleAuthorTv = (TextView) itemView.findViewById(R.id.tv_article_author);
        createTimeTv = (TextView) itemView.findViewById(R.id.tv_create_time);
    }
}
