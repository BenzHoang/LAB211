
package model;

public class GroundStaff extends Crew {
    private String department;

    public GroundStaff(String department, String id, String name, int age, String role) {
        super(id, name, age, role);
        this.department = department;
    }

    

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Additional getters and setters specific to Ground Staff

    @Override
    public String toString() {
        return super.toString()+ " | " +department;
    }
    
}