package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentBooksinfo;

@Configuration
public class RentBooksinfoRowMapper implements RowMapper<RentBooksinfo> {
	
	 @Override
	    public RentBooksinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		 
		 RentBooksinfo rentBookInfo = new RentBooksinfo();
		  
		  rentBookInfo.setRentDate(rs.getDate("rentdate"));
		  return rentBookInfo;
	
	 	}

}
