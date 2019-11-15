package com.example.spring02.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.model.board.dto.BoardDTO;
import com.example.spring02.service.board.BoardService;
import com.example.spring02.service.board.Pager;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute BoardDTO dto,
						HttpSession session)throws Exception{
		//로그인한 사용자의 아이디
		String writer = (String)session.getAttribute("userid");
		dto.setWriter(writer);
		//레코드가 저장됨
		boardService.create(dto);
		//목록 갱신
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("write.do")
	public String write() {
		return "board/write";
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search_option,
			@RequestParam(defaultValue="") String keyword) throws Exception{
		int count = boardService.countArticle(search_option, keyword);
		Pager pager = new Pager(count,curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();
		List<BoardDTO> list = boardService.listAll(search_option, keyword, start, end);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list");
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		map.put("count", count);
		map.put("keyword", keyword);
		map.put("pager", pager);
		map.put("search_option", search_option);
		map.put("keyword",keyword); 
		mav.addObject("map", map);
		return mav;
	}
	
	@RequestMapping(value="view.do", method=RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno,
							@RequestParam int curPage,
							@RequestParam String search_option,
							@RequestParam String keyword,
							HttpSession session) throws Exception{
		boardService.increaseViewcnt(bno, session);	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/view");
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("curPage", curPage);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		
		return mav;
	}
	
	@RequestMapping("update.do")
	public String update(BoardDTO dto) throws Exception{
		boardService.update(dto);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") int bno){
		return boardService.getAttach(bno);
	}
	
	@RequestMapping("delete.do")
	public String delete(int bno) throws Exception{
		boardService.delete(bno);
		return "redirect:/board/list.do";
	}
}
