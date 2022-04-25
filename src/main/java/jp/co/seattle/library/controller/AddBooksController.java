package jp.co.seattle.library.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class AddBooksController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

	@Autowired
	private BooksService booksService;

	@Autowired
	private ThumbnailService thumbnailService;

	@RequestMapping(value = "/addBook", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String login(Model model) {
		return "addBook";
	}

	/**
	 * 書籍情報を登録する
	 * 
	 * @param locale    ロケール情報
	 * @param title     書籍名
	 * @param author    著者名
	 * @param publisher 出版社
	 * @param file      サムネイルファイル
	 * @param model     モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/insertBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String insertBook(Locale locale, @RequestParam("title") String title, @RequestParam("author") String author,
			@RequestParam("publisher") String publisher, @RequestParam("publishDate") String publishDate,
			@RequestParam("ISBN") String ISBN, @RequestParam("explain") String explain,
			@RequestParam("thumbnail") MultipartFile file, Model model) {
		logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

		// パラメータで受け取った書籍情報をDtoに格納する。
		BookDetailsInfo bookInfo = new BookDetailsInfo();
		bookInfo.setTitle(title);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPublishDate(publishDate);
		bookInfo.setISBN(ISBN);
		bookInfo.setExplain(explain);

		List<String> errorMessage = new ArrayList<String>();

		// クライアントのファイルシステムにある元のファイル名を設定する
		String thumbnail = file.getOriginalFilename();

		if (!file.isEmpty()) {
			try {
				// サムネイル画像をアップロード
				String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
				// URLを取得
				String thumbnailUrl = thumbnailService.getURL(fileName);

				bookInfo.setThumbnailName(fileName);
				bookInfo.setThumbnailUrl(thumbnailUrl);

			} catch (Exception e) {

				// 異常終了時の処理
				logger.error("サムネイルアップロードでエラー発生", e);
				model.addAttribute("bookDetailsInfo", bookInfo);
				return "addBook";
			}
		}



		// 出版日・ISBN入力バリデーションチェック
		if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || publishDate.isEmpty()) {
			errorMessage.add("<br>必須項目を入力してください</br>");
			// return "addBook";
		}

		if (!(publishDate.matches("^[0-9]{8}+$"))) {
			errorMessage.add("<br>出版日は半角数字のYYYYMMDD形式で入力してください</br>");

//			return "addBook";	
		}

		Boolean digitNumberCheck = !ISBN.matches("^[0-9]{10}+$") && !ISBN.matches("^[0-9]{13}+$");
		
		System.out.println();
		
		if (!ISBN.isEmpty() && digitNumberCheck) {
			errorMessage.add("<br>ISBNの桁数または半角数字が正しくありません</br>");

		}

		if (errorMessage.size() > 0) {
			model.addAttribute("errorMessage", errorMessage);
			model.addAttribute("bookInfo", bookInfo);
			return "addBook";
		}

		// 書籍情報を新規登録する
		booksService.registBook(bookInfo);
		model.addAttribute("resultMessage", "登録完了");

		// TODO 登録した書籍の詳細情報を表示するように実装
		// 詳細画面に遷移する
		model.addAttribute("bookInfo", booksService.getBookList());
		return "details";

	}
	
//	booksService.getBookList();
//	model.addAttribute("bookDetailsInfo", bookInfo);
}
