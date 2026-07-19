package creational_pattern.abstract_factory.application;

public class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in macOS style.");
    }
}
