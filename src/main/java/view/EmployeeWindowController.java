package view;

import controller.PersonDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Engineer;
import model.Guard;
import model.Person;
import model.Worker;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static view.Display.person;
import static view.Display.daoManager;
import static view.LoginScreenController.check_ceo;

public class EmployeeWindowController implements Initializable {

    @FXML
    private Label age;

    @FXML
    private Button promote;

    @FXML
    private Button demote;

    @FXML
    private Label coefS;

    @FXML
    private Label contact;

    @FXML
    private Label emp_age;

    @FXML
    private TextField emp_coefS;

    @FXML
    private TextField emp_contact;

    @FXML
    private TextField emp_exp;

    @FXML
    private Label emp_gender;

    @FXML
    private Label emp_hometown;

    @FXML
    private Label emp_id;

    @FXML
    private TextField emp_level;

    @FXML
    private Label emp_name;

    @FXML
    private TextField emp_workdays;

    @FXML
    private Label exp;

    @FXML
    private Label gender;

    @FXML
    private Label hometown;

    @FXML
    private Label id;

    @FXML
    private Label level;

    @FXML
    private Label major;

    @FXML
    private Label name;

    @FXML
    private Label workdays;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updatebtn;

    @FXML
    private ImageView emp_avt;

    @FXML
    private Button changeAvt;

    private PreparedStatement store, retrieve;
    private String storeStatement ;
    private String retrieveStatement;

