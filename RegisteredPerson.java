
public class RegisteredPerson extends Person {
    private String govID;

    public RegisteredPerson(String firstName, String lastName, OCCCDate dob, String govID) {
        super(firstName, lastName, dob);
        this.govID = govID;
    }

    public RegisteredPerson(Person p, String govID) {
        super(p);
        this.govID = govID;
    }

    public RegisteredPerson(RegisteredPerson rp) {
        super(rp.getFirstName(), rp.getLastName(), rp.getBirthDate());
        this.govID = rp.govID;
    }

    public String getGoverementID() {
        return new String(govID);
    }

    public boolean equals(RegisteredPerson p) {
        return govID.equalsIgnoreCase(p.govID);
    }

    public boolean equals(Person p) {
        return getFirstName().equalsIgnoreCase(p.getFirstName()) && getLastName().equalsIgnoreCase(p.getLastName());
    }

    @Override
    public String toString() {
        return super.toString() + " [" + govID + "]";
    }
}
