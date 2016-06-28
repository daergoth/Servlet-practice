package net.daergoth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.daergoth.model.UserProfileVO;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Gson gson = new GsonBuilder().create();
		
		response.setContentType("application/json; charset=utf-8");
		
		UserProfileVO user = (UserProfileVO)request.getSession().getAttribute("profile");
		
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		
		response.getWriter().append(gson.toJson(user));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=utf-8");
		Gson gson = new GsonBuilder().create();
		
		if (request.getReader().ready()) {
			UserProfileVO user = gson.fromJson(request.getReader(), UserProfileVO.class);
			
			request.getSession().setAttribute("profile", user);
			
		} else {
			response.getWriter().append("User profile data required!");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().create();
		JsonParser parser = new JsonParser();
		
		JsonObject received = parser.parse(request.getReader()).getAsJsonObject();
		JsonObject stored = gson.toJsonTree(request.getSession().getAttribute("profile")).getAsJsonObject();
		
		for (Map.Entry<String, JsonElement> entry : received.entrySet()) {
			stored.add(entry.getKey(), entry.getValue());
		}
		
		UserProfileVO newProfile = gson.fromJson(stored, UserProfileVO.class);
		
		request.getSession().setAttribute("profile", newProfile);
		
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("profile", null);
	}

}
