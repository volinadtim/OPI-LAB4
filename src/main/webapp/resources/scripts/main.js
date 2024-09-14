'use strict';

let isError = false;

function hasOnlyDigits(value) {
	return value.match(/^([0-9\-]*|[0-9\-]*\.[0-9\-]*)$/);
}

function makesSense(value) {
	if (value === '0') return true;
	return value.match(/^\-?([1-9][0-9]*|0)(\.[0-9\-]*)?$/);
}

function validateFloat(value, min = null, max = null) {
    if (value === null) return 'Введите не нул';
	if (!hasOnlyDigits(value)) {
		return 'Введите число';
	}
	if (!makesSense(value)) {
		return 'Нет, введите число';
	}
	const floatValue = parseFloat(value);
	if (min !== null) {
		if (floatValue <= min) return `Число должно быть больше, чем ${min}`
	}
	if (max !== null) {
		if (floatValue >= max) return `Число должно быть меньше, чем ${max}`
	}
	return null;
}

const formEl = document.getElementsByTagName('form')[0];
const formId = formEl.id;
const submitEl = document.getElementById(`${formId}:submit`);


const coordinateXField = new TextField(`${formId}:coordinateX`, xInputMin, xInputMax, `${formId}:coordinateX`, `coordinateXCont`);
const coordinateYField = new TextField(`${formId}:coordinateY`, yInputMin, yInputMax, `${formId}:coordinateY`, `coordinateYCont`);
const radiusField = new EmptyField(`${formId}:radius`, `radiusCont`);

setTimeout(() => {
    coordinateXField.writeValue(xInputValue);
    coordinateYField.writeValue(yInputValue);
    radiusField.writeValue(rInputValue);
}, 0);

//const radiusField = new SliderField(`${formId}:radius`, rInputMin, rInputMax, `${formId}:radius`, `radiusCont`);

coordinateXField.changed = () => {
	this.checkIsError();
};
coordinateYField.changed = () => {
	this.checkIsError();
};
radiusField.changed = () => {
	this.checkIsError();
};

function checkIsError() {
	const valid = coordinateXField.valid && coordinateYField.valid && radiusField.valid;
	isError = !valid;
	submitEl.disabled = isError;
}

function newShot({x, y}) {
	if (!radiusField.value) {
	    alert('Определите радиус сперва.');
	    return;
	}

	coordinateXField.onChange(x.toString());
	coordinateYField.onChange(y.toString());

	console.log(x, y);


	requestShoot([
	{
        name: 'x',
        value: x
	},
        {
        name: 'y',
        value: y
        }
	]);
}

setTimeout(() => {
    checkIsError();
});