/**
 * 
 */
package com.example.orbix_web.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

/**
 * @author GODFREY
 *
 */
@Component
@FxmlView("MainView.fxml")
public class MainWindowController implements Initializable {
	
	Scene sceneSettings;
    public Stage primaryStage;
    
    @FXML private ComboBox<String> cmbDBServer;
    @FXML private TextField txtDBServerAddress;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private TextField txtDBName;
    
    private String DBServer;
    private String DBServerAddress;
    private String username;
    private String password;
    private String DBName;
     
    private String dataSourceURL;
    private String dataSourceUsername;
    private String dataSourcePassword;
	  
    public void setPrimaryStage(Stage stage) {
    	  this.primaryStage = stage;
    }    
    @FXML 
    public void saveButtonClicked(ActionEvent e) {
    	
    	Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(cmbDBServer.getSelectionModel().getSelectedItem().toString());
        alert.showAndWait();    	
    	System.out.println("Clicked: ");
    }
    
    @FXML 
    public void testDBConnection(ActionEvent e) {    	
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Database Connection");
        alert.setHeaderText(null);
        alert.setContentText("Connection Successiful");
        alert.showAndWait();
    }
    
    @FXML
    public void startServer(ActionEvent e) {
    	//Start or restart the application and server
    }
    @FXML
    public void stopServer(ActionEvent e) {
    	//Stop the application server
    }
    @FXML
    public void saveDBProperties(ActionEvent e) {
    	//Save database properties
    	
    	this.setDBServer(cmbDBServer.getSelectionModel().getSelectedItem().toString());
    	this.setDBServerAddress(txtDBServerAddress.getText());
    	this.setUsername(txtUsername.getText());
    	this.setPassword(txtPassword.getText());
    	this.setDBName(txtDBName.getText());
    	
    	if(this.getDBServerAddress().equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Configuration");
            alert.setHeaderText("Missing information");
            alert.setContentText("Missing or Invalid Server Address");
            alert.showAndWait();
    	}else if(this.getUsername().equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Configuration");
            alert.setHeaderText("Missing information");
            alert.setContentText("Missing or Invalid Username");
            alert.showAndWait();
    	}else if(this.getDBName().equals("")) {
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Configuration");
            alert.setHeaderText("Missing information");
            alert.setContentText("Missing or Invalid Database name");
            alert.showAndWait();
    	}else {    		
    		if(this.getDBServer()=="MySQL") {
    			final String portNo="3306";
    			this.setDataSourceURL("jdbc:mysql://"+this.getDBServerAddress()+":"+portNo+"/"+this.getDBName()+"?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false");
    		}
	    	this.setDataSourceUsername(this.getUsername());
	    	this.setDataSourcePassword(this.getPassword());
	    		    	
	    	Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Database Configuration");
	        alert.setHeaderText(null);
	        alert.setContentText("Database information saved successively \n Data source URL: "+this.getDataSourceURL()+"\n Data source username: "+this.getDataSourceUsername()+"\n Data source password: "+this.getDataSourcePassword());
	        alert.showAndWait();    			        
    	}    	    	
    }
    @FXML
    public void cancelDBProperties(ActionEvent e) {
    	//Cancel 
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
		cmbDBServer.getItems().removeAll(cmbDBServer.getItems());
		cmbDBServer.getItems().addAll("MySQL", "Oracle", "SQL Server","Postgress");
		cmbDBServer.getSelectionModel().select("MySQL");		
	}
	/**
	 * @return the dBServer
	 */
	public String getDBServer() {		
		return DBServer;
	}
	/**
	 * @param dBServer the dBServer to set
	 */
	public void setDBServer(String dBServer) {
		DBServer = dBServer;
	}
	/**
	 * @return the dBServerAddress
	 */
	public String getDBServerAddress() {
		return DBServerAddress;
	}
	/**
	 * @param dBServerAddress the dBServerAddress to set
	 */
	public void setDBServerAddress(String dBServerAddress) {
		DBServerAddress = dBServerAddress;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the dBName
	 */
	public String getDBName() {
		return DBName;
	}
	/**
	 * @param dBName the dBName to set
	 */
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	/**
	 * @return the dataSourceURL
	 */
	public String getDataSourceURL() {
		return dataSourceURL;
	}
	/**
	 * @param dataSourceURL the dataSourceURL to set
	 */
	public void setDataSourceURL(String dataSourceURL) {
		this.dataSourceURL = dataSourceURL;
	}
	/**
	 * @return the dataSourceUsername
	 */
	public String getDataSourceUsername() {
		return dataSourceUsername;
	}
	/**
	 * @param dataSourceUsername the dataSourceUsername to set
	 */
	public void setDataSourceUsername(String dataSourceUsername) {
		this.dataSourceUsername = dataSourceUsername;
	}
	/**
	 * @return the dataSourcePassword
	 */
	public String getDataSourcePassword() {
		return dataSourcePassword;
	}
	/**
	 * @param dataSourcePassword the dataSourcePassword to set
	 */
	public void setDataSourcePassword(String dataSourcePassword) {
		this.dataSourcePassword = dataSourcePassword;
	}
		
}
