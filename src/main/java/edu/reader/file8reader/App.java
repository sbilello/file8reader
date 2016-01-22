package edu.reader.file8reader;

/**
 * @author Sergio Bilello
 */

import edu.reader.model.Person;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class App {


    public static void main(String[] args) {

        /** check arguments **/
        if (!checkArgs(args)) {
            return;
        }

        List<Person> results = getPersons(args[0]);

        if (CollectionUtils.isNotEmpty(results)) {
            Collections.sort(results, (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
            printPersons(results, SORT.FIRST_NAME);
            Collections.sort(results, (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()));
            printPersons(results, SORT.LAST_NAME);
        } else {
            System.out.println(Constants.EMPTY_RESULTS_LIST_MESSAGE);
        }
    }

    private static void printPersons(List<Person> results, SORT sort) {
        results.forEach(
                (person) -> {
                    switch (sort) {
                        case FIRST_NAME:
                            System.out.println(person.getFirstName() + " " + person.getLastName());
                            break;
                        case LAST_NAME:
                            System.out.println(person.getLastName() + " " + person.getFirstName());
                    }
                }
        );
    }

    private static List<Person> getPersons(String fileName) {
        List<Person> persons = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(s -> persons.add(addPerson(s)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return persons;
        }
    }

    private static Person addPerson(String line) {
        if (StringUtils.isNotEmpty(line)) {
            String[] splitted = line.split(" ");
            if (splitted.length > 1) {
                return new Person(splitted[0], splitted[1]);
            }
        }
        // weird record just put inside a person to don't lose it
        System.err.print("Saving person from file: " + line);
        return new Person(line, line);
    }

    private static boolean checkArgs(String[] args) {
        if (args == null) {
            throw new NullPointerException("ERROR: args is null");
        }
        if (args.length > 1) {
            System.err.println(Constants.TOO_MANY_ARGUMENTS);
            return false;
        }
        if (args.length == 0) {
            System.err.println(Constants.USAGE);
            return false;
        }
        if (StringUtils.isEmpty(args[0])) {
            System.err.println(Constants.EMPTY_STRING);
            return false;
        }
        if (args[0].length() > Constants.MAX_STRING_LENGTH) {
            System.err.println(Constants.MAX_STRING_LENGTH_MESSAGE + Constants.MAX_STRING_LENGTH);
            return false;
        }
        File f = new File(args[0]);
        if (!f.exists() || f.isDirectory()) {
            System.err.println(Constants.FILE_NOT_EXISTS);
            return false;
        }
        return true;
    }

    private enum SORT {
        FIRST_NAME, LAST_NAME;
    }
}