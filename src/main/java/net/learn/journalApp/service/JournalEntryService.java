package net.learn.journalApp.service;

import net.learn.journalApp.entity.JournalEntry;
import net.learn.journalApp.entity.User;
import net.learn.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error occured while saving.", e);
        }

    }


    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournal(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id, String userName){
         User user = userService.findByUserName(userName);
         user.getJournalEntries().removeIf(x-> x.getId().equals(id));
         userService.saveUser(user);
         journalEntryRepository.deleteById(id);
    }
}


// controller ---> service ---> repository