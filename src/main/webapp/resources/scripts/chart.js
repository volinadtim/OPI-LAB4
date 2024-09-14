'use strict'

const canvas = document.getElementById('canvas');

if (!canvas.getContext) throw new Error('Канвасы дружно пошли домой');

const ctx = canvas.getContext("2d");

let currentX = 0;
let currentY = 0;

let CANVAS_WIDTH;
let CANVAS_HEIGHT

let CANVAS_VIEWPORT_WIDTH;
let CANVAS_VIEWPORT_HEIGHT;

let CANVAS_VIEWPORT_X_PADDINGS;
let CANVAS_VIEWPORT_Y_PADDINGS;

let CANVAS_AXIS_WIDTH;
let CANVAS_AXIS_HEIGHT;

let CANVAS_CHARTS_WIDTH;
let CANVAS_CHARTS_HEIGHT;

let CANVAS_X_PADDINGS;
let CANVAS_Y_PADDINGS;

const HEAD_LEN = 7;
const PRIMARY_COLOR_WHITE_THEME = '#3399ff';
const PRIMARY_COLOR = '#005bdb';

let CURSOR_X = null;
let CURSOR_Y = null;

const dots = [];
let markDot = null;

init();
draw();

window.addEventListener('resize', (event) => {init(); draw();});

canvas.addEventListener('mousemove', (event) => {
  const { x, y } = getCursorInViewport(event.pageX, event.pageY);
  CURSOR_X = x;
  CURSOR_Y = y;

  draw();
});

canvas.addEventListener('mouseleave', (event) => {
  CURSOR_X = null;
  CURSOR_Y = null;

  draw();
});

canvas.addEventListener('click', (event) => {
  let radius = radiusField.value;
  if (!radius) {
    let radius = prompt('Раудиусик нужон');
    radiusField.onChange(radius);
    if (!radius) return;
  }
  const { x, y } = getCursorInViewport(event.pageX, event.pageY);

  const dot = calculateRealCoordinates({x, y});
  markDot = dot;

  dots.push(dot);

  newShot({
    x: parseFloat(dot.x.toFixed(2)),
    y: parseFloat(dot.y.toFixed(2))
  });

  draw();
});

function init() {
	CANVAS_WIDTH = canvas.offsetWidth;
	CANVAS_HEIGHT = Math.min(500, canvas.offsetWidth);

	CANVAS_VIEWPORT_WIDTH = CANVAS_WIDTH - 50;
	CANVAS_VIEWPORT_HEIGHT = CANVAS_HEIGHT - 50;

	CANVAS_VIEWPORT_X_PADDINGS = 50; // (CANVAS_WIDTH - CANVAS_VIEWPORT_WIDTH) / 2
	CANVAS_VIEWPORT_Y_PADDINGS = 50; // (CANVAS_HEIGHT - CANVAS_VIEWPORT_HEIGHT) / 2

	CANVAS_AXIS_WIDTH = CANVAS_WIDTH - 100; // 400;
	CANVAS_AXIS_HEIGHT = Math.min(400, CANVAS_HEIGHT - 100);

	CANVAS_AXIS_WIDTH = Math.min(CANVAS_AXIS_WIDTH, CANVAS_AXIS_HEIGHT);
	CANVAS_AXIS_HEIGHT = CANVAS_AXIS_WIDTH;

	CANVAS_CHARTS_WIDTH = Math.min(300, CANVAS_AXIS_WIDTH - 100);
	CANVAS_CHARTS_HEIGHT = Math.min(300, CANVAS_AXIS_HEIGHT - 100);

	CANVAS_X_PADDINGS = (CANVAS_WIDTH - CANVAS_AXIS_WIDTH) / 2;
	CANVAS_Y_PADDINGS = (CANVAS_HEIGHT - CANVAS_AXIS_HEIGHT) / 2;

	canvas.width = CANVAS_WIDTH;
	canvas.height = CANVAS_HEIGHT;
}

function draw() {
  	ctx.clearRect(0, 0, canvas.width, canvas.height);

  	if (currentX != null && currentY != null) {
  	    markDot = {x: currentX, y: currentY};
  	} else {
  	    markDot = null;
  	}

	drawSquare();
	drawAxis();
	drawDots();
	drawMarkDot();
	drawCursor();
}

