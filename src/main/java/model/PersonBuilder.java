package model;

public abstract class PersonBuilder {
    protected String id;
    protected String name;
    protected int age;
    protected String gender;
    protected String address;
    protected String phoneNumber;
    protected String hometown;
    protected int coefficientsSalary;
    protected int numberOfWorkdays;

    public PersonBuilder id(final String id) {
        this.id = id;
        return this;
    }
    public PersonBuilder name(final String name) {
        this.name = name;
        return this;
    }
    public PersonBuilder age(final int age) {
        this.age = age;
        return this;
    }
    public PersonBuilder gender(final String gender) {
        this.gender = gender;
        return this;
    }
    public PersonBuilder address(final String address) {
        this.address = address;
        return this;
    }
    public PersonBuilder phoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    public PersonBuilder hometown(final String hometown) {
        this.hometown = hometown;
        return this;
    }
    public PersonBuilder coefficientsSalary(final int coefficientsSalary) {
        this.coefficientsSalary = coefficientsSalary;
        return this;
    }
    public PersonBuilder numberOfWorkdays(final int numberOfWorkdays) {
        this.numberOfWorkdays = numberOfWorkdays;
        return this;
    }
    public abstract Person build();

    public abstract PersonBuilder major(String major);

    public abstract PersonBuilder yearsOfExperience(int yearsOfExperience);

    public abstract PersonBuilder level(int level);

    public abstract PersonBuilder area(String area);
}
