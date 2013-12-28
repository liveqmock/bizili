1、修改web.xml,切换不同的spring主配置文件
2、修改WEB-INF/下的jboss-deployment-structure.xml
3、去掉pom.xml中的CXF的依赖，JBoss提供了
4、去掉pom.xml中的hibernate-infinispan.jar依赖，或者加上<scope>provided</scope>
5、infinispan-configs.xml中的query-statement缓存，在JBoss中不需要指定transactionManagerLookup,去掉

A、META-INF/services/org.infinispan.commands.module.ModuleCommandExtensions暂时是不使用的。不过是正确的。
B、META-INF/MANIFEST.MF中配置的依赖是正确的，但是现在不使用。使用jboss-deployment-structure.xml