package view;

import controller.PersonDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Engineer;
import model.Guard;
import model.Worker;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import static view.Display.daoManager;

public class AddStaffController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            daoManager.open();
            choiceBox.getItems().addAll("Engineer", "Guard", "Worker");
            genderText.getItems().addAll("Male","Female");
            genderText.setValue("Male");
            coefText.setValue(1);
            coefText.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField nameText;

    @FXML
    private TextField experienceText;

    @FXML
    private TextField areaText;

    @FXML
    private TextField levelText;

    @FXML
    private TextField ageText;

    @FXML
    private ChoiceBox<String> genderText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private TextField hometownText;

    @FXML
    private ChoiceBox<Integer> coefText;

    @FXML
    private TextField workDaysText;

    @FXML
    private TextField majorText;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button subbtn;

    public void getAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Warning: ");
        alert.setContentText(text);
        alert.show();
    }

    public void onMenuChange() {
        if(choiceBox.getValue() == "Engineer") {
            majorText.setVisible(true);
            experienceText.setVisible(true);
            areaText.setVisible(false);
            levelText.setVisible(false);
        }
        if(choiceBox.getValue() == "Worker") {
            majorText.setVisible(false);
            experienceText.setVisible(false);
            areaText.setVisible(false);
            levelText.setVisible(true);
        }
        if(choiceBox.getValue() == "Guard") {
            majorText.setVisible(false);
            experienceText.setVisible(false);
            areaText.setVisible(true);
            levelText.setVisible(false);
        }

    }

    private boolean confirmation(){
        Stage stage = (Stage) subbtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT");
        alert.setHeaderText("You're about to add a staff");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            getAlert("Success in adding staff");
            stage.close();
            return true;
        }
        return false;
    }

    public void submit(){
        PersonDAO m = new PersonDAO(daoManager.conn);

        if(nameText.getText().equals("") || ageText.getText().equals("") || addressText.getText().equals("") ||
                phoneNumberText.getText().equals("") || hometownText.getText().equals("") || workDaysText.getText().equals("")) {
            getAlert("You have to fill in all required information!");
            return;
        }

        String name = nameText.getText();
        int age;

        try {
            age = Integer.parseInt(ageText.getText());
            if(age < 0 || age > 100) {
                getAlert("Invalid age!");
                return;
            }
        } catch (NumberFormatException e) {
            getAlert("Invalid age!");
            return;
        }
        String gender = genderText.getValue();
        String address = addressText.getText();
        String phoneNumber = phoneNumberText.getText();
        String hometown = hometownText.getText();
        int coefSalary = coefText.getValue();
        int numberOfWorkdays;

        try {
            numberOfWorkdays = Integer.parseInt(workDaysText.getText());
        } catch (NumberFormatException e) {
            getAlert("Invalid number of working days!");
            return;
        }

        String id;

        if(choiceBox.getValue() == "Engineer") {
            if(majorText.getText().equals("") || experienceText.getText().equals("")) {
                getAlert("You have to fill all the Engineer's information!");
                return;
            }

            String major = majorText.getText();

            int yearsEx;
            try {
                yearsEx = Integer.parseInt(experienceText.getText());
                if(yearsEx > age) {
                    getAlert("Invalid Engineer's years of experience!");
                    return;
                }
            } catch (NumberFormatException e) {
                getAlert("Invalid Engineer's years of experience!");
                return;
            }

            id = m.getId("engineers");
            Engineer eng = (Engineer) new Engineer.EngineerBuilder()
                    .id(id).name(name).age(age).gender(gender).address(address)
                    .phoneNumber(phoneNumber).major(major).yearsOfExperience(yearsEx)
                    .hometown(hometown).coefficientsSalary(coefSalary)
                    .numberOfWorkdays(numberOfWorkdays).build();

            if(confirmation()) {
                m.addEngineer(eng);
            }
        } else {
            if (choiceBox.getValue()    == "Guard") {
                if(areaText.getText().equals("")) {
                    getAlert("You have to fill all the Guard's information!");
                    return;
                }

                String area = areaText.getText();
                id = m.getId("guards");
                Guard gua = (Guard) new Guard.GuardBuilder().id(id).name(name).age(age)
                        .gender(gender).address(address)
                        .phoneNumber(phoneNumber).hometown(hometown)
                        .coefficientsSalary(coefSalary)
                        .numberOfWorkdays(numberOfWorkdays).area(area).build();
                if(confirmation()) {
                    m.addGuard(gua);
                }
            } else {
                if(levelText.getText().equals("")) {
                    getAlert("You have to fill all the Worker's information!");
                    return;
                }

                int level;
                try {
                    level = Integer.parseInt(levelText.getText());
                } catch (NumberFormatException e) {
                    getAlert("Invalid Worker's rank");
                    return;
                }

                id = m.getId("workers");
                Worker wor =(Worker) new Worker.WorkerBuilder().id(id).name(name).age(age)
                        .gender(gender).address(address).phoneNumber(phoneNumber)
                        .hometown(hometown).coefficientsSalary(coefSalary)
                        .numberOfWorkdays(numberOfWorkdays).level(level).build();

                if (confirmation()){
                    m.addWorker(wor);
                }
            }
        }
    }
}
