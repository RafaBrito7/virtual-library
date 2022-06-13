package com.axians.virtuallibrary.api.rest;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.api.service.UserService;
import com.axians.virtuallibrary.core.config.PasswordEncoderConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRest.class)
@AutoConfigureMockMvc
public class UserRestTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private PasswordEncoderConfig passEncoder;

	@WithMockUser(value = "spring")
	@Test
	public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
		PasswordEncoder bcrypt = passEncoder.getPasswordEncoder();
		UserDTO userDTO = new UserDTO("francisco", "chico@gmail.com", bcrypt.encode("123"), "USER", "45121", new Date());
		
		List<UserDTO> list = new ArrayList<>();
		list.add(userDTO);
		
		Mockito.when(userService.listAll()).thenReturn(list);
		
		mockMvc.perform(get("/api/user/list")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasItems(1)))
			.andExpect(jsonPath("$[0].name", is(userDTO.getName())));
		
	}

}
