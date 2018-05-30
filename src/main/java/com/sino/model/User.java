package com.sino.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * @author liudk
 * 2017-07-02 21:15
 */
@Entity
@Table(name = "sys_users")
@DynamicUpdate
public class User implements Serializable{

	private static final long serialVersionUID = -4720070672877637766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long uid;
	
	@Column(length = 128)
//	@JsonIgnore
	private String userName;
	
	@Column(length = 64)
	private String password;
	
	@Column(length = 64)
	private String salt;
	
	@Column(length = 32)
	private String mobile;
	
	@Column(length = 128)
	private String nickname; // 昵称

	@Column(length = 10)
	private String sex; // 性别

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public User() {
		super();
	}

	/**
	 * @param uid
	 * @param userName
	 * @param password
	 * @param salt
	 * @param mobile
	 * @param nickname
	 * @param sex
	 */
	public User(long uid, String userName, String password, String salt,
			String mobile, String nickname, String sex) {
		super();
		this.uid = uid;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.mobile = mobile;
		this.nickname = nickname;
		this.sex = sex;
	}
	
}
