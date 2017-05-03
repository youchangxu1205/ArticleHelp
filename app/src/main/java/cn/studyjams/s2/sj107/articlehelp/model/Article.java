package cn.studyjams.s2.sj107.articlehelp.model;

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

    public Article() {
    }

    public Article(String articleTitle, String articleContent, String authorId, Long createTime, String authorName) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.authorId = authorId;
        this.createTime = createTime;
        this.authorName = authorName;
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

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("authorId", authorId);
        result.put("authorName", authorName);
        result.put("articleTitle", articleTitle);
        result.put("articleContent", articleContent);
        result.put("createTime", createTime);

        return result;
    }
}
