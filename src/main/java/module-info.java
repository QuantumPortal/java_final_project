module ca.bcit.comp2522.java_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;


    opens ca.bcit.comp2522.java_final_project to javafx.fxml;
    exports ca.bcit.comp2522.java_final_project;
}