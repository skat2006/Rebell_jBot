package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {
    private String BOT_TOKEN = null;
    private String BOT_NAME = "Rebell_jBot";
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final Storage storage;
    private CrossZero cz;

    Bot() throws InterruptedException {
        BOT_TOKEN = getToken();
        storage = new Storage();
        cz = new CrossZero();
        cz.setGameOngoing(false);
    }


    public String getToken() {
        Properties prop = new Properties();
        try {
            //load a properties file from class path, inside static method
            prop.load(Bot.class.getClassLoader().getResourceAsStream("config.properties"));
            return prop.getProperty("token");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage outMess = null;

        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage(); //Извлекаем из объекта сообщение пользователя
                String chatId = inMess.getChatId().toString(); //Достаем из inMess id чата пользователя
                String response = parseMessage(inMess.getText()); //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                outMess = new SendMessage(); //Создаем объект класса SendMessage - наш будущий ответ пользователю

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkup);

                //Отправка в чат
                execute(outMess);

            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMessage(String textMsg) {
        String response = null;

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (cz.isGameOngoing()) {
            switch (textMsg) {
                case "1" -> cz.gameOngoing(1);
                case "2" -> cz.gameOngoing(2);
                case "3" -> cz.gameOngoing(3);
                case "4" -> cz.gameOngoing(4);
                case "5" -> cz.gameOngoing(5);
                case "6" -> cz.gameOngoing(6);
                case "7" -> cz.gameOngoing(7);
                case "8" -> cz.gameOngoing(8);
                case "9" -> cz.gameOngoing(9);
                default -> response = "Сообщение не распознано";
            }

            if (response == null) response = cz.drawingOutput();
        }

        switch (textMsg) {
            case "/start" -> response = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
            case "/get", "Просвяти" -> response = storage.getRandQuote();
            case "/game", "Новая игра" -> {
                cz = new CrossZero();
                cz.setGameOngoing(true);
                response = "Начнем игру! И та-а-ак... Крестики-Нолики!!!\n\n" + cz.drawingOutput()
                        + "\nВаш ход - первый! Левое поле - игровое, а на правом поле вы можете видеть соответствие цифр клетке, сделайте свой ход...";
            }
        }

        initKeyboard(); //Клавиатура, инициация
        return response;
    }

    void initKeyboard() {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем ряды кнопок и добавляем их в список
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRows.add(keyboardRow1);
        //Добавляем кнопки
        keyboardRow1.add(new KeyboardButton("Новая игра"));
        keyboardRow1.add(new KeyboardButton("Просвяти"));
        if (cz.isGameOngoing()) {
            keyboardRows.add(keyboardRow2);
            keyboardRows.add(keyboardRow3);
            keyboardRows.add(keyboardRow4);
            keyboardRow4.add(new KeyboardButton("1"));
            keyboardRow4.add(new KeyboardButton("2"));
            keyboardRow4.add(new KeyboardButton("3"));
            keyboardRow3.add(new KeyboardButton("4"));
            keyboardRow3.add(new KeyboardButton("5"));
            keyboardRow3.add(new KeyboardButton("6"));
            keyboardRow2.add(new KeyboardButton("7"));
            keyboardRow2.add(new KeyboardButton("8"));
            keyboardRow2.add(new KeyboardButton("9"));
        }
        //добавляем лист кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
