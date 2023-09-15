package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	BoardMapper boardMapper;
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/boardList.do")
	public @ResponseBody List<Board> boardList(){
		List<Board> list = boardMapper.getLists();
		return list;
	}
	@PostMapping("/boardInsert.do")
	public @ResponseBody void  boardInsert(Board vo){
		boardMapper.boardInsert(vo);
	}
	@RequestMapping("/boardDelete.do")
	public @ResponseBody void boardDelete(@RequestParam("idx")int idx){
		boardMapper.boardDelete(idx);
	}
}
