package io.samituga.demo;

import io.samituga.slumber.malz.model.Entity;

public class BookEntity extends Entity<Integer> {

    private final Integer id;
    private final Integer authorId;
    private final String title;
    private final Integer publishedIn;
    private final Integer languageId;

    BookEntity(Integer id, Integer authorId, String title, Integer publishedIn, Integer languageId) {
        super(id);
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.publishedIn = publishedIn;
        this.languageId = languageId;
    }

    public static BookEntityBuilder builder() {
        return new BookEntityBuilder();
    }

    public Integer getId() {
        return id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublishedIn() {
        return publishedIn;
    }

    public Integer getLanguageId() {
        return languageId;
    }
}
