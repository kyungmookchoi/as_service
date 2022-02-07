package web.as.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.as.common.MailForm;
import web.as.common.MailSend;
import web.as.common.SessionUtil;
import web.as.service.AsinfoService;
import web.as.service.MemberService;
import web.as.vo.MailMstVO;
import web.as.vo.asinfoVO;
import web.as.vo.memberVO;

@Controller
public class ViewController {

	@Autowired
	MemberService service;
	
	@Autowired
	AsinfoService asservice;
	
	
	
	@RequestMapping("/")
	public String main(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception
	{
		// 사용자 세션.
		memberVO membervo = (memberVO)SessionUtil.getAttribute("member");
		
		if(membervo == null ) 
		{
			return "login";
		}
		else 
		{
			return "index";
		}
	
	}
	
	
	@ResponseBody
	@RequestMapping("/member/login")
	public boolean memberlogin(memberVO vo, HttpServletRequest req, HttpServletResponse res, Model model) throws Exception
	{
		vo = service.selectMemberOne(vo);
			
		if(vo!=null) {
			// TODO : vo를 세션에 등록해줌.
			SessionUtil.setAttribute("member", vo);
			
			return true;	// 로그인 완료
		}else {
			return false;	//로그인 실패
		}
		
	}
	
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception{
		
		return "index";
	}

	@RequestMapping("/register")
	public String register(HttpServletRequest req, HttpServletResponse res, Model model) throws Exception{
		
		return "register";
	}
	
	@ResponseBody 
	@RequestMapping("/member/join")
	public boolean join(@ModelAttribute("memberVO") memberVO vo ) throws Exception{
		
		int cnt = service.selectMemberCount(vo);
		
		if (cnt>0) {
			return false;
		}else {
			service.insertMember(vo);
		}
		
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/asinfo/search")
	public List<asinfoVO> getList(@ModelAttribute("asinfoVO") asinfoVO vo) throws Exception{
		
		List<asinfoVO> list = asservice.selectAsInfoList(vo) ;
		
		return list;
		
	}
	
	@ResponseBody
	@RequestMapping("/asinfo/view")
	public asinfoVO getView(@ModelAttribute("asinfoVO") asinfoVO vo) throws Exception{
		
		vo = asservice.selectAsInfo(vo) ;
		
		return vo;
		
	}
	
	@ResponseBody
	@RequestMapping("/asinfo/save")
	public boolean save(@ModelAttribute("asinfoVO") asinfoVO vo) throws Exception{
		int c=0;
		
		memberVO membervo = (memberVO)SessionUtil.getAttribute("member");
		
		vo.setId(membervo.getId());
		
		if(vo.getNum()!=null && !"".equals(vo.getNum()) ) {   //update
			c = asservice.updateAsinfo(vo);
			
		} else {  //insert
			c = asservice.insertAsinfo(vo);
			
		}
		
		
		//메일 발송.
		if(vo.getMailYn()!=null && "Y".equals(vo.getMailYn())) {
			
			MailMstVO mailvo = new MailMstVO();
	         
	         mailvo.setTitle("AS 결과 메일 입니다.");      //제목
	         mailvo.setContent(vo.getAnswer());       //내용
	         
	         MailForm mform = new MailForm();
	         mailvo.setContent(mform.getHtml(mailvo));
	         

	         String[] recipients = new String[1];		//수신자 메일
	         
	         recipients[0] = vo.getEmail();
	         
	         mailvo.setRecipients(recipients);
	         
	         MailSend mail = new MailSend();
	         mail.send(mailvo);
			
		}
		
		if(c>0) {
			return true;
		}else {
			
			return false;
		}
		
		
		
	}
	
	@ResponseBody
	@RequestMapping("/asinfo/delete")
	public boolean delete(@ModelAttribute("asinfoVO") asinfoVO vo) throws Exception{
		int	c = asservice.deleteAsinfo(vo);
		
		if(c>0) {
			return true;
		}else {
			
			return false;
		}
		
		
		
	}
}
