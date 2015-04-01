package hello;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Created by moyong on 15/4/1.
 */
@Configuration
public class MongoConfiguration {

    /*
        查看com.mongodb.MongoOptions源代码，其中有connectionsPerHost和threadsAllowedToBlockForConnectionMultiplier两个重要的属性。
        connectionsPerHost默认是10，threadsAllowedToBlockForConnectionMultiplier默认是5，也就是线程池有50个连接数可供使用。
     */
    @Bean
    public MongoFactoryBean mongo() {
        // 增大Mongo驱动的并发连接数量
        System.setProperty("MONGO.POOLSIZE", "1000");

        MongoOptions options=new MongoOptions();
        options.connectionsPerHost = 100;
        options.setThreadsAllowedToBlockForConnectionMultiplier(10);
        //options.setFsync(true);

        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setMongoOptions(options);
        mongo.setHost("192.168.59.103");
        mongo.setPort(27017);

//        ServerAddress serverAddress= null;
//        try {
//            serverAddress = new ServerAddress("192.168.59.103",27017);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        MongoFactoryBean mongo = new Mongo(serverAddress,options);



        return mongo;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongo().getObject(),"test");
    }
}
