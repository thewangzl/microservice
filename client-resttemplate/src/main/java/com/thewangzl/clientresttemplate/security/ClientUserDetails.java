package com.thewangzl.clientresttemplate.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.thewangzl.clientresttemplate.user.ClientUser;

public class ClientUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private ClientUser clientUser;
	
	public ClientUserDetails(ClientUser clientUser) {
		this.clientUser = clientUser;
	}
	
	public ClientUser getClientUser() {
		return clientUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>();
	}

	@Override
	public String getPassword() {
		return this.clientUser.getPassword();
	}

	@Override
	public String getUsername() {
		return this.clientUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
