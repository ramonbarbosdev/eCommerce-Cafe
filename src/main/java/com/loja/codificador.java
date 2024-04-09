package com.loja;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class codificador {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("123");
		System.out.println(result);
		

	}

}
