package view;

import controller.PersonDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

import static view.Display.daoManager;

public class ChangePassword {

    @FXML
    private TextField old_pw;

    @FXML
    private TextField new_pw;

    @FXML
    private TextField user_name;

    @FXML
    private Button changePassBtn;

    private boolean confirmation(){
        Stage stage = (Stage) changePassBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT");
        alert.setHeaderText("You're about to change your password");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            getAlert("Success in changing the password!!");
            stage.close();
            return true;
        }
        return false;
    }

    public void changePW() {
        PersonDAO m = new PersonDAO(daoManager.conn);
        if (user_name.getText().trim().equals("")
                || old_pw.getText().trim().equals("")
                || new_pw.getText().trim().equals("")) {
            getAlert("You need to fill all fields!!");
        } else {
            if(LoginScreenController.getHashPass(old_pw.getText(),user_name.getText()).equals(m.getPass(user_name.getText()))) {
                if(confirmation()) {
                    m.updatePassword(user_name.getText().trim(), new_pw.getText().trim());
                }
            } else {
                getAlert("Incorrect old password or username does not exist!!");
            }
        }
    }

    public void getAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(s);
        alert.show();
    }
}
