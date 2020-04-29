package backend.Entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
    public Set<Person> friends;

    public void playWith(Person person) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(person);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "'s friends => "
                + Optional.ofNullable(this.friends).orElse(
                Collections.emptySet()).stream()
                .map(Person::getName)
                .collect(Collectors.toList());
    }
}
