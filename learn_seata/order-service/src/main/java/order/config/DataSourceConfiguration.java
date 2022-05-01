package order.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 切换成XA模式
 *
 * @author zhuyc
 * @date 2021/10/9 10:41
 */
@Configuration
@Order(Integer.MIN_VALUE)
public class DataSourceConfiguration {

//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource() {
//
//        return druidDataSource;
//    }

//    @Bean("dataSourceProxy")
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        druidDataSource.setUrl("jdbc:mysql://zhuyc.top:13306/order?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         return new DataSourceProxy(druidDataSource);

        // DataSourceProxyXA for XA mode
//        return new DataSourceProxyXA(druidDataSource);
    }

//    @Bean("jdbcTemplate")
//    public JdbcTemplate jdbcTemplate(DataSource dataSourceProxy) {
//        return new JdbcTemplate(dataSourceProxy);
//    }

//    @Bean
//    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dataSourceProxy") DataSource dataSource) throws IOException {
//        // 这里用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
//        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
//        return mybatisSqlSessionFactoryBean;
//    }



}
