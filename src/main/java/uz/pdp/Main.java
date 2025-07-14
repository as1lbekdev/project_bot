package uz.pdp;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.bot.AmirBot;

public class Main {
     public static void main(String[] args) {
          try {
               TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
               botsApi.registerBot(new AmirBot());
               System.out.println("🤖 Bot ishga tushdi!");
          } catch (TelegramApiException e) {
               e.printStackTrace();
          }

     }
}