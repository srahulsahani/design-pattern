package creational_pattern.abstract_factory.application;

public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in macOS style.");
    }
}
