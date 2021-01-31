package com.jdc.main;

import java.io.File;
import java.util.function.Consumer;

import com.jdc.View.CollectionController;
import com.jdc.model1.EmployeeModel;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CollectionMain extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		File  file=new File(".trace");
		if (file.exists()) {
			throw new RuntimeException("Application is running already!");
		}else {
			file.createNewFile();
		}
		file.deleteOnExit();
	
			Parent root = FXMLLoader.load(CollectionController.class.getResource("CollectionView.fxml"));
			Scene scene = new Scene(root);
//	scene.getStylesheets().add(getClass().getResource("common1.css").toExternalForm());
			primaryStage.setTitle("Collection");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle("EXIT");
				alert.setContentText("Want to Exit?");
				alert.showAndWait().ifPresent(new Consumer<ButtonType>() {

					@Override
					public void accept(ButtonType t) {
						if (t==ButtonType.CANCEL) {
							event.consume();
						}
						
					}
				});
				
				}
			});
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() throws Exception {
		EmployeeModel.getInstance().saveToFile();
		
	}

}
