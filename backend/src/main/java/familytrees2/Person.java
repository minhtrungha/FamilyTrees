package familytrees2;

import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private int gradYear;
    private String major;

    public Person(String firstName, String lastName, int gradYear, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gradYear = gradYear;
        this.major = major;
    }

    public Person() {
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGradYear() {
        return gradYear;
    }

    public String getMajor() {
        return major;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return gradYear == person.gradYear &&
               Objects.equals(firstName, person.firstName) &&
               Objects.equals(lastName, person.lastName) &&
               Objects.equals(major, person.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gradYear, major);
    }
}
