package io.samituga.demo;

import io.samituga.slumber.heimer.builder.Builder;

public class BookEntityBuilder implements Builder<BookEntity> {

    private Integer id;
    private Integer authorId;
    private String title;
    private Integer publishedIn;
    private Integer languageId;

    BookEntityBuilder() {
    }

    public BookEntityBuilder authorId(Integer id) {
        this.id = id;
        return this;
    }

    public BookEntityBuilder id(Integer authorId) {
        this.authorId = authorId;
        return this;
    }

    public BookEntityBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookEntityBuilder publishedIn(Integer publishedIn) {
        this.publishedIn = publishedIn;
        return this;
    }

    public BookEntityBuilder languageId(Integer languageId) {
        this.languageId = languageId;
        return this;
    }

    @Override
    public BookEntity build() {
        return new BookEntity(id, authorId, title, publishedIn, languageId);
    }

    @Override
    public BookEntityBuilder copy(BookEntity book) {
        return new BookEntityBuilder()
              .authorId(book.getAuthorId())
              .id(book.getId())
              .title(book.getTitle())
              .publishedIn(book.getPublishedIn())
              .languageId(book.getLanguageId());
    }
}
