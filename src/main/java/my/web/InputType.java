package my.web;

public enum InputType {
    selectOneRadio("selectOneRadio"),
    selectOneMenu("selectOneMenu"),
    selectBooleanCheckbox("selectBooleanCheckbox"),
    commandButton("commandButton"),
    commandLink("commandLink"),
    inputText("inputText"),
    pSpinner("p:spinner"),
    pSlider("p:slider"),
    aceSliderEntry("ace:sliderEntry"),
    ;

    private final String label;

    InputType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
