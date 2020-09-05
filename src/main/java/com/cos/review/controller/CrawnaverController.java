package com.cos.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.review.model.Product;
import com.cos.review.model.SearchKeyword;
import com.cos.review.repository.ProductRepository;
import com.cos.review.repository.SearchKeywordRepository;
import com.cos.review.util.CrawNaverBlog;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CrawnaverController {
    
	private final ProductRepository productRepository;
	private final SearchKeywordRepository searchKeywordRepository;
	
	@GetMapping("/searchKeyword")
	public @ResponseBody List<SearchKeyword> searchKeyword(){
		 System.out.println("searchKeyword 호출됨");
		 return searchKeywordRepository.findAll();
	}
	
	@GetMapping("/product")
	public @ResponseBody List<Product> product(){
		int keywordId = searchKeywordRepository.findAll().get(0).getId(); 
		return productRepository.findByKeywordId(keywordId);
	}
	
	@GetMapping("/product/{keywordId}")
	public @ResponseBody List<Product> productKeyword(@PathVariable int keywordId){
		return productRepository.findByKeywordId(keywordId);
	}
	
	@GetMapping("/craw/naver")
	public String crawNaver(Model model) {
		  model.addAttribute("keywords", searchKeywordRepository.findAll());
		return "craw_naver";
	}
	
	@GetMapping("/craw/list")  
	public String crawList(Model model) {
		 model.addAttribute("keywords", searchKeywordRepository.findAll()); 
		 return "craw_list";
	}
	@GetMapping("/craw/clear")
	public String crawClear() { 
		return "craw_clear";
	}
	
	@PostMapping("/craw/naver/proc")
	public @ResponseBody String crawNaverProc(String keyword) {
		
		System.out.println(keyword);
		List<Product> products = new CrawNaverBlog().startAllCraw(keyword);
		SearchKeyword searchKeywordEntity = searchKeywordRepository.findByKeyword(keyword);
		
		for (Product product : products) {
			   product.setKeyword(searchKeywordEntity);
		}
		
		
		productRepository.saveAll(products);
		return "성공";
	}
	   
	 @PostMapping("/craw/keyword/proc")
	 public String crawkeywordProc(String keyword) {
		    SearchKeyword entity = SearchKeyword.builder()
		    		    .keyword(keyword)
		    		    .build();
		    searchKeywordRepository.save(entity);
		    return "redirect:/craw/list"; 
	 } 
	 
	 @DeleteMapping("/craw/keyword/delete/{id}")
	 public ResponseEntity<?> crawKeywordDelete(@PathVariable int id){
		  searchKeywordRepository.deleteById(id);
		  return new ResponseEntity<String>("ok", HttpStatus.OK);
	 }
}
