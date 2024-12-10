package com.example;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PrintWriter logger = new PrintWriter(System.out, false);
        
        DocumentManager manager = new DocumentManager();

        // create new documents

        DocumentManager.Author author = DocumentManager.Author.builder()
                .name("Netflix Inc.")
                .build();

        DocumentManager.Document document = DocumentManager.Document.builder()
                .title("Netflix Fin Report 2016 Q3")
                .content("Income of the company consists of ...")
                .author(author)
                .build();
        
        DocumentManager.Document document2 = DocumentManager.Document.builder()
                .title("Netflix Fin Report 2024 Q4")
                .content("Income of the company includes of ...")
                .author(author)
                .build();

        // save the doc instance 1
        DocumentManager.Document saved = manager.save(document);
        logger.println("\n[Saved Document - 1]: " + saved);

        // save the doc instance 2
        DocumentManager.Document saved2 = manager.save(document2);
        logger.println("\n[Saved Document - 2]: " + saved2);
        
        // search for docs
        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder()
                .titlePrefixes(List.of("2016"))
                .build();
        
        // find by document id
        logger.println("\n[Search Results - docId:existing]: " + manager.findById(document2.getId()).isPresent());

        // find by document id
        logger.println("\n[Search Results - docId:non-existing]: " + manager.findById("dksjdejjd").isPresent());

        // title
        logger.println("\n[Search Results - title:existing]: " + manager.search(request));

        // title
        request.setTitlePrefixes(List.of("2024"));
        logger.println("\n[Search Results - title:existing]: " + manager.search(request));
        
        // title
        request.setTitlePrefixes(List.of("Netflix"));
        logger.println("\n[Search Results - title:existing]: " + manager.search(request));

        // title
        request.setTitlePrefixes(List.of("2019"));
        logger.println("\n[Search Results - title:non-existing]: " + manager.search(request));

        request.setTitlePrefixes(null);
        
        // content
        request.setContainsContents(List.of("tax", "include"));
        logger.println("\n[Search Results - contents]: " + manager.search(request));
        
        // author id
        request.setContainsContents(null);
        request.setAuthorIds(List.of(saved2.getAuthor().getId()));
        logger.println("\n[Search Results - authorIds]: " + manager.search(request));

        // createdTo
        request.setCreatedTo(Instant.now());
        logger.println("\n[Search Results - createdTo]: " + manager.search(request));
        
        // createdFrom
        request.setCreatedFrom(Instant.now().minusMillis(100000));
        logger.println("\n[Search Results - createdFrom]: " + manager.search(request));
        
        // request instance is empty
        request = DocumentManager.SearchRequest.builder().build();
        logger.println("\n[Search Results - request is empty]: " + manager.search(request));

        // request instance is null
        request = null;
        logger.println("\n[Search Results - request is null]: " + manager.search(request));

        logger.flush();
        logger.close();
    }
}
