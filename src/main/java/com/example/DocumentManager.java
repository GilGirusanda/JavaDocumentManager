package com.example;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * For implement this task focus on clear code, and make this solution as simple readable as possible
 * Don't worry about performance, concurrency, etc
 * You can use in Memory collection for sore data
 * <p>
 * Please, don't change class name, and signature for methods save, search, findById
 * Implementations should be in a single class
 * This class could be auto tested
 */
public class DocumentManager {

    private final Map<String, Document> database = new ConcurrentHashMap<>();

    /**
     * Implementation of this method should upsert the document to your storage
     * And generate unique id if it does not exist, don't change [created] field
     *
     * @param document - document content and author data
     * @return saved document
     */
    public Document save(Document document) {
        if (document.getId() == null) {
            document.setId(UUID.randomUUID().toString());
            
            if (document.getAuthor() == null) {
                document.setAuthor(new Author(UUID.randomUUID().toString(), "Unknown author"));
            } else if (document.getAuthor().getId() == null) {
                document.getAuthor().setId(UUID.randomUUID().toString());
            }

            document.setCreated(Instant.now());
        }
        
        database.put(document.getId(), document);
        return document;
    }

    /**
     * Implementation this method should find documents which match with request
     *
     * @param request - search request, each field could be null
     * @return list matched documents
     */
    public List<Document> search(SearchRequest request) {
        if (request == null) {
            return Collections.emptyList();
        }

        // if (!Objects.isNull(request.getCreatedFrom())) {
        //     resultSet.removeIf((document) -> document.getCreated().isBefore(request.getCreatedFrom()));
        // }

        // if (!Objects.isNull(request.getCreatedTo())) {
        //     resultSet.removeIf((document) -> document.getCreated().isAfter(request.getCreatedTo()));
        // }

        // if (!Objects.isNull(request.getAuthorIds())) {
        //     resultSet.removeIf((document) -> document.getId() == null || 
        //         !request.getAuthorIds().contains(document.getAuthor().getId()));
        // }

        // if (!Objects.isNull(request.getContainsContents())) {
        //     resultSet.removeIf((document) -> request.getContainsContents().stream()
        //         .noneMatch((content) -> document.getContent() != null && 
        //             document.getContent().contains(content)));
        // }

        // if (!Objects.isNull(request.getTitlePrefixes())) {
        //     resultSet.removeIf((document) -> request.getTitlePrefixes().stream()
        //         .noneMatch((pref) -> document.getTitle() != null && document.getTitle().startsWith(pref)));
        // }

        // return resultSet;
        
        Instant createdFrom = request.getCreatedFrom();
        Instant createdTo = request.getCreatedTo();
        List<String> authorIds = request.getAuthorIds();
        List<String> containsContents = request.getContainsContents();
        List<String> titlePrefixes = request.getTitlePrefixes();

        return database.values().stream()
            .filter(document -> createdFrom == null || !document.getCreated().isBefore(createdFrom) || document.getCreated().equals(createdFrom))
            .filter(document -> createdTo == null || !document.getCreated().isAfter(createdTo) || document.getCreated().equals(createdTo))
            .filter(document -> authorIds == null || 
                    (document.getAuthor() != null && authorIds.contains(document.getAuthor().getId())))
            .filter(document -> titlePrefixes == null || 
                    titlePrefixes.stream().anyMatch(prefix -> 
                        document.getTitle() != null && document.getTitle().contains(prefix)))
            .filter(document -> containsContents == null || 
                    containsContents.stream().anyMatch(content -> 
                        document.getContent() != null && document.getContent().contains(content)))
            .toList();
    }

    /**
     * Implementation this method should find document by id
     *
     * @param id - document id
     * @return optional document
     */
    public Optional<Document> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Data
    @Builder
    public static class SearchRequest {
        private List<String> titlePrefixes;
        private List<String> containsContents;
        private List<String> authorIds;
        private Instant createdFrom;
        private Instant createdTo;
    }

    @Data
    @Builder
    public static class Document {
        private String id;
        private String title;
        private String content;
        private Author author;
        private Instant created;
    }

    @Data
    @Builder
    public static class Author {
        private String id;
        private String name;
    }
}