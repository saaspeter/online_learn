-- this sql require table: User, currently this table is not in this file

--
-- Table structure for table  resources 
--

DROP TABLE IF EXISTS  resources ;
CREATE TABLE  resources  (
   id             bigint NOT NULL auto_increment,
   name           varchar(64),
   res_type       varchar(8),
   res_string     varchar(128),
   syscode        char(8),
   pID            bigint,
   ordNo          int,
   oper_type      smallint,
   status         smallint(6),
   path           varchar(200),
   rescFold       smallint, 
   linkID         bigint,
   visible        smallint,
   descn       	  varchar(256),
   objectparam    varchar(128),
   expression     varchar(512),
  PRIMARY KEY  ( id )
) 
ENGINE = MYISAM;

--
-- Table structure for table  role_resc 
--

DROP TABLE IF EXISTS  role_resc ;
create table role_resc(   resc_id              bigint,   role_id              bigint,   syscode              char(8),
   primary key (resc_id, role_id, syscode))
ENGINE = MYISAM;alter table role_resc add constraint FK_ROLE_RES_RESOURCE foreign key (resc_id)      references resources (id) on delete restrict on update restrict;alter table role_resc add constraint FK_ROLE_RES_ROLES foreign key (role_id)      references roles (id) on delete restrict on update restrict;

--
-- Table structure for table  roles 
--

DROP TABLE IF EXISTS  roles ;
CREATE TABLE  roles  (
   id              bigint     NOT NULL auto_increment,
   name            varchar(64) default NULL,
   code            varchar(32),
   shopID          bigint,
   syscode         char(8) default NULL,
   upId            bigint,
   status          smallint,
   isDefaultSet    smallint,
   roleLevel       smallint,
   creator         bigint, 
   descn           varchar(128) default NULL,
  PRIMARY KEY  ( id )
)
ENGINE = MYISAM;

--
-- Table structure for table  user_role 
--

DROP TABLE IF EXISTS  user_role ;
create table user_role
(
   role_id         bigint,
   user_id         bigint,
   shopID          bigint,
   usetype         smallint,
   syscode         char(8),
   primary key (role_id, user_id, shopID, syscode)
)
ENGINE = MYISAM;

alter table user_role add constraint FK_USER_ROLE foreign key (user_id)
      references User (userID) on delete restrict on update restrict;

alter table user_role add constraint FK_USER_ROL_ROLES foreign key (role_id)
      references roles (id) on delete restrict on update restrict;


create table rolesValue
(
   valueID              bigint not null auto_increment,
   id                   bigint,
   localeID             bigint,
   name                 varchar(64),
   shopID               bigint,
   descn                varchar(128),
   primary key (valueID)
)
ENGINE = MYISAM;

alter table rolesValue add constraint FK_ROLE_VALUE foreign key (id)
      references roles (id) on delete restrict on update restrict;


/*==============================================================*/
/* Table: resourcesValue                                        */
/*==============================================================*/
create table resourcesValue
(
   rescValueID          bigint not null auto_increment,
   id                   bigint,
   localeID             bigint,
   name                 varchar(64),
   descn                varchar(256),
   primary key (rescValueID)
)
ENGINE = MYISAM;

alter table resourcesValue add constraint FK_RESOURCE_VALUE foreign key (id)
      references resources (id) on delete restrict on update restrict;
	  

/*==============================================================*/
/* Table: rescLink                                              */
/*==============================================================*/
create table rescLink
(
   linkID               bigint not null,
   methodID             bigint not null,
   back1                bigint,
   primary key (linkID, methodID)
)
ENGINE = MYISAM;

/*==============================================================*/
/* Table: superUsers                                            */
/*==============================================================*/
create table superUser
(
   loginname            varchar(64),
   password             varchar(32),
   createTime           datetime,
   dueTime              datetime,
   ipStr                varchar(255),
   syscodeStr           varchar(255),
   state                smallint,
   primary key (loginname, syscodeStr)
)
ENGINE = MYISAM;


DROP TABLE IF EXISTS  menus ;
/*==============================================================*/
/* Table: menus                                                 */
/*==============================================================*/
create table menus
(
   id                   bigint not null,
   title                varchar(64),
   target               varchar(16),
   onclick              varchar(100),
   onmouseover          varchar(100),
   onmouseout           varchar(100),
   image                varchar(50),
   altImage             varchar(30),
   tooltip              varchar(100),
   page                 varchar(100),
   width                varchar(8),
   height               varchar(8),
   forward              varchar(50),
   action               varchar(50),
   menuType             smallint,
   primary key (id)
)
ENGINE = MYISAM;

alter table menus add constraint FK_MENU_RESOURCE foreign key (id)
      references resources (id) on delete restrict on update restrict;

DROP TABLE IF EXISTS  menusValue ;
/*==============================================================*/
/* Table: menusValue                                            */
/*==============================================================*/
create table menusValue
(
   menuValueID          bigint not null auto_increment,
   id                   bigint,
   localeID             bigint,
   title                varchar(64),
   tooltip              varchar(100),
   primary key (menuValueID)
)
ENGINE = MYISAM;

alter table menusValue add constraint FK_FK_MENUS_VALUE foreign key (id)
      references menus (id) on delete restrict on update restrict;
