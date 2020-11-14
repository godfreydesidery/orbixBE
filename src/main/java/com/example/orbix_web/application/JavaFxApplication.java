/**
 * 
 */
package com.example.orbix_web.application;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.orbix_web.OrbixWebApplication;
import com.example.orbix_web.controllers.MainWindowController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author GODFREY
 *
 */
public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    public Stage primaryStage;
    
    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
    @Override
    public void start(Stage stage) throws IOException {
    	MainWindowController controller = new MainWindowController();
    	final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/example/orbix_web/view/MainView.fxml"));
    	loader.setController(controller);
    	Parent root = loader.load();    	
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();    	
    }
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(OrbixWebApplication.class)
                .run(args);
    }
}
