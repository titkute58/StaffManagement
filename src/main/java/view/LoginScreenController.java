package view;

import controller.PersonDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static view.Display.daoManager;

public class LoginScreenController implements Initializable {
    private String ceo_user = "ceo";
    private String ceo_pass = "Ceo";
    public static boolean check_ceo = false;

    @FXML
    private TextField enter_pass;

    @FXML
    private TextField enter_user;

    @FXML
    private Label password;

    @FXML
    private Label user_name;

    @FXML
    private Button login;

    public LoginScreenController() throws Exception {
    }

    // matuan : mat123
    // tuanils : tuan123
    // vuong : vuong123
    // thanh : thanh123
    public boolean checkLogin() {
        PersonDAO m = new PersonDAO(daoManager.conn);
        String id = enter_user.getText().trim();
        String pass = getHashPass(enter_pass.getText().trim(), id);
        if(id.equals(ceo_user) && enter_pass.getText().equals(ceo_pass)) {
            try {
                check_ceo = true;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewScreen.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Employees Management");
                stage.show();
                return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if(pass.equals(m.getPass(enter_user.getText()))) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewScreen.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Employees Management");

                stage.show();
                return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getHashPass(String str, String id) {
        String pass = str;
        MessageDigest md;
        StringBuilder password = new StringBuilder();
        try {
            md = MessageDigest.getInstance("SHA-256");

            byte[] salt = new byte[16];
            String sql = "select salt from users where id = ?";
            PreparedStatement ps = daoManager.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String[] tmp = rs.getString(1).split(",");
                for (int i = 0; i < 16; i++) {
                    salt[i] = Byte.parseByte(tmp[i]);
                }
            }
            md.update(salt);

            byte[] hashPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));

            for (byte b : hashPassword) password.append(String.format("%02x", b));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password.toString();
    }

    public void getAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Warning: Wrong username or password!!");
        alert.show();
    }

    @FXML
    private void openNewWindow(){
        Stage stage = (Stage) login.getScene().getWindow();
        if(checkLogin()) {
            stage.close();
        }else {
            getAlert();
        }
    }

    @FXML
    public void openChangePW() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/ChangePassword.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            daoManager.open();
            check_ceo = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
