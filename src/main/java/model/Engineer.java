package model;

public class Engineer extends Person {
    private  String major;
    private  int yearsOfExperience;

    private Engineer(EngineerBuilder engineerBuilder) {
        super(engineerBuilder);
        this.major = engineerBuilder.major;
        this.yearsOfExperience = engineerBuilder.yearsOfExperience;
    }

    public static class EngineerBuilder extends PersonBuilder {

        private  String major;
        private  int yearsOfExperience;

        public EngineerBuilder major(final String major) {
            this.major = major;
            return this;
        }
        public EngineerBuilder yearsOfExperience(final int yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
            return this;
        }

        @Override
        public PersonBuilder level(int level) {
            return null;
        }

        @Override
        public PersonBuilder area(String area) {
            return null;
        }

        public Engineer build() {
            return new Engineer(this);
        }
    }

    public String getMajor() {
        return major;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String display() {
        return "["+getId()+"] " + getName() + ", " + getAge() +
                " years old.\n\t\t      Address: " + getAddress() +
                ".\n\t\t      Phone number: " + getPhoneNumber();
    }

    @Override
    public String displayInfo() {
        return "["+getId()+"] " + getName() + ", " + getAge() +
                " years old.\n\t      Address: " + getAddress() +
                ".\n\t      Phone number: " + getPhoneNumber();
    }

    public String getPos(){
        return "engineers";
    }

    @Override
    public int salaryCalculation() {
        return this.getCoefficientsSalary()
                * this.getCoefficientsSalary()
                * this.yearsOfExperience;
    }
}
