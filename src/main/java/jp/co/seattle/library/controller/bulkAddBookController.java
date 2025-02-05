package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class bulkAddBookController {
	final static Logger logger = LoggerFactory.getLogger(bulkAddBookController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * ログイン処理
	 *
	 * 
	 *
	 * @param model
	 * @return 一括登録画面に遷移
	 */
	@RequestMapping(value = "/bulkAddBook", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String bulkAddBook(Model model) {
		return "bulkAddBook";
	}

	/**
	 * 書籍情報を一括登録する
	 * 
	 * @param locale ロケール情報
	 * @param file   サムネイルファイル
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/bulkInsertBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String insertBook(Locale locale, @RequestParam("file") MultipartFile file, Model model) {
		logger.info("Welcome bulkAddBooks.java! The client locale is {}.", locale);

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

			String bookValue;
			int lineCount = 0;
			List<String> errorList = new ArrayList<String>();
			List<BookDetailsInfo> bookList = new ArrayList<BookDetailsInfo>();



			while ((bookValue = br.readLine()) != null) {
				String[] bookValues = bookValue.split(",", -1);
				BookDetailsInfo bookInfo = new BookDetailsInfo();

				lineCount++;

				Boolean digitNumberCheck = !bookValues[4].matches("^[0-9]{10}+$")
						&& !bookValues[4].matches("^[0-9]{13}+$");

				if ((bookValues[0].isEmpty() || bookValues[1].isEmpty() || bookValues[2].isEmpty()
						|| bookValues[3].isEmpty()) || (!bookValues[3].matches("^[0-9]{8}+$"))
						|| ((!bookValues[4].isEmpty() && digitNumberCheck))) {
					errorList.add(lineCount + "行目の書籍登録でエラーが発生しました");

				} else {

					bookInfo.setTitle(bookValues[0]);
					bookInfo.setAuthor(bookValues[1]);
					bookInfo.setPublisher(bookValues[2]);
					bookInfo.setPublishDate(bookValues[3]);
					bookInfo.setISBN(bookValues[4]);

					bookList.add(bookInfo);
				}

			}
			
			if (bookList.size() == 0) {
				errorList.add("csvに書籍情報がありません");
			}

			if (CollectionUtils.isEmpty(errorList)) {
				booksService.bulkAddBook(bookList);
				return "redirect:/home";

			} else {
				model.addAttribute("errorMessage", errorList);
				return "bulkAddBook";
			}

		} catch (Exception e) {
			List<String> errorList = new ArrayList<String>();
			errorList.add("ファイルを読み込めません");
			model.addAttribute("errorMessage", errorList);

			return "bulkAddBook";

		}

	}
}
