
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helper.SerializerHelper;
import service.event.EventHelper;

@WebServlet("/TimeRange")
public class TimeRange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EventHelper eventHelper = new EventHelper();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SerializerHelper serializer = new SerializerHelper();
		String result = serializer.javaObjectToJson(eventHelper.retrieveByTimeRange(Long.valueOf(request.getParameter("start")),Long.valueOf(request.getParameter("end"))));		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}

}
