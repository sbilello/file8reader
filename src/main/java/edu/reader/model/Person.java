package edu.reader.model;

/**
 * Created by sergio on 1/21/16.
 */

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/* @Author Sergio Bilello
 *
 * Simple POJO
 *
 */
public class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Person) {
            final Person other = (Person) obj;
            return new EqualsBuilder()
                    .append(firstName, other.firstName)
                    .append(lastName, other.lastName)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
