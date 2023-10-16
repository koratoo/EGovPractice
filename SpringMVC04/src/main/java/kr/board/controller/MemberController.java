package kr.board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	MemberMapper memberMapper;
	
	@RequestMapping("/memJoin.do")
	public String memJoin() {
		return "member/join";
	}
	
	@RequestMapping("/memRegisterCheck.do")
	public @ResponseBody int memRegisterCheck(@RequestParam("memID")String memID) {
		Member m = memberMapper.registerCheck(memID);
		if(m != null || memID.equals("")) {
			return 0;//이미 존재하는 회원, 입력불가
		}
		return 1;//사용가능한 아이디
	}
	
	@RequestMapping("/memRegister.do")
	public String memRegister(Member m,String memPassword1, String memPassword2,
							  RedirectAttributes rttr,HttpSession session) {
		
		if(m.getMemID()==null || m.getMemID().equals("")||
				   memPassword1 ==null|| memPassword1.equals("")||
				   memPassword2 ==null|| memPassword2.equals("")||
				   m.getMemName()==null || m.getMemName().equals("")||
				   m.getMemAge()==0 ||
				   m.getMemGender()==null || m.getMemGender().equals("")||
				   m.getMemEmail()==null || m.getMemEmail().equals("")
				   ) {
					//누락메세지를 가지고 가기 => 객체 바인딩
					rttr.addFlashAttribute("msgType", "누락 메세지");
					rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
					return "redirect:/memJoin.do";
				}
		
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
			return "redirect:/memJoin.do";
		}
		log.info(m.toString());
		
		m.setMemProfile("");//사진이미지는 없다는 의미 ""
		//회원을 테이블에 저장하기
		int result = memberMapper.register(m);
		if(result==1) {
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
			session.setAttribute("mvo", m);//${!empty mvo}
			return "redirect:/";
		}else {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
			return "redirect:/memJoin.do";
		}
	}
	
	// 로그아웃 처리
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	// 로그인 처리
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm";
	}
	
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		log.info(m.toString());
		if(m.getMemID() ==null || m.getMemID().equals("")||
		   m.getMemPassword() ==null || m.getMemPassword().equals("")) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			return "redirect:/memLoginForm.do";
		}
		Member mvo = memberMapper.memLogin(m);
		if(mvo!=null) {//로그인 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("mvo", mvo);
			return "redirect:/";
		}else {//로그인 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "다시 로그인 해주세요.");
			return "redirect:/memLoginForm.do";
		}
	}
	
	//회원정보 수정화면
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		return "member/memUpdateForm";
	}
	//회원정보 수정화면
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m, RedirectAttributes rttr,String memPassword1, String memPassword2,HttpSession session) {
		
		if(m.getMemID()==null || m.getMemID().equals("")||
		   memPassword1 ==null|| memPassword1.equals("")||
		   memPassword2 ==null|| memPassword2.equals("")||
		   m.getMemName()==null || m.getMemName().equals("")||
		   m.getMemAge()==0 ||
		   m.getMemGender()==null || m.getMemGender().equals("")||
		   m.getMemEmail()==null || m.getMemEmail().equals("")
		   ) {
			//누락메세지를 가지고 가기 => 객체 바인딩
			rttr.addFlashAttribute("msgType", "누락 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			return "redirect:/memUpdateForm.do";
		}
		
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
			return "redirect:/memUpdateForm.do";
		}
		log.info(m.toString());
		
		m.setMemProfile("");//사진이미지는 없다는 의미 ""
		//회원을 테이블에 저장하기
		int result = memberMapper.memUpdate(m);
		if(result==1) {
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
			
			Member mvo = memberMapper.getMember(m.getMemID());
			
			session.setAttribute("mvo", mvo);//${!empty mvo}

			return "redirect:/";
		}else {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "수정오류가 발생했습니다.");
			return "redirect:/memUpdateForm.do";
		}
		
	}
	
	//회원사진 등록화면
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		return "member/memImageForm";
	}
	
	@RequestMapping("/memImageUpdate.do")
	public String memImageUpdate(HttpServletRequest request,HttpSession session, RedirectAttributes rttr) {
		//파일 업로드 API(cos.jar, 3가지)
		MultipartRequest multi=null;
		//업로드 파일 사이즈
		int fileMaxSize = 10*1024*1024; //10MB
		String savePath = request.getRealPath("resources/upload");
		try {
			multi=new MultipartRequest(request,savePath,fileMaxSize,"UTF-8",new DefaultFileRenamePolicy());
		} catch (Exception e) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");
			return "redirect:/memImageForm.do";
		}
		
		//데이터베이스 테이블에 회원이미지를 업데이트
		String memID = multi.getParameter("memID");
		String newProfile = "";
		File file = multi.getFile("memProfile");
		if(file != null) {//업로드가 된 상태(.png, .jpg, .gif)
			//이미지파일 여부를 체크 => 이미지 파일이 아니면 삭제하기(1.png)
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
			ext = ext.toUpperCase();
			if(ext.equals("PNG")||ext.equals("GIF")||ext.equals("JPG")) {
				//새로 업로드된 이미지(new->1.png), 현재 DB에 있는 이미지(old->4.png)
				String oldProfile = memberMapper.getMember(memID).getMemProfile();				
				File oldFile = new File(savePath +"/"+oldProfile);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				newProfile = file.getName();
			}else {//이미지 파일이 아니면 
				if(file.exists()) {
					file.delete();//삭제
				}
				rttr.addFlashAttribute("msgType", "실패 메세지");
				rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");
				return "redirect:/memImageForm.do";
			}
		}
		//새로운 이미지를 DB에 저장하기 -> 업데이트
		Member mvo = new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		memberMapper.memProfileUpdate(mvo);
		Member m = memberMapper.getMember(memID);
		
		//세션을 새롭게 생성한다.
		session.setAttribute("mvo",m);
		rttr.addFlashAttribute("msgType", "성공 메세지");
		rttr.addFlashAttribute("msg", "이미지 변경이 성공했습니다.");
		return "redirect:/";
	}
}
