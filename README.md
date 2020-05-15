# mybatis-generator
mybatis-generator

# 本地安装
### 下载到本地
```
git clone https://github.com/catchance/mybatis-generator 到本地
```
### 本地安装
使用`gradle push`安装 *mybatis-generator-core* 模块到本地maven仓库

# 插件使用
### Maven
1) 修改pom文件
```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.2</version>
    <configuration>
        <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.chance</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>0.1.0</version>
        </dependency>
    </dependencies>
</plugin>
```

2) 修改generatorConfig.xml文件

3) 运行命令，看结果吧
```
mvn mybatis-generator:generate
```

### gradle
- 使用`gradle push`安装 *mybatis-generator-gradle-plugin* 模块到本地maven仓库
- 参见 *mybatis-generator-sample* 的使用方式

# Reference
- https://github.com/kimichen13/mybatis-generator-plugin
- https://github.com/mybatis/generator

# License
Strategy is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).


