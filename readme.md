#mybatis 持久层 编写流程：
###1
  dao层：编写要查询或修改数据的方法 文件命名 *Mapper.java *Mapper.xml
  pojo/entity 实体层：数据库的表应用的类， service层将实例化这些类
  service层：接口+接口的实现类调用dao层接口的方法（接口的实现是通过
  *Mapper.xml配置，mybatis实现，service层调用）
 
 servlet-api.jar 包记得引入
 
 
 ##技巧 配置虚拟目录的2种方法
  http://blog.csdn.net/cheney550995353/article/details/70185282
  
  ## 图片上传 SpringMvc MultipartFile 图片文件上传
  https://www.cnblogs.com/LEARN4J/p/5426980.html
  
  ##
  https://www.zhihu.com/question/20450079