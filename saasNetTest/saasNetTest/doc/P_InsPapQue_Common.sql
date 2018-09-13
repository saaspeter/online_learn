DELIMITER $$

DROP PROCEDURE IF EXISTS `P_InsPapQue_Common` $$
CREATE PROCEDURE `P_InsPapQue_Common` (in v_geneType smallint,in vv_shopID bigint,in v_paperid bigint,in v_quesTypeID bigint,
                                       in v_quesType int,in vv_productBaseID bigint,in v_quesScore float,in vv_wareQuesID bigint,
									   in vv_wareID bigint)
BEGIN
     # declare vv_paperquesID bigint;

     if(v_geneType=1) then
          insert into PaperQues(shopID,paperID,quesTypeID,quesType,quesCreateTime,productBaseID,paperQuesScore,quesID,wareID)
                       values(vv_shopID,v_paperid,v_quesTypeID,v_quesType,SYSDATE(),vv_productBaseID,v_quesScore,vv_wareQuesID,vv_wareID);
          # select LAST_INSERT_ID() into vv_paperquesID;

     else
          if(v_geneType=2) then  # is dynamic paper,so just insert RefPaperQues
              insert into RefPaperQues(quesID,shopID,paperID,paperQuesOrder,paperQuesScore,wareID,status)
                                values(vv_wareQuesID,vv_shopID,v_paperid,null,v_quesScore,vv_wareID,1);
          end if;
     end if;

END $$

DELIMITER ;