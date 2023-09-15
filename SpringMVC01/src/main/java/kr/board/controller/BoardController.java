package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	private BoardMapper mapper;
	 
	// /boardList.do
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		List<Board> list = mapper.getLists();
		model.addAttribute("list", list);
		return "boardList";
	}
	@GetMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm";
	}
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) {
		mapper.boardInsert(vo);
		return "redirect:/boardList.do";
	}
	@GetMapping("/boardContent.do")
	public String boardContent(int idx, Model model) { // ?idx=6
		Board vo=mapper.boardContent(idx);
		// 조회수 증가
		mapper.boardCount(idx);
		model.addAttribute("vo", vo); // ${vo.idx}...
		return "boardContent"; // boardContent.jsp
	}
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) { // ?idx=6
		mapper.boardDelete(idx); //삭제		
		return "redirect:/boardList.do";
	}	
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo=mapper.boardContent(idx);
		model.addAttribute("vo", vo);		
		return "boardUpdate"; // boardUpdate.jsp
	}
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) { // idx, title, content
		mapper.boardUpdate(vo); // 수정		
		return "redirect:/boardList.do";
	}
}
