package uz.pdp.bot.factory;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkupFactory extends ReplyKeyboardMarkup {


     public static ReplyKeyboardMarkup createReplyKeyboardMarkup(List<String> buttons, int callCount) {
          ReplyKeyboardMarkup r = new ReplyKeyboardMarkup();
          r.setResizeKeyboard(true);
          r.setOneTimeKeyboard(true);
          List<KeyboardRow> row = new ArrayList<>();
          r.setKeyboard(row);


          int index = 0;
          KeyboardRow keyboardRow = new KeyboardRow();

          for (String b : buttons) {
               row.add(new KeyboardRow());

               if (index % callCount == 0) {
                    row.add(keyboardRow);
               }

          }
          if (!row.isEmpty()) {
               row.add(keyboardRow);
          }
          return r;

     }
}
