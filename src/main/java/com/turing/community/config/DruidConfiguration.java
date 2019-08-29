package com.turing.community.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Meng
 * @date 2019/8/9
 */
@Configuration
public class DruidConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
       DruidDataSource druidDataSource=new DruidDataSource();
        List<Filter> filters=new ArrayList<>();
        filters.add(statFilter());
       druidDataSource.setProxyFilters(filters);
       return druidDataSource;
    }

    @Bean
    public ServletRegistrationBean statViewServle(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername","root");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        return servletRegistrationBean;
    }

    @Bean
    public Filter statFilter(){
        StatFilter filter=new StatFilter();
        return filter;
    }


}
