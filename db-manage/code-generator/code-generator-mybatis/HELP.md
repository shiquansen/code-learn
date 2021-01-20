
- mybatis-generator 使用
    - 1. 配置pom
      ```
         <build>
                <plugins>
                    <plugin>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-maven-plugin</artifactId>
                    </plugin>
        
                    <plugin>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-maven-plugin</artifactId>
                        <version>1.4.0</version>
                        <dependencies>
                            <!-- 此处需要引入mysql依赖，不然会报异常, 低版本的情况下使用5.1，不然不能生成insert之外的 -->
                            <dependency>
                                <groupId>mysql</groupId>
                                <artifactId>mysql-connector-java</artifactId>
                                <version>8.0.13</version>
                                <scope>runtime</scope>
                            </dependency>
                        </dependencies>
                        <configuration>
                            <!-- generator 工具配置文件的位置 -->
                            <configurationFile>src/com.sbzl.dbmanage.codegenerator.codegeneratormybatisplus.CodeGeneratorMybatisplusApplication.main/resources/generatorConfig.xml</configurationFile>
                            <!-- 是否覆盖 -->
                            <!-- 此处要特别注意,如果不加这个设置会导致每次运行都会在原目录再次创建-->
                            <overwrite>true</overwrite>
                        </configuration>
                    </plugin>
                </plugins>
            </build>  
      ```
      
    - 2. 配置generatorConfig.xml
    ```
        <!DOCTYPE generatorConfiguration
                PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
        
        <generatorConfiguration>
            <!--
              targetRuntime:设置自动生成的版本
              MyBatis3:
              MyBatis3Simple:简单增删改查
              -->
            <context id="DB2Tables" targetRuntime="MyBatis3">
                <!-- 当表名或者字段名为SQL关键字的时候，可以设置该属性为true，MBG会自动给表名或字段名添加**分隔符**。 -->
                <property name="autoDelimitKeywords" value="true" />
                <property name="javaFileEncoding" value="UTF-8" />
                <!-- 格式化java代码 -->
                <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter" />
                <!-- 格式化xml -->
                <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter" />
                <property name="beginningDelimiter" value="`" />
                <property name="endingDelimiter" value="`" />
                <!-- 生成的pojo，将implements Serializable -->
                <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
                <plugin type="org.mybatis.generator.plugin.ext.impl.MysqlPaginationPlugin"/>
                <!-- 		<plugin type="org.mybatis.generator.plugin.ext.impl.EntityNullPlugin"></plugin>-->
        
                <commentGenerator>
                    <!-- type="org.mybatis.generator.plugin.ext.impl.CommentExtGenerator"> -->
                    <!--阻止生成注释  false为生成-->
                    <property name="suppressAllComments" value="true" />
                    <property name="javaFileEncoding" value="UTF-8" />
                    <!--阻止生成日期  false为生成-->
                    <property name="suppressDate" value="true" />
                    <property name="addRemarkComments" value="true" />
                </commentGenerator>
        
                <!--数据库链接URL，用户名、密码 -->
                <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                                connectionURL="jdbc:mysql://localhost:3306/x02_business_book?useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=GMT"
                                userId="root"
                                password="root">
        <!--            防止生成其他库表的do-->
                    <property name="nullCatalogMeansCurrent" value="true"/>
        <!--            <property name="useInformationSchema" value="true" />-->
        <!--            <property name="remarksReporting" value="true" />-->
                </jdbcConnection>
        
                <!-- java类型处理器 用于处理DB中的类型到Java中的类型，默认使用JavaTypeResolverDefaultImpl； 注意一点，默认会先尝试使用Integer，Long，Short等来对应DECIMAL和
                    NUMERIC数据类型； -->
                <javaTypeResolver
                        type="org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl">
                    <property name="forceBigDecimals" value="false" />
                </javaTypeResolver>
        
        
                <!-- 生成模型的包名和位置 java实体类 -->
                <javaModelGenerator
                        targetPackage="com.eebbk.onlinepointread.pojo" targetProject="src/com.sbzl.dbmanage.codegenerator.codegeneratormybatisplus.CodeGeneratorMybatisplusApplication.main/java">
                    <property name="enableSubPackages" value="true" />
                    <property name="trimStrings" value="true" />
                </javaModelGenerator>
        
                <!-- 生成映射文件的包名和位置 -->
                <sqlMapGenerator targetPackage="mapper"
                                 targetProject="src/com.sbzl.dbmanage.codegenerator.codegeneratormybatisplus.CodeGeneratorMybatisplusApplication.main/resources">
                    <property name="enableSubPackages" value="true" />
                </sqlMapGenerator>
        
        
                <!-- 生成DAO的包名和位置 -->
                <javaClientGenerator type="mapper"
                                     targetPackage="com.eebbk.onlinepointread.dao" targetProject="src/com.sbzl.dbmanage.codegenerator.codegeneratormybatisplus.CodeGeneratorMybatisplusApplication.main/java">
                    <property name="enableSubPackages" value="true" />
                </javaClientGenerator>
        
        <!--        <table tableName="%" enableCountByExample="true" enableUpdateByExample="true" enableDeleteByExample="true" enableSelectByExample="true" selectByExampleQueryId="true"/>&lt;!&ndash; 所有表 &ndash;&gt;-->
                <table tableName="t_business_book" domainObjectName="BookDO"
                       enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
                </table>
        
                <table tableName="t_grade" domainObjectName="GradeDO"
                       enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
                </table>
        
                <table tableName="t_publisher" domainObjectName="PublisherDO"
                       enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
                </table>
        
            </context>
        
        
        </generatorConfiguration>
    ```


- pageHelper 接入文档
    1. 引入pom
       ```
              <dependency>
                        <groupId>com.github.pagehelper</groupId>
                        <artifactId>pagehelper-spring-boot-starter</artifactId>
                        <version>1.2.5</version>
                    </dependency> 
       ```
    
    2. 配置properties
    
       ```
            # 指定数据库，不指定的话会默认自动检测数据库类型
            pagehelper.helperDialect=mysql
            pagehelper.reasonable=true
            pagehelper.supportMethodsArguments=true
            pagehelper.params=count=countSql
       ```
       
    3. 业务代码
        ```
           ### controller
           @GetMapping("list")
           public ResultVO<PageInfo<BookBaseVO>> getBookListByAllCondition(
                   @RequestParam(name = "gradeTerm", required = false)String gradeTerm,
                   @RequestParam(name = "publisherId", required = false)Integer publisherId,
                   @RequestParam(name = "subjectId", required = false)Integer subjectId,
                   @RequestParam(name = "pageNum", defaultValue = "1")Integer pageNum,
                   @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize
           ){
               return bookService.getBookListByAllCondition(gradeTerm, publisherId, subjectId, pageNum, pageSize);
           }
       
            ### service
            @Override
            public ResultVO<PageInfo<BookBaseVO>> getBookListByAllCondition(String gradeTerm, Integer publisherId, Integer subjectId, Integer pageNum, Integer pageSize) {
                Integer gradeId = null == gradeTerm ? null : Integer.valueOf(gradeTerm.split("#")[0]);
                Integer termId = null == gradeTerm ? null : Integer.valueOf(gradeTerm.split("#")[1]);
                PageInfo<BookBaseVO> pageInfo = PageHelper.startPage(pageNum, pageSize)
                                .doSelectPageInfo(() -> bookDOMapper.getBookListByAllCondition(gradeId, termId, publisherId, subjectId));
                return ResultVO.ok(UUID.randomUUID().toString(), pageInfo);
            }
       
            ###mapper
            List<BookBaseVO> getBookListByAllCondition(Integer gradeId, Integer termId, Integer publisherId, Integer subjectId);
        ```



