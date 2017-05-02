package cn.studyjams.s2.sj107.articlehelp.model;

/**
 * Created by dtkj_android on 2017/4/28.
 */

public class Article {
    private String articleTitle;
    private String articleContent;
    private String authorId;
    private Long creatTime;

    private String authorName;

    public Article() {
    }

    public Article(String articleTitle, String articleContent, String authorId, Long creatTime, String authorName) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.authorId = authorId;
        this.creatTime = creatTime;
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

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
