module ChatroomApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application.controllers to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
