package cn.rainspace.logistics.config;

import cn.rainspace.logistics.controller.interceptor.LoginInterceptor;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	@Autowired
	private LoginInterceptor loginHandlerInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	    WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration ir = registry.addInterceptor(loginHandlerInterceptor);
		// 拦截路径
		ir.addPathPatterns("/*");
		// 不拦截路径
		List<String> irs = new ArrayList<String>();
		irs.add("/login");
		irs.add("/register");
		ir.excludePathPatterns(irs);
	}

	//跨域处理
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	            .allowedOriginPatterns("*")
	            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
	            .allowCredentials(true)
	            .allowedHeaders("*")
	            .maxAge(3600);
	}

	//设置cookie的SameSite为None
	@Bean
	public TomcatContextCustomizer sameSiteCookiesConfig() {
		return context -> {
			final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
			cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
			context.setCookieProcessor(cookieProcessor);
		};
	}

}