package net.daergoth;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TokenAuthFilter implements Filter {
	
	private ConcurrentHashMap<String, String> tokens;

    public TokenAuthFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    	tokens = new ConcurrentHashMap<String, String>();
    }
    
    public void destroy() {
    	
    }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		if (req.getMethod() == "POST") {
			
		}
		
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}


}
