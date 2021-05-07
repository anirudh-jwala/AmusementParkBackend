package com.cg.AmusementPark.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin("*")
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		return "Welcome to Amusement Park";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Dashboard";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Dashboard";
	}

}