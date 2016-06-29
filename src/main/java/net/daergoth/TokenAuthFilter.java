package net.daergoth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (!req.getMethod().equals("POST")) {
			
			String token = req.getHeader("Authorization");
			if (!isTokenValid(req.getSession().getId(), token)) {
				resp.setContentType("text/plain; charset=utf-8");
				
				resp.getWriter().append("Missing or invalid authentication token!");
				
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				
				return;
			}
			
		} 
		
		chain.doFilter(request, response);
		
		if (req.getMethod().equals("POST")) {
			if (!resp.isCommitted()) {
				String token = TokenStorage.INSTANCE.getToken(req.getSession().getId());
				
				resp.setContentType("application/json; charset=utf-8");
				
				resp.getWriter().append("{ \"token\":\"" + token + "\" }");
			}
		}
		
	}

	private boolean isTokenValid(String sessionId, String token) {
		if (token == null) {
			return false;
		}
		
		if (!token.equals(TokenStorage.INSTANCE.getToken(sessionId))) {
			return false;
		}
		
		return true;
	}
}
