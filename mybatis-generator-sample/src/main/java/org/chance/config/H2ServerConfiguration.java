package org.chance.config;

// 需要 implementation com.h2database:h2 jar包
// 不能 runtimeOnly jar包
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * H2ServerConfiguration
 * Only activate this in the "dev" profile
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/23
 */
@Configuration
@ConditionalOnClass(Server.class)
@Profile("dev")
public class H2ServerConfiguration {

    // TCP port for remote connections, default 9092
    @Value("${h2.tcp.port:9092}")
    private String h2TcpPort;

    // Web port, default 8082
//    @Value("${h2.web.port:8082}")
//    private String h2WebPort;

    /**
     * 配置开启h2服务客户端tpc方式连接(远程连接)
     * jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>
     * jdbc:h2:tcp://localhost:9092/mem:testdb
     * jdbc:h2:tcp://localhost:9092/~/db/testdb
     *
     * 开启后可以使用 Idea的 Database连接h2内存数据库
     * url: jdbc:h2:tcp://localhost:9092/mem:testdb
     * username: sa
     * password: null
     *
     * @return
     * @throws SQLException
     */
    @Bean
    @ConditionalOnExpression("${h2.tcp.enabled:true}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }


//    @Bean
//    @ConditionalOnExpression("${h2.web.enabled:true}")
//    public Server h2WebServer() throws SQLException {
//        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
//    }

}
