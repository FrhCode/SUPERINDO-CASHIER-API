package com.superindo.cashier.response;

import java.util.Date;

import com.superindo.cashier.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	private User user;
	private String jwtToken;
	private Date expires;

}
