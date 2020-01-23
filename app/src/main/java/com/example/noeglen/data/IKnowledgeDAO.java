package com.example.noeglen.data;

import java.util.List;

/**
 * Interface for håndering af data overførslen af databasen når det tales om artikler
 */

public interface IKnowledgeDAO {

    KnowledgeDTO getArticle(String collection, String articleTitle);
    List<KnowledgeDTO> getListOfArticles(String collection);

}
