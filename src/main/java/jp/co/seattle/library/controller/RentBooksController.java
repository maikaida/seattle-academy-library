package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.RentBookInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentbooksService;

@Controller
public class RentBooksController {

	final static Logger logger = LoggerFactory.getLogger(RentBooksController.class);
	@Autowired
	private RentbooksService RentbooksService;
	@Autowired
	private BooksService BooksService;

	@Transactional
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String RentBook(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {
		logger.info("Welcome RentBooks.java! The client locale is {}.", locale);

		RentBookInfo selectedRentBookInfo = RentbooksService.selectedRentBookInfo(bookId);

		
		if (selectedRentBookInfo == null) {
			RentbooksService.rentBook(bookId);
			model.addAttribute("bookDetailsInfo", BooksService.getBookInfo(bookId));

			return "details";
			
		} else {
			model.addAttribute("errorMessages", "貸出し済みです。");
			model.addAttribute("bookDetailsInfo", BooksService.getBookInfo(bookId));
			return "details";
	
		}
	}
}
