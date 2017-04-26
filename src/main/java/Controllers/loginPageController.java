package Controllers;

import UserAccounts.Authentication;
import UserAccounts.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Status: complete.
 */
public class loginPageController extends UIController{

    @FXML
    private TextField username, password;
    @FXML
    private Button createUserButton;

    private Authentication isAuthentic = new Authentication();
    private User user = new User();

    /**
     * Allows ENTER key press when focused on password text field to call loginAction.
     * Allows ENTER key press when focused on loginButton to fire loginAction.
     */
    void setActionOnEnter() {
        password.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                event.consume();
            }
        });
        loginButton.defaultButtonProperty().bind(loginButton.focusedProperty());
    }

    /**
     * Redirects to createUser.fxml
     * @throws IOException - throws exception
     */
    @FXML
    private void setDisplayToCreateUser() throws IOException{
        Stage stage;
        stage=(Stage) createUserButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createUser.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        createUserController controller = loader.getController();
        controller.init(main);
        controller.initializeComboBox();
    }

    /**
     * Login user if valid, redirect to page with correct authentication.
     * Update errorLabel otherwise
     */
    @FXML
    public void loginAction() {
        String name = username.getText();
        String pass = password.getText();
        isAuthentic.setUsername(name);
        isAuthentic.setPassword(pass);
        // Check if valid user
        if(isAuthentic.authenticate()) {
            user = isAuthentic.getFoundUser();
        }
        // Redirect to correct page if valid using returnToMainPage() from UIController
        if(user.getUsername() != null && user.getUsername().equals(name)){
            try {
               super.main.userData.setUserInformation(user);
               super.returnToMainPage();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            loginPageErrorLabel.setText("Invalid Username / Password, Please try again");
            clearFields();
        }
    }

    /**
     * Clears username and password text fields
     */
    private void clearFields(){
        username.clear();
        password.clear();
    }

}
