package net.daergoth;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum TokenStorage {
	INSTANCE;
	
	private ConcurrentHashMap<String, String> tokens = new ConcurrentHashMap<String, String>();
	
	public void createToken(String sessionId) {
		String newToken = UUID.randomUUID().toString();
		
		tokens.put(sessionId, newToken);
	}
	
	public String getToken(String sessionId) {
		return tokens.get(sessionId);
	}
	
	public void removeToken(String sessionId) {
		tokens.remove(sessionId);
	}
	
}
