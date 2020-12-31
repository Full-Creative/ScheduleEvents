
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

import service.event.EventServiceImp;
import service.participant.ParticipantServiceImp;

/**
 * Servlet implementation class ParticipantEvent
 */
@WebServlet("/ParticipantEvent")
public class ParticipantEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ParticipantServiceImp participantService = new ParticipantServiceImp();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SerializerHelper serializer = new SerializerHelper();
		String result;
		try {
			result = serializer.javaObjectToJson(participantService.retrieveEvents(request.getParameter("email")));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(result);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			response.getWriter().print("Event not found");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
