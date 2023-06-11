package com.code.AssJava5.entity;

import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	@Column
	private String password;

	@Column
	private String fullname;

	@Column
	private String email;

	@Column
	private String img;

	@Column
	private boolean activated = true;

	@Column
	private boolean admin = false;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

}
