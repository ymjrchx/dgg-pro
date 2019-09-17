package net.dgg.gspt.dqc.config;

import net.dgg.gspt.dqc.dao.JdbcDao;
import net.dgg.gspt.dqc.dao.jdbc.JdbcDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 李程 on 2018/11/13.
 */
@Configuration
public class DbConf {

    @Bean
    public JdbcDao jdbcDao(){
        return new JdbcDaoImpl();
    }

}
