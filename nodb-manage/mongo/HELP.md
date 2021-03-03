https://www.iocoder.cn/Spring-Boot/MongoDB/?self

#构造动态查询
https://blog.csdn.net/long476964/article/details/79677526

基础操作：
    普通crud
    //==================document数据类型=======================
    Double	                    1	 
    String	                    2	 
    Object	                    3	 
    Array	                    4	 
    Binary data	                5	 
    Undefined	                6	        已废弃。
    Object id	                7	 
    Boolean	                    8	 
    Date	                    9	 
    Null	                    10	 
    Regular Expression	        11	 
    JavaScript	                13	 
    Symbol	                    14	 
    JavaScript (with scope)	    15	 
    32-bit integer	            16	 
    Timestamp	                17	 
    64-bit integer	            18	 
    Min key	                    255	        Query with -1.
    Max key	                    127	 

    //==========collection的单表查找==============
    (and or not)
    筛选
    (大于，大于等于，等于，小于等于，小于)
        大于：db.col.find({likes : {$gt : 100}})
        大于等于：db.col.find({likes : {$gte : 100}})
        小于：db.col.find({likes : {$lt : 100}})
        小于等于：db.col.find({likes : {$lte : 100}})
        小于200大于100：db.col.find({likes : {$lt :200, $gt : 100}})
    (in ,not in)
    (like, not like)
        查询 title 包含"教"字的文档：       db.col.find({title:/教/})
        查询 title 字段以"教"字开头的文档：  db.col.find({title:/^教/})
        查询 titl e字段以"教"字结尾的文档：  db.col.find({title:/教$/})
    (Limit与Skip)
        db.col.find({},{"title":1,_id:0}).limit(2)              只显示两条
        db.col.find({},{"title":1,_id:0}).limit(1).skip(1)     显示第二条数据
    (sort)
        db.col.find({},{"title":1,_id:0}).sort({"likes":-1})    按likes 降序
        
    //=============================聚合===========================================
    (aggregate)=(group by)
        db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : 1}}}])
        类似
        select by_user, count(*) from mycol group by by_user
        
        $sum	    计算总和。	                            db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : "$likes"}}}])
        $avg	    计算平均值	                            db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$avg : "$likes"}}}])
        $min	    获取集合中所有文档对应值得最小值。	        db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$min : "$likes"}}}])
        $max	    获取集合中所有文档对应值得最大值。	        db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$max : "$likes"}}}])
        $push	    在结果文档中插入值到一个数组中。	            db.mycol.aggregate([{$group : {_id : "$by_user", url : {$push: "$url"}}}])
        $addToSet	在结果文档中插入值到一个数组中，但不创建副本。	db.mycol.aggregate([{$group : {_id : "$by_user", url : {$addToSet : "$url"}}}])
        $first	    根据资源文档的排序获取第一个文档数据。	        db.mycol.aggregate([{$group : {_id : "$by_user", first_url : {$first : "$url"}}}])
        $last	    根据资源文档的排序获取最后一个文档数据	        db.mycol.aggregate([{$group : {_id : "$by_user", last_url : {$last : "$url"}}}])
    管道在Unix和Linux中一般用于将当前命令的输出结果作为下一个命令的参数。
    MongoDB的聚合管道将MongoDB文档在一个管道处理完毕后将结果传递给下一个管道处理。管道操作是可以重复的。
    表达式：处理输入文档并输出。表达式是无状态的，只能用于计算当前聚合管道的文档，不能处理其它的文档。
    这里我们介绍一下聚合框架中常用的几个操作：
        $project：修改输入文档的结构。可以用来重命名、增加或删除域，也可以用于创建计算结果以及嵌套文档。
        $match：用于过滤数据，只输出符合条件的文档。$match使用MongoDB的标准查询操作。
        $limit：用来限制MongoDB聚合管道返回的文档数。
        $skip：在聚合管道中跳过指定数量的文档，并返回余下的文档。
        $unwind：将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。
        $group：将集合中的文档分组，可用于统计结果。
        $sort：将输入文档排序后输出。
        $geoNear：输出接近某一地理位置的有序文档。
    //============  0代表不输出，1代表输出  ==============
    db.article.aggregate(
        { $project : {
            _id : 0 ,       
            title : 1 ,
            author : 1
        }});     
    //===============$match====================
    db.articles.aggregate([
                           { $match : { score : { $gt : 70, $lte : 90 } } },
                           { $group: { _id: null, count: { $sum: 1 } } }
                          ]);
    $match用于获取分数大于70小于或等于90记录，然后将符合条件的记录送到下一阶段$group管道操作符进行处理。
    //===============$skip====================
    db.article.aggregate(
        { $skip : 5 });
    
    
    //==============================索引=========================================
    db.col.createIndex({"title":1})
    
    Parameter	        Type	    Description
    background	        Boolean	    建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为false。
    unique	            Boolean	    建立的索引是否唯一。指定为true创建唯一索引。默认值为false.
    name	            string	    索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。
    dropDups	        Boolean	    3.0+版本已废弃。在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 false.
    sparse	            Boolean	    对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 false.
    expireAfterSeconds	integer	    指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。
    weights	            document	索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。
    default_language	string	    对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语
    language_override	string	    对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language.
    实例
    在后台创建索引：
    db.values.createIndex({open: 1, close: 1}, {background: true})  后台创建索引
    
    
