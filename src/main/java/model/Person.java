package model;
public abstract class Person {
    protected String id;
    protected String name;
    protected int age;
    protected String gender;
    protected String address;
    protected String phoneNumber;
    protected String hometown;
    protected int coefficientsSalary;
    protected int numberOfWorkdays;

    protected Person(PersonBuilder personBuilder) {
        this.id = personBuilder.id;
        this.name = personBuilder.name;
        this.age = personBuilder.age;
        this.gender = personBuilder.gender;
        this.address = personBuilder.address;
        this.phoneNumber = personBuilder.phoneNumber;
        this.hometown = personBuilder.hometown;
        this.coefficientsSalary = personBuilder.coefficientsSalary;
        this.numberOfWorkdays = personBuilder.numberOfWorkdays;
    }

    public abstract String getPos();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHometown() {
        return hometown;
    }

    public int getCoefficientsSalary() {
        return coefficientsSalary;
    }

    public int getNumberOfWorkdays() {
        return numberOfWorkdays;
    }

    public abstract String display();

    public abstract String displayInfo();

    public abstract int salaryCalculation();
}