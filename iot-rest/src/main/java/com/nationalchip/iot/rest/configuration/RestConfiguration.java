package com.nationalchip.iot.rest.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nationalchip.iot.data.configuration.DataProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.file.Paths;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/8/18 9:56 PM
 * @Modified:
 */

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(RestProperty.class)
@PropertySource(value = "classpath:iot-rest-default.properties",encoding = "utf-8")
public class RestConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private DataProperty dataProperty;

    @Autowired
    private RestProperty restProperty;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PATCH","HEAD")
                .exposedHeaders(restProperty.getCountHeader(),restProperty.getExistedHeader())//如果不设置ajax将无法读取到自定义的header
                .maxAge(3600);
    }

//    @Bean("objectMapper")
//    public ObjectMapper mapper() {
//        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler(appendFsSplitter(dataProperty.getFs().getCaptcha())+"**")
//                .addResourceLocations("file:"+ appendFsSplitter(Paths.get(dataProperty.getFs().getRepo(), dataProperty.getFs().getImage()).toString()));

    }

    private String appendFsSplitter(String path){
        if(!path.endsWith("/")){
            path=path+"/";
        }
        return path;
    }



    @Bean
    public Docket restDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nationalchip.iot.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("DevHub后端Api说明文档")
                .description("Api由SpringBoot开发")
                .version("1.0")
                .build();
    }

}
