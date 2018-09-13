/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2007-8-26 15:26:26                           */
/*==============================================================*/

drop table if exists I18n;

drop table if exists ProductBase;

drop table if exists ProductCategory;

drop table if exists ProductCategoryValue;

drop table if exists RechargeRecord;

drop table if exists ShopApp;

drop table if exists ShopStyleConfig;

drop table if exists UserShop;

drop table if exists shopDescArticle;

drop table if exists Shop;

drop table if exists ShopValue;

drop table if exists User;

drop table if exists SysParam;

drop table if exists ProductLog;

drop table if exists AccountValidateTask;

drop table if exists comments;

drop table if exists CustOrder;

drop table if exists emailTemplate;

drop table if exists InfoNews;

drop table if exists leaveMessage;

drop table if exists SocialOathToken;

drop table if exists UserActivity;

drop table if exists UserNotification;

drop table if exists UserProdBuyLog;

drop table if exists UserProdStatusLog;

drop table if exists UseComment;


/*==============================================================*/
/* Table: SysParam                                              */
/*==============================================================*/
create table SysParam
(
   code                 varchar(64) not null,
   value                varchar(16),
   typeCode             varchar(32),
   syscode              char(8),
   notes                varchar(128),
   primary key (code)
)
;

/*==============================================================*/
/* Table: SysConst                                              */
/*==============================================================*/
create table SysConst
(
   sysConstID           bigint not null auto_increment,
   sysConstCode         varchar(64) not null,
   syscode              char(8),
   getValueWay          smallint,
   valueWay             varchar(64),
   primary key (sysConstID)
)
;

/*==============================================================*/
/* Table: SysConstVal                                           */
/*==============================================================*/
create table SysConstVal
(
   constValueID         bigint not null auto_increment,
   sysConstID           bigint,
   localeID             bigint,
   name                 varchar(64) comment '常量名称',
   primary key (constValueID)
);

/*==============================================================*/
/* Table: SysConstItem                                          */
/*==============================================================*/
create table SysConstItem
(
   itemID               bigint not null auto_increment,
   sysConstCode         varchar(64) not null,
   value                varchar(16) comment 'each item value',
   orderNO              int,
   param1               varchar(8) comment 'param1',
   param2               varchar(8),
   primary key (itemID)
)
;

/*==============================================================*/
/* Index: LogicKey                                              */
/*==============================================================*/
create unique index LogicKey_SysConstItem on SysConstItem
(
   sysConstCode,
   value
);

/*==============================================================*/
/* Table: SysConstItemVal                                       */
/*==============================================================*/
create table SysConstItemVal
(
   itemValueID          bigint not null auto_increment,
   itemID               bigint,
   localeID             bigint,
   labelName            varchar(128) comment '显示内容',
   value                varchar(16),
   primary key (itemValueID)
)
;

/*==============================================================*/
/* Table: CountryRegion                                         */
/*==============================================================*/
create table CountryRegion
(
   regionCode           varchar(8) not null,
   localeID             bigint not null,
   country              varchar(8),
   name                 varchar(64),
   parentCode           varchar(8),
   hotRegion            smallint,
   primary key (regionCode, localeID)
)
;

/*==============================================================*/
/* Table: emailTemplate                                         */
/*==============================================================*/
create table emailTemplate
(
   id                   bigint not null auto_increment,
   templateName         varchar(128),
   recipient            varchar(128),
   fromwho              varchar(128),
   replyTo              varchar(128),
   subject              varchar(256),
   locale               char(5),
   syscode              char(8),
   content              varchar(2000),
   lasttime             datetime,
   primary key (id),
   unique key AK_mailLogicKey (templateName, locale, syscode)
)
;

create index Index_EmailTemplate on emailTemplate
(
   templateName,
   locale
);


/*==============================================================*/
/* Table: User                                           */
/*==============================================================*/
create table User
(
   userID               bigint not null auto_increment,
   displayname          varchar(64),
   loginname            varchar(64) not null,
   email                varchar(128),
   pass                 varchar(64),
   gender               smallint,
   localeID             bigint,
   timezone             varchar(50),
   lcoinRemain          double,
   regisTime            datetime,
   userType             smallint,
   status               smallint,
   stageStatus          smallint,
   primary key (userID)
);

create index Index_User on User
(
   loginname,
   email
);


