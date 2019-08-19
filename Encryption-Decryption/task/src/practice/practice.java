package practice;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;


class SelectionContext {

    private PersonSelectionAlgorithm algorithm;

    public void setAlgorithm(PersonSelectionAlgorithm algorithm) {
        // write your code here
        this.algorithm = algorithm;
    }

    public Person[] selectPersons(Person[] persons) {
        // write your code here
        return algorithm.select(persons);
    }
}

interface PersonSelectionAlgorithm {

    Person[] select(Person[] persons);
}

class TakePersonsWithStepAlgorithm implements PersonSelectionAlgorithm {
    private int k;
    public TakePersonsWithStepAlgorithm(int step) {
        // write your code here
        this.k = step;
    }

    @Override
    public Person[] select(Person[] persons) {
        // write your code here
        List<Person> pList = IntStream.range(0, persons.length)
                .filter(i -> i % k == 0)
                .mapToObj(i -> persons[i])
                .collect(Collectors.toList());

        Person[] output = new Person[pList.size()];
        for (int i = 0; i < pList.size(); i++){
            output[i] = pList.get(i);
        }
        return output;
    }
}


class TakeLastPersonsAlgorithm implements PersonSelectionAlgorithm {

    private int k;
    public TakeLastPersonsAlgorithm(int count) {
        // write your code here
        this.k = count;
    }

    @Override
    public Person[] select(Person[] persons) {
        // write your code here
        return Arrays.copyOfRange(persons, k, persons.length);
    }
}

class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }
}

/* Do not change code below */
public class practice {

    public static void main(String args[]) {
        final Scanner scanner = new Scanner(System.in);

        final int count = Integer.parseInt(scanner.nextLine());
        final Person[] persons = new Person[count];

        for (int i = 0; i < count; i++) {
            persons[i] = new Person(scanner.nextLine());
        }

        final String[] configs = scanner.nextLine().split("\\s+");

        final PersonSelectionAlgorithm alg = create(configs[0], Integer.parseInt(configs[1]));
        SelectionContext ctx = new SelectionContext();
        ctx.setAlgorithm(alg);

        final Person[] selected = ctx.selectPersons(persons);
        for (Person p : selected) {
            System.out.println(p.name);
        }
    }

    public static PersonSelectionAlgorithm create(String algType, int param) {
        switch (algType) {
            case "STEP": {
                return new TakePersonsWithStepAlgorithm(param);
            }
            case "LAST": {
                return new TakeLastPersonsAlgorithm(param);
            }
            default: {
                throw new IllegalArgumentException("Unknown algorithm type " + algType);
            }
        }
    }
}

/*
3
AshAllen
Bailey Cox
Danni Taylor
LAST 3
*/