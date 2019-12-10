package com.bitacademy.myportal.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/upload";	// 저장될 위치
	private static final Logger logger = 
			LoggerFactory.getLogger(FileUploadService.class);
	
	public String store(MultipartFile mFile) {
		// 실제 클라이언트에서의 파일 이름
		// Rename이 필요할 경우
		String savedFilename = "";
		String originalFilename = mFile.getOriginalFilename();
		Long size = mFile.getSize();
		
		logger.debug("원본 파일명:" + originalFilename);
		logger.debug("파일의 사이즈:" + size);
		
		// 파일명 변경
		// 확장자 분리
		String extName = originalFilename.substring(
				originalFilename.lastIndexOf(".")).toLowerCase();
		logger.debug("파일의 확장자:" + extName);
		// 파일명 생성(시스템 날짜를 기준으로 생성)
		Calendar cal = Calendar.getInstance();
		savedFilename =  String.valueOf(cal.getTimeInMillis()) + extName;
		logger.debug("새로 만든 파일 명:" + savedFilename) ;
				
		try {
		writeFile(mFile, savedFilename);	//IOException throw 때문에 에러체크 필수
		} catch (Exception e) {
			throw new RuntimeException(e);
			// 익셉션의 전환 : 커스텀 익셉션을 생성하여 처리할 것 (권장)
		}
		return savedFilename;
	}
	
	// 실제 저장을 위한 메서드
		private void writeFile(MultipartFile mFile, String saveFilename) 
				throws IOException {
			byte[] fileData = mFile.getBytes();
			
			// 저장을 위해 FileOutputStream을 생성
			FileOutputStream fos = 
					new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			fos.write(fileData);
			fos.close();
		}
		
}
