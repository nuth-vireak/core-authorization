package com.core.authorization.controller;

public class OAUTH_CLIENT_DETAILS {
	
	/*
		SELECT * from oauth_client_details

		create table oauth_client_details (
		  client_id VARCHAR(256) PRIMARY KEY,
		  resource_ids VARCHAR(256),
		  client_secret VARCHAR(256),
		  scope VARCHAR(256),
		  authorized_grant_types VARCHAR(256),
		  web_server_redirect_uri VARCHAR(256),
		  authorities VARCHAR(256),
		  access_token_validity INTEGER,
		  refresh_token_validity INTEGER,
		  additional_information VARCHAR(4096),
		  autoapprove VARCHAR(256)
		);
		
		create table oauth_client_token (
		  token_id VARCHAR(256),
		  token LONGVARBINARY,
		  authentication_id VARCHAR(256) PRIMARY KEY,
		  user_name VARCHAR(256),
		  client_id VARCHAR(256)
		);
		
		create table oauth_access_token (
		  token_id VARCHAR(256),
		  token LONGVARBINARY,
		  authentication_id VARCHAR(256) PRIMARY KEY,
		  user_name VARCHAR(256),
		  client_id VARCHAR(256),
		  authentication LONGVARBINARY,
		  refresh_token VARCHAR(256)
		);
		
		create table oauth_refresh_token (
		  token_id VARCHAR(256),
		  token LONGVARBINARY,
		  authentication LONGVARBINARY
		);
		
		create table oauth_code (
		  code VARCHAR(256), authentication LONGVARBINARY
		);
		
		create table oauth_approvals (
			userId VARCHAR(256),
			clientId VARCHAR(256),
			scope VARCHAR(256),
			status VARCHAR(10),
			expiresAt TIMESTAMP,
			lastModifiedAt TIMESTAMP
		);
		
		select * from oauth_access_token
		
		select * from oauth_refresh_token
		
		select * from oauth_code
		
		select * from oauth_approvals
		
		select * from ClientDetails
		
		select * from OAUTH_CLIENT_DETAILS
		
		select * from oauth_client_token
		
		select * from user_role


	CREATE TABLE user_role (
	  user_id bigint NOT NULL,
	  role_id bigint NOT NULL
	);

	SELECT USER_ID  as "userid" , password  as "pwd" from MERCHANT.USERS WHERE 

		-- customized oauth_client_details table
		create table ClientDetails (
		  appId VARCHAR(256) PRIMARY KEY,
		  resourceIds VARCHAR(256),
		  appSecret VARCHAR(256),
		  scope VARCHAR(256),
		  grantTypes VARCHAR(256),
		  redirectUrl VARCHAR(256),
		  authorities VARCHAR(256),
		  access_token_validity INTEGER,
		  refresh_token_validity INTEGER,
		  additionalInformation VARCHAR(4096),
		  autoApproveScopes VARCHAR(256)
		);


		INSERT INTO oauth_client_details
		(client_id, client_secret, scope, authorized_grant_types,
		authorities, access_token_validity, refresh_token_validity)
		VALUES ('test', '$2a$10$qgfrPSuoOvcoTYW1oka1r.XuQ67t9tt6erpZ4pa3/rx4Np0EF.fB6',
		'read,write', 'password,refresh_token,client_credentials,authorization_code',
		'ROLE_TRUSTED_CLIENT', 300, 1800);


	 */
}
