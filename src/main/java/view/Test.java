package view;

import controller.ConnectionManager;
import controller.PersonDAO;
import model.Person;
import model.Worker;

public class Test {
    public static void main(String[] args) throws Exception {
//        ConnectionManager daoMngr = new ConnectionManager();
//        daoMngr.open();
//
//        PersonDAO m = new PersonDAO(daoMngr.conn);
//        m.insertUser("CN002", "CN002");
//
//        daoMngr.close();

        Worker w = (Worker) new Worker.WorkerBuilder().id("CN123").level(12)
                .name("Tuan").age(100).hometown("Chợ trời").address("Tokyo").build();
        if(w instanceof Worker) {
            System.out.println("yes");
        }
        System.out.println(w.display());

    }
}
