package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id, title, author, publisher, publish_date, isbn, explain, thumbnail_url FROM books ORDER BY title ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql = "SELECT * FROM books where id =" + bookId;

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}
	
	/**
	 * booksTBとrentbooksTBを外部結合する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public String getBooksInfo(int bookId) {
		
		String sql = "SELECT rent_id FROM books LEFT OUTER JOIN rentbooks ON books.id = rentbooks.book_id where id =" + bookId;
		String bookStatus = jdbcTemplate.queryForObject(sql, String.class);
		
		return bookStatus;
	}
	
	

	/**
	 * 書籍を登録する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void registBook(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title, author,publisher, publish_date, isbn, explain, thumbnail_name, thumbnail_url, reg_date, upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" 
				+ bookInfo.getAuthor() + "','" 
				+ bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" 
				+ bookInfo.getIsbn() + "','" 
				+ bookInfo.getExplain() + "','"
				+ bookInfo.getThumbnailName() + "','" 
				+ bookInfo.getThumbnailUrl() + "'," + "now()," + "now())";

		jdbcTemplate.update(sql);
		
	}
	

	/**
	 * 書籍を一括登録する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void bulkAddBook(List<BookDetailsInfo> bookList) {
		
		for(BookDetailsInfo bookInfo : bookList) {

		String sql = "INSERT INTO books (title, author,publisher, publish_date, isbn, explain, thumbnail_url, reg_date, upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" 
				+ bookInfo.getAuthor() + "','" 
				+ bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','"
				+ bookInfo.getIsbn() + "','" 
				+ bookInfo.getExplain() + "','"
				+ bookInfo.getThumbnailUrl() + "',"
				+ "now()," + "now())";
		
		
		jdbcTemplate.update(sql);
		
		}
	
	}	
	
	
	
	
	/**
	 * 書籍を更新する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		
		String sql = "UPDATE books SET (title, author,publisher, publish_date, isbn, explain, thumbnail_name, thumbnail_url, upd_date) = ('" 
				+ bookInfo.getTitle() + "','" 
				+ bookInfo.getAuthor() + "','" 
				+ bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" 
				+ bookInfo.getIsbn() + "','" 
				+ bookInfo.getExplain() + "','"
				+ bookInfo.getThumbnailName() + "','" 
				+ bookInfo.getThumbnailUrl() + "'," + "now()) WHERE id = " + bookInfo.getBookId();
	
		jdbcTemplate.update(sql);
	}
	
	

	/**
	 * 書籍を削除する
	 *
	 * @param bookId 書籍ID
	 */	
	public void deleteBook(int bookId) {

		String sql = "DELETE FROM books where id = " + bookId;

		jdbcTemplate.update(sql);
	}
	
}
