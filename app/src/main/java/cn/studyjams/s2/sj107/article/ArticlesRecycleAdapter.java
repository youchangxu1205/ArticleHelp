package cn.studyjams.s2.sj107.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.studyjams.s2.sj107.article.model.Article;

/**
 * Created by dtkj_android on 2017/5/4.
 */

public class ArticlesRecycleAdapter extends RecyclerView.Adapter<ArticlesRecycleAdapter.ViewHolder> {

    private Context context;
    private List<Article> articleList;


    public ArticlesRecycleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public Article getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = getItem(position);

        holder.articleTitleTv.setText(article.getArticleTitle());
        holder.articleContentTv.setText(article.getArticleContent());
        holder.articleAuthorTv.setText(article.getAuthorName());
        Date date = new Date(article.getCreateTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String format = simpleDateFormat.format(date);
        holder.createTimeTv.setText(format);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView articleTitleTv;
        TextView articleContentTv;
        TextView articleAuthorTv;
        TextView createTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            articleTitleTv = (TextView) itemView.findViewById(R.id.tv_article_title);
            articleContentTv = (TextView) itemView.findViewById(R.id.tv_article_content);
            articleAuthorTv = (TextView) itemView.findViewById(R.id.tv_article_author);
            createTimeTv = (TextView) itemView.findViewById(R.id.tv_create_time);
        }
    }
}
