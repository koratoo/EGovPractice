package kr.board.mapper;


import org.apache.ibatis.annotations.Mapper;

import kr.board.entity.Member;

@Mapper
public interface MemberMapper {	 
	public Member registerCheck(String memID);
	public int register(Member m);
}

