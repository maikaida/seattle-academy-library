package jp.co.seattle.library.dto;

import java.sql.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 貸出し書籍情報格納DTO
 *
 */
@Configuration
@Data
public class RentBookInfo {

	private String title;
    private Date rentDate;
    private Date returnDate;

    public RentBookInfo() {

    }

    public RentBookInfo(String title, Date rentDate, Date returnDate) {
    	this.title = title;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

}