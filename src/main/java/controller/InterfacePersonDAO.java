package controller;

import javafx.collections.ObservableList;
import model.Engineer;
import model.Guard;
import model.Person;
import model.Worker;

import java.util.List;

public interface InterfacePersonDAO {
    void addWorker(Worker other);
    void addGuard(Guard other);
    void addEngineer(Engineer other);
    String getId(String employee);
    ObservableList<String> printAllPerson(String employee);
    ObservableList<String> searchName(String name, String employee);
    ObservableList<String> printInfo(List<Person> lst);
    void deleteStaff(Person other, String pos);
    void updateStaff(Person other, Person newOther);
    Person searchID(String id);
    String getPass(String id);
    void insertUser(String id, String pass);
    void updatePassword(String id, String pass);
    void deleteUser(String id);
}
