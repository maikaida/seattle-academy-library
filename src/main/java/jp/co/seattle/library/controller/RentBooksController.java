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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentbooksService;

@Controller
public class RentBooksController {

	final static Logger logger = LoggerFactory.getLogger(RentBooksController.class);
	@Autowired
	private RentbooksService rentbooksService;
	@Autowired
	private BooksService booksService;

	/**
	 * 対象書籍を借りる
	 * 
	 * @param locale ロケール情報
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String RentBook(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {
		logger.info("Welcome RentBooks.java! The client locale is {}.", locale);

		/**
		 * 貸出情報を取得する
		 * 
		 * 
		 */
		Boolean rentLog = rentbooksService.selectedRentBookInfo(bookId);
		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		
		if(!rentLog) {
			rentbooksService.rentBook(bookId);
			
		} else {
			rentbooksService.updateRentBook(bookId);
			model.addAttribute("errorMessages", "貸出し済みです。");
		}
		

		/**
		 * 貸出ステータスを表示する
		 * 
		 * @return
		 */
		String bookStatus = booksService.getBooksInfo(bookId);

		model.addAttribute("resultMessage", bookStatus);
		return "details";
	}

}
