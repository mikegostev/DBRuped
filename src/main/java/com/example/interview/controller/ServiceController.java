package com.example.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.interview.component.BackendService;
import com.example.interview.component.DataUnit;

@Controller
public class ServiceController {

	@Autowired
	private BackendService service;
	
	@GetMapping("data")
	public @ResponseBody DataUnit getData(@RequestParam String id) {
		return service.getById(id);
	}
}
