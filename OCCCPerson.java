public class OCCCPerson extends RegisteredPerson {
    private String studentID;

    public OCCCPerson(RegisteredPerson p, String studentID) {
        super(p);
        this.studentID = studentID;
    }

    public OCCCPerson(OCCCPerson p) {
        super(p.getFirstName(), p.getLastName(), p.getBirthDate(), p.getGoverementID());
        this.studentID = p.studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public boolean equals(OCCCPerson p) {
        return studentID.equalsIgnoreCase(p.studentID);

    }

    public boolean equals(RegisteredPerson p) {
        return getGoverementID().equalsIgnoreCase(p.getGoverementID());
    }

    public boolean equals(Person p) {
        return getFirstName().equalsIgnoreCase(p.getFirstName()) && getLastName().equalsIgnoreCase(p.getLastName());
    }

    @Override
    public String toString() {
        return super.toString() + " [" + studentID + "]";
    }
}