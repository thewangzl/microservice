package com.thewangzl.authcodeapp.client.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AccessToken {

    @JsonProperty("access_token")
    private String value;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonIgnore
    private Long issuedAt = new Date().getTime();

    private String scope;

    public boolean isExipired(){
        Long expirationTimeInseconds = (issuedAt / 1000)+ expiresIn;
        Long nowInSeconds = new Date().getTime()/1000;

        return expirationTimeInseconds < nowInSeconds;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }

    @Override
    public String toString() {
        return "value:"+this.value +",\ntokenType:"+ this.tokenType +",\nexpiresIn:"+this.expiresIn +",\nscope:"+this.scope+
                ",\nissuedAt:"+this.issuedAt +",\nisExipired:"+this.isExipired();
    }
}
