$(document).ready(function() {
	$("#getEvents").click(function() {
		getEvents();
	});
	$("#getEventsByMail").click(function() {
		getEventsByMail();
	});

});

function getEvents() {
	$.ajax({
		type: "GET",
		url: "Events",
		contentType: "application/json",
		//data: JSON.stringify(obj),
		success: function(response) {
			printEvents(response);
		}

	});
};
function getEventsByMail() {
	$.ajax({
		type: "GET",
		url: "ParticipantEvent?email="+document.getElementById("email").value,
		contentType: "application/json",
		//data: JSON.stringify(obj),
		success: function(response) {
			printParticipantEvents(response);
		}

	});
};
function printEvents(response) {

	var result = '<tr><td>Event ID</td><td>Event Title</td><td>Event Duration</td><td>Event Start Time</td><td>Event Created Time</td><td>Participant Email</td></tr>';
	for (var i = 0; i < response.length; i++) {
		result += ('<tr>' +
			'<td>' + response[i].eventID + '</td>' +
			'<td>' + response[i].eventTitle + '</td>' +
			'<td>' + response[i].eventDuration + '  ' + response[i].eventDuration / 3600000 + 'hour</td>' +
			'<td>' + response[i].eventTime + '<br>' + new Date(response[i].eventTime).toUTCString() +'<br>'+new Date(response[i].eventTime).toLocaleString()+ '</td>' +
			'<td>' + response[i].eventCreatedTime + '<br>' + new Date(response[i].eventCreatedTime).toUTCString() +'<br>'+new Date(response[i].eventCreatedTime).toLocaleString()+ '</td>' +
			'<td>' + response[i].participantEmail + '</td>'
			+ '</tr>');
	}
	$('#events').html(result);

}
function printParticipantEvents(response) {

	var result = '<tr><td>Event ID</td><td>Event Title</td><td>Event Duration</td><td>Event Start Time</td><td>Event Created Time</td><td>Participant Email</td></tr>';
	for (var i = 0; i < response.length; i++) {
		result += ('<tr>' +
			'<td>' + response[i].eventID + '</td>' +
			'<td>' + response[i].eventTitle + '</td>' +
			'<td>' + response[i].eventDuration + '  ' + response[i].eventDuration / 3600000 + 'hour</td>' +
			'<td>' + response[i].eventTime + '<br>' + new Date(response[i].eventTime).toUTCString() +'<br>'+new Date(response[i].eventTime).toLocaleString()+ '</td>' +
			'<td>' + response[i].eventCreatedTime + '<br>' + new Date(response[i].eventCreatedTime).toUTCString() +'<br>'+new Date(response[i].eventCreatedTime).toLocaleString()+ '</td>' +
			'<td>' + response[i].participantEmail + '</td>'
			+ '</tr>');
	}
	$('#events').html(result);

}
