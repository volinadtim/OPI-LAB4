package my.web;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Поле Input чтобы красиво отображать.
 */
public final class Input {
    private final InputType type;
    private final List<Double> options;
    private final double min;
    private final double max;
    private final double step;

    public Input(InputType type, List<Double> options) {
        this.type = type;
        this.options = options;
        this.min = 0;
        this.max = 0;
        this.step = 0;
    }

    public Input(InputType type, double min, double max) {
        this.type = type;
        this.options = null;
        this.min = min;
        this.max = max;
        this.step = 0;
    }

    public Input(InputType type, double min, double max, double step) {
        this.type = type;
        this.options = null;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public Input(InputType type, List<Double> options, double min, double max, double step) {
        this.type = type;
        this.options = options;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public InputType getType() {
        return type;
    }

    public List<Double> getOptions() {
        return options;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    public String validate(double value) {
        if (options != null) {
            // Если установлен options, то min, max, step не влияют
            if (options.contains(value)) {
                return null;
            } else {
                var optionsList = options.stream().map(Object::toString).reduce((i, j) -> i + ", " + j);
                return "Value %.2f should be one of %s".formatted(value, optionsList);
            }
        }

        // Ответ должен быть в (min, max)
        if (value <= min) return "Value %.2f should be greater than %.2f".formatted(value, min);
        if (value >= max) return "Value %.2f should be lower than %.2f".formatted(value, max);

        // Если step=0, то он не используется
        if (step == 0) return null;

        // TODO: фалидация по step
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Input) obj;
        return Objects.equals(this.type, that.type) && Objects.equals(this.options, that.options) && Double.doubleToLongBits(this.min) == Double.doubleToLongBits(that.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(that.max) && Double.doubleToLongBits(this.step) == Double.doubleToLongBits(that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, options, min, max, step);
    }

    @Override
    public String toString() {
        return "Input[" + "type=" + type + ", " + "options=" + options + ", " + "min=" + min + ", " + "max=" + max + ", " + "step=" + step + ']';
    }

}
