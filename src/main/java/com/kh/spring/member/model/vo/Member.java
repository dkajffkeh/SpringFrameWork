package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 *  Lombok(롬복)
 *  롬복은 자바에서 VO클래스를 만들 때 필드에 대한 getter/setter toString 생성자 
 *  구문을 어노테이션 기술만으로 해당 구문이 자동으로 만들어지는 라이브러리
 *  => 즉 후에 필드 수정한다거나 , 새로운 필드 추가 및 삭제 할 때 마다 일일이 setter getter toString 생성자 손댈 필요가없음.
 *  
 *  1.라이브러리 추가 Maven 을 통해서 추가
 *  2.롬복은 라이브러리 추가만으로 쓸 수 없음. (sts또는 이클립스에서 롬복을 쓸거임)
 *  => 다운로드된 라이브러리(.jar 더블클릭하면 창이 뜸 => install)
 *  3. sts 또는 이클립스 재부팅
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private String enrollDate;
	private Date modiftDate;
	private String status;
	//rombok 에서는 최소 소문자 2개 이상으로 시작 되도록 작성.
}
