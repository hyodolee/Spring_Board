package com.example.spring02.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.spring02.model.board.dao.ReplyDAO;
import com.example.spring02.model.board.dto.ReplyDTO;

@Service //service bean
public class ReplyServiceImpl implements ReplyService {
	@Inject
	ReplyDAO replyDao;

	//댓글 목록
	@Override
	public List<ReplyDTO> list(int bno, int start, int end, HttpSession session) {
		return replyDao.list(bno, start, end);
	}
	@Override
	public int count(int bno) {
		return replyDao.count(bno);
	}
//댓글 쓰기	
	@Override
	public void create(ReplyDTO dto) {
		replyDao.create(dto);
	}
}











