package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.dto.RentBookInfo;
import jp.co.seattle.library.service.RentbooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class RentHistoryController {
	static Logger logger = LoggerFactory.getLogger(RentHistoryController.class);

	@Autowired
	private RentbooksService rentbooksService;
	
	/**
	 * 
	 *
	 * @param model
	 * @return 貸出履歴一覧に遷移
	 */
	@RequestMapping(value = "/rentHistory", method = RequestMethod.GET) 
	public String RentHistory(Locale locale, Model model) {
		
		List<RentBookInfo> rentLog = rentbooksService.rentHistoryList();
		model.addAttribute("rentLog", rentLog);
		
		return "rentHistory";
	}
	
}