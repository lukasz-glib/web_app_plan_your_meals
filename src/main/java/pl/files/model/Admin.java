package pl.files.model;

import org.mindrot.jbcrypt.BCrypt;

public class Admin {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int superadmin;
    private int enable;
    private String salt = BCrypt.gensalt();

    public Admin(String firstName, String lastName, String email, String password, int superadmin, int enable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = BCrypt.hashpw(password, this.salt);
        this.superadmin = superadmin;
        this.enable = enable;
    }

    public Admin() {
    }

    public Admin(String name, String surname, String email, String password) {
        this.firstName = name;
        this.lastName = surname;
        this.email = email;
        this.password = BCrypt.hashpw(password, this.salt);
        this.superadmin = 0;
        this.enable = 1;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void  setHashedPassword(String hashedPassword){
        this.password = hashedPassword;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, this.salt) ;
    }

    public int getSuperadmin() {
        return superadmin;
    }

    public void setSuperadmin(int superadmin) {
        this.superadmin = superadmin;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", superadmin=" + superadmin +
                ", enable=" + enable +
                ", salt=" + salt +
                '}';
    }

    public String getSalt() {
        return this.salt;
    }
}
