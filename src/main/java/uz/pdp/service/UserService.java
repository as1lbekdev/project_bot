package uz.pdp.service;

import org.telegram.telegrambots.meta.api.objects.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserService {

private final Map<Long, User> users=new HashMap<>();
     private static final String FILE_PATH = "users.txt";
     public void
     registerUser(Long userId, String username,String name) {
          try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
               writer.println("ID: " + userId + " | Username: @" + username+" name : "+name);
          } catch (IOException e) {
               e.printStackTrace();
          }
     }
     public User getUser(Long chatId){
          return users.get(chatId);
     }
}
