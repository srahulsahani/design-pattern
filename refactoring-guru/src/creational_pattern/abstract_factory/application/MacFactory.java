package creational_pattern.abstract_factory.application;

public class MacFactory implements GUIFactory {
    
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