/*==============================================================*/
/* Table: UserContactInfo                                          */
/*==============================================================*/
create table UserContactInfo
(
   userID               bigint not null,
   regionCode           varchar(8),
   address              varchar(256),
   telephone            varchar(128),
   primary key (userID)
)
;


/*==============================================================*/
/* Table: CustInfoEx                                            */
/*==============================================================*/
create table CustInfoEx
(
   userID               bigint not null,
   birthday             varchar(10),
   tipQuestion          varchar(128),
   tipAnswer            varchar(128),
   occupation           varchar(128),
   creator              bigint,
   notes                varchar(128),
   primary key (userID)
);


/*==============================================================*/
/* Table: CustStatus                                            */
/*==============================================================*/
create table CustStatus
(
   CustStatusID         bigint not null auto_increment,
   userID               bigint,
   bfStatus             smallint,
   afStatus             smallint,
   statusTime           datetime,
   statusDisc           varchar(128),
   primary key (CustStatusID)
)
;

/*==============================================================*/
/* Table: UserAccountSetting                                    */
/*==============================================================*/
create table UserAccountSetting
(
   userID               bigint not null,
   shopCreateable       smallint default 1,
   primary key (userID)
);


/*==============================================================*/
/* Table: AccountValidateTask                                   */
/*==============================================================*/
create table AccountValidateTask
(
   id                   bigint not null auto_increment,
   userID               bigint not null,
   email                varchar(128),
   validateType         varchar(32),
   validateCode         varchar(128),
   status               smallint not null,
   createDate           datetime not null,
   aliveEndDate         datetime,
   activeDate           datetime,
   primary key (id)
);

create index index_AccountValidateTask on AccountValidateTask
(
   userID,
   validateType
);


/*==============================================================*/
/* Table: UserActivity                                          */
/*==============================================================*/
create table UserActivity
(
   activityID           bigint not null auto_increment,
   userID               bigint not null,
   actionType           varchar(32) not null comment 'UpdateForgotPassword  , current, only log this action',
   targetObject         varchar(32) comment 'target object, format is:  objectType:objectID',
   result               varchar(8) comment 'SUCCESS or FAIL',
   note                 varchar(128),
   createTime           datetime not null,
   primary key (activityID)
);


