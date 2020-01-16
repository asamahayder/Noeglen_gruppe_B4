package com.example.noeglen.data;

import java.util.List;

public interface IKnowledgeDAO {

    KnowledgeDTO getArticle(String collection, String articleTitle);
    List<KnowledgeDTO> getListOfArticles(String collection);

}
