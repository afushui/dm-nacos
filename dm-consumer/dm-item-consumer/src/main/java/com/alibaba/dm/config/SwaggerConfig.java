package com.hdax.dm.config;

import com.google.common.base.Predicate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    // http://127.0.0.1:8082/swagger-ui.html
    @Bean
    public Docket createRestApi() {
        // return new
        // Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
        // .apis(RequestHandlerSelectors.basePackage("com.xf.controller")).paths(PathSelectors.any()).build();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(basePackage("com.alibaba.dm.controller")).paths(PathSelectors.any()).build();
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("阿福水", "localhost:8080", "2412903178@qq.com");
        return new ApiInfo(
                "SwaggerAPI文档",
                "这个作者有点牛逼",
                "v1.0",
                "localhost:8080",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );

    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, @Nullable Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(";")) {
                assert input != null;
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

}

