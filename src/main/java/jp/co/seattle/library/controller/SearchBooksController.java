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

@Controller
public class SearchBooksController {
	static Logger logger = LoggerFactory.getLogger(SearchBooksController.class);

	@Autowired
	private BooksService BooksService;

	/**
	 * 書籍検索処理
	 * 
	 * 
	 * @param locale ロケール情報
	 * @param title タイトル
	 * @param model モデル
	 * @return ホーム画面に遷移
	 */
	@Transactional
	@RequestMapping(value = "/searchBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String SearchBook(Locale locale, @RequestParam("title") String title, Model model) {
		logger.info("Welcome SearchBooks.java! The client locale is {}.", locale);

		model.addAttribute("bookList", BooksService.searchBookList(title));
		return "home";

	}
}
