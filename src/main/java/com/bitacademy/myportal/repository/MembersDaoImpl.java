package com.bitacademy.myportal.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.myportal.exception.MembersDaoException;

@Repository("membersDao") 
// 해당 이름으로 객체 식별 가능. 실제 클래스 명과 다른 이름으로 객체를 생성하고자 할때 사용. 
// 안할 경우 클래스 이름으로 생성됨. Spring Elements/Beans에서 확인 가능.
public class MembersDaoImpl implements MembersDao {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int insert(MemberVo vo) {
		//가입을 위한 insert
		int insertedCount = 0;
		try {
			insertedCount = sqlSession.insert("member.insert",vo);		
		} catch (Exception e) {
			// 명시적인 Exception으로 변환
			throw new MembersDaoException("가입 중 오류 발생", vo);
		}		
		return insertedCount;
	}

	@Override
	public MemberVo selectUser(String email) {
		MemberVo member = sqlSession.selectOne("member.selectByEmail",email);		
		return member;
	}

	@Override
	public MemberVo selectUser(String email, String password) {
		// 템플릿에 전달할 파라미터가 둘 이상일 경우, 별도의 VO 객체가 없을 때는 Map을 만들어 넘겨줄 수 있음
		// 템플릿 안 쪽에서는 map 타입으로 설정 가능
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("password", password);
		
		// 객체 받아오기
		MemberVo member = sqlSession.selectOne("member.selectByEmailAndPassword", paramMap);
		// 주의 : selectOne 메소드는 단일 레코드가 넘어올 때만 사용할 수 있다. 두 개 이상에 사용시 에러 발생
		
		return member;
	}

}
