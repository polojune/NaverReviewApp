package com.cos.review;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

// http://hare.kr/222065140009
// https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=%EA%B0%A4%EB%9F%AD%EC%8B%9C20&sm=tab_pge&srchby=all&st=sim&where=post&start=41
// 섬네일, 블로그주소, 제목, 날짜
public class NaverBlogCrawTest {

	@Test
	public void 날짜_파싱() {

		String today = LocalDate.now().toString();
		System.out.println(today);

		String date1 = "2일 전";
		if (date1.contains("일 전")) {
			char minusDay = date1.charAt(0);
			String date1Temp = LocalDate.now().minusDays(Integer.parseInt(minusDay + ""))
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			System.out.println(date1Temp);
		}
		String date2 = "2시간 전";
		if (date2.contains("시간 전")) {
			String date2Temp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			System.out.println(date2Temp);
		}

		String date3 = "어제";
		if (date3.contains("어제")) {
			String date3Temp = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			System.out.println(date3Temp);
		}

		String date4 = "2020.08.01.";
		char date4End = date4.charAt(date4.length() - 1);
		if (date4End == '.') {
			String date4Temp = date4.substring(0, date4.length() - 1);
			date4Temp = date4Temp.replace(".", "-");
			System.out.println(date4Temp);
		}

	}

//	//@Test
//	public void 제품리뷰_크롤링() {
//		String keyword = "갤럭시20";
//		String url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="+keyword+"&oquery=%EA%B0%A4%EB%9F%AD%EC%8B%9C+%EB%85%B8%ED%8A%B8+20&tqi=Uz88Lsp0YihsssC770RssssstG8-057521";
//		try {
//			Document doc = Jsoup.connect(url).get();
//			Elements els = doc.select(".blog .sh_blog_top a");
//			for (Element el : els) {
//				System.out.println(el.attr("title"));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(url);
//	}
}