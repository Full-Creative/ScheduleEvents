

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import model.Event;
import model.ParticipantDetails;


public class SerializerHelper {
	protected String bufferedReaderToString(BufferedReader reader) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	protected Event bufferedReaderToJavaObject(BufferedReader reader) throws IOException,NumberFormatException {
		String javaString = bufferedReaderToString(reader);
		Event data = new Gson().fromJson(javaString, Event.class);
		return data;
	}
	protected ParticipantDetails bufferedReaderToParticipantObject(BufferedReader reader) throws IOException {
		String javaString = bufferedReaderToString(reader);
		ParticipantDetails data = new Gson().fromJson(javaString, ParticipantDetails.class);
		return data;
	}
	protected String javaObjectToJson(Object data) {
		String jsonString = new String();
		try {
			jsonString = new Gson().toJson(data);
		} catch (NullPointerException e) {
			System.out.print("Null pointer exception");
		}
		return jsonString;
	}
}
