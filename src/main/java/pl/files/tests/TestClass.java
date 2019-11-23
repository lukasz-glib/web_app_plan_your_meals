package pl.files.tests;

import pl.files.dao.AdminDao;
import pl.files.model.Admin;

public class TestClass {

    public static void main(String[] args) {

        Admin admin = new Admin("Dupa", "Wołowa", "email@email.com", "admin", 1, 1);
        admin.setId(1);
        new AdminDao().update(admin);

    }

}
