package web.as.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.as.dao.AsinfoDao;
import web.as.vo.asinfoVO;

@Service
public class AsinfoServiceImpl implements AsinfoService{

	@Autowired
	AsinfoDao asinfodao;

	
	@Override
	public List<asinfoVO> selectAsInfoList(asinfoVO vo) throws Exception {
		return asinfodao.selectAsInfoList(vo);
	}


	@Override
	public asinfoVO selectAsInfo(asinfoVO vo) throws Exception {
		return asinfodao.selectAsInfo(vo);
	}


	@Override
	public int updateAsinfo(asinfoVO vo) throws Exception {
		return asinfodao.updateAsinfo(vo);
	}


	@Override
	public int insertAsinfo(asinfoVO vo) throws Exception {
		return asinfodao.insertAsinfo(vo);
	}


	@Override
	public int deleteAsinfo(asinfoVO vo) throws Exception {
		return asinfodao.deleteAsinfo(vo);
	}
	
	
}
