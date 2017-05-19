package com.dnweb.springmvcshoeshop.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dnweb.springmvcshoeshop.interceptor.MenuInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Autowired
	private MenuInterceptor menuInterceptor;

	// Cấu hình UTF-8 cho các trang.
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
		converters.add(stringConverter);

		// Add other converters ...
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/").setCachePeriod(31556926);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// Hàm này được Spring gọi tự động sau khi tiêm hết?? Thử để biết.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// MenuInterceptor áp dụng cho mọi URL.
		registry.addInterceptor(menuInterceptor);
	}
}
