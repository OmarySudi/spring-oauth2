SET sql_mode = '';

create table if not exists  oauth_client_details (
  id int(11) not null auto_increment,
  client_id varchar(255) not null,
  client_secret varchar(255) not null,
  web_server_redirect_uri varchar(2048) default null,
  scope varchar(255) default null,
  access_token_validity int(11) default null,
  refresh_token_validity int(11) default null,
  resource_ids varchar(1024) default null,
  authorized_grant_types varchar(1024) default null,
  authorities varchar(1024) default null,
  additional_information varchar(4096) default null,
  autoapprove varchar(255) default null,
  primary key (id)
) engine=innodb ;

create table if not exists  permission (
  id int(11) not null auto_increment,
  name varchar(512) default null,
  primary key (id),
  unique key name (name)
) engine=innodb ;

create table if not exists role (
  id int(11) not null auto_increment,
  name varchar(255) default null,
  primary key (id),
  unique key name (name)
) engine=innodb ;

--create table if not exists  user (
--  id int(11) not null auto_increment,
--  username varchar(100) not null,
--  password varchar(1024) not null,
--  userID varchar(100) not null,
--  full_name varchar(100) not null,
--  phone_number varchar(100) not null,
--  profile_picture varchar(1024) null,
--  identinty_card varchar (1024) null,
--  driving_licence varchar (1024) null,
--  certificate_of_good_conduct varchar (1024) null,
--  carID varchar(100) null,
--  car_approval tinyint(4) default 0,
--  driver_status varchar(20) default 'inactive',
--  email varchar(1024) not null,
--  enabled tinyint(4) default 1,
--  email_confirmation tinyint(4) default 0,
--  admin_document_verification tinyint(4) default 0,
--  account_activation tinyint(4) default 0,
--  accountNonExpired tinyint(4) default 1,
--  credentialsNonExpired tinyint(4) default 1,
--  accountNonLocked tinyint(4) default 1,
--  primary key (id),
--  unique key username (username)
--) engine=innodb ;

create table if not exists  user (
  id int(11) not null auto_increment,
  username varchar(200) not null,
  password varchar(1024) not null,
  userID varchar(100) not null,
  full_name varchar(100) not null,
  phone_number varchar(100) not null,
  profile_picture varchar(1024) null,
  identinty_card varchar (1024) null,
  driving_licence varchar (1024) null,
  certificate_of_good_conduct varchar (1024) null,
  carID varchar(100) null,
  car_approval tinyint(4) default 0,
  driver_status varchar(20) default 'inactive',
  enabled tinyint(4) default 0,
  email_confirmation tinyint(4) default 0,
  admin_document_verification tinyint(4) default 0,
  account_activation tinyint(4) default 0,
  accountNonExpired tinyint(4) default 0,
  credentialsNonExpired tinyint(4) default 0,
  accountNonLocked tinyint(4) default 0,
  primary key (id),
  constraint uc_username_userid unique (username,userID)
) engine=innodb ;

create table  if not exists permission_role (
  permission_id int(11) default null,
  role_id int(11) default null,
  key permission_id (permission_id),
  key role_id (role_id),
  constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
  constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
) engine=innodb ;

create table if not exists role_user (
  role_id int(11) default null,
  user_id int(11) default null,
  key role_id (role_id),
  key user_id (user_id),
  constraint role_user_ibfk_1 foreign key (role_id) references role (id),
  constraint role_user_ibfk_2 foreign key (user_id) references user (id)
) engine=innodb ;


---- token store
--create table if not exists oauth_client_token (
--  token_id VARCHAR(256),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(256) PRIMARY KEY,
--  user_name VARCHAR(256),
--  client_id VARCHAR(256)
--);
--
--create table if not exists oauth_access_token (
--  token_id VARCHAR(256),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(256) PRIMARY KEY,
--  user_name VARCHAR(256),
--  client_id VARCHAR(256),
--  authentication LONG VARBINARY,
--  refresh_token VARCHAR(256)
--);
--
--create table if not exists oauth_refresh_token (
--  token_id VARCHAR(256),
--  token LONG VARBINARY,
--  authentication LONG VARBINARY
--);
--
--create table if not exists oauth_code (
--  code VARCHAR(256), authentication LONG VARBINARY
--);
--
--create table if not exists oauth_approvals (
--	userId VARCHAR(256),
--	clientId VARCHAR(256),
--	scope VARCHAR(256),
--	status VARCHAR(10),
--	expiresAt TIMESTAMP,
--	lastModifiedAt TIMESTAMP
--);