package com.example.noeglen.data;

import java.util.List;

public interface IArticleDAO {

    ArticleDTO getArticle(String collection, String articleTitle);
    List<ArticleDTO> getListOfArticles(String collection);

}
