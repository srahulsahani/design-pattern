package creational_pattern.abstract_factory.application;

public class Main {
    
    public static void main(String[] args) {
        // Windows Application
        System.out.println("=== Windows Application ===");
        GUIFactory windowsFactory = new WindowsFactory();
        Application windowsApp = new Application(windowsFactory);
        windowsApp.paint();
        
        // Mac Application
        System.out.println("\n=== Mac Application ===");
        GUIFactory macFactory = new MacFactory();
        Application macApp = new Application(macFactory);
        macApp.paint();
    }
}