高级操作
    对象关系：
        嵌入式：
        
        {
           "_id":ObjectId("52ffc33cd85242f436000001"),
           "contact": "987654321",
           "dob": "01-01-1991",
           "name": "Tom Benzamin",
           "address": [
              {
                 "building": "22 A, Indiana Apt",
                 "pincode": 123456,
                 "city": "Los Angeles",
                 "state": "California"
              },
              {
                 "building": "170 A, Acropolis Apt",
                 "pincode": 456789,
                 "city": "Chicago",
                 "state": "Illinois"
              }]
        } 
        查询用户地址： >db.users.findOne({"name":"Tom Benzamin"},{"address":1})
        
        引用式关系
        
        引用式关系是设计数据库时经常用到的方法，这种方法把用户数据文档和用户地址数据文档分开，通过引用文档的 id 字段来建立关系。
        {
           "_id":ObjectId("52ffc33cd85242f436000001"),
           "contact": "987654321",
           "dob": "01-01-1991",
           "name": "Tom Benzamin",
           "address_ids": [
              ObjectId("52ffc4a5d85242602e000000"),
              ObjectId("52ffc4a5d85242602e000001")
           ]
        }
        用户文档的 address_ids 字段包含用户地址的对象id（ObjectId）数组。
        我们可以读取这些用户地址的对象id（ObjectId）来获取用户的详细地址信息。
        这种方法需要两次查询，第一次查询用户地址的对象id（ObjectId），第二次通过查询的id获取用户的详细地址信息。
        
        >var result = db.users.findOne({"name":"Tom Benzamin"},{"address_ids":1})
        >var addresses = db.address.find({"_id":{"$in":result["address_ids"]}})
        
    //================== DBrefs ===========================
    { $ref : , $id : , $db :  }
    三个字段表示的意义为：
    
    $ref：集合名称
    $id：引用的id
    $db:数据库名称，可选参数
    以下实例中用户数据文档使用了 DBRef, 字段 address：
    {
       "_id":ObjectId("53402597d852426020000002"),
       "address": {
       "$ref": "address_home",
       "$id": ObjectId("534009e4d852427820000002"),
       "$db": "runoob"},
       "contact": "987654321",
       "dob": "01-01-1991",
       "name": "Tom Benzamin"
    }
    address DBRef 字段指定了引用的地址文档是在 runoob 数据库下的 address_home 集合，id 为 534009e4d852427820000002。
    以下代码中，我们通过指定 $ref 参数（address_home 集合）来查找集合中指定id的用户地址信息：
    >var user = db.users.findOne({"name":"Tom Benzamin"})
    >var dbRef = user.address
    >db[dbRef.$ref].findOne({"_id":(dbRef.$id)})
    
    
    //==================== 覆盖索引 ==========================
    使用覆盖索引查询
    为了测试覆盖索引查询，使用以下 users 集合:
    {
       "_id": ObjectId("53402597d852426020000002"),
       "contact": "987654321",
       "dob": "01-01-1991",
       "gender": "M",
       "name": "Tom Benzamin",
       "user_name": "tombenzamin"
    }
    我们在 users 集合中创建联合索引，字段为 gender 和 user_name :
    
    >db.users.ensureIndex({gender:1,user_name:1})
    现在，该索引会覆盖以下查询：
    >db.users.find({gender:"M"},{user_name:1,_id:0})
    也就是说，对于上述查询，MongoDB的不会去数据库文件中查找。相反，它会从索引中提取数据，这是非常快速的数据查询。
    由于我们的索引中不包括 _id 字段，_id在查询中会默认返回，我们可以在MongoDB的查询结果集中排除它。
    下面的实例没有排除_id，查询就不会被覆盖：
    >db.users.find({gender:"M"},{user_name:1})
    最后，如果是以下的查询，不能使用覆盖索引查询：
    所有索引字段是一个数组
    所有索引字段是一个子文档4
    
    //=================  MongoDB Map Reduce  ========================
    
    
    //====================其他=========================
    查询索引分析：         https://www.runoob.com/mongodb/mongodb-analyzing-queries.html
    原子操作：            https://www.runoob.com/mongodb/mongodb-atomic-operations.html
    mapreduce           https://www.runoob.com/mongodb/mongodb-map-reduce.html
    全文检索：            https://www.runoob.com/mongodb/mongodb-text-search.html
    mongo正则            https://www.runoob.com/mongodb/mongodb-regular-expression.html
    
tips：   skip(), limilt(), sort()三个放在一起执行的时候，执行的顺序是先 sort(), 然后是 skip()，最后是显示的 limit()。
         find 返回的数据类型是数组，findOne 返回的数据类型是对象。
         mongodb不支持事务
