package jj.tech.paolu.biz.login;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jj.tech.paolu.utils.Results;

@RestController
public class LoginController {
	
	@GetMapping("/login")
	@Transactional
	public Object login(String name, String pass) {
		return Results.SUCCESS();
	}

}