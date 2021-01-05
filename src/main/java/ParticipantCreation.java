
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import helper.SerializerHelper;
import model.Event;
import model.ParticipantDetails;
import service.participant.*;

@WebServlet(name = "ParticipantCreation", urlPatterns = { "/participant" })
public class ParticipantCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ParticipantServiceImp participantService = new ParticipantServiceImp();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SerializerHelper serializer = new SerializerHelper();
		ParticipantDetails participant = serializer.bufferedReaderToParticipantObject(request.getReader());
		participantService.createParticipant(participant);

		// doGet(request, response);
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SerializerHelper serializer = new SerializerHelper();
		try {
			ParticipantDetails participant = serializer.bufferedReaderToParticipantObject(request.getReader());
			participantService.modifyParticipant(participant);
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Modified participant");

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (DataBaseException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			System.out.println("Event not found");
		}

	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			participantService.removeParticipant((request.getParameter("id")));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("Deleted");

		} catch (IllegalArgumentException | EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
