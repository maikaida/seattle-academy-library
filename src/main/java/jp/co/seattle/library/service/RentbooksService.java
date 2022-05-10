
package jp.co.seattle.library.service;

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
	 * 貸出書籍を登録する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
	 */
	public void rentBook(int bookId) {

		String sql = "INSERT INTO rentbooks (book_id) VALUES (" + bookId + ")";

		jdbcTemplate.update(sql);

	}

	/**
	 * 貸出書籍情報を取得する
	 * 
	 * 
	 * 
	 * @param bookInfo 書籍情報
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

}
