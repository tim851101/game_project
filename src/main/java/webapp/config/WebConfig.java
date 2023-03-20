package webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableJpaRepositories

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 配置靜態資源路徑
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");


    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {


        registry.addViewController("/open_hour").setViewName("/html/open_hour.html");
        registry.addViewController("/foreground/").setViewName("/foreground/index.html");
        registry.addViewController("/foreground/register").setViewName("/foreground/register.html");
        registry.addViewController("/foreground/login").setViewName("/foreground/login.html");
//        registry.addViewController("/index").setViewName("/index.html");
    }
}
