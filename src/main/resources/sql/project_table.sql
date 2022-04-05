-- Drop table

-- DROP TABLE merchant.users;

CREATE TABLE merchant.users (
	user_id varchar(20) NOT NULL,
	servicestatuscode varchar(2) NOT NULL,
	lastlogindate varchar(8) NULL,
	lastloginhms varchar(8) NULL,
	firstlogindate varchar(8) NULL,
	"password" varchar(256) NOT NULL,
	userpassworderrorcount numeric(2) NULL,
	passwordchangedate varchar(8) NULL,
	serviceceasedate varchar(8) NULL,
	profile varchar(500) NULL,
	creationid varchar(20) NOT NULL,
	creationdatetime timestamp NOT NULL,
	updateid varchar(20) NOT NULL,
	updatedatetime timestamp NOT NULL,
	subidyn varchar(1) NOT NULL,
	masteruserid varchar(20) NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (user_id)
);



-- Drop table

-- DROP TABLE merchant.users_d;

CREATE TABLE merchant.users_d (
	user_id varchar(20) NOT NULL,
	username varchar(80) NOT NULL DEFAULT ''::character varying,
	phone varchar(20) NOT NULL DEFAULT ''::character varying,
	email varchar(100) NOT NULL DEFAULT ''::character varying,
	remark varchar(100) NOT NULL DEFAULT ''::character varying,
	creationid varchar(20) NOT NULL DEFAULT ''::character varying,
	creationdatetime timestamp NOT NULL ,
	updateid varchar(20) NOT NULL DEFAULT ''::character varying,
	updatedatetime timestamp NOT NULL 
);




-- Drop table

-- DROP TABLE merchant.menu_i;

CREATE TABLE merchant.menu_i (
	seqno numeric(10) NOT NULL,
	inbn_prog_dvcd varchar(1) NOT NULL DEFAULT ''::character varying,
	inbn_lvel1_menu_cd varchar(20) NOT NULL DEFAULT ''::character varying,
	inbn_lvel2_menu_cd varchar(20) NOT NULL DEFAULT ''::character varying,
	inbn_lvel1_menu_desc varchar(100) NOT NULL DEFAULT ''::character varying,
	inbn_lvel2_menu_desc varchar(100) NOT NULL DEFAULT ''::character varying,
	inbn_menu_use_auth_dvcd varchar(1) NOT NULL DEFAULT ''::character varying,
	crtn_id varchar(20) NOT NULL DEFAULT ''::character varying,
	crtn_dttm timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updt_id varchar(20) NOT NULL DEFAULT ''::character varying,
	updt_dttm timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	inbn_menu_show_yn varchar(1) NOT NULL DEFAULT ''::character varying,
	menu_uri varchar(100) NOT NULL DEFAULT ''::character varying,
	CONSTRAINT pk_ebcib_cmenu_i PRIMARY KEY (seqno)
);



-- Drop table

-- DROP TABLE merchant.menu_d;

CREATE TABLE merchant.menu_d (
	user_id varchar(20) NOT NULL,
	inbn_prog_dvcd varchar(1) NOT NULL,
	inbn_lvel1_menu_cd varchar(20) NOT NULL,
	inbn_lvel2_menu_cd varchar(20) NOT NULL,
	inbn_menu_use_auth_dvcd varchar(1) NOT NULL DEFAULT ''::character varying,
	reg_dt varchar(8) NOT NULL DEFAULT ''::character varying,
	chng_dt varchar(8) NOT NULL DEFAULT ''::character varying,
	cls_dt varchar(8) NOT NULL DEFAULT ''::character varying,
	crtn_id varchar(20) NOT NULL DEFAULT ''::character varying,
	crtn_dttm timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updt_id varchar(20) NOT NULL DEFAULT ''::character varying,
	updt_dttm timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_ebcib_cmbun_d PRIMARY KEY (inbn_lvel1_menu_cd, inbn_lvel2_menu_cd, inbn_prog_dvcd, user_id)
);



--------------- FOR OAUTH --------------------
-- Drop table

-- DROP TABLE public.clientdetails;

CREATE TABLE public.clientdetails (
	appid varchar(256) NOT NULL,
	resourceids varchar(256) NULL,
	appsecret varchar(256) NULL,
	"scope" varchar(256) NULL,
	granttypes varchar(256) NULL,
	redirecturl varchar(256) NULL,
	authorities varchar(256) NULL,
	access_token_validity int4 NULL,
	refresh_token_validity int4 NULL,
	additionalinformation varchar(4096) NULL,
	autoapprovescopes varchar(256) NULL,
	CONSTRAINT clientdetails_pkey PRIMARY KEY (appid)
);


-- Drop table

-- DROP TABLE public.oauth_access_token;

CREATE TABLE public.oauth_access_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL,
	authentication bytea NULL,
	refresh_token varchar(256) NULL
);



-- Drop table

-- DROP TABLE public.oauth_approvals;

CREATE TABLE public.oauth_approvals (
	userid varchar(256) NULL,
	clientid varchar(256) NULL,
	"scope" varchar(256) NULL,
	status varchar(10) NULL,
	expiresat timestamp NULL,
	lastmodifiedat timestamp NULL
);


-- Drop table

-- DROP TABLE public.oauth_client_details;

CREATE TABLE public.oauth_client_details (
	client_id varchar(256) NOT NULL,
	resource_ids varchar(256) NULL,
	client_secret varchar(256) NULL,
	"scope" varchar(256) NULL,
	authorized_grant_types varchar(256) NULL,
	web_server_redirect_uri varchar(256) NULL,
	authorities varchar(256) NULL,
	access_token_validity int4 NULL,
	refresh_token_validity int4 NULL,
	additional_information varchar(4096) NULL,
	autoapprove varchar(256) NULL,
	CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
);


-- Drop table

-- DROP TABLE public.oauth_client_token;

CREATE TABLE public.oauth_client_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication_id varchar(256) NOT NULL,
	user_name varchar(256) NULL,
	client_id varchar(256) NULL,
	CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id)
);


-- Drop table

-- DROP TABLE public.oauth_code;

CREATE TABLE public.oauth_code (
	code varchar(256) NULL,
	authentication bytea NULL
);


-- Drop table

-- DROP TABLE public.oauth_refresh_token;

CREATE TABLE public.oauth_refresh_token (
	token_id varchar(256) NULL,
	"token" bytea NULL,
	authentication bytea NULL
);

-- Drop table

-- DROP TABLE public.user_role;

CREATE TABLE public.user_role (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL
);







