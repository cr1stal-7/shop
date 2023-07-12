module com.example.practice {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.example.practice to javafx.fxml;
    exports com.example.practice;
}