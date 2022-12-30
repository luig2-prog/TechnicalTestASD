package com.asd.tecnical_test_asd.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebMvc
//@ComponentScan(basePackages ="com.konecta.autshedule.controller")
public class WebConfig implements WebMvcConfigurer {

	/**
	 * @description función encargada de agregar el encabezado de CORS
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param registry
	 * @see <a href = "https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS" /> Cross-Origin Resource Sharing (CORS) </a>
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		CorsRegistration cors = registry.addMapping("/**");
		cors.allowedOrigins(
				"http://localhost:4200",
				"http://127.0.0.1:4200",
				"http://localhost:5500"
				)
				.allowedMethods("POST", "GET", "PUT","OPTIONS")
				.allowCredentials(true).maxAge(3600)
		;
    }
//
//
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.jsp("/WEB-INF/views/", ".jsp");
//	}
//
//
//	@Bean
//	public MultipartResolver multiPartResolver() {
//		return new StandardServletMultipartResolver();
//	}
//
//	///Metodo para procesar la informacion en Json
//	@Bean
//	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
//				.indentOutput(true);
//	    return new MappingJackson2HttpMessageConverter(builder.build());
//	}
//
//
//	///
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(mappingJackson2HttpMessageConverter());
//	    converters.add(new ResourceHttpMessageConverter());
//    }
//
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
//		registry.addResourceHandler("/img/**").addResourceLocations("/static/img/");
//		registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
//	}

}
