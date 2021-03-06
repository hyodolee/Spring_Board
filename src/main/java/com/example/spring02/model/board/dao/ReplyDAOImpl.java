package com.example.spring02.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring02.model.board.dto.ReplyDTO;
@Repository //dao bean
public class ReplyDAOImpl implements ReplyDAO {
	@Inject //의존관계 주입
	SqlSession sqlSession;

	//댓글 목록
	@Override
	public List<ReplyDTO> list(int bno, int start, int end) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("bno", bno);
		return sqlSession.selectList("reply.listReply", map);
	}
	
	@Override
	public int count(int bno) {
		return sqlSession.selectOne("reply.count", bno);
	}
//댓글 추가	
	@Override
	public void create(ReplyDTO dto) {
		sqlSession.insert("reply.insertReply", dto); 
	}
}













