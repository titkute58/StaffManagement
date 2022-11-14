package model;


public class Guard extends Person{
    private String area;

    private Guard(GuardBuilder guardBuilder) {
        super(guardBuilder);
        this.area = guardBuilder.area;
    }

    public static class GuardBuilder extends PersonBuilder {

        private  String area;

        public GuardBuilder area(final String area) {
            this.area = area;
            return this;
        }



        public Guard build() {
            return new Guard(this);
        }

        @Override
        public PersonBuilder major(String major) {
            return null;
        }

        @Override
        public PersonBuilder yearsOfExperience(int yearsOfExperience) {
            return null;
        }

        @Override
        public PersonBuilder level(int level) {
            return null;
        }
    }

    public String getArea() {
        return area;
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
        return "guards";
    }

    public int salaryCalculation(){
        return this.getNumberOfWorkdays() * this.getCoefficientsSalary();
    }
}
