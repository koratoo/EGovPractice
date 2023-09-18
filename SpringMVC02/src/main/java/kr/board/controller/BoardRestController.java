package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/all")
	public List<Board> boardList(){
		List<Board> list = boardMapper.getLists();
		return list;
	}
	@PostMapping("/new")
	public void  boardInsert(Board vo){
		boardMapper.boardInsert(vo);
	}
	@DeleteMapping("/{idx}")
	public void boardDelete(@PathVariable("idx")int idx){
		boardMapper.boardDelete(idx);
	}
	@PutMapping("/update")
	public void boardUpdate(@RequestBody Board vo) {
		boardMapper.boardUpdate(vo);
	}
	@GetMapping("/{idx}")
	public Board boardContent(@PathVariable("idx")int idx) {
		Board vo = boardMapper.boardContent(idx);
		return vo;
	}
	@PutMapping("/count/{idx}")
	public Board boardCount(@PathVariable("idx")int idx) {
		boardMapper.boardCount(idx);
		Board vo = boardMapper.boardContent(idx);
		return vo;
	}
}
