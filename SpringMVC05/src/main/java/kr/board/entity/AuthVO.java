package kr.board.entity;

import lombok.Data;

@Data
public class AuthVO {

	private int no;								
	private String memID;							//회원아이디
	private String auth;                            //회원번호
}
