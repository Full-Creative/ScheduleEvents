$(document).ready(function() {
	$("#getEvents").click(function() {
		getEvents();
	});
	$("#getEventsByMail").click(function() {
		getEventsByMail();
	});
	$("#postEvent").click(function() {
		submitEvent();
	});
	$('#delete').click(function() {
		deleteEvent();
	});
});

//$("#delete").on("click", function() {
	//deleteEvent();
//});

function getEvents() {
	$.ajax({
		type: "GET",
		url: "events",
		contentType: "application/json",
		success: printEvents,
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus, errorThrown);
		}

	});
};
function getEventsByMail() {
	$.ajax({
		type: "GET",
		url: "ParticipantEvent?email=" + document.getElementById("email").value,
		contentType: "application/json",
		success: printEvents
	});
};
function deleteEvent() {
	var id = $('input[type=radio][name=radioChoose]:checked').attr('id');
	$.ajax({
		type: "DELETE",
		url: "event?id=" + id,
		contentType: "application/json",
		success: function(response) {
			alert(response);
		}
	});
};

function printEvents(response) {
	var result = '<tr><td>Event ID</td><td>Event Title</td><td>Event Duration</td><td>Event Start Time</td><td>Event Created Time</td><td>Participant Key</td><td>Select</td></tr>';
	for (var i = 0; i < response.length; i++) {
		result += ('<tr>' +
			'<td>' + response[i].eventID + '</td>' +
			'<td>' + response[i].eventTitle + '</td>' +
			'<td>' + response[i].eventDuration + '  ' + response[i].eventDuration / 3600000 + 'hour</td>' +
			'<td>' + response[i].eventTime + '<br>' + new Date(response[i].eventTime).toUTCString() + '<br>' + new Date(response[i].eventTime).toLocaleString() + '</td>' +
			'<td>' + response[i].eventCreatedTime + '<br>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br>' + new Date(response[i].eventCreatedTime).toLocaleString() + '</td>' +
			'<td>' + ((response[i].participantKey != null) ? response[i].participantKey : 'no participant') + '</td>' +
			'<td>' + '<input type="radio" id=' + response[i].eventID + ' name="radioChoose"></input>' + '</td>'
		//	'<td>' + '<button type="button" id=' + response[i].eventID + ' class="delete">Delete</button>' + '</td>'
			+ '</tr>');
	}

	$('#events').html(result);

}
function submitEvent() {
	var obj = {
		'eventTitle': $('#title').val(),
		'eventTime': $('#time').val(),
		'eventDuration': $('#duration').val()
	};
	$.ajax({
		type: "POST",
		url: "event",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			alert(response);
			//printEvents(response);
		}

	});
};

