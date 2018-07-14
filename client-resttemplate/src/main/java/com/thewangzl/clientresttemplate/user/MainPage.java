package com.thewangzl.clientresttemplate.user;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.thewangzl.clientresttemplate.oauth.AuthorizationCodeTokenService;
import com.thewangzl.clientresttemplate.oauth.OAuth2Token;
import com.thewangzl.clientresttemplate.security.ClientUserDetails;

@Controller
public class MainPage {
	
	@Autowired
	private AuthorizationCodeTokenService tokenService;
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/callback")
	public ModelAndView callback(String code, String stage	) {
		ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientUser clientUser = userDetails.getClientUser();
		
		OAuth2Token token = tokenService.getToken(code);
		clientUser.setAccessToken(token.getAccessToken());
		
		Calendar tokenValidity = Calendar.getInstance();
		tokenValidity.add(Calendar.SECOND,Integer.parseInt(token.getExpiresIn()));
		clientUser.setAccessTokenValidity(tokenValidity);
		
		users.save(clientUser);
		
		return new ModelAndView("redirect:/mainpage");
	}
	
	@GetMapping("/mainpage")
	public ModelAndView mainpage() {
		ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientUser clientUser = userDetails.getClientUser();
		
		if(clientUser.getAccessToken() == null || clientUser.getAccessToken().equals("")) {
			String authEndpoint = tokenService.getAuthorizationEndpoint();
			return new ModelAndView("redirect:"+ authEndpoint);
		}
		
		clientUser.setEntries(Arrays.asList(
				new Entry("entry 1"),
				new Entry("entry 2")
				));
		
		ModelAndView mav = new ModelAndView("mainpage");
		mav.addObject("user", clientUser);
		this.tryToGetUserInfo(mav, clientUser.getAccessToken());
		return mav;
	}
	
	private void tryToGetUserInfo(ModelAndView mav, String token) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer "+ token);
		String endpoint = "http://localhost:8080/api/userinfo";
		
		try {
			RequestEntity<Object> request = new RequestEntity<Object>(headers, HttpMethod.GET,	URI.create(endpoint));
			
			ResponseEntity<UserInfo> userInfo = restTemplate.exchange(request, UserInfo.class);
			
			if(userInfo.getStatusCode().is2xxSuccessful()) {
				mav.addObject("userInfo", userInfo.getBody());
			}else {
				throw new RuntimeException("it was not possible to retrieve user profile");
			}
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile,"+e.getMessage());
		}
	}
	
	@GetMapping("/registry")
	@ResponseBody
	public String registry(ClientUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		users.save(user);
		return "registry ok";
	}
}
