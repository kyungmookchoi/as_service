package web.as.common;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import web.as.vo.MailMstVO;




public class MailSend { 
	static Properties mailServerProperties; 
	static Session getMailSession; 
	static MimeMessage generateMailMessage; 
	
	public void send(MailMstVO vo) throws Exception{
        String[] emailList = vo.getRecipients();			// 메일 받는사람 리스트 
        String emailMsgTxt = vo.getContent(); 				// 내용
        String emailSubjectTxt = vo.getTitle();				// 제목
        
        // 메일보내기 
        postMail(emailList, emailSubjectTxt, emailMsgTxt);
	}

	
	private void postMail(String recipients[], String subject, String msgText) throws MessagingException, UnsupportedEncodingException, Exception {
		Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        
        Authenticator auth = new MailAuth();		//구글 권한 가져오기
        
        Session session = Session.getDefaultInstance(prop, auth);		//메일 세션 생성
        
        MimeMessage msg = new MimeMessage(session);	//메일 발송 메시지 클래스
    
        try {
            msg.setSentDate(new Date());	//보내는 날짜
            
            InternetAddress[] to = new InternetAddress[recipients.length];	//받는 사람 목록
            
            msg.setFrom(new InternetAddress(Constant.MAIL_SENDER, Constant.MAIL_SENDER_NM));
            
            for (int i = 0; i < recipients.length; i++) {
            	if(recipients[i]!=null && !"".equals(recipients[i])) {
            		to[i] = new InternetAddress(recipients[i]);
            	}
	        }
	        msg.setRecipients(Message.RecipientType.TO, to);
	        
            //제목            
            msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
            
            MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(msgText, "text/html;charset=utf-8");

	        //creates multi-part
	        Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			
	        // sets the multi-part as e-mail's content
	        msg.setContent(multipart);
	        
            Transport.send(msg);
            
        } catch(AddressException ae) {            
            System.out.println("AddressException : " + ae.getMessage());           
        } catch(MessagingException me) {            
            System.out.println("MessagingException : " + me.getMessage());
        } catch(UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException : " + e.getMessage());			
        }
                
    }
	
}