    private void chooseImage(Person p) {
        FileChooser fch = new FileChooser();
        File file = fch.showOpenDialog(changeAvt.getScene().getWindow());
        try {
            FileInputStream fIS = new FileInputStream(file);
            store.setBinaryStream(1, fIS,fIS.available());
            store.setString(2,p.getId());
            store.execute();
            Image img = new Image(fIS);
            emp_avt.setImage(img);

            displayImage(p, emp_avt);
        } catch(  IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayImage(Person p, ImageView view) {
        try {
            retrieve.setString(1, p.getId());
            ResultSet resultSet = retrieve.executeQuery();
            if(resultSet.first()) {
                Blob blob = resultSet.getBlob(1);
                InputStream ip = blob.getBinaryStream();
                Image img = new Image(ip);
                view.setImage(img);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void promote() {

        PersonDAO m = new PersonDAO(daoManager.conn);

        m.insertUser(person.getId(), person.getId());
        promote.setVisible(false);
        demote.setVisible(true);
        getAlert(emp_name.getText() + " is now a manager!!");
    }

    public void demote() {
        PersonDAO m = new PersonDAO(daoManager.conn);

        m.deleteUser(person.getId());
        promote.setVisible(true);
        demote.setVisible(false);
        getAlert(emp_name.getText() + " is now a normal staff!!");
    }



    public void deleteStaff() {
        PersonDAO m = new PersonDAO(daoManager.conn);
        Stage stage = (Stage) deleteBtn.getScene().getWindow();
        if(check_ceo){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT");
            alert.setHeaderText("You're about to fire a staff");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                m.deleteStaff(person, person.getPos());
                getAlert("Success in removing the staff!!");
                stage.close();
            }
        }else {
            getAlert("You're not the CEO!!");
        }
    }

    InputStream ip;
    public void tempStoreImg() {
        String pos = person.getPos();
        try {
            retrieveStatement = "select picture_profile from " + pos + " where (id = ?)";
            retrieve = daoManager.conn.prepareStatement(retrieveStatement,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            retrieve.setString(1, person.getId());
            ResultSet resultSet = retrieve.executeQuery();
            if(resultSet.first()) {
                Blob blob = resultSet.getBlob(1);
                ip = blob.getBinaryStream();
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void returnUpdateImg() {
        String pos = person.getPos();
        try {
            storeStatement = "update " + pos + " set picture_profile = ? where (id = ?)";
            store = daoManager.conn.prepareStatement(storeStatement);
            store.setBinaryStream(1, ip);
            store.setString(2, person.getId());
            store.execute();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean updateInfoCheck(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private boolean confirmUpdate(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT");
        alert.setHeaderText("You're about to update the profile");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            getAlert("Success in updating the profile!!");
            return true;
        }
        return false;
    }

    public void updateStaff() {
        PersonDAO m = new PersonDAO(daoManager.conn);
        if(  !(updateInfoCheck(emp_contact.getText()) && updateInfoCheck(emp_workdays.getText())
            && updateInfoCheck(emp_coefS.getText()) && updateInfoCheck(emp_exp.getText()))   ) {

            getAlert("Invalid information, check again!");
            return;
        }

        if (person instanceof Worker) {

            tempStoreImg();

            Worker e =(Worker) new Worker.WorkerBuilder().id(person.getId()).name(person.getName())
                    .age(person.getAge()).gender(person.getGender()).address(person.getAddress()).phoneNumber(emp_contact.getText())
                    .hometown(person.getHometown()).coefficientsSalary(Integer.parseInt(emp_coefS.getText()))
                    .numberOfWorkdays(Integer.parseInt(emp_workdays.getText())).level(Integer.parseInt(emp_exp.getText())).build();

            if(confirmUpdate()) {
                m.updateStaff(person, e);
            }
            returnUpdateImg();

        } else if (person instanceof Engineer) {
            tempStoreImg();
            Engineer e =(Engineer) new Engineer.EngineerBuilder().id(person.getId()).name(person.getName())
                    .age(person.getAge()).gender(person.getGender()).address(person.getAddress()).phoneNumber(emp_contact.getText())
                    .hometown(person.getHometown()).coefficientsSalary(Integer.parseInt(emp_coefS.getText()))
                    .numberOfWorkdays(Integer.parseInt(emp_workdays.getText())).major(((Engineer) person).getMajor()).yearsOfExperience(Integer.parseInt(emp_exp.getText())).build();

            if (confirmUpdate()) {
                m.updateStaff(person, e);
            }
            returnUpdateImg();
        } else {
            tempStoreImg();
            Guard e =(Guard) new Guard.GuardBuilder().id(person.getId()).name(person.getName())
                    .age(person.getAge()).gender(person.getGender()).address(person.getAddress()).phoneNumber(emp_contact.getText())
                    .hometown(person.getHometown()).coefficientsSalary(Integer.parseInt(emp_coefS.getText()))
                    .numberOfWorkdays(Integer.parseInt(emp_workdays.getText())).area(emp_exp.getText()).build();
            if(confirmUpdate()){
                m.updateStaff(person, e);
            }
            returnUpdateImg();
        }
    }

    public void getAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(str);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            daoManager.open();
            emp_name.setText(person.getName());
            emp_gender.setText(person.getGender());
            emp_age.setText(String.valueOf(person.getAge()));
            emp_hometown.setText(person.getHometown());
            emp_contact.setText(person.getPhoneNumber());
            emp_id.setText(person.getId());
            emp_coefS.setText(String.valueOf(person.getCoefficientsSalary()));
            emp_workdays.setText(String.valueOf(person.getNumberOfWorkdays()));

            if (check_ceo) {
                promote.setVisible(true);
                demote.setVisible(true);
                deleteBtn.setVisible(true);

            } else {
                promote.setVisible(false);
                demote.setVisible(false);
                deleteBtn.setVisible(false);
            }

            PersonDAO m = new PersonDAO(daoManager.conn);

            if(m.getPass(person.getId())==null && check_ceo) {
                promote.setVisible(true);
                demote.setVisible(false);
            } else if (check_ceo && m.getPass(person.getId())!=null){
                promote.setVisible(false);
                demote.setVisible(true);
            }

            if(person instanceof Engineer) {
                exp.setText("Years of experience:");
                emp_exp.setText(String.valueOf(((Engineer) person).getYearsOfExperience()));
                major.setText(((Engineer) person).getMajor());

                storeStatement = "update engineers set picture_profile = ? where (id = ?)";
                retrieveStatement = "select picture_profile from engineers where (id = ?)";
            }
            if(person instanceof Worker) {
                exp.setText("Level:");
                major.setText("Worker");
                emp_exp.setText(String.valueOf(((Worker) person).getLevel()));

                storeStatement = "update workers set picture_profile = ? where (id = ?)";
                retrieveStatement = "select picture_profile from workers where (id = ?)";
            }
            if(person instanceof Guard) {
                exp.setText("Area:");
                major.setText("Guard");
                emp_exp.setText(((Guard) person).getArea());

                storeStatement = "update guards set picture_profile = ? where (id = ?)";
                retrieveStatement = "select picture_profile from guards where (id = ?)";
            }

            store = daoManager.conn.prepareStatement(storeStatement);
            changeAvt.setOnAction(e->chooseImage(person));

            retrieve = daoManager.conn.prepareStatement(retrieveStatement,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            displayImage(person, emp_avt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
