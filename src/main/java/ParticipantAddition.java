
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

import model.ParticipantDetails;
import service.event.EventServiceImp;

@WebServlet(name = "ParticipantAddition", urlPatterns = { "/event/participant" })
public class ParticipantAddition extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SerializerHelper serializer = new SerializerHelper();
		ParticipantDetails participant = serializer.bufferedReaderToParticipantObject(request.getReader());
		EventServiceImp eventService = new EventServiceImp();
		try {
			eventService.addParticipant(participant);

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Inserted participant");
		} catch (EntityNotFoundException e) {
			System.out.println("Event not available");
		}
		// doGet(request, response);
	}

}
