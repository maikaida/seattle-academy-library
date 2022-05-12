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
		 
		  rentBookInfo.setRentId(rs.getInt("rent_id"));
		  rentBookInfo.setBookId(rs.getInt("book_id"));
		  return rentBookInfo;
	
	 	}

}
