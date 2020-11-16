/**
 * 
 */
package com.example.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author GODFREY
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter  implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest requestToUse = (HttpServletRequest) request;
		HttpServletResponse responseToUse = (HttpServletResponse) response;
		
		responseToUse.setHeader("Access-Control-Allow-Origin", requestToUse.getHeader("*"));
		
		chain.doFilter(requestToUse, responseToUse);
	}
	@Override
	public void init(FilterConfig filhterConfig) throws ServletException {
		LOGGER.info("Initializing CORS filter");
	
	}
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
