
package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.RentBookInfo;
import jp.co.seattle.library.rowMapper.RentRowMapper;

/**
 * 書籍サービス
 * 
 * rentbooksテーブルに関する処理を実装する
 */
@Service
public class RentbooksService {
	final static Logger logger = LoggerFactory.getLogger(RentbooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * rentbooksテーブルに貸出書籍を登録する
	 * 
	 * 
	 * 
	 * @param bookId 書籍Id
	 */
	public void rentBook(int bookId) {

		String sql = "INSERT INTO rentbooks (book_id, rentDate) VALUES (" + bookId + "," + "now())";

		jdbcTemplate.update(sql);

	}

	/**
	 * 貸出書籍情報を取得する
	 * 
	 * 
	 * 
	 * @param bookId 書籍Id
	 */
	public RentBookInfo selectedRentBookInfo(int bookId) {

		String sql = "SELECT * FROM rentbooks where book_id =" + bookId;

		try {
			RentBookInfo rentBookInfo = jdbcTemplate.queryForObject(sql, new RentRowMapper());
			return rentBookInfo;

		} catch (Exception e) {

			return null;
		}

	}
	
	/**
	 * 貸出履歴を取得する
	 * 
	 * 
	 * 
	 * @param bookId 書籍Id
	 */
	public List<RentBookInfo> rentHistoryList() {
		
		List<RentBookInfo> rentHistoryList = jdbcTemplate.query("SELECT title, rentDate, returnDate FROM rentbooks INNER JOIN books ON rentbooks.book_id = books.id", new RentRowMapper());
		
		return rentHistoryList;
	}

	/**
	 * rentbooksテーブルの貸出書籍を更新する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void updateRentBook(int bookId) {
		
		String sql = "UPDATE rentbooks SET (rentDate, returnDate) = (now(), null) where book_id =" + bookId;
		jdbcTemplate.update(sql);
	}
	

	/**
	 * rentbooksテーブルの返却日を更新する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void updateReturnBoook(int bookId) {

		String sql = "UPDATE rentbooks SET (rentDate, returnDate) = (null, now()) where book_id =" + bookId; 
		jdbcTemplate.update(sql);
	}

}
