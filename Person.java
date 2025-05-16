
import java.io.Serializable;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private OCCCDate dob;

    public Person(String firstName, String lastName, OCCCDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Person(Person p) {
        this.firstName = p.firstName;
        this.lastName = p.lastName;
    }

    public String getFirstName() {
        return new String(firstName);
    }

    public String getLastName() {
        return new String(lastName);
    }

    public OCCCDate getBirthDate() {
        return dob;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + " [" + dob + "]";
    }

    public boolean equals(Person p) {
        return firstName.equalsIgnoreCase(p.firstName) && lastName.equalsIgnoreCase(p.lastName);
    }

    public void eat() {
        System.out.println(getClass().getName() + " " + toString() + " is eating...");
    }

    public void sleep() {
        System.out.println(getClass().getName() + " " + toString() + " is sleeping...");
    }

    public void play() {
        System.out.println(getClass().getName() + " " + toString() + " is playing...");
    }

    void run() {
        System.out.println(getClass().getName() + " " + toString() + " is running	...");
    }
}
