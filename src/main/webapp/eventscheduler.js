$(document).ready(function() {
	getEvents();
	init();

});


$("table").on("click", ".delete", function() {
	deleteEvent(this.id);
});
$("table").on("click", ".participant", function() {
	var modal = document.getElementById("participantModal");
	var addParticipant = document.getElementById("participantAddition");

	var span = document.getElementsByClassName("close")[1];

	var addBtn = document.getElementById("addParticipant");
	var eventID = this.id;

	addParticipant.style.display = "none";
	modal.style.display = "block";
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	addBtn.onclick = function() {
		addEventParticipant(eventID);
	}

});

$("table").on("click", ".updateBtn", function() {
	var modal = document.getElementById("updateModal");
	var span = document.getElementsByClassName("close")[0];
	var updateBtn = document.getElementById("update");
	var eventID = this.parentElement.parentElement.cells[0].innerHTML;
	modal.style.display = "block";
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
	updateBtn.onclick = function() {
		updateEvent(eventID);
	}
	populateDropDown();
	populateForm(this);
	//this.parentElement.parentElement.setAttribute('contenteditable', true);

});

function populateForm(e) {
	document.getElementById('eventTitle').value = e.parentElement.parentElement.cells[1].innerText;
	var d = parseInt(e.parentElement.parentElement.cells[3].innerText);
	document.getElementById('eventTime').value = moment(d).format('YYYY-MM-DDTHH:mm'); //yyyy-MM-ddThh:mm
	//document.getElementById('eventDuration').value = e.parentElement.parentElement.cells[2].innerText;
	// $("#duration").val(e.parentElement.parentElement.cells[2].innerText).attr("selected","selected");
	document.getElementById('duration').selectedIndex = e.parentElement.parentElement.cells[2].innerText / 15 - 1;
};
function getEventsByDate() {
	var date = moment(new Date($('#eventDate').val())).format('DD/MM/YYYY');
	$.ajax({
		type: "GET",
		url: "EventDate?date=" + date,
		contentType: "application/json",
		success: printParticipantEvents
	});

};

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
		success: printParticipantEvents,
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR,textStatus, errorThrown);
		}
	});
};

function updateEvent(eventID) {

	var obj = {
		'eventID': eventID,
		'eventTitle': $('#eventTitle').val(),
		'eventTime': new Date($('#eventTime').val()).getTime(),
		'eventDuration': $('#duration').val() * 60000
	};
	$.ajax({
		type: "PUT",
		url: "event",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			alert(response);
			getEvents();
			//printEvents(response);
		}

	});

}
function deleteEvent(eventID) {
	var id = eventID;
	$.ajax({
		type: "DELETE",
		url: "event?id=" + id,
		contentType: "application/json",
		success: function(response) {
			alert(response);
			getEvents();
		}
	});
};

function addEventParticipant(eventID) {
	var obj = {
		'eventID': eventID,
		'email': $('#email').val(),
		'name': $('#name').val(),
		'timeZone': $('#timezone').val()
	};
	//	var id = $('input[type=radio][name=radioChoose]:checked').attr('id');
	$.ajax({
		type: "POST",
		url: "event/participant",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			alert(response);
			clearResponse();
			getEvents();
		},
		error: function(xhr, status, error) {
			alert("Participant does not exist. Please Create new participant.");
			var addParticipantDiv = document.getElementById("participantAddition");
			addParticipantDiv.style.display = "block";
		}
	});
};

function clearResponse() {
	document.getElementById('email').value = '';
	document.getElementById('name').value = '';
}
function printParticipantEvents(response) {
	var result = '<tr><td>Event Title</td><td>Event Duration</td><td>Event Start Time</td><td>Event Created Time</td><td>Participant Email</td></tr>';
	for (var i = 0; i < response.length; i++) {
		result += ('<tr>' +
			'<td>' + response[i].eventTitle + '</td>' +
			'<td>' + response[i].eventDuration / 60000 + 'min</td>' +
			'<td>' + new Date(response[i].eventTime).toUTCString() + '<br><br>' + new Date(response[i].eventTime).toLocaleString() + '</td>' +
			'<td>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br><br>' + new Date(response[i].eventCreatedTime).toLocaleString() + '</td>' +
			'<td>' + ((response[i].participantEmail != null) ? response[i].participantEmail : 'no participant') + '</td>' +
			+ '</tr>');
	}

	$('#participantEvent').html(result);

}
function printEvents(response) {
	var result = '<tr><td>Event Title</td><td>Event Duration in minutes</td><td>Event Start Time</td><td>Event Created Time</td><td>Email</td><td>Update</td><td>Select</td><td></td></tr>';
	for (var i = 0; i < response.length; i++) {
		result += ('<tr>' +
			'<td style="display:none">' + response[i].eventID + '</td>' +
			'<td>' + response[i].eventTitle + '</td>' +
			'<td>' + response[i].eventDuration / 60000 + '</td>' +
			'<td style="display:none">' + response[i].eventTime + '<!--<br>' + new Date(response[i].eventTime).toUTCString() + '<br>' + new Date(response[i].eventTime).toLocaleString() + '--></td>' +
			'<td>' + new Date(response[i].eventTime).toUTCString() + '<br><br>' + new Date(response[i].eventTime).toLocaleString() + '</td>' +

			'<td>' + new Date(response[i].eventCreatedTime).toUTCString() + '<br><br>' + new Date(response[i].eventCreatedTime).toLocaleString() + '</td>' +
			'<td>' + ((response[i].participantEmail != null) ? response[i].participantEmail : 'no participant') + '</td>' +
		//	'<td>' + ((response[i].participantKey != null) ? response[i].participantKey : 'no participant') + '</td>' +

			//	'<td>' + '<input type="radio" id=' + response[i].eventID + ' name="radioChoose"></input>' + '</td>'
			'<td><button class="updateBtn">Update</button></td>' +
			//'<td>' + '<button type="button" id=' + response[i].eventID + ' class="update">Update</button>' + '</td>' +

			'<td>' + '<button type="button" id=' + response[i].eventID + ' class="delete">Delete</button>' + '</td>' +
			'<td>' + '<button type="button" id=' + response[i].eventID + ' class="participant">Add participant</button>' + '</td>'

			+ '</tr>');
	}

	$('#events').html(result);

}
function submitEvent() {
	var obj = {
		'eventTitle': $('#title').val(),
		'eventTime': new Date($('#eventtime').val()).getTime(),
		'eventDuration': $('#duration').val() * 60000
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
function getEventsByTimeRange() {
	var start = new Date($('#start').val()).getTime();
	var end = new Date($('#end').val()).getTime();
	$.ajax({
		type: "GET",
		url: "TimeRange?start=" + start + "&end=" + end,
		contentType: "application/json",
		//	data: JSON.stringify(obj),
		success: printParticipantEvents

	});
}
function init() {
	$('#getEventsByMail').click(function() {
		getEventsByMail();
	});
	$('#postEvent').click(function() {
		submitEvent();
	});
	$('#duration').click(function() {
		populateDropDown();
	});
	$('#date').click(function() {
		submit();
	});
	$('#getEventsByTimeRange').click(function() {
		getEventsByTimeRange();
	});
	$('#getEventsByDate').click(function() {
		getEventsByDate();
	});

};


function populateDropDown() {
	for (var i = 15; i <= 120; i = i + 15) {
		var option = document.createElement("option");

		option.text = i;
		option.value = i;

		var select = document.getElementById("duration");
		select.appendChild(option);
	}
}

