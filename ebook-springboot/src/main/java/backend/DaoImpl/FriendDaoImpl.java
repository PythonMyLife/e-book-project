package backend.DaoImpl;

import backend.Dao.FriendDao;
import backend.Entity.Person;
import backend.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FriendDaoImpl implements FriendDao {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getFriendList(String name) {
        if(personRepository.findByName(name) == null) {
            Person person = new Person(name);
            personRepository.save(person);
        }
        return personRepository.findFriends(name);
    }

    public Boolean addFriend(String name, String friend) {
        try{
            if(personRepository.findByName(name) == null) {
                Person person = new Person(name);
                personRepository.save(person);
            }
            if(personRepository.findByName(friend) == null) {
                Person person = new Person(friend);
                personRepository.save(person);
            }
            Person nowPerson = personRepository.findByName(name);
            Person toPerson = personRepository.findByName(friend);
            nowPerson.playWith(toPerson);
            personRepository.save(nowPerson);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean deleteFriend(String name, String friend) {
        try{
            personRepository.deleteFriend(name, friend);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
