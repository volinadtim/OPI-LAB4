'use strict'



class EmptyField {
  containerEl;
  errorEl;
  value;
  name;
  valid = true;

  constructor(name, containerId) {
    this.name = name;
    this.containerEl = document.getElementById(containerId);
//    this.errorEl = document.createElement('div');
//    this.errorEl.classList.add('error');
//    this.containerEl.appendChild(this.errorEl);
  }

  validate(newValue) {
    this.valid = true;
  }

  prepareValue(value) {
    return value;
  }

  onChange(newValue) {
    this.validate(newValue);
    const floatValue = parseFloat(newValue);
    if (!isNaN(floatValue)) {
      this.value = this.prepareValue(this.value);
    }
    if (draw) {
        draw();
    }
    this.changed();
  }

  writeValue(newValue) {
//    this.validate(newValue);
//    const floatValue = parseFloat(newValue);
//    if (!isNaN(floatValue)) {
//      this.value = this.prepareValue(this.value);
//    }
    this.value = newValue;
    if (draw) {
        draw();
    }
    this.changed();
  }

  changed = () => {};
}

class Field extends EmptyField {
  containerEl;
  errorEl;
  value;
  name;
  min;
  max;
  valid = false;

  constructor(name, min, max, containerId) {
    super(name, containerId);
    this.name = name;
    this.min = min;
    this.max = max;
    this.containerEl = document.getElementById(containerId);
    this.errorEl = document.createElement('div');
    this.errorEl.classList.add('error');
    this.containerEl.appendChild(this.errorEl);
  }

  validate(newValue) {
    const error = validateFloat(newValue, this.min, this.max);
    this.errorEl.innerText = error;
    this.value = newValue;
    this.valid = error === null;
  }

  prepareValue(value) {
    return value;
//    console.log(value);
//    return parseFloat(value); // .toFixed(2);
  }

  onChange(newValue) {
    this.validate(newValue);
    const floatValue = parseFloat(newValue);
    if (!isNaN(floatValue)) {
      this.value = this.prepareValue(this.value);
    }
    if (draw) {
        draw();
    }
    this.changed();
  }

  changed = () => {};
}

class TextField extends Field {
  inputEl;

  constructor(name, min, max, inputId, containerId) {
    super(name, min, max, containerId);
    this.inputEl = document.getElementById(inputId);
    this.inputEl.name = name;
    this.inputEl.onkeydown = (event) => this.onChange(event.target.value);
    this.inputEl.onchange = (event) => this.onChange(event.target.value);
    this.inputEl.oninput = (event) => this.onChange(event.target.value);
    setTimeout(() =>
        { this.onChange(this.inputEl.value); }
    );
  }

  onChange(newValue) {
    super.onChange(newValue);
    this.inputEl.value = this.value;
  }

  writeValue(newValue) {
    super.writeValue(newValue);
    this.inputEl.value = this.value;
  }
  
}

class SliderField extends Field {
  sliderEl;

  constructor(name, min, max, sliderId, containerId) {
    super(name, min, max, containerId);
    this.sliderEl = document.getElementById(sliderId);
    this.sliderEl.name = name;
    this.sliderEl.onkeydown = (event) => this.onChange(event.target.value);
    this.sliderEl.onchange = (event) => this.onChange(event.target.value);
    this.sliderEl.oninput = (event) => this.onChange(event.target.value);
    setTimeout(() =>
        { this.onChange(this.sliderEl.value); }
    );
  }

  onChange(newValue) {
    super.onChange(newValue);
    this.sliderEl.value = this.value;
  }
  
}

class SelectField extends Field {
  selectEl;
  options;

  constructor(name, min, max, options, selectId, containerId) {
    super(name, min, max, containerId);
    this.options = options;
    this.selectEl = document.getElementById(selectId);
    this.selectEl.name = name;
    this.selectEl.onkeydown = (event) => this.onChange(event.target.value);
    this.selectEl.onchange = (event) => this.onChange(event.target.value);
    this.selectEl.oninput = (event) => this.onChange(event.target.value);
  }
  
}

class CheckboxField extends Field {
  containerEl;
  options;
  checkboxEls;

  constructor(name, min, max, options, checkboxContainerId, containerId) {
    super(name, min, max, containerId);
    this.options = options;
    this.containerEl = document.getElementById(checkboxContainerId);
    this.containerEl.onkeydown = (event) => this.onChange(event.target.value);
    this.containerEl.onchange = (event) => this.onChange(event.target.value);
    this.initOptions();
  }
  
  initOptions() {
    this.checkboxEls = [];
    this.options.forEach(option => {
      const labelEl = document.createElement("label");
      const optionEl = document.createElement("input");
      const spanEl = document.createElement("span");
      optionEl.type = 'checkbox';
      optionEl.value = option;
      optionEl.name = this.name;
      optionEl.onclick = (event) => {
        this.onClick(event.target);
      }
      spanEl.innerText = option;
      this.containerEl.appendChild(labelEl);
      labelEl.appendChild(optionEl);
      labelEl.appendChild(spanEl);
      this.checkboxEls.push(optionEl);
    })
  }

  onClick(target) {
    const {checked, value} = target;
    if (checked) {
      this.onChange(value);
    } else {
      this.onChange(null);
    }
  }

  onChange(newValue) {
    this.checkboxEls.forEach(checkboxEl => {
      checkboxEl.checked = checkboxEl.value == newValue;
    });
    super.onChange(newValue);
  }

}