package cn.studyjams.s2.sj107.articlehelp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.studyjams.s2.sj107.articlehelp.model.Article;

/**
 * Created by dtkj_android on 2017/4/28.
 */

public class ArticlesAdapter extends ArrayAdapter<Article> {
    public ArticlesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_article, parent, false);
        }

        TextView articleTitleTv = (TextView) convertView.findViewById(R.id.tv_article_title);
        TextView articleContentTv = (TextView) convertView.findViewById(R.id.tv_article_content);
        TextView articleAuthorTv = (TextView) convertView.findViewById(R.id.tv_article_author);
        TextView createTimeTv = (TextView) convertView.findViewById(R.id.tv_create_time);

        Article article = getItem(position);
        articleTitleTv.setText(article.getArticleTitle() == null ? "-" : article.getArticleTitle());
        articleContentTv.setText(article.getArticleContent() == null ? "-" : article.getArticleContent());
        articleAuthorTv.setText(article.getAuthorName() == null ? "-" : article.getAuthorName());
        Date date = new Date(article.getCreateTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String format = simpleDateFormat.format(date);
        createTimeTv.setText(format);

        return convertView;
    }
}
