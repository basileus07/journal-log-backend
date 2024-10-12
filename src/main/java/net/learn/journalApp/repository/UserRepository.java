package net.learn.journalApp.repository;

import net.learn.journalApp.entity.JournalEntry;
import net.learn.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
