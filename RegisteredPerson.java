
public class RegisteredPerson extends Person {
    // Private variable for gov ID
    private String govID;

    // Constructors
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

    // Getter for govID. No setter since you can't change the govID.
    public String getGoverementID() {
        return new String(govID);
    }

    // Check if two RegisteredPersons have the same govID
    public boolean equals(RegisteredPerson p) {
        return govID.equalsIgnoreCase(p.govID);
    }

    // Check if two RegisteredPersons have the same name.
    public boolean equals(Person p) {
        return getFirstName().equalsIgnoreCase(p.getFirstName()) && getLastName().equalsIgnoreCase(p.getLastName());
    }

    // Adds the govID onto the parent .toString
    @Override
    public String toString() {
        return super.toString() + " [" + govID + "]";
    }
}
