package com.swsk.frame.springboot.project.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@ComponentScan(basePackages = {"com.swsk"})
@EntityScan(basePackages = {"com.swsk"})
@EnableJpaRepositories(basePackages = {"com.swsk"})
@ServletComponentScan(basePackages = {"com.swsk"})
@SpringBootApplication(scanBasePackages = {"com.swsk"})
@EnableAsync
// 开启审计功能，自动补充创建时间和最后修改时间
@EnableJpaAuditing
public class Application {
    public static void main(String[] args) {

        // 解决HeadlessException 异常问题
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).run(args);
        String port = context.getEnvironment().getProperty("server.port");
        String contextPath = context.getEnvironment().getProperty("server.servlet.context-path");
        openHome(port, contextPath);
    }

    //通过浏览器打开接口文档
    public static void openHome(String port, String contextPath) {
        String url = "http://localhost:" + port + contextPath + "/swagger-ui.html";
        try {
            //通过浏览器打开主页
            Runtime.getRuntime().exec("cmd   /c   start   " + url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
