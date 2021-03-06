package com.bitacademy.myportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.myportal.repository.GuestbookVo;
import com.bitacademy.myportal.service.GuestbookService;

@RequestMapping("/guestbook")
@Controller
public class GuestbookController {
	@Autowired
	GuestbookService guestbookServiceImpl;
	
	// @ResponseBody	// 리턴된 문자열을 직접 응답에 출력
	// ResponseBody가 없을 경우 String을 리턴
	@RequestMapping({"", "/", "/list"}) // 여러가지 url 패턴에 반응할 수 있게
	public String list(Model model) {
		List<GuestbookVo> list = guestbookServiceImpl.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	// 방명록 저장
	// @ResponseBody
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute GuestbookVo vo) {
		boolean isSuccess = guestbookServiceImpl.writeMessage(vo);
		// 여기서는 성공 여부는 판단하지 않음
		return "redirect:/guestbook";
	}
	
	// 삭제 폼
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String deleteForm(@PathVariable Long no, Model model) {
		// 폼 안에 no를 히든값으로 받아야 무엇을 지울 지 알 수 있음
		model.addAttribute("no",no);
		return "guestbook/deleteform";
		
	}	
	      
	// 삭제 액션
	//@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deleteAction(@ModelAttribute GuestbookVo vo) {
		boolean isSuccess = guestbookServiceImpl.deleteMessage(vo);
		// ERROR 체크는 별도로 추가
		System.out.println("DELETE Result:" + isSuccess);
		return "redirect:/guestbook";
	}
	
	
}


