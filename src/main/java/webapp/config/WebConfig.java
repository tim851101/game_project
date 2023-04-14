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

        // static resource mapping
        registry.addResourceHandler("/foreground/static/**")
            .addResourceLocations("classpath:/static/foreground/static/");

        registry.addResourceHandler("/foreground/**")
            .addResourceLocations("classpath:/static/foreground/");

        registry
            .addResourceHandler("/background/static/**")
            .addResourceLocations("classpath:/static/background/static/");

        registry
            .addResourceHandler("/background/**")
            .addResourceLocations("classpath:/static/background/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/foreground/index").setViewName("/foreground/index.html");
        registry.addViewController("/foreground/register").setViewName("/foreground/register.html");
        registry.addViewController("/foreground/login").setViewName("/foreground/login.html");
        registry.addViewController("/foreground/my-account").setViewName("/foreground/my-account.html");
        registry.addViewController("/foreground/contact-us").setViewName("/foreground/contact-us.html");

        registry.addViewController("/management").setViewName("/background/index-backend.html");
        registry.addViewController("/background/login").setViewName("/background/employee-login.html");

    }
}