/*==============================================================*/
/* Table: SysFunctionItem                                           */
/*==============================================================*/
create table SysFunctionItem
(
   functionCode         char(8) not null,
   functionName         varchar(64),
   syscode              char(8),
   payType              smallint,
   cost                 double,
   isMust               smallint,
   startDate            datetime,
   status               smallint,
   funcDesc             varchar(128),
   primary key (functionCode)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: I18n                                                  */
/*==============================================================*/
create table I18n
(
   localeID             bigint not null auto_increment,
   language             varchar(3),
   country              varchar(3),
   isSet                smallint,
   isDefaultSet         smallint,
   primary key (localeID)
)
;

/*==============================================================*/
/* Table: UserNotification                                          */
/*==============================================================*/
create table UserNotification
(
   notifyID             bigint not null auto_increment,
   notifyType           smallint,
   messCode             varchar(128),
   content              varchar(1000),
   objectName           varchar(128),
   toUserID             bigint,
   toUserName           varchar(64),
   createTime           datetime,
   creatorID            bigint,
   creatorName          varchar(64),
   isRead               smallint,
   linkUrl              varchar(256),
   openLinkType         smallint default 1,
   primary key (notifyID)
);

create index Index_UserNotification_touser on UserNotification
(
   toUserID
);

create index Index_UserNotification_creator on UserNotification
(
   creatorID
);

/*==============================================================*/
/* Table: CustOrder                                             */
/*==============================================================*/
create table CustOrder
(
   orderID              bigint not null auto_increment,
   shopID               bigint,
   userID               bigint,
   orderCode            varchar(32),
   orderName            varchar(32),
   orderType            smallint,
   orderTime            datetime not null,
   baseAccountID        bigint,
   fullCost             double,
   discount             float(6),
   fullPayTime          datetime,
   payWay               smallint,
   payStatus            smallint,
   orderStatus          smallint,
   operatorID           bigint,
   approveDate          datetime,
   primary key (orderID)
);

create index Index_CustOrder on CustOrder
(
   shopID,
   userID,
   orderCode,
   orderStatus
);

/*==============================================================*/
/* Table: CustOrderExt                                          */
/*==============================================================*/
create table CustOrderExt
(
   orderID              bigint not null,
   notes                varchar(600),
   appNotes             varchar(600),
   primary key (orderID)
);

/*==============================================================*/
/* Table: OrderProduct                                          */
/*==============================================================*/
create table OrderProduct
(
   productBaseID        bigint not null,
   orderID              bigint,
   orderCode            varchar(32), 
   shopID               bigint,
   userID               bigint,
   productName          varchar(128),
   productPrice         double,
   quantity             int default 1,
   payType              smallint,
   useLimitNum          int,
   status               smallint,
   primary key (productBaseID, orderID)
);

/*==============================================================*/
/* Table: ProductBase                                           */
/*==============================================================*/
create table ProductBase
(
   productBaseID        bigint not null auto_increment,
   categoryID           bigint,
   shopID               bigint,
   productName          varchar(128),
   productPrice         double,
   payType              smallint, 
   useLimitNum          int,
   canTry               smallint,
   tryUseType           smallint,
   tryUseLimitNum       int,
   isNeedApprove        smallint,
   promotable           smallint,
   isOpen               smallint,
   status               smallint,
   logoImage            varchar(128),
   primary key (productBaseID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: ProductExt                                            */
/*==============================================================*/
create table ProductExt
(
   productBaseID        bigint not null,
   productDesc          text,
   primary key (productBaseID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: ProductSatis                                          */
/*==============================================================*/
create table ProductSatis
(
   productBaseID        bigint not null,
   warequesNum          bigint default 0,
   learncontDocNum      int default 0,
   learncontMediaNum    int default 0,
   paperNum             int default 0,
   exerciseNum          int default 0,
   testinfoNum          int default 0,
   currentLearnUserNum  int default 0,
   allLearnedUserNum    int default 0,
   primary key (productBaseID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: GoodProduct                                           */
/*==============================================================*/
create table GoodProduct
(
   productBaseID        bigint not null auto_increment,
   categoryID           bigint,
   shopID               bigint,
   goodValue            int default 9999,
   fromSource           smallint,
   scope                smallint default 1,
   lastDate             datetime,
   primary key (productBaseID, categoryID)
)
;

/*==============================================================*/
/* Table: UseComment                                            */
/*==============================================================*/
create table UseComment
(
   commentID            bigint not null auto_increment,
   grade                smallint not null,
   content              varchar(128),
   objectID             bigint not null,
   objectType           varchar(16) not null,
   userID               bigint not null,
   updateDate           datetime not null,
   primary key (commentID)
)
ENGINE = MYISAM;

/*==============================================================*/
/* Table: ProductCategory                                       */
/*==============================================================*/
create table ProductCategory
(
   categoryID           bigint not null auto_increment,
   localeID             bigint,
   categoryName         varchar(128),
   pID                  bigint,
   categoryLevel        smallint,
   categoryDesc         varchar(256),
   disOrder             int,
   path                 varchar(200),
   status               smallint not null,
   primary key (categoryID)
)
ENGINE = InnoDB;

create index Index_productcategory on ProductCategory
(
   localeID
);


/*==============================================================*/
/* Table: HotCategory                                           */
/*==============================================================*/
create table HotCategory
(
   categoryID           bigint not null,
   localeID             bigint not null,
   syscode              char(8) not null,
   pID                  bigint,
   disorder             int,
   primary key (categoryID, localeID, syscode)
);

/*==============================================================*/
/* Table: ProductLog                                            */
/*==============================================================*/
create table ProductLog
(
   productID            bigint not null,
   productName          varchar(128),
   logType              smallint not null,
   shopID               bigint not null,
   bfStatus             smallint,
   afStatus             smallint,
   creatorID            bigint not null,
   logDate              datetime not null,
   note                 varchar(128),
   primary key (productID, logType, logDate)
);

/*==============================================================*/
/* Table: SysProductCate                                        */
/*==============================================================*/
create table SysProductCate
(
   categoryID           bigint not null,
   syscode              char(8) not null,
   primary key (categoryID, syscode)
);

/*==============================================================*/
/* Table: NewsCategory                                          */
/*==============================================================*/
create table NewsCategory
(
   id                   bigint not null auto_increment,
   categoryID           bigint,
   type                 smallint not null default 1,
   orderNo              int,
   primary key (id)
);

/*==============================================================*/
/* Table: NewsCategoryValue                                     */
/*==============================================================*/
create table NewsCategoryValue
(
   valueID              bigint not null auto_increment,
   newsCategoryID       bigint not null,
   localeID             bigint not null,
   name                 varchar(256),
   primary key (valueID)
);

/*==============================================================*/
/* Table: SocialNewsSource                                      */
/*==============================================================*/
create table SocialNewsSource
(
   id                   bigint not null auto_increment,
   newsCategoryID       bigint not null,
   source               varchar(64) not null comment 'google+ or facebook',
   fromAccount          varchar(128) not null comment 'get news from which account',
   syncTime             datetime comment 'get news to which time, program will get all news from this time till now ',
   lastUpdateTime       datetime not null comment 'last update time, each sync news will update this time',
   notes                varchar(128),
   primary key (id),
   unique key AK_unique_source (newsCategoryID, source, fromAccount)
);

/*==============================================================*/
/* Table: ProdCateShop                                          */
/*==============================================================*/
create table ProdCateShop
(
   shopID               bigint not null,
   categoryID           bigint not null,
   syscode              char(8) not null,
   pID                  bigint,
   primary key (shopID, categoryID, syscode)
);

/*==============================================================*/
/* Table: ProductCategoryValue                                  */
/*==============================================================*/
create table ProductCategoryValue
(
   categoryValueID      bigint not null auto_increment,
   localeID             bigint,
   categoryID           bigint,
   categoryName         varchar(128),
   categoryDesc         varchar(256),
   disOrder             int,
   primary key (categoryValueID)
);

/*==============================================================*/
/* Table: RechargeRecord                                        */
/*==============================================================*/
create table RechargeRecord
(
   rechargeID           bigint not null auto_increment,
   baseAccountID        bigint,
   userID               bigint,
   userType             smallint,
   rechargeTime         datetime,
   rechargeMoney        double,
   moneyType            varchar(64),
   lcoinQuantity        double,
   lcoinRemain          double,
   primary key (rechargeID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: Shop                                                  */
/*==============================================================*/
create table Shop
(
   shopID               bigint not null auto_increment,
   localeID             bigint,
   shopName             varchar(128),
   shopCode             varchar(32) not null,
   userID               bigint,
   openType             smallint,
   regisDate            datetime,
   userNum              int,
   userNumScale         varchar(16),
   bizArea              int,
   ownerType            smallint,
   chargeType           smallint,
   isAuthen             smallint,
   shopStatus           smallint,
   primary key (shopID)
)
ENGINE = InnoDB;

create index Index_Shop_code on Shop
(
   shopCode
);


/*==============================================================*/
/* Table: ShopExt                                               */
/*==============================================================*/
create table ShopExt
(
   shopID               bigint not null,
   authenIDType         smallint,
   authenIdNo           varchar(64),
   authenImage          varchar(128),
   applyChargeType      smallint,
   applyUserID          bigint,
   approveUserID        bigint,
   authenStatus         smallint,
   authenDesc           varchar(250),
   authenDate           datetime,
   chargeDate           datetime,
   payInfo              varchar(500),
   primary key (shopID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: ShopStyleConfig                                            */
/*==============================================================*/
create table ShopStyleConfig
(
   shopID               bigint not null,
   pageStyle            varchar(64) comment 'e.g: blue style, green style, yellow style . etc',
   pageTemplate         varchar(64) comment 'template',
   bannerImg            varchar(128) comment 'path to the image',
   primary key (shopID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: ShopValue                                             */
/*==============================================================*/
create table ShopValue
(
   shopValueID          bigint not null auto_increment,
   shopID               bigint,
   localeID             bigint,
   isDefaultSet         smallint, 
   shopName             varchar(128) comment '网店名称',
   shopDesc             text,
   primary key (shopValueID)
)
comment = "记录商店多国文字信息"
ENGINE = InnoDB;

/*==============================================================*/
/* Table: shopDescArticle                                       */
/*==============================================================*/
create table shopDescArticle
(
   articleID            bigint not null auto_increment,
   shopID               bigint,
   articleType          smallint,
   createTime           datetime,
   creator              bigint,
   content              text,
   primary key (articleID)
);

/*==============================================================*/
/* Table: ShopFunc                                              */
/*==============================================================*/
create table ShopFunc
(
   shopFuncID           bigint not null auto_increment,
   shopID               bigint,
   functionCode         char(8) not null,
   payType              smallint,
   syscode              char(8),
   cost                 double,
   isPay                smallint,
   startDate            datetime,
   endDate              datetime,
   status               smallint,
   primary key (shopFuncID)
)
;

/*==============================================================*/
/* Table: ShopLicense                                           */
/*==============================================================*/
create table ShopLicense
(
   shopID               bigint,
   syscode              char(8),
   resourceCode         varchar(32),
   allocateNum          bigint,
   useNum               bigint,
   updateDate           datetime,
   primary key (shopID, syscode, resourceCode)
);

/*==============================================================*/
/* Table: ShopStatusLog                                         */
/*==============================================================*/
create table ShopStatusLog
(
   logID                bigint not null auto_increment,
   shopID               bigint,
   logType              smallint not null,
   bfStatus             smallint,
   afStatus             smallint,
   statusTime           datetime,
   statusDisc           varchar(128),
   primary key (logID)
)
;

/*==============================================================*/
/* Table: ShopApp                                               */
/*==============================================================*/
create table ShopApp
(
   shopAppID            bigint not null auto_increment,
   shopName             varchar(128),
   shopCode             varchar(32) not null,
   shopID               bigint,
   localeID             bigint,
   userID               bigint,
   openType             smallint,
   userNumScale         varchar(16),
   ownerType            smallint,
   bizArea              int,
   appDate              datetime,
   appStatus            smallint,
   replyDate            datetime,
   regionCode           varchar(8),
   contactname          varchar(64),
   email                varchar(128),
   telephone             varchar(128),
   postcode             varchar(16),
   address              varchar(256),
   notes                varchar(200),
   primary key (shopAppID)
)
;

create index Index_ShopApp on ShopApp
(
   userID,
   appStatus
);

/*==============================================================*/
/* Table: ShopContactInfo                                       */
/*==============================================================*/
create table ShopContactInfo
(
   contactInfoID        bigint not null auto_increment,
   shopID               bigint,
   localeID             bigint,
   regionCode           varchar(8),
   contactname          varchar(64),
   email                varchar(128),
   telephone            varchar(128),
   postcode             varchar(16),
   address              varchar(1000),
   isdefault            smallint,
   primary key (contactInfoID)
)
;

/*==============================================================*/
/* Table: BaseAccount                                           */
/*==============================================================*/
create table BaseAccount
(
   baseAccountID        bigint not null auto_increment,
   objectID             bigint,
   accountCode          varchar(32),
   accountType          smallint,
   createTime           datetime,
   accountNum           double,
   prepayNum            double,
   status               smallint,
   statusDisc           varchar(128) comment '状态描述',
   primary key (baseAccountID)
)
comment = "记录了用户的账户信息";


/*==============================================================*/
/* Table: CostRecord                                            */
/*==============================================================*/
create table CostRecord
(
   costRecordID         bigint not null auto_increment,
   baseAccountID        bigint,
   objectID             bigint comment '对象ID，可以是用户ID或商店ID',
   accountType          smallint,
   needPayTime          date comment '即该笔费用应该什么时间缴纳。一般为年月格式。',
   payTime              datetime comment '缴纳费用的时间，如果没有缴纳，则为空',
   totalCost            double comment '本月该店总共应交多少点数',
   isPay                smallint,
   isCurCost            smallint comment '方便查询，是否是本期的费用',
   costDisc             varchar(128),
   primary key (costRecordID)
)
comment = "记录客户的消费总表"
ENGINE = InnoDB;


/*==============================================================*/
/* Table: UserProduct                                           */
/*==============================================================*/
create table UserProduct
(
   userProductID        bigint not null auto_increment,
   productBaseID        bigint,
   userID               bigint,
   shopID               bigint,
   geneType             smallint, 
   payType              smallint,
   cost                 double,
   prodUseType          smallint,
   startDate            datetime,
   endDate              datetime,
   isPay                smallint,
   useLimitNum          int,
   useNum               int,
   status               smallint,
   statusRank           smallint,
   lastAccessTime       datetime,
   primary key (userProductID)
);


/*==============================================================*/
/* Table: UserProdBuyLog                                        */
/*==============================================================*/
create table UserProdBuyLog
(
   orderID              bigint,
   userProductID        bigint not null,
   userID               bigint,
   productBaseID        bigint,
   productName          varchar(128),
   shopID               bigint,
   action               smallint not null,
   happenDate           datetime,
   primary key (userProductID, action)
)
;

create index Index_userprodbuylog on UserProdBuyLog
(
   userID,
   productBaseID
);

/*==============================================================*/
/* Table: UserProdStatusLog                                     */
/*==============================================================*/
create table UserProdStatusLog
(
   logID                bigint not null auto_increment,
   userProductID        bigint,
   userID               bigint,
   productBaseID        bigint,
   productName          varchar(128),
   shopID               bigint,
   bfStatus             smallint not null,
   afStatus             smallint not null,
   happenDate           datetime,
   bfStatusRank         smallint,
   afStatusRank         smallint,
   opertorID            bigint,
   statusDisc           varchar(128),
   primary key (logID)
);

create index Index_userprodstatuslog on UserProdStatusLog
(
   userProductID,
   userID,
   productBaseID
);

/*==============================================================*/
/* Table: UserShop                                              */
/*==============================================================*/
create table UserShop
(
   userShopID           bigint not null auto_increment,
   shopID               bigint,
   userID               bigint,
   nickName             varchar(64),
   loginname            varchar(64),
   userShopType         smallint,
   joinDate             datetime,
   joinWay              smallint,
   areaInProduct        smallint,
   userShopStatus       smallint,
   userShopNotes        varchar(128),
   primary key (userShopID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: UsrCosRecDet                                          */
/*==============================================================*/
create table UsrCosRecDet
(
   usrCosRecDetID       bigint not null auto_increment,
   costRecordID         bigint,
   baseAccountID        bigint,
   orderID              bigint,
   orderItemID          bigint,
   userID               bigint,
   shopID               bigint,
   payTime              datetime,
   payType              smallint,
   lcoinCost            double,
   isPay                smallint,
   costType             smallint,
   costDisc             varchar(128),
   primary key (usrCosRecDetID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: ShopCosRecDet                                         */
/*==============================================================*/
create table ShopCosRecDet
(
   shopCosRecDetID      bigint not null auto_increment,
   costRecordID         bigint,
   baseAccountID        bigint,
   shopID               bigint,
   functionCode         bigint,
   systemID             char(8),
   functionName         varchar(64),
   payType              smallint,
   payTime              datetime,
   cost                 bigint,
   isPay                smallint,
   costType             smallint,
   costDisc             varchar(128),
   primary key (shopCosRecDetID)
)
;

/*==============================================================*/
/* Table: TodoTasks                                             */
/*==============================================================*/
create table TodoTasks
(
   taskID               bigint not null auto_increment,
   taskName             varchar(128),
   parameter            bigint,
   taskType             int,
   runType              smallint,
   runTime              datetime,
   intervTime           bigint,
   status               smallint,
   createTime           datetime,
   syscode              char(8),
   primary key (taskID)
)
;

/*==============================================================*/
/* Table: ShopPost                                              */
/*==============================================================*/
create table ShopPost
(
   id                   bigint not null auto_increment,
   shopID               bigint,
   syscode              char(8),
   caption              varchar(256),
   createTime           datetime,
   creator              bigint,
   type                 smallint,
   logoUrl              varchar(256),
   openUrl              varchar(256),
   refID                bigint,
   status               smallint,
   orderNo              int,
   primary key (id)
)
;

/*==============================================================*/
/* Table: ShopPostText                                          */
/*==============================================================*/
create table ShopPostText
(
   id                   bigint not null,
   content              text,
   primary key (id)
);

/*==============================================================*/
/* Table: InfoNews                                              */
/*==============================================================*/
create table InfoNews
(
   id                   bigint not null auto_increment,
   shopID               bigint,
   syscode              char(8),
   caption              varchar(256),
   createTime           datetime,
   creator              bigint,
   type                 smallint,
   logoUrl              varchar(256),
   openUrl              varchar(256),
   refID                bigint,
   categoryID           bigint,
   status               smallint,
   orderNo              int,
   primary key (id)
)
;

create index Index_InfoNews on InfoNews
(
   type,
   refID
);

/*==============================================================*/
/* Table: InfoNewsText                                          */
/*==============================================================*/
create table InfoNewsText
(
   id                   bigint not null,
   content              text,
   primary key (id)
);


/*==============================================================*/
/* Table: comments                                              */
/*==============================================================*/
create table comments
(
   commentID            bigint not null auto_increment,
   content              varchar(2000),
   parent               bigint,
   creatorID            bigint,
   updateDate           datetime,
   objectID             varchar(32),
   objectType           varchar(16),
   sourceUrl            varchar(128),
   subClassID           varchar(32),
   sourceType           varchar(16),
   policyScope          smallint,
   syscode              char(8),
   subNum               int default 0,
   primary key (commentID)
);

create index Index_Comments on comments
(
   objectID,
   objectType,
   subClassID
);


/*==============================================================*/
/* Table: leaveMessage                                          */
/*==============================================================*/
create table leaveMessage
(
   messID               bigint not null auto_increment,
   content              varchar(2000),
   parent               bigint,
   creatorID            bigint,
   updateDate           datetime,
   messType             smallint,
   objectID             varchar(32),
   objectType           varchar(16),
   syscode              char(8),
   primary key (messID)
);

create index Index_Leavemessage on leaveMessage
(
   objectID,
   objectType
);

/*==============================================================*/
/* Table: SocialOathToken                                       */
/*==============================================================*/
create table SocialOathToken
(
   identityID           bigint not null comment 'it could be userID or shopID',
   identityType         smallint not null comment '1: userID,  2: shopID',
   serviceType          smallint not null comment '1: google account , 2: MSN account , 3: facebook account , 4: dropbox account , 5: QQ acount',
   socialUserID         varchar(128) comment 'user id in social app, e.g: in facebook/google',
   socialUserAccount    varchar(128),
   displayname          varchar(64),
   refreshToken         varchar(1024),
   accessToken          varchar(1024),
   updateTimeRefresh    datetime,
   updateTimeAccess     datetime,
   createDate           datetime,
   primary key (identityID, identityType, serviceType, socialUserID)
);

create index Index_socialoathtoken on SocialOathToken
(
   identityID,
   identityType,
   serviceType
);

create unique index Index_Socialuser on SocialOathToken
(
   identityType,
   serviceType,
   socialUserID
);

/*==============================================================*/
/* Table: SystemLog                                             */
/*==============================================================*/
create table SystemLog
(
   logID                bigint not null auto_increment,
   logCode              varchar(64),
   createTime           datetime,
   endTime              datetime,
   result               smallint,
   syscode              char(8),
   content              varchar(500),
   primary key (logID)
);

/*==============================================================*/
/* Table: ReverseSearch                                         */
/*==============================================================*/
create table ReverseSearch
(
   searchText           varchar(128),
   entityID             bigint,
   entityType           varchar(32),
   createTime           datetime
);

/*==============================================================*/
/* Index: Index_ReverseSearch                                   */
/*==============================================================*/
create index Index_ReverseSearch on ReverseSearch
(
   searchText,
   entityType
);

/*==============================================================*/
/* Table: sequence_orderCode   */
/*==============================================================*/
create table sequence_orderCode
(
   id           bigint not null auto_increment,
   nouse        varchar(1),
   primary key (id)
);

/*==============================================================*/
/* Table: OpenActivity                                      */
/*==============================================================*/
create table OpenActivity
(
   activityID           bigint not null auto_increment,
   name                 varchar(128) not null,
   startTime            datetime not null,
   endTime              datetime not null comment 'how long of the activity, minutes',
   joinType             smallint not null comment '1: course training or activity offline  2: online activity',
   onlineUrl            varchar(256) comment 'when joinType is online, how to join it',
   localeID             bigint,
   regionCode           varchar(8) comment 'refer to table: CountryRegion',
   place                varchar(256) comment 'when joinType is offline, where to join it',
   contactInfo          varchar(256) not null,
   shopID               bigint not null,
   categoryID           bigint not null comment 'categoryID of the activity',
   productid            bigint comment 'reference to product',
   creatorID            bigint comment 'who created it',
   joinStatus           smallint not null default 0 comment '1: can join,  2: cannot join, Participant is full',
   status               smallint not null default 1 comment '1: scheduled   2: ended',
   userNum              int default 0 comment 'joined user number',
   createTime           datetime not null,
   lastUpdateTime       datetime not null,
   primary key (activityID)
)
comment = "shop arranged open activities. everyone can register the acitvity, it usually is training or visit";


/*==============================================================*/
/* Table: OpenActivityContent                                   */
/*==============================================================*/
create table OpenActivityContent
(
   activityID           bigint not null,
   shopID               bigint not null,
   description          varchar(4000) not null,
   primary key (activityID)
);

alter table OpenActivityContent add constraint FK_OpenActivityContent foreign key (activityID)
      references OpenActivity (activityID) on delete restrict on update restrict;


/*==============================================================*/
/* Table: OpenActivityMember                                */
/*==============================================================*/
create table OpenActivityMember
(
   activityID           bigint not null,
   userID               bigint not null,
   displayName          varchar(64),
   phone                varchar(32),
   otherContactInfo     varchar(64),
   registerTime         datetime not null,
   joinStatus           smallint not null default 1 comment '1: registered 2: joined activity',
   joinedTime           datetime comment 'joined time',
   note                 varchar(128) comment 'activity admin can take note on users',
   primary key (activityID, userID)
);

alter table OpenActivityMember add constraint FK_OpenActivity_Member foreign key (activityID)
      references OpenActivity (activityID) on delete restrict on update restrict;


/*============================== below is the table constrain ================================*/

alter table InfoNewsText add constraint FK_InfoNews_Text foreign key (id)
      references InfoNews (id) on delete restrict on update restrict;

alter table ShopPost add constraint FK_ShopPost foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;
	  
alter table ShopPostText add constraint FK_ShopPost_Text foreign key (id)
      references ShopPost (id) on delete restrict on update restrict;

alter table SysConstVal add constraint FK_sysconst_value foreign key (sysConstID)
      references SysConst (sysConstID) on delete restrict on update restrict;

alter table SysConstItemVal add constraint FK_sysconstitem_val foreign key (itemID)
      references SysConstItem (itemID) on delete restrict on update restrict;

alter table UsrCosRecDet add constraint FK_Reference_115 foreign key (costRecordID)
      references CostRecord (costRecordID) on delete restrict on update restrict;

alter table ShopCosRecDet add constraint FK_costRecord_shop foreign key (costRecordID)
      references CostRecord (costRecordID) on delete restrict on update restrict;

alter table ProductBase add constraint FK_Reference_27 foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;

alter table ProductBase add constraint FK_Reference_7 foreign key (categoryID)
      references ProductCategory (categoryID) on delete restrict on update restrict;

alter table ProductCategoryValue add constraint FK_Reference_33 foreign key (categoryID)
      references ProductCategory (categoryID) on delete restrict on update restrict;

alter table CostRecord add constraint FK_costRec_baseAccount foreign key (baseAccountID)
      references BaseAccount (baseAccountID) on delete restrict on update restrict;

alter table Shop add constraint FK_ShopCreator_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table ShopValue add constraint FK_Reference_21 foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;

alter table UserProduct add constraint FK_Reference_101 foreign key (productBaseID)
      references ProductBase (productBaseID) on delete restrict on update restrict;

alter table UserProduct add constraint FK_UserProduct_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table UserShop add constraint FK_UserShop_Shop foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;

alter table UserShop add constraint FK_UserShop_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table ShopFunc add constraint FK_Reference_109 foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;

alter table ShopFunc add constraint FK_Reference_116 foreign key (functionCode)
      references SysFunctionItem (functionCode) on delete restrict on update restrict;

alter table CustOrder add constraint FK_Order_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table CustInfoEx add constraint FK_CustInfoEx_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table CustStatus add constraint FK_CustStatus_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table ShopStatusLog add constraint FK_Shop_StatusLog foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;
      
alter table CustOrderExt add constraint FK_custorder_ext foreign key (orderID)
      references CustOrder (orderID) on delete restrict on update restrict;

alter table OrderProduct add constraint FK_OrderProduct foreign key (orderID)
      references CustOrder (orderID) on delete restrict on update restrict;
	  
alter table shopDescArticle add constraint FK_Shop_Article foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;
	  
alter table UserAccountSetting add constraint FK_UserAccountSetting foreign key (userID)
      references User (userID) on delete restrict on update restrict;
	  
alter table ShopExt add constraint FK_ShopExt foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;
	  
alter table ShopContactInfo add constraint FK_ShopContactInfo foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;

alter table ShopStyleConfig add constraint FK_ShopStyleConfig foreign key (shopID)
      references Shop (shopID) on delete restrict on update restrict;
	  
alter table NewsCategory add constraint FK_NewsCategory foreign key (categoryID)
      references ProductCategory (categoryID) on delete restrict on update restrict;
	  
alter table UserActivity add constraint FK_UserActivity foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table UserContactInfo add constraint FK_Contract_User foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table SysProductCate add constraint FK_SysProduct_Cate foreign key (categoryID)
      references ProductCategory (categoryID) on delete restrict on update restrict;

alter table NewsCategoryValue add constraint FK_NewsCategoryValue foreign key (newsCategoryID)
      references NewsCategory (id) on delete restrict on update restrict;

alter table SocialNewsSource add constraint FK_SocialNewsSource foreign key (newsCategoryID)
      references NewsCategory (id) on delete restrict on update restrict;
