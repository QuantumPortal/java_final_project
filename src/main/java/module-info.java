module ca.bcit.comp2522.java_final_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.java_final_project to javafx.fxml;
    exports ca.bcit.comp2522.java_final_project;
}