public class OCCCPerson extends RegisteredPerson {
    // Private variable for the studentID
    private String studentID;

    // Constructors
    public OCCCPerson(RegisteredPerson p, String studentID) {
        super(p);
        this.studentID = studentID;
    }

    public OCCCPerson(OCCCPerson p) {
        super(p.getFirstName(), p.getLastName(), p.getBirthDate(), p.getGoverementID());
        this.studentID = p.studentID;
    }

    // Getter (no setter since you can't change studentID after you get it)
    public String getStudentID() {
        return studentID;
    }

    // Check if two people have the same studentID
    public boolean equals(OCCCPerson p) {
        return studentID.equalsIgnoreCase(p.studentID);

    }

    // Check if two people have the same gov id
    public boolean equals(RegisteredPerson p) {
        return getGoverementID().equalsIgnoreCase(p.getGoverementID());
    }

    // Check if two people have the same first and last name
    public boolean equals(Person p) {
        return getFirstName().equalsIgnoreCase(p.getFirstName()) && getLastName().equalsIgnoreCase(p.getLastName());
    }

    // toString for added studentID
    @Override
    public String toString() {
        return super.toString() + " [" + studentID + "]";
    }
}