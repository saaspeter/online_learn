/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2007-8-26 15:26:26                           */
/*==============================================================*/

drop table if exists DeptInfo;

drop table if exists OrgBase;

drop table if exists OrgBaseRel;

drop table if exists OrgUser;

drop table if exists Paper;

drop table if exists PaperQues;

drop table if exists UserAnswer;

drop table if exists ProductGroup;

drop table if exists Question;

drop table if exists QuestionItem;

drop table if exists TestDept;

drop table if exists TestInfo;

drop table if exists TestUser;

drop table if exists Ware;

drop table if exists WareRelOpen;

drop table if exists ContentCate;

drop table if exists Exercise;

drop table if exists LearnContent;

drop table if exists OrgBase;

drop table if exists UserExerAnswer;

drop table if exists CoursePost;


/*==============================================================*/
/* Table: QuestypeShop                                          */
/*==============================================================*/
create table QuestypeShop
(
   quesTypeID           bigint not null auto_increment,
   quesTypeName         varchar(60),
   shopID               bigint,
   productBaseID        bigint,
   createType           smallint,
   questypeValue        int,
   createDate           datetime,
   primary key (quesTypeID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: DeptInfo                                              */
/*==============================================================*/
create table DeptInfo
(
   orgBaseID            bigint not null,
   deptChildNum         int comment '子单位的个数',
   shopID               bigint,
   isSetForNew          smallint,
   primary key (orgBaseID)
)
comment = "单位信息数据"
ENGINE = InnoDB;


/*==============================================================*/
/* Table: OrgBase                                               */
/*==============================================================*/
create table OrgBase
(
   orgBaseID            bigint not null auto_increment,
   shopID               bigint not null,
   orgName              varchar(128),
   orgNameSim           varchar(128),
   createOrg            bigint,
   createTime           datetime,
   pID                  bigint,
   orgLevel             int,
   path                 varchar(250),
   orgDesc              varchar(128),
   orgType              smallint,
   orgStatus            smallint,
   primary key (orgBaseID)
)
ENGINE = InnoDB;

create index Index_Orgbase on OrgBase
(
   shopID,
   pID
);


/*==============================================================*/
/* Table: OrgBaseRel                                            */
/*==============================================================*/
create table OrgBaseRel
(
   orgBaseRelID         bigint not null auto_increment,
   shopID               bigint,
   parentID             bigint,
   childID              bigint,
   relType              smallint,
   primary key (orgBaseRelID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Index: DeptLevelShow11                                       */
/*==============================================================*/
create index DeptLevelShow11 on OrgBaseRel
(
   parentID
);

/*==============================================================*/
/* Index: DeptLevelShow22                                       */
/*==============================================================*/
create index DeptLevelShow22 on OrgBaseRel
(
   childID
);


/*==============================================================*/
/* Table: OrgUser                                               */
/*==============================================================*/
create table OrgUser
(
   orgUserID            bigint not null auto_increment,
   userID               bigint not null,
   orgBaseID            bigint not null,
   shopID               bigint,
   orgpath              varchar(250),
   primary key (orgUserID)
);
ALTER TABLE OrgUser ADD UNIQUE (userID, shopID);


/*==============================================================*/
/* Table: ContentCate                                           */
/*==============================================================*/
create table ContentCate
(
   contentCateID        bigint not null auto_increment,
   productBaseID        bigint,
   contentCateName      varchar(128) not null,
   shopID               bigint,
   description          varchar(128),
   pID                  bigint,
   path                 varchar(200),
   disOrder             int,
   primary key (contentCateID)
)
comment = "产品内容的目录分类，可以给考试中的问题使用，也可以给学习系统中的文档资料用。有上下级关系";

create index Index_ContentCate on ContentCate
(
   productBaseID
);


/*==============================================================*/
/* Table: Paper                                                 */
/*==============================================================*/
create table Paper
(
   paperID              bigint not null auto_increment,
   shopID               bigint,
   paperNO              varchar(32),
   paperName            varchar(90),
   creatorID            bigint,
   createTime           datetime,
   orgBaseID            bigint,
   paperTotalScore      float(8),
   paperTime            int,
   paperQuaScore        float(8),
   productBaseID        bigint,
   contentCateID        bigint,
   geneType             smallint,
   phase                smallint,
   wareIdStr            varchar(250),
   status               smallint,
   modiTime             datetime,
   version              int not null default 1,
   primary key (paperID)
)
ENGINE = InnoDB;

create index Index_Paper on Paper
(
   productBaseID,
   contentCateID
);

/*==============================================================*/
/* Table: PaperProp                                             */
/*==============================================================*/
create table PaperProp
(
   paperID              bigint not null,
   shopID               bigint,
   orgName              varchar(128),
   productName          varchar(128),
   paperQuaPer          float(8),
   paperAverScore       float(8),
   paperUseNum          int,
   paperDesc            varchar(255),
   warenameStr          varchar(640),
   primary key (paperID)
)
;

/*==============================================================*/
/* Table: DynamicPaper                                          */
/*==============================================================*/
create table DynamicPaper
(
   dynPaperID           bigint not null,
   paperID              bigint not null,
   testID               bigint,
   createTime           datetime,
   status               smallint comment '1未使用；2使用过了',
   primary key (dynPaperID)
);

/*==============================================================*/
/* Table: PaperQuesType                                         */
/*==============================================================*/
create table PaperQuesType
(
   patternID            bigint not null auto_increment,
   paperID              bigint,
   shopID               bigint,
   quesTypeID           bigint, 
   quesType             int,
   quesTypeName         varchar(64),
   quesTypeNote         varchar(640),
   patternQuesNum       int,
   quesScore            float(8),
   patternScore         float(8),
   pageSize             int,
   disOrder             int,
   primary key (patternID)
)
;

/*==============================================================*/
/* Table: PaperPatternRatio                                          */
/*==============================================================*/
create table PaperPatternRatio
(
   paperID              bigint not null,
   shopID               bigint,
   quesTypeID           bigint not null,
   quesType             int not null,
   elementID            bigint not null,
   elementName          varchar(128),
   elementNum           int,
   elementRatio         float(8),
   elementType          smallint not null,
   primary key (paperID, quesTypeID, elementID, elementType)
);


/*==============================================================*/
/* Table: PaperQues                                             */
/*==============================================================*/
create table PaperQues
(
   paperquesID          bigint not null auto_increment,
   shopID               bigint,
   paperID              bigint not null,
   quesTypeID           bigint,
   quesType             int,
   quesCreateTime       datetime,
   productBaseID        bigint,
   paperQuesOrder       int,
   paperQuesScore       float(8),
   quesID               bigint not null,
   wareID               bigint,
   primary key (paperquesID)
)
Engine = MyISAM;

create index Index_PaperQues on PaperQues
(
   paperID,
   quesTypeID
);


/*==============================================================*/
/* Table: SEQ_QuesItemFlag                                      */
/*==============================================================*/
create table SEQ_QuesItemFlag
(
   id                   bigint not null
);
INSERT INTO SEQ_QuesItemFlag VALUES (0);

/*==============================================================*/
/* Table: RefPaperQues                                          */
/*==============================================================*/
create table RefPaperQues
(
   quesID               bigint not null,
   shopID               bigint,
   paperID              bigint not null,
   paperQuesOrder       int comment '题目顺序号，该题在试卷中的顺序',
   paperQuesScore       double comment '每题分值',
   wareID               bigint comment '记录题目所在的题库',
   status               smallint comment '有效（1），失效（2）',
   primary key (quesID, paperID)
)
;

/*==============================================================*/
/* Table: UserAnswer                                          */
/*==============================================================*/
create table UserAnswer
(
   userAnswerID         bigint not null auto_increment,
   shopID               bigint not null,
   testID               bigint not null,
   userID               bigint not null,
   paperID              bigint not null,
   quesID               bigint not null,
   quesItemFlag         varchar(20),
   quesTypeID           bigint,
   quesType             int,
   pID                  bigint,
   quesCheckType        smallint,
   answer               varchar(4000),
   answerScore          float(8),
   isRight              varchar(64),
   examineStatus        smallint,
   quesOrder            int,
   itemOrderStr         varchar(80),
   primary key (userAnswerID)
)
ENGINE = InnoDB;

create index Index_UserAnswer on UserAnswer
(
   testID,
   userID,
   paperID
);

/*==============================================================*/
/* Table: Question                                              */
/*==============================================================*/
create table Question
(
   quesID               bigint not null auto_increment,
   wareID               bigint not null,
   shopID               bigint,
   question             varchar(10000),
   quesTypeID           bigint,
   quesType             int,
   quesOptNum           int,
   quesCreateTime       datetime,
   quesCheckType        smallint,
   productBaseID        bigint,
   contentCateID        bigint,
   difficultID          int,
   fileUrl              varchar(128),
   fileType             smallint,
   answerSim            varchar(8),
   compType             smallint,
   rowItems             smallint default 1,
   pID                  bigint,
   refNum               int,
   creatorID            bigint,
   status               smallint,
   primary key (quesID)
)
Engine = MyISAM;

create index Index_Question on Question
(
   wareID,
   quesType,
   contentCateID
);


/*==============================================================*/
/* Table: QuesAnswer                                            */
/*==============================================================*/
create table QuesAnswer
(
   quesID               bigint not null,
   shopID               bigint,
   answerText           varchar(4000),
   solveDesc            varchar(4000),
   primary key (quesID)
)
Engine = MyISAM;

/*==============================================================*/
/* Table: QuestionItem                                          */
/*==============================================================*/
create table QuestionItem
(
   quesItemID           bigint not null auto_increment,
   quesID               bigint,
   shopID               bigint,
   quesItemFlag         varchar(20),
   quesItemContent      varchar(1000),
   isRight              smallint,
   fileUrl              varchar(128),
   fileType             smallint,
   disOrder             int,
   primary key (quesItemID)
)
Engine = MyISAM;

create index Index_Questionitem on QuestionItem
(
   quesID
);

/*==============================================================*/
/* Table: TestDept                                              */
/*==============================================================*/
create table TestDept
(
   testDeptID           bigint not null auto_increment,
   shopID               bigint,
   testID               bigint,
   orgBaseID            bigint not null,
   orgName              varchar(128),
   qualifyNum           int,
   selfQulifyNum        int,
   aveScore             float(8),
   selfAveScore         float(8),
   examerNum            int,
   selfExamerNum        int,
   examingNum           int,
   selfExamingNum       int,
   endExamNum           int,
   selfEndExamNum       int,
   type                 smallint,
   orgLevel             int,
   deptPath             varchar(200),
   primary key (testDeptID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: TestInfo                                              */
/*==============================================================*/
create table TestInfo
(
   testID               bigint not null auto_increment,
   shopID               bigint,
   testName             varchar(128),
   paperID              bigint,
   isDynamicPaper       smallint,
   paperVersion         int,
   paperTime            int,
   createTime           datetime,
   testStartDate        datetime,
   testEndDate          datetime,
   productBaseID        bigint,
   testStatus           smallint,
   createOrgID          bigint,
   createOrgName        varchar(128),
   productName          varchar(128),
   updateTime           datetime,
   openType             smallint,
   viewResultType       smallint,
   primary key (testID)
)
ENGINE = InnoDB;

create index Index_Testinfo on TestInfo
(
   shopID,
   testStartDate,
   testEndDate,
   productBaseID,
   testStatus
);

/*==============================================================*/
/* Table: TestInfoProp                                          */
/*==============================================================*/
create table TestInfoProp
(
   testID               bigint not null,
   shopID               bigint,
   testAveScore         float(8),
   testQuaPer           float(8),
   testCanStop          smallint,
   canstopTime          int,
   createUserID         bigint,
   createLoginname      varchar(64),
   createUserName       varchar(64),
   paperName            varchar(128),
   testNotes            varchar(255),
   primary key (testID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: TestUser                                            */
/*==============================================================*/
create table TestUser
(
   testUserID           bigint not null auto_increment,
   shopID               bigint,
   testID               bigint,
   userID               bigint not null,
   orgBaseID            bigint,
   paperID              bigint,
   startTime            datetime,
   leftTime             int,
   suspendTestTime      datetime,
   resumeTestTime       datetime,
   endTime              datetime,
   objectScore          float(8),
   totalScore           float(8),
   isQualify            smallint,
   status               smallint,
   orderNoDept          int,
   orderNoAll           int,
   primary key (testUserID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: TestChecker                                           */
/*==============================================================*/
create table TestChecker
(
   shopID               bigint,
   testID               bigint not null,
   userID               bigint not null,
   checkQuesNum         int,
   isExamCreator        smallint,
   primary key (testID, userID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: Ware                                                  */
/*==============================================================*/
create table Ware
(
   wareID               bigint not null auto_increment,
   shopID               bigint,
   wareName             varchar(60),
   wareCreateTime       datetime,
   productBaseID        bigint,
   wareQuesNum          bigint,
   creatorID            bigint,
   status               smallint,
   wareDesc             varchar(255),
   primary key (wareID)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: LearnContent                                          */
/*==============================================================*/
create table LearnContent
(
   contentID            bigint not null auto_increment,
   shopID               bigint,
   caption              varchar(256),
   productBaseID        bigint not null,
   contentCateID        bigint,
   contentType          int,
   linktype             smallint,
   linkFileID           varchar(400),
   linkFileSource       varchar(512),
   linkFileName         varchar(256),
   linkFileDate         datetime,
   linkUserID           varchar(64),
   filesize             bigint,
   isTry                smallint default 1,
   downloadable         smallint default 2,
   parentID             bigint,
   orderNo              int,
   createTime           datetime not null,
   lastupdateTime       datetime not null,
   creator              bigint,
   status               smallint,
   primary key (contentID)
)
;

create index Index_Learncontent on LearnContent
(
   productBaseID,
   contentCateID
);

/*==============================================================*/
/* Table: LearnContentText                                      */
/*==============================================================*/
create table LearnContentText
(
   contentID            bigint not null,
   content              text,
   primary key (contentID)
);


/*==============================================================*/
/* Table: CoursePost                                            */
/*==============================================================*/
create table CoursePost
(
   id                   bigint not null auto_increment,
   productBaseID        bigint,
   shopID               bigint,
   caption              varchar(256),
   creator              bigint,
   refObjectType        varchar(16),
   refObjectID          bigint,
   generateType         smallint comment '1: course admin create it in page.  2: auto generated by another object. e.g: creating a new examination, may create this post',
   openUrl              varchar(256) comment 'can forward it, e.g: when create a new examination, can forward to the examination page using this url',
   orderNo              int,
   content              varchar(400) comment 'only allow 400 characters',
   createTime           datetime,
   updateTime           datetime,
   primary key (id)
)
comment = "new post of Course"
ENGINE = MyISAM;

/*==============================================================*/
/* Index: Index_CoursePost                                      */
/*==============================================================*/
create index Index_CoursePost on CoursePost
(
   productBaseID,
   createTime
);


/*==============================================================*/
/* Table: Exercise                                              */
/*==============================================================*/
create table Exercise
(
   exerID               bigint not null auto_increment,
   exerName             varchar(90),
   shopID               bigint,
   contentCateID        bigint,
   productBaseID        bigint,
   creatorID            bigint,
   createTime           datetime,
   version              int,
   modiTime             datetime,
   totalScore           float(8),
   exerDesc             varchar(255),
   primary key (exerID)
)
ENGINE = InnoDB;

create index Index_Exercise on Exercise
(
   productBaseID,
   contentCateID
);


/*==============================================================*/
/* Table: ExerQuesType                                          */
/*==============================================================*/
create table ExerQuesType
(
   patternID            bigint not null auto_increment,
   exerID               bigint not null,
   shopID               bigint,
   quesType             int not null,
   quesTypeName         varchar(64),
   quesTypeNote         varchar(640),
   patternQuesNum       int,
   quesScore            float(8),
   patternScore         float(8),
   pageSize             int,
   disOrder             int,
   primary key (patternID)
)
;

alter table ExerQuesType add constraint FK_Exercise_Pattern foreign key (exerID)
      references Exercise (exerID) on delete restrict on update restrict;


/*==============================================================*/
/* Table: ExerQues                                              */
/*==============================================================*/
create table ExerQues
(
   exerquesID           bigint not null auto_increment,
   quesID               bigint not null,
   exerID               bigint not null,
   quesType             int,
   shopID               bigint,
   quesOrder            int,
   quesScore            float(8),
   wareID               bigint,
   primary key (exerquesID)
)
ENGINE = MyISAM;
ALTER TABLE ExerQues ADD UNIQUE (quesID, exerID);


/*==============================================================*/
/* Table: ExerUser                                              */
/*==============================================================*/
create table ExerUser
(
   exerUserID           bigint not null auto_increment,
   shopID               bigint,
   exerID               bigint not null,
   exerVersion          int,
   userID               bigint not null,
   orgBaseID            bigint,
   startTime            datetime,
   endTime              datetime,
   objectScore          float(8),
   totalScore           float(8),
   status               smallint,
   orderNo              int,
   examTimes            int,
   primary key (exerUserID)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: UserExerAnswer                                        */
/*==============================================================*/
create table UserExerAnswer
(
   userAnswerID         bigint not null auto_increment,
   shopID               bigint not null,
   exerID               bigint not null,
   userID               bigint not null,
   exerquesID           bigint not null,
   quesID               bigint not null,
   quesItemFlag         varchar(20),
   quesType             int,
   pID                  bigint,
   quesCheckType        smallint,
   answer               varchar(4000),
   answerScore          float(8),
   isRight              varchar(64),
   examineStatus        smallint,
   primary key (userAnswerID)
)
ENGINE = InnoDB;

create index Index_UserExerAnswer on UserExerAnswer
(
   exerID,
   userID
);

/*==============================================================*/
/* Table: LearnActivity                                   */
/*==============================================================*/
create table LearnActivity
(
   userID               bigint not null,
   objectID             bigint not null,
   objectType           varchar(32) not null,
   actionType           smallint not null,
   contentCateID        bigint,
   productID            bigint not null,
   shopID               bigint not null,
   learnStatus          smallint,
   progress             varchar(32),
   startTime            datetime not null,
   endTime              datetime,
   primary key (userID, objectID, objectType, actionType)
)
;

/*==============================================================*/
/* Table: QuesDifficult                                         */
/*==============================================================*/
create table QuesDifficult
(
   difficultID          bigint not null auto_increment,
   shopID               bigint,
   difficultName        varchar(32),
   difficultValue       float(8),
   status               smallint,
   primary key (difficultID)
);

alter table LearnContentText add constraint FK_Learn_Content_Text foreign key (contentID)
      references LearnContent (contentID) on delete restrict on update restrict;

alter table DeptInfo add constraint FK_deptinfo_orgbase foreign key (orgBaseID)
      references OrgBase (orgBaseID) on delete restrict on update restrict;

alter table PaperQues add constraint FK_PaperQues_Paper foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;
	  
alter table PaperQues add constraint FK_Paperques_Ques foreign key (quesID)
      references Question (quesID) on delete restrict on update restrict;

alter table RefPaperQues add constraint FK_RefPaperQues foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;

alter table Question add constraint FK_Question_ware foreign key (wareID)
      references Ware (wareID) on delete restrict on update restrict;

alter table QuesAnswer add constraint FK_quesAnswer foreign key (quesID)
      references Question (quesID) on delete restrict on update restrict;

alter table QuestionItem add constraint FK_quesItem_ques foreign key (quesID)
      references Question (quesID) on delete restrict on update restrict;

alter table TestDept add constraint FK_testDept_testInfo foreign key (testID)
      references TestInfo (testID) on delete restrict on update restrict;

alter table TestUser add constraint FK_testUser_testInfo foreign key (testID)
      references TestInfo (testID) on delete restrict on update restrict;

alter table Ware add constraint FK_Ware foreign key (productBaseID)
      references ProductBase (productBaseID) on delete restrict on update restrict;

alter table ContentCate add constraint FK_contentCate_productbase foreign key (productBaseID)
      references ProductBase (productBaseID) on delete restrict on update restrict;

alter table OrgUser add constraint FK_orguser_orgbase foreign key (orgBaseID)
      references OrgBase (orgBaseID) on delete restrict on update restrict;

alter table OrgUser add constraint FK_orguser_user foreign key (userID)
      references User (userID) on delete restrict on update restrict;

alter table PaperProp add constraint FK_paper_prop foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;

alter table DynamicPaper add constraint FK_dynamicPaper foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;

alter table PaperQuesType add constraint FK_paperQuesType_paper foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;

alter table PaperPatternRatio add constraint FK_patternRatio_paper foreign key (paperID)
      references Paper (paperID) on delete restrict on update restrict;

alter table TestInfoProp add constraint FK_testInfo_prop foreign key (testID)
      references TestInfo (testID) on delete restrict on update restrict;

alter table TestChecker add constraint FK_testChecker_rel foreign key (testID)
      references TestInfo (testID) on delete restrict on update restrict;

alter table ExerQues add constraint FK_ExerQues_Ques foreign key (quesID)
      references Question (quesID) on delete restrict on update restrict;

alter table ExerQues add constraint FK_RefExerQues foreign key (exerID)
      references Exercise (exerID) on delete restrict on update restrict;
	  
alter table ExerUser add constraint FK_Exeruser foreign key (exerID)
      references Exercise (exerID) on delete restrict on update restrict;

alter table LearnContent add constraint FK_LearnContent_prod foreign key (productBaseID)
      references ProductBase (productBaseID) on delete restrict on update restrict;
	  
alter table CoursePost add constraint FK_CoursePost foreign key (productBaseID)
      references ProductBase (productBaseID) on delete restrict on update restrict;


/**************************   Defines Views starts    ***************************/

/*==============================================================*/
/* View:ViewOrgBaseRel                                             */
/*==============================================================*/
create view ViewOrgBaseRel as 
select
   OrgBaseRel.shopID,
   OrgBaseRel.parentID,
   OrgBaseRel.childID,
   OrgBaseRel.relType,
   OrgBase.orgName,
   OrgBase.createOrg,
   OrgBase.createTime,
   OrgBase.pID,
   OrgBase.orgDesc,
   OrgBase.orgType,
   OrgBase.orgStatus
from
   OrgBaseRel,
   OrgBase
   where OrgBaseRel.childID=OrgBase.orgBaseID
;
