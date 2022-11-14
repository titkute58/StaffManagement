package model;

public class Worker extends Person{
    private int level;

    private Worker(WorkerBuilder workerBuilder) {
        super(workerBuilder);
        this.level = workerBuilder.level;
    }

    public static class WorkerBuilder extends PersonBuilder {

        private int level;

        public WorkerBuilder level(final int level) {
            this.level = level;
            return this;
        }

        public Worker build() {
            return new Worker(this);
        }

        @Override
        public PersonBuilder area(String area) {
            return null;
        }

        @Override
        public PersonBuilder major(String major) {
            return null;
        }

        @Override
        public PersonBuilder yearsOfExperience(int yearsOfExperience) {
            return null;
        }
    }

    public int getLevel() {
        return level;
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
        return "workers";
    }

    @Override
    public int salaryCalculation() {
        return this.level*this.getCoefficientsSalary()*this.getNumberOfWorkdays();
    }
}
