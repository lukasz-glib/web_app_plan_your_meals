package pl.files.dao;

import pl.files.exception.NotFoundException;
import pl.files.model.Book;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    // ZAPYTANIA SQL
    private static final String CREATE_BOOK_QUERY = "INSERT INTO book(title,author,isbn) VALUES (?,?,?);";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book where id = ?;";
    private static final String FIND_ALL_BOOKS_QUERY = "SELECT * FROM book;";
    private static final String READ_BOOK_QUERY = "SELECT * from book where id = ?;";
    private static final String UPDATE_BOOK_QUERY = "UPDATE	book SET title = ? , author = ?, isbn = ? WHERE	id = ?;";

    /**
     * Get book by id
     *
     * @param bookId
     * @return
     */
    public Book read(Integer bookId) {
        Book book = new Book();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(BookDao.READ_BOOK_QUERY,true)
        ) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    book.setId(resultSet.getInt("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setIsbn(resultSet.getString("isbn"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;

    }

    /**
     * Return all books
     *
     * @return
     */
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(BookDao.FIND_ALL_BOOKS_QUERY,true);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book bookToAdd = new Book();
                bookToAdd.setId(resultSet.getInt("id"));
                bookToAdd.setTitle(resultSet.getString("title"));
                bookToAdd.setAuthor(resultSet.getString("author"));
                bookToAdd.setIsbn(resultSet.getString("isbn"));
                bookList.add(bookToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;

    }

    /**
     * Create book
     *
     * @param book
     * @return
     */
    public Book create(Book book) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(BookDao.CREATE_BOOK_QUERY,true)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    book.setId(generatedKeys.getInt(1));
                    return book;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove book by id
     *
     * @param bookId
     */
    public void delete(Integer bookId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(BookDao.DELETE_BOOK_QUERY,true)) {
            preparedStatement.setInt(1, bookId);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Update book
     *
     * @param book
     */
    public void update(Book book) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(BookDao.UPDATE_BOOK_QUERY,true)) {
            preparedStatement.setInt(4, book.getId());
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}