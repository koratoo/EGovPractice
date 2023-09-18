package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@RestController
@RequestMapping("/board")
public class BoardRestController {


	@Autowired
	BoardMapper boardMapper;
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/boardList.do")
	public List<Board> boardList(){
		List<Board> list = boardMapper.getLists();
		return list;
	}
	@PostMapping("/new")
	public void  boardInsert(Board vo){
		boardMapper.boardInsert(vo);
	}
	@RequestMapping("/boardDelete.do")
	public void boardDelete(@RequestParam("idx")int idx){
		boardMapper.boardDelete(idx);
	}
	@RequestMapping("/boardUpdate.do")
	public void boardUpdate(Board vo) {
		boardMapper.boardUpdate(vo);
	}
	@RequestMapping("/boardContent.do")
	public Board boardContent(int idx) {
		Board vo = boardMapper.boardContent(idx);
		return vo;
	}
	@RequestMapping("/boardCount.do")
	public Board boardCount(int idx) {
		boardMapper.boardCount(idx);
		Board vo = boardMapper.boardContent(idx);
		return vo;
	}
}
