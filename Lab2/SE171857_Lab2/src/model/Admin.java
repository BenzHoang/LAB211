
package model;


public class Admin extends Crew {
    private String passWord;

    public Admin(String passWord, String id, String name, int age, String role) {
        super(id, name, age, role);
        this.passWord = passWord;
    }

    public Admin(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + passWord;
    }

}
