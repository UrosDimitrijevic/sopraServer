package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true) 
	private String username;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true) 
	private String token;

	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private LocalDate creationDate;

	//stores the id of the user this user is challenging
	@Column(nullable = true)
	private Long challenging;

	//stores the id of the user who is challenging this user
	@Column(nullable = true)
	private Long gettingChallengedBy;

	public Long getChallenging() {
		return challenging;
	}

	public void setChallenging(Long challenging) {
		this.challenging = challenging;
	}

	public Long getGettingChallengedBy() {
		return gettingChallengedBy;
	}

	public void setGettingChallengedBy(Long gettingChallengedBy) {
		this.gettingChallengedBy = gettingChallengedBy;
	}

	public void initCreationDate( ){
		this.creationDate = LocalDate.now();
	}

	public LocalDate getcreationDate(){
		return this.creationDate;
	}

	public LocalDate getBirthday(){
		return this.birthday;
	}

	public void setBirthday(LocalDate date){
		this.birthday = date;
	}

	public void setBirthday(String date){
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.birthday = LocalDate.parse(date, DATEFORMATTER);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() { return this.password; }

	public void setPassword(String password) {this.password = password;}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public User removePassword(){
		this.password = "";
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}


}
