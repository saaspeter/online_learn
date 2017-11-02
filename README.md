# online_learn
online learning system

open source my online learning system. The code is old, but it has its special design:

1. It is a saas system, each user can open its shop, in its shop, can create its courses
2. everyone can open its shop, also can join other shop's course.
3. support internations language switch, you even can name different shop/course for different languages.
4. support students management
5. support course management, include: online exam/quiz, questions ware management(import)
6. support auto-generated paper by select and define how to select questions for paper
7. course teachers can arrange an testing, auto-generate a paper for this testing, pick up the students for the testing
8. after testing time is over, system can auto-checking students papers, and gives the scores. then give all students testing results and give report.
9. the course support upload document/flash/video, students can wath document or videos in each chapter. also student can ask questions to teachers and teacher can answer the question.
10. the media video store location support: google drive / dropbox / microsoft oneDrive and Qiniu(china).

sub projects:

/saasNetTest/commonService : common tool and common service for all system
/saasNetTest/platform : the platform module provide common models and service for Saas service, including : shop management
/saasNetTest/netTest : online leaning models and implementation logic.
/saasNetTest/netTestWeb : online learning web modules, including: controller and pages

the demo site is: www.tomylearn.com

系统开发的比较早，在saas刚盛行的时候，个人开发的，只是没有精力没有人员去维护这个系统，现在开源。建立本系统的目的是：为所有想开网店开课程的老师提供平台，想让学生更方便的选课。

Hop it can help you


