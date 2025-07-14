package uz.pdp.bot;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.bot.factory.ReplyKeyboardMarkupFactory;
import uz.pdp.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AmirBot extends TelegramLongPollingBot {
     private static final UserService userService = new UserService();
     private static final String BOT_USERNAME = "t.me/Asilbek_Dev_Bot.";
     private static final String BOT_TOKEN = "7879982197:AAHZsiw8eoDWFjIC0YG0AV-fYpWjoopA71w";
     private static final Map<Long, String> userLangMap = new HashMap<>();

     @Override
     public void onUpdateReceived(Update update) {
          if (update.hasMessage() && update.getMessage().hasText()) {
               String text = update.getMessage().getText();
               Long chatId = update.getMessage().getChatId();
               User user = update.getMessage().getFrom();

               if (text.equals("/start")) {
                    sendMessageWithButtons(chatId,
                              getText("welcome", chatId) + " @" + user.getUserName(),
                              List.of(getText("about_me", chatId), getText("settings", chatId)));

               } else if (text.equals(getText("about_me", chatId))) {
                    sendMessageWithButtons(chatId,
                              getText("about", chatId),
                              List.of(getText("study", chatId), getText("contact", chatId), getText("back", chatId)));

               } else if (text.equals(getText("contact", chatId))) {
                    String info = """
                📱 *Aloqa ma'lumotlari:*

                [📞 Telefon: +998 97 679 22 08](tel:+998976792208)
                📸 Instagram: [@asilbek_irgashaliyev](https://instagram.com/asilbek_irgashaliyev)
                💬 Telegram: [@Asilbek_Irgashaliyev](https://t.me/Asilbek_Irgashaliyev)
                💻 GitHub: [asilbek](https://github.com/as1lbekdev)
                """;
                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText(info);
                    message.setParseMode("Markdown");
                    try {
                         execute(message);
                    } catch (TelegramApiException e) {
                         e.printStackTrace();
                    }

               } else if (text.equals(getText("settings", chatId))) {
                    sendMessageWithButtons(chatId,
                              getText("choose_language", chatId),
                              List.of("🇺🇿 Uzbek", "🇷🇺 Russian", "🇬🇧 English", getText("back", chatId)));

               } else if (text.equals("🇺🇿 Uzbek")) {
                    userLangMap.put(chatId, "uz");
                    sendMessageWithButtons(chatId, getText("language_set_uz", chatId), List.of(getText("back", chatId)));

               } else if (text.equals("🇷🇺 Russian")) {
                    userLangMap.put(chatId, "ru");
                    sendMessageWithButtons(chatId, getText("language_set_ru", chatId), List.of(getText("back", chatId)));

               } else if (text.equals("🇬🇧 English")) {
                    userLangMap.put(chatId, "en");
                    sendMessageWithButtons(chatId, getText("language_set_en", chatId), List.of(getText("back", chatId)));

               } else if (text.equals(getText("back", chatId))) {
                    sendMessageWithButtons(chatId,
                              getText("main_menu", chatId),
                              List.of(getText("about_me", chatId), getText("settings", chatId)));

               }else if (text.equals(getText("study", chatId))) {
//                    sendMessageWithButtons(chatId,
//                              getText("study_info", chatId),
//                              List.of(getText("back", chatId)));
//
                    String info = getText("study_info", chatId);

                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText(info);
                    message.setParseMode("Markdown"); // <-- Bu yer muhim!

                    try {
                         execute(message);
                    } catch (TelegramApiException e) {
                         e.printStackTrace();
                    }
               }
               else {
                    sendMessage(chatId, getText("unknown_command", chatId));
               }
          }
     }



     private void sendMessage(Long chatId, String text) {
          SendMessage message = new SendMessage();
          message.setChatId(chatId);
          message.setText(text);
          try {
               execute(message);
          } catch (TelegramApiException e) {
               e.printStackTrace();
          }
     }

     private void sendMessageWithButtons(Long chatId, String text, List<String> buttons) {
          SendMessage message = new SendMessage();
          message.setChatId(chatId);
          message.setText(text);

          ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
          keyboardMarkup.setResizeKeyboard(true);
          List<KeyboardRow> rows = new ArrayList<>();

          KeyboardRow row = new KeyboardRow();
          for (int i = 0; i < buttons.size(); i++) {
               row.add(new KeyboardButton(buttons.get(i)));
               // 2 ta tugma bilan bir qatorda bo'lishini xohlasangiz:
               if ((i + 1) % 2 == 0 || i == buttons.size() - 1) {
                    rows.add(row);
                    row = new KeyboardRow();
               }
          }

          keyboardMarkup.setKeyboard(rows);
          message.setReplyMarkup(keyboardMarkup);

          try {
               execute(message);
          } catch (TelegramApiException e) {
               e.printStackTrace();
          }
     }

     @Override
     public String getBotUsername() {
          return BOT_USERNAME;
     }


     public String getBotToken() {
          return BOT_TOKEN;
     }

     private String getText(String key, Long chatId) {
          String lang = userLangMap.getOrDefault(chatId, "uz");
          return switch (key) {

               // Main greetings
               case "welcome" -> switch (lang) {
                    case "ru" -> "Добро пожаловать";
                    case "en" -> "Welcome";
                    default -> "Xush kelibsiz";
               };

               case "about" -> switch (lang) {
                    case "ru" -> "Добро пожаловать:";
                    case "en" -> "Welcome:";
                    default -> "Xush kelibsiz :";
               };

               case "main_menu" -> switch (lang) {
                    case "ru" -> "Главное меню";
                    case "en" -> "Main Menu";
                    default -> "Bosh menyu";
               };

               // Buttons
               case "about_me" -> switch (lang) {
                    case "ru" -> "Обо мне";
                    case "en" -> "About me";
                    default -> "About me";
               };

               case "settings" -> switch (lang) {
                    case "ru" -> "⚙️ Настройки";
                    case "en" -> "⚙️ Settings";
                    default -> "⚙️ Sozlamalar";
               };

               case "study" -> switch (lang) {
                    case "ru" -> "📚 Учёба";
                    case "en" -> "📚 Study";
                    default -> "📚 O‘qish";
               };

               case "contact" -> switch (lang) {
                    case "ru" -> "📞 Контакты";
                    case "en" -> "📞 Contact";
                    default -> "📞 Aloqa";
               };

               case "back" -> switch (lang) {
                    case "ru" -> "⬅️ Назад";
                    case "en" -> "⬅️ Back";
                    default -> "⬅️ Orqaga";
               };case "study_info" -> switch (lang) {
                    case "ru" -> """
        🎓 Образование:

        🏫 Университет: [ТАТУ — Ташкентский Университет Информационных Технологий](https://tuit.uz)
        💼 Факультет: Программная инженерия
        🧠 Специализация: Java Backend
        📅 Год поступления: 2022
        """;

                    case "en" -> """
        🎓 Education:

        🏫 University:[TUIT — Tashkent University of Information Technologies](https://tuit.uz)
        💼 Faculty: Software Engineering
        🧠 Specialization: Java Backend
        📅 Year of admission: 2022
        """;

                    default -> """
        🎓 Ta'lim:

        🏫 Universitet: [TATU — Toshkent Axborot Texnologiyalari Universiteti](https://tuit.uz)
        💼 Fakultet: Dasturiy injiniring
        🧠 Yo'nalish: Java Backend
        📅 Boshlangan yili: 2022
        """;
               };


               // Language messages
               case "language_set_uz" -> "Til o'rnatildi: O'zbek tili ✅";
               case "language_set_ru" -> "Язык установлен: Русский ✅";
               case "language_set_en" -> "Language set: English ✅";

               case "choose_language" -> switch (lang) {
                    case "ru" -> "Выберите язык:";
                    case "en" -> "Choose a language:";
                    default -> "Tilni tanlang:";
               };

               // Unknown command
               case "unknown_command" -> switch (lang) {
                    case "ru" -> "Неизвестная команда.";
                    case "en" -> "Unknown command.";
                    default -> "Noma'lum buyruq.";
               };

               // Default fallback
               default -> key;
          };
     }

}



