package netTest.product.constant;

public class GoodproductConstant {
   
	 /** 推荐原因，1 系统自动评级产生 **/
	 public static final Short FromSource_SysAuto = 1;
	 /** 推荐原因，2 手动添加产品，竞价产生 **/
	 public static final Short FromSource_AdminAdd = 2;
	 /** 推荐原因，3 自动评级和竞价  **/
	 public static final Short FromSource_Both = 3;
	 
	 /** 范围:产品自身所在locale **/
	 public static final Short Scope_Local = 1;
	 /** 范围:所有locale都可以查到 **/
	 public static final Short Scope_All = 2;
}
