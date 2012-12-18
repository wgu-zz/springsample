package me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringSampleController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/sampleroot")
	@ResponseBody
	public void sampleroot() throws Exception {
		System.out.println(System.getenv("VCAP_SERVICES"));
		System.out.println(jdbcTemplate.getDataSource().getConnection());
	}

}
