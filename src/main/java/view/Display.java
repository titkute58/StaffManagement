package view;

import controller.ConnectionManager;
import controller.PersonDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Person;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static view.LoginScreenController.check_ceo;

public class Display implements Initializable {
    public static ConnectionManager daoManager;

    static {
        try {
            daoManager = new ConnectionManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Person person;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            daoManager.open();
            showAllMember();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Warning: ");
        alert.setContentText(text);
        alert.show();
    }

    @FXML
    private ObservableList<String> lst = FXCollections.observableArrayList();

    @FXML
    private TextField fullName;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button logout;

    @FXML
    private ListView listView = new ListView(lst);

    Thread thread;



    public void getInfo(ActionEvent event) {
        PersonDAO m = new PersonDAO(daoManager.conn);
        if(fullName.getText().equals("")){
            getAlert("You have to enter the employee's name!");
            return;
        }
        lst.clear();
        lst.addAll(m.searchName(fullName.getText(),"engineers"));
        lst.addAll(m.searchName(fullName.getText(),"workers"));
        lst.addAll(m.searchName(fullName.getText(),"guards"));
        if(lst.isEmpty()){
            getAlert("No employee named: " + fullName.getText());
            return;
        } else listView.setItems(lst);
    }

    public void showAllMember(){
        try {
            thread = new Thread() {
                public void run() {
                    PersonDAO m = new PersonDAO(daoManager.conn);
                    ObservableList<String> list = FXCollections.observableArrayList();
                    list.add("Workers:");
                    list.addAll(m.printAllPerson("workers"));
                    list.add("Engineers:");
                    list.addAll(m.printAllPerson("engineers"));
                    list.add("Guards:");
                    list.addAll(m.printAllPerson("guards"));
                    lst = FXCollections.observableArrayList(list);
                    listView.setItems(lst);
                }

            };
            thread.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addMember(ActionEvent event) {
        if(!check_ceo) {
            getAlert("You are not the CEO!!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddStaff.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Employees Management");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void searchMember(MouseEvent event) {
        try {
            String s = (String) listView.getSelectionModel().selectedItemProperty().getValue();
            String id = "";
            for(int i = 0;i < s.length();i++) {
                boolean flag = true;
                if(s.charAt(i) == '[') {
                    for(int j = i+1;j < s.length();j++) {
                        if(s.charAt(j) == ']') {
                            flag = false;
                            break;
                        }
                        id += s.charAt(j);
                    }
                    if(flag == false) break;
                }
            }
            PersonDAO m = new PersonDAO(daoManager.conn);
            person = m.searchID(id);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Employees Management");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        check_ceo = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root1 = fxmlLoader.load();
        stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Employees Management");
        stage.show();
    }
}
