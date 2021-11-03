package ru.pokrov.auth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class StaticResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/").setViewName("forward:/app/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/app/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // this is needed for angular to redirect right way
                        return requestedResource.exists() && requestedResource.isReadable()
                                ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                })
        ;
    }

}
