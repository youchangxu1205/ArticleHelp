package cn.studyjams.s2.sj107.article.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dtkj_android on 2017/4/28.
 */

public class Article {
    private String articleTitle;
    private String articleContent;
    private String authorId;
    private Long createTime;
    private String authorName;
    private String articleId;

    public Article() {
    }

    public Article(String articleTitle, String articleContent, String authorId, Long createTime, String authorName,String articleId) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.authorId = authorId;
        this.createTime = createTime;
        this.authorName = authorName;
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("authorId", authorId);
        result.put("authorName", authorName);
        result.put("articleTitle", articleTitle);
        result.put("articleContent", articleContent);
        result.put("createTime", createTime);
        result.put("articleId", articleId);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return articleId.equals(article.articleId);

    }

    @Override
    public int hashCode() {
        return articleId.hashCode();
    }
}