function drawSquare() {
	ctx.strokeStyle = 'white';
	ctx.fillStyle = PRIMARY_COLOR;
	ctx.lineWidth = 0;

    ctx.translate(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    ctx.rotate(0.5 * Math.PI);
    ctx.translate(-CANVAS_WIDTH / 2, -CANVAS_HEIGHT / 2);

	for (let i = 0; i < 4; i ++) {

	    const g = GRAPH[i];

        const width = (i % 2) ? g.width : g.height;
        const height = (i % 2) ? g.height : g.width;

	    switch (g.shape) {
	        case 'Rectangle':
	            ctx.fillRect(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2, -CANVAS_CHARTS_WIDTH / 4 * width, -CANVAS_CHARTS_HEIGHT / 4 * height);
                break;
	        case 'Triangle':
                let region = new Path2D();
                region.moveTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
                region.lineTo(CANVAS_WIDTH / 2 - (CANVAS_CHARTS_WIDTH / 4) * width, CANVAS_HEIGHT / 2);
                region.lineTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 - (CANVAS_CHARTS_HEIGHT / 4) * height);
                region.closePath();
                ctx.fill(region, "evenodd");
                break;
            case 'Circle':
                ctx.beginPath();
                ctx.moveTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
                ctx.ellipse(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2, CANVAS_CHARTS_WIDTH / 4 * width, CANVAS_CHARTS_HEIGHT / 4 * height, 0, 1 * Math.PI, 1.5 * Math.PI);
                ctx.closePath();
                ctx.fill();
                break;
            case 'None':
                break;
	    }

        ctx.translate(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        ctx.rotate(-0.5 * Math.PI);
        ctx.translate(-CANVAS_WIDTH / 2, -CANVAS_HEIGHT / 2);
	}

    ctx.translate(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    ctx.rotate(-0.5 * Math.PI);
    ctx.translate(-CANVAS_WIDTH / 2, -CANVAS_HEIGHT / 2);
}

function drawDot(dot, radius, color) {
  if (!radiusField.value) return;
  const {x, y} = calculatePixelCoordinates({realX: dot.x, realY: dot.y});

  ctx.fillStyle = color;
  ctx.beginPath(x, y);
  ctx.moveTo(x, y);
  ctx.arc(x, y, radius, 0, 2 * Math.PI);
  ctx.fill();
}

function drawDots() {
	const minRadius = 1;
	const maxRadius = 3;
	for (let i = 0; i < dots.length; i++) {
		drawDot(dots[i], (i / dots.length) * (maxRadius - minRadius) + minRadius, dots[i].hit ? 'blue' : 'red');
	}
}

function drawMarkDot() {
    if (markDot)
    drawDot(markDot, 3, 'yellow');
}

function drawAxis() {
	ctx.strokeStyle = 'white';
	ctx.fillStyle = 'white';
	ctx.lineWidth = 2;
	ctx.font = '12px Ubuntu';
	ctx.textAlign = 'center';

	// Draw x-axis
	ctx.beginPath();
	ctx.moveTo(CANVAS_X_PADDINGS, CANVAS_HEIGHT / 2);
	ctx.lineTo(CANVAS_WIDTH - CANVAS_X_PADDINGS, CANVAS_HEIGHT / 2);
	drawArrow(CANVAS_X_PADDINGS, CANVAS_HEIGHT / 2, CANVAS_WIDTH - CANVAS_X_PADDINGS, CANVAS_HEIGHT / 2, HEAD_LEN);
	ctx.stroke();

	// Draw y-axis
	ctx.beginPath();
	ctx.moveTo(CANVAS_WIDTH / 2, CANVAS_Y_PADDINGS);
	ctx.lineTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT - CANVAS_Y_PADDINGS);
	drawArrow(CANVAS_WIDTH / 2, CANVAS_HEIGHT - CANVAS_Y_PADDINGS, CANVAS_WIDTH / 2, CANVAS_Y_PADDINGS, HEAD_LEN);
	ctx.stroke();

	// Add labels to x-axis
	ctx.fillText('x', CANVAS_WIDTH - CANVAS_X_PADDINGS - 10, CANVAS_HEIGHT / 2 + 20);
	// ctx.fillText('-x', CANVAS_X_PADDINGS + 10, CANVAS_HEIGHT / 2 + 20);
	ctx.fillText('0', CANVAS_WIDTH / 2 - 10, CANVAS_HEIGHT / 2 + 20);
	ctx.fillText('R/2', CANVAS_WIDTH / 2 + CANVAS_CHARTS_WIDTH / 4, CANVAS_HEIGHT / 2 + 20);
	ctx.fillText('R', CANVAS_WIDTH / 2 + CANVAS_CHARTS_WIDTH / 2, CANVAS_HEIGHT / 2 + 20);
	ctx.fillText('-R/2', CANVAS_WIDTH / 2 - CANVAS_CHARTS_WIDTH / 4, CANVAS_HEIGHT / 2 + 20);
	ctx.fillText('-R', CANVAS_WIDTH / 2 - CANVAS_CHARTS_WIDTH / 2, CANVAS_HEIGHT / 2 + 20);

	// Add labels to y-axis
	ctx.fillText('y', CANVAS_WIDTH / 2 - 20, CANVAS_Y_PADDINGS + 20);
	// ctx.fillText('-y', CANVAS_WIDTH / 2 - 20, CANVAS_HEIGHT - CANVAS_Y_PADDINGS - 10);
	ctx.fillText('R/2', CANVAS_WIDTH / 2 - 20, CANVAS_HEIGHT / 2 - CANVAS_CHARTS_HEIGHT / 4 + 5);
	ctx.fillText('R', CANVAS_WIDTH / 2 - 20, CANVAS_HEIGHT / 2 - CANVAS_CHARTS_HEIGHT / 2 + 5);
	ctx.fillText('-R/2', CANVAS_WIDTH / 2 - 20, CANVAS_HEIGHT / 2 + CANVAS_CHARTS_HEIGHT / 4 + 5);
	ctx.fillText('-R', CANVAS_WIDTH / 2 - 20, CANVAS_HEIGHT / 2 + CANVAS_CHARTS_HEIGHT / 2 + 5);

}

function drawArrow(fromx, fromy, tox, toy, headlen = 10) {
  const dx = tox - fromx;
  const dy = toy - fromy;
  const angle = Math.atan2(dy, dx);
  ctx.moveTo(fromx, fromy);
  ctx.lineTo(tox, toy);
  ctx.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
  ctx.moveTo(tox, toy);
  ctx.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
}

function drawCursor() {
  if (CURSOR_X === null || CURSOR_Y === null) return;
  const x = CURSOR_X;
  const y = CURSOR_Y;
  ctx.beginPath();
  ctx.strokeStyle = 'white';
  ctx.fillStyle = 'white';
  ctx.lineWidth = 1;
  ctx.font = '12px Ubuntu';
  ctx.arc(x, y, 10, 0, 2 * Math.PI);
  ctx.stroke();
  if (radiusField.value) {
      const {x: realX, y: realY} = calculateRealCoordinates({x, y});
      if (x > CANVAS_WIDTH - 100) {
        ctx.textAlign = 'right';
        ctx.fillText(`${realX.toFixed(2)} ${realY.toFixed(2)}`, x - 20, y + 3);
      } else {
        ctx.textAlign = 'left';
        ctx.fillText(`${realX.toFixed(2)} ${realY.toFixed(2)}`, x + 20, y + 3);
      }
  }
}

function calculateRealCoordinates({x, y}) {
    if (!radiusField.value) throw new Error('Error');
	const cmInPixels = 2 * radiusField.value / CANVAS_CHARTS_WIDTH;
	const realX = (x - CANVAS_WIDTH / 2) * cmInPixels;
	const realY = -(y - CANVAS_HEIGHT / 2) * cmInPixels;
	return {x: realX, y: realY};
}

function calculatePixelCoordinates({realX, realY}) {
    if (!radiusField.value) throw new Error('Error');
	const pixelsInCm = CANVAS_CHARTS_WIDTH / (2 * radiusField.value);
	const x = realX * pixelsInCm + CANVAS_WIDTH / 2;
	const y = -(realY * pixelsInCm - CANVAS_HEIGHT / 2);
	return {x, y};
}

function getInViewport(x, y) {
  x = Math.min(CANVAS_WIDTH - CANVAS_VIEWPORT_X_PADDINGS, Math.max(CANVAS_VIEWPORT_X_PADDINGS, x));
  y = Math.min(CANVAS_HEIGHT - CANVAS_VIEWPORT_Y_PADDINGS, Math.max(CANVAS_VIEWPORT_Y_PADDINGS, y));
  return {x, y};
}

function getCursorInViewport(x, y) {
  x = x - canvas.offsetLeft;
  y = y - canvas.offsetTop;
  x = Math.min(CANVAS_WIDTH - CANVAS_VIEWPORT_X_PADDINGS, Math.max(CANVAS_VIEWPORT_X_PADDINGS, x));
  y = Math.min(CANVAS_HEIGHT - CANVAS_VIEWPORT_Y_PADDINGS, Math.max(CANVAS_VIEWPORT_Y_PADDINGS, y));
  return {x, y};
}
