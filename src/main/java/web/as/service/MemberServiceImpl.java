package web.as.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.as.dao.MemberDao;
import web.as.vo.asinfoVO;
import web.as.vo.memberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberDao memberdao;

	
	@Override
	public List<memberVO> selectMemberList(memberVO vo) throws Exception {
		return memberdao.selectMemberList(vo);
	}

	@Override
	public memberVO selectMemberOne(memberVO vo) throws Exception {
		return memberdao.selectMemberOne(vo);
	}

	@Override
	public int selectMemberCount(memberVO vo) throws Exception {
		return memberdao.selectMemberCount(vo);
	}

	@Override
	public int insertMember(memberVO vo) throws Exception {
		return memberdao.insertMember(vo);
	}

	
	
}
