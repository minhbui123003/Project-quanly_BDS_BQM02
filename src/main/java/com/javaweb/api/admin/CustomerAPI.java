package com.javaweb.api.admin;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customerAPIOfAdmin")
@RequestMapping("/api/customer")
@Transactional
public class CustomerAPI {
}
