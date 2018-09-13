DELIMITER $$

DROP PROCEDURE IF EXISTS `P_InsertPaperQues` $$
CREATE PROCEDURE `P_InsertPaperQues`(in v_paperid bigint,in v_shopID bigint,in v_quesType int,in v_quesTypeID bigint,in v_wareIdStr varchar(250),
                                     in v_elementID bigint,in v_startRow int,in v_patternQuesNum int,in v_quesScore float,in v_geneType smallint,
                                     inout v_selQuesNums int,inout v_sum_quesOptNum int,inout v_quesID_str varchar(5000))
BEGIN
     declare vv_patternQuesNum bigint default 5;
     declare vv_quesID bigint;
     declare vv_wareQuesID bigint;
     declare vv_productBaseID bigint;
	 declare vv_quesOptNum int;
	 declare vv_wareID bigint;

     if(v_quesID_str is not null and length(v_quesID_str)>0) then
        if(substr(v_quesID_str,length(v_quesID_str))=',') then
           set v_quesID_str = substr(v_quesID_str,length(v_quesID_str)-1);
        end if;
     else
        set v_quesID_str = '-1';
     end if;
     begin
     declare flagloop boolean; 
     
     declare cursor_1 cursor for select a.quesID, a.quesOptNum, a.productBaseID, a.wareID 
                                 from  (SELECT @rownum:=@rownum+1 rownum, 
								         t.quesID, t.quesOptNum, t.productBaseID,t.wareID from (select @rownum:=0) r, question t
				                         where t.shopID=v_shopID and t.status=1 and t.compType in(0,1) and t.quesType=v_quesType 
										 and find_in_set(t.wareID,v_wareIdStr)>0 and t.contentCateID=v_elementID 
                                         and find_in_set(t.quesID,v_quesID_str)=0) 
								 a where a.rownum>v_startRow and a.rownum<=(v_startRow+v_patternQuesNum);
     
     declare cursor_2 cursor for select a.quesID, a.quesOptNum, a.productBaseID, a.wareID
                                 from  (SELECT @rownum:=@rownum+1 rownum, 
								         t.quesID, t.quesOptNum, t.productBaseID, t.wareID from (select @rownum:=0) r, question t
				                         where t.shopID=v_shopID and t.status=1 and t.compType in(0,1) and t.quesType=v_quesType 
										 and find_in_set(t.wareID,v_wareIdStr)>0 and find_in_set(t.quesID,v_quesID_str)=0) 
								 a where a.rownum>v_startRow and a.rownum<=(v_startRow+v_patternQuesNum);
	
     declare continue handler for NOT FOUND set flagloop = true;
     set flagloop = false;
     
     if(v_elementID is not null) then
         open cursor_1;
         loop1:Loop
			 if flagloop then
				leave loop1;
			 else
				fetch cursor_1 into vv_wareQuesID, vv_quesOptNum, vv_productBaseID, vv_wareID;
									
#if(v_quesType=8) then
#select CONCAT('select count(*) from  (SELECT @rownum:=@rownum+1 rownum, t.* from (select @rownum:=0) r, question t','where t.status=1 and t.compType in(0,1) and t.quesType=',v_quesType,' and find_in_set(t.wareID,',v_wareIdStr,')>0','and t.contentCateID=',v_elementID,' and find_in_set(t.quesID,',v_quesID_str,')=0) a where','a.rownum>',v_startRow,' and a.rownum<=(',v_startRow,'+',v_patternQuesNum,')');
#end if;
									
				if(!flagloop) then
				   set v_quesID_str =  CONCAT(v_quesID_str,',');
				   call P_InsPapQue_Common (v_geneType, v_shopID, v_paperid, v_quesTypeID, v_quesType, 
                   				            vv_productBaseID, v_quesScore, vv_wareQuesID, vv_wareID);
				   set v_selQuesNums = v_selQuesNums+1;
				   set v_sum_quesOptNum = v_sum_quesOptNum+vv_quesOptNum;
				   set v_quesID_str = CONCAT(v_quesID_str,vv_wareQuesID);
				end if;
			 end if;
         end Loop;
         close cursor_1;
     else
         open cursor_2;
         loop2:Loop
			 if flagloop then
				leave loop2;
			 else
				fetch cursor_2 into vv_wareQuesID, vv_quesOptNum, vv_productBaseID, vv_wareID;

#if(v_quesType=8) then
#select CONCAT('select count(*) from  (SELECT @rownum:=@rownum+1 rownum, t.* from (select @rownum:=0) r, question t','where t.status=1 and t.compType in(0,1) and t.quesType=',v_quesType,' and find_in_set(t.wareID,',v_wareIdStr,')>0','and find_in_set(t.quesID,',v_quesID_str,')=0) a where','a.rownum>',v_startRow,' and a.rownum<=(',v_startRow,'+',v_patternQuesNum,')');
#end if;
									
				if(!flagloop) then
				   set v_quesID_str =  CONCAT(v_quesID_str,',');
				   call P_InsPapQue_Common (v_geneType, v_shopID, v_paperid, v_quesTypeID, v_quesType, 
											vv_productBaseID, v_quesScore, vv_wareQuesID, vv_wareID);
				   set v_selQuesNums = v_selQuesNums+1;
				   set v_sum_quesOptNum = v_sum_quesOptNum+vv_quesOptNum;
				   set v_quesID_str = CONCAT(v_quesID_str,vv_wareQuesID);
				end if;
			 end if;
         end Loop;
         close cursor_2;
     end if;
     end ;
END $$

DELIMITER ;