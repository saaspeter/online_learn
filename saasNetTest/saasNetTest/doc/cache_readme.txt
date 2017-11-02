
老cache的做法:(2012-05-01之前，之后已经废弃，使用spring3.1 cache)
1. 定义MethodCacheInterceptor，用于在方法前先在cache中查找结果,这个类是parent类，
   ExampaperCacheInterceptor继承了MethodCacheInterceptor，用于拦截ExampaperLogicImpl类中
   的getTestPaper方法，然后配置一个exampaperMethodCachePointCut，配置在basicSetting.xml中，
   然后在exampaperLogicTarget的定义中应用这个PointCut（applicationContext_exam.xml）。这样
   当调用exampaperLogic中的getTestPaper()方法时就会用到这个PonitCut了。
   
2. 刷新也是通过一个方法拦截器做到的。定义一个方法后执行器<bean id="exampaperCacheAfterAdvice" class="netTest.exam.logic.impl.ExampaperCacheAfterAdvice">
   (在basicSetting.xml中)，定义个方法执行pointCut: pointCutExampaperCacheAdvice。然后在
   paperquesLogicTarget的定义中weave入这个pintCut.(applicationContext_paper.xml)

security中的cache做法:
   参见applicationContext-security.xml中userCache的配置，当用户改变的时候(包括权限role改变)需要刷新这个cache
   resourceLoader的配置，使用了自定义的commonTool.cache.EHCacheImpl来处理cache

新的cache采用spring3.1的anotation来处理
    配置在basicSetting.xml中。
    理想的做法应该是使用自己定义的 AbstractCache 和 CacheInf来定义，然后自定义cache的annotation注释标签
   