package io.samituga.demo;

import io.samituga.demo.jooq.tables.Book;
import io.samituga.demo.jooq.tables.records.BookRecord;
import io.samituga.slumber.malz.repository.AbstractEntityRepository;
import org.jooq.ConnectionProvider;
import org.jooq.Table;
import org.jooq.TableField;

public class BookRepository extends AbstractEntityRepository<Integer, BookEntity, BookRecord> {

    private static final Table<BookRecord> TABLE = Book.BOOK;
    private static final TableField<BookRecord, Integer> ID_FIELD = Book.BOOK.ID;

    public BookRepository(ConnectionProvider connectionProvider) {
        super(connectionProvider, TABLE, ID_FIELD);
    }

    @Override
    protected BookEntity toEntity(BookRecord record) {
        return BookEntity.builder()
              .authorId(record.getAuthorId())
              .id(record.getId())
              .title(record.getTitle())
              .publishedIn(record.getPublishedIn())
              .languageId(record.getLanguageId())
              .build();
    }

    @Override
    protected BookRecord toRecord(BookEntity entity) {
        return new BookRecord(
              entity.getId(),
              entity.getAuthorId(),
              entity.getTitle(),
              entity.getPublishedIn(),
              entity.getLanguageId()
        );
    }
}
