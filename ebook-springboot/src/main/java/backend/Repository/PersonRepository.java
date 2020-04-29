package backend.Repository;

import backend.Entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @Query("match (n1:Person)-[r1:FRIEND]-(n2:Person) where n1.name={name} return n2")
    List<Person> findFriends(@Param("name") String name);
    @Query("match (n1:Person)-[r1:FRIEND]-(n2:Person) where n1.name={name} and n2.name={friend} delete r1")
    void deleteFriend(@Param("name") String name, @Param("friend") String friend);
    Person findByName(String name);
}
