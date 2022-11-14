package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Engineer;
import model.Guard;
import model.Person;
import model.Worker;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements InterfacePersonDAO {
    private Connection conn;

    public PersonDAO(Connection conn) {
        this.conn =conn;
    }

    InputStream ip;
    public void getDefaultImg(){
        try {
            String sql = "select picture_profile from picture where id = 1 ";
            PreparedStatement ps = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.first()) {
                Blob blob = resultSet.getBlob(1);
                ip = blob.getBinaryStream();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void addWorker(Worker other) {
        try {
            getDefaultImg();

            String sql = "INSERT INTO workers VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other.getId());
            ps.setString(2, other.getName());
            ps.setInt(3, other.getAge());
            ps.setString(4, other.getGender());
            ps.setString(5, other.getAddress());
            ps.setString(6, other.getPhoneNumber());
            ps.setString(7, other.getHometown());
            ps.setInt(8, other.getCoefficientsSalary());
            ps.setInt(9, other.getNumberOfWorkdays());
            ps.setInt(10, other.getLevel());

            ps.setBinaryStream(11,ip);

            ps.executeUpdate();
            System.out.println("Worker has been inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGuard(Guard other) {
        try {
            getDefaultImg();

            String sql = "INSERT INTO guards VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other.getId());
            ps.setString(2, other.getName());
            ps.setInt(3, other.getAge());
            ps.setString(4, other.getGender());
            ps.setString(5, other.getAddress());
            ps.setString(6, other.getPhoneNumber());
            ps.setString(7, other.getHometown());
            ps.setInt(8, other.getCoefficientsSalary());
            ps.setInt(9, other.getNumberOfWorkdays());
            ps.setString(10, other.getArea());

            ps.setBinaryStream(11,ip);

            ps.executeUpdate();
            System.out.println("Guard has been inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEngineer(Engineer other) {
        try {
            getDefaultImg();

            String sql = "INSERT INTO engineers VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other.getId());
            ps.setString(2, other.getName());
            ps.setInt(3, other.getAge());
            ps.setString(4, other.getGender());
            ps.setString(5, other.getAddress());
            ps.setString(6, other.getPhoneNumber());
            ps.setString(7, other.getHometown());
            ps.setInt(8, other.getCoefficientsSalary());
            ps.setInt(9, other.getNumberOfWorkdays());
            ps.setString(10, other.getMajor());
            ps.setInt(11, other.getYearsOfExperience());

            ps.setBinaryStream(12,ip);

            ps.executeUpdate();
            System.out.println("Engineer has been inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getId(String employee) {
        try {
            String sql = "select id from " + employee + " order by id desc limit 1;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String id = rs.getString(1);
                int n = Integer.parseInt(id.substring(2)) +1 ;
                String str = String.valueOf(n);
                while(str.length()<3){
                    str = "0" +str;
                }
                return (id.substring(0,2) + str);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<String> printAllPerson(String employee) {
        try {
            String sql = "select *from " + employee;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            return print(getResult(rs, employee));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList();
    }

    public ObservableList<String> searchName(String name, String employee) {
        try {
            String sql = "select *from " + employee + " where name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return printInfo(getResult(rs,employee));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<String> printInfo(List<Person> lst) {
        ObservableList<String> ans = FXCollections.observableArrayList();

        for(Person x : lst) {
            String salary = String.valueOf(x.salaryCalculation());
            ans.add(x.displayInfo()+"\n\t      Salary: " + salary);
        }
        return ans;
    }

    public ObservableList<String> print(List<Person> lst) {
        ObservableList<String> ans = FXCollections.observableArrayList();

        for(Person x : lst) {
            String salary = String.valueOf(x.salaryCalculation());
            ans.add("\t" + x.display() + "\n\t\t      Salary: " + salary);
        }
        return ans;
    }

    public List<Person> getResult(ResultSet rs, String employee) throws SQLException {
        List<Person> lst = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            String gender = rs.getString(4);
            String address = rs.getString(5);
            String phoneNumber = rs.getString(6);
            String hometown = rs.getString(7);
            int coefficientsSalary = rs.getInt(8);
            int numberOfWorkdays = rs.getInt(9);
            if(employee.equals("engineers")) {
                String major = rs.getString(10);
                int yearEx = rs.getInt(11);
                Engineer e =(Engineer) new Engineer.EngineerBuilder().id(id).name(name)
                        .age(age).gender(gender).address(address).phoneNumber(phoneNumber)
                        .hometown(hometown).coefficientsSalary(coefficientsSalary)
                        .numberOfWorkdays(numberOfWorkdays).major(major).yearsOfExperience(yearEx).build();
                lst.add(e);
                continue;
            }

            if(employee.equals("workers")) {
                int level = rs.getInt(10);
                Worker e =(Worker) new Worker.WorkerBuilder().id(id).name(name)
                        .age(age).gender(gender).address(address).phoneNumber(phoneNumber)
                        .hometown(hometown).coefficientsSalary(coefficientsSalary)
                        .numberOfWorkdays(numberOfWorkdays).level(level).build();
                lst.add(e);
                continue;
            }

            if(employee.equals("guards")) {
                String area = rs.getString(10);
                Guard e =(Guard) new Guard.GuardBuilder().id(id).name(name)
                        .age(age).gender(gender).address(address).phoneNumber(phoneNumber)
                        .hometown(hometown).coefficientsSalary(coefficientsSalary)
                        .numberOfWorkdays(numberOfWorkdays).area(area).build();
                lst.add(e);
            }
        }
        return lst;
    }

    public void deleteStaff(Person other, String pos) {
        try {
            String sql = "delete from "+ pos + " where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, other.getId());
            ps.execute();
            System.out.println("Data has been removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Person other, Person newOther) {
        String pos1;
        if(other instanceof Worker){
            pos1 = "workers";
        } else {
            if(other instanceof Engineer) pos1= "engineers";
            else pos1= "guards";
        }
        deleteStaff(other, pos1);
        if(newOther instanceof Worker) {
            addWorker((Worker) newOther);
        } else {
            if(newOther instanceof Engineer) addEngineer((Engineer) newOther);
            else addGuard((Guard) newOther);
        }
        System.out.println("The word has been updated!");
    }

    public Person searchID(String id) {
        String employee;
        String str = id.substring(0,2);
        if(str.equals("CN")){
            employee = "workers";
        } else {
            if(str.equals("KS")) employee = "engineers";
            else employee = "guards";
        }
        return searchIdStaff(id, employee);
    }

    public Person searchIdStaff(String id, String employee) {
        try {
            String sql = "select *from " + employee + " where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            List<Person> res = getResult(rs,employee);
            if(!res.isEmpty()) return res.get(0);
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPass(String id) {
        try {
            String pass;
            String sql = "select pass from users where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                pass = rs.getString(1);
                return pass;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertUser(String id, String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            md.update(salt);

            byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder password = new StringBuilder();
            StringBuilder pepper = new StringBuilder();
            for (byte b : hashedPassword) password.append(String.format("%02x", b));
            for (byte b : salt) pepper.append(b).append(",");

            String sql = "insert into users VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password.toString());
            ps.setString(3, pepper.toString().substring(0, pepper.length() - 1));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String id, String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            md.update(salt);

            byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder password = new StringBuilder();
            StringBuilder pepper = new StringBuilder();
            for (byte b : hashedPassword) password.append(String.format("%02x", b));
            for (byte b : salt) pepper.append(b).append(",");

            String sql = "update users set pass = ?, salt = ? where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, password.toString());
            ps.setString(2, pepper.toString().substring(0, pepper.length() - 1));
            ps.setString(3, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id) {
        try {
            String sql = "delete from users where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            System.out.println("Data has been removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
