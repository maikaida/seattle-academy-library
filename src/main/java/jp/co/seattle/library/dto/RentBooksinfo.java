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
public class RentBooksinfo {

    private Date rentDate;

    public RentBooksinfo() {

    }

    public RentBooksinfo(Date rentDate, Date returnDate) {
        this.rentDate = rentDate;
    }

}