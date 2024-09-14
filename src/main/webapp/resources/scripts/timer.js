'use strict'

const timeEl = document.getElementById('time');
actualizeTime();
setInterval(actualizeTime, UPDATE_TIME);

function actualizeTime() {
	const time = new Date().toLocaleTimeString();
	timeEl.innerText = time;
}