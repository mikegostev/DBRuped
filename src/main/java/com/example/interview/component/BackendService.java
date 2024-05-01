package com.example.interview.component;

import org.springframework.stereotype.Component;

@Component
public class BackendService {

	public DataUnit getById(String id) {
		return new DataUnit("Mike","my@email.com");
	}

}
