-- create the database structure for OAuth2
create table oauth_client_details (
  client_id VARCHAR(512) PRIMARY KEY,
  resource_ids VARCHAR(512),
  client_secret VARCHAR(512),
  scope VARCHAR(512),
  authorized_grant_types VARCHAR(512),
  web_server_redirect_uri VARCHAR(512),
  authorities VARCHAR(512),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(512)
);

create table oauth_access_token (
  token_id VARCHAR(512),
  token BINARY,
  authentication_id VARCHAR(512) PRIMARY KEY,
  user_name VARCHAR(512),
  client_id VARCHAR(512),
  authentication BINARY,
  refresh_token VARCHAR(512)
);

create table oauth_approvals (
    userId VARCHAR(512),
    clientId VARCHAR(512),
    scope VARCHAR(512),
    status VARCHAR(100),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

-- insert a default client credentials
insert into oauth_client_details
(client_id, client_secret, scope,
 authorized_grant_types, web_server_redirect_uri)
values
('mobileclient', '$2a$10$xFl9edgKC56YN9e2eOEpFOGLakhhKRgoypTNCdyOrVwq4UpPWcp8C', 'read_userinfo,read_contacts',
'authorization_code,implicit,password', 'oauth2://userinfo/callback,http://localhost:9000/callback'
);
