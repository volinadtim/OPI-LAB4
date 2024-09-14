'use strict'

function clearShots() {
	const req = new XMLHttpRequest();

	req.addEventListener("progress", updateProgress);
	req.addEventListener("load", transferComplete);
	req.addEventListener("error", transferFailed);
	req.addEventListener("abort", transferCanceled);

	req.open("POST", './clear');
	req.send(null);

	function updateProgress(event) {
		if (event.lengthComputable) {
			const percentComplete = (event.loaded / event.total) * 100;
			// ...
		} else {
			// Unable to compute progress information since the total size is unknown
		}
	}

	function transferComplete(evt) {
	    if (req.status == 200) {
		    alert('Подчищено. Можете обновлять пейджу.')
	    } else {
	        alert(req.response);
	    }
	}

	function transferFailed(evt) {
		console.log("An error occurred while transferring the file.");
	}

	function transferCanceled(evt) {
		console.log("The transfer has been canceled by the user.");
	}

}