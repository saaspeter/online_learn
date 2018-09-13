DELIMITER $$

DROP PROCEDURE IF EXISTS `P_SelPaperQues` $$
CREATE PROCEDURE `P_SelPaperQues`(in v_paperid bigint)
BEGIN
     declare v_isRandom boolean default true; # if select the question randomly
	 declare v_shopID bigint;
     declare v_isDynPaper smallint default 1;
     declare v_wareIdStr varchar(250) default '';
     declare v_quesTypeID bigint;
     declare v_quesType int;
     declare v_patternQuesNum int;
     declare v_quesScore float(8);
     declare v_quesID_str varchar(5000) default '-1';  # store the quesID selected,seperated by comma
     declare v_quesCount int default 1;  # the number of quarlified questions.default 1 in order to call P_InsertPaperQues if v_isRandom is false.
     declare v_startRow int default 0;  # search the question from the startRow,if random is available
     declare v_selQuesNums int default 0; # the numbers of the selected questions
     declare v_sum_quesOptNum int default 0; # the numbers of the subques of the question selected.
     declare v_paperTotalScore float(8) default 0; # total score of the paper

     declare flagloop1 boolean; # define the flag for loop judgement
     declare cursor_1 cursor for select quesType,patternQuesNum,quesScore,quesTypeID from PaperQuesType where paperID=v_paperid order by disOrder;
     declare continue handler for NOT FOUND set flagloop1 = true;
     set flagloop1 = false;
     # get paper ware
     select shopID, geneType, wareIdStr into v_shopID, v_isDynPaper, v_wareIdStr from paper where paperID=v_paperid;
     # begin loop questype
     open cursor_1;
         loop1:Loop
         if flagloop1 then
            leave loop1;
         else
            set v_selQuesNums = 0;   # clean the selected question nums for each quesType
            set v_sum_quesOptNum = 0;
            fetch cursor_1 into v_quesType,v_patternQuesNum,v_quesScore,v_quesTypeID;
            if(!flagloop1) then
      #if(v_quesType=8) then
      #select  'debuginfo0:',v_quesType,v_patternQuesNum,v_quesScore,v_quesTypeID;
      #end if;
            begin
                declare flagloop2 boolean default false;
                declare v_elementID bigint;
                declare v_elementNum int;
                declare v_elementType smallint;
                declare cursor_2 cursor for select elementID,elementNum,elementType from PaperPatternRatio where paperID=v_paperid and quesTypeID=v_quesTypeID and elementType=3;
                declare continue handler for not found set flagloop2 = true;
                set flagloop2 = false;
                # loop  PaperPatternRatio
                open cursor_2;
                    loop2:loop
                    if flagloop2 then
                       leave loop2;
                    else
                        fetch cursor_2 into v_elementID,v_elementNum,v_elementType;
                        if(!flagloop2) then
      #if(v_quesType=8) then
      #select  'debuginfo1:',v_elementID,v_elementNum,v_elementType;
      #end if;
                           # if it needs random,then get the ramdom startrow and then call a procedure
                           if(v_isRandom)  then
                              set v_quesCount = 0;
                              set v_startRow = 0;
                              select count(*) into v_quesCount from question where shopID=v_shopID and status=1 
							                  and compType in(0,1) and quesType=v_quesType and find_in_set(wareID,v_wareIdStr)>0 
                                              and contentCateID=v_elementID and find_in_set(quesID,v_quesID_str)=0;
                              if(v_quesCount>v_elementNum) then
                                 SELECT FLOOR(0 + (RAND() * (v_quesCount-v_elementNum))) into v_startRow; # get the start search row number
                              end if;
                           end if;
                           # select the question
                           if(v_quesCount>0) then
       #if(v_quesType=8) then
       #select  'debuginfo2:',v_paperid,v_quesType,v_quesTypeID,v_elementID,v_startRow,v_elementNum,v_quesScore,v_selQuesNums,v_sum_quesOptNum ;
       #end if;
                              call P_InsertPaperQues(v_paperid,v_shopID,v_quesType,v_quesTypeID,v_wareIdStr,v_elementID,v_startRow,v_elementNum,v_quesScore,v_isDynPaper,v_selQuesNums,v_sum_quesOptNum,v_quesID_str);
                           end if;
                        end if;
                    end if;
                    end loop;
                close cursor_2;
                # if the selected questions don't match the numbers required then select the question again without the constrains.
       #if(v_quesType=8) then
       #select  'debuginfo3:',v_patternQuesNum,v_selQuesNums ;
       #end if;
                if(v_patternQuesNum-v_selQuesNums>0) then
                    set v_patternQuesNum = v_patternQuesNum-v_selQuesNums;
                    if(v_isRandom)  then
                           set v_quesCount = 0;
                           set v_startRow = 0;
                           select count(*) into v_quesCount from question where shopID=v_shopID and status=1 
						                   and compType in(0,1) and quesType=v_quesType and find_in_set(wareID,v_wareIdStr)>0 
										   and find_in_set(quesID,v_quesID_str)=0;
       #if(v_quesType=8) then
       #select  'debuginfo4:',v_quesCount,v_patternQuesNum ;
       #end if;
                           if(v_quesCount>v_patternQuesNum) then
                              SELECT FLOOR(0 + (RAND() * (v_quesCount-v_patternQuesNum))) into v_startRow; # get the start search row number
                           end if;
                    end if;
                    if(v_quesCount>0) then
       #if(v_quesType=8) then
       #select  'debuginfo5:',v_paperid,v_quesType,v_quesTypeID,v_startRow,v_patternQuesNum,v_quesScore,v_selQuesNums,v_sum_quesOptNum ;
       #end if;
                       call P_InsertPaperQues(v_paperid,v_shopID,v_quesType,v_quesTypeID,v_wareIdStr,null,v_startRow,v_patternQuesNum,v_quesScore,v_isDynPaper,v_selQuesNums,v_sum_quesOptNum,v_quesID_str);
                    end if;
                end if;
                # update the pattern questions scores
                update PaperQuesType set patternScore = v_sum_quesOptNum*v_quesScore where paperID = v_paperid and quesTypeID = v_quesTypeID;
                set v_paperTotalScore = v_paperTotalScore + v_sum_quesOptNum*v_quesScore;
            end;
            end if;

         end if;
         end Loop;
     close cursor_1;
     # update total score of the paper
     update paper set paperTotalScore = v_paperTotalScore, phase=5 where paperID = v_paperid;

END $$

DELIMITER ;