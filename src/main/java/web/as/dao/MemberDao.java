package web.as.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import web.as.vo.memberVO;

@Repository
public class MemberDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<memberVO> selectMemberList(memberVO vo) throws Exception{
		
		return sqlSession.selectList("member.selectMemberList", vo);
	
		//1. select : 0~1 : selectOne, 0,1,여러개 : selectList.
		//2.update < int
		//3. delete : int
		//4. insert : int
	}
	
	public memberVO selectMemberOne(memberVO vo) throws Exception{
		
		return sqlSession.selectOne("member.selectMember", vo);
		
	}

	public int selectMemberCount(memberVO vo) throws Exception{
		
		return sqlSession.selectOne("member.selectMemberCount", vo);
		
	}
	
	public int insertMember(memberVO vo) throws Exception{
		return sqlSession.insert("member.insertMember", vo);
	}

	
	
}
