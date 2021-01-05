
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helper.SerializerHelper;
import service.event.EventHelper;

/**
 * Servlet implementation class EventDate
 */
@WebServlet("/EventDate")
public class EventDate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EventHelper eventHelper = new EventHelper();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SerializerHelper serializer = new SerializerHelper();
		String result;
		try {
			result = serializer.javaObjectToJson(eventHelper.retrieveByDate(request.getParameter("date")));

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
