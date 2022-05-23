package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentBookInfo;

@Configuration
public class RentRowMapper implements RowMapper<RentBookInfo> {
	
	 @Override
	    public RentBookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		 
		  RentBookInfo rentBookInfo = new RentBookInfo();
		  rentBookInfo.setBookId(rs.getInt("book_id"));
		  rentBookInfo.setTitle(rs.getString("title"));
		  rentBookInfo.setRentDate(rs.getDate("rentdate"));
		  rentBookInfo.setReturnDate(rs.getDate("returndate"));
		  return rentBookInfo;
	
	 	}

}
