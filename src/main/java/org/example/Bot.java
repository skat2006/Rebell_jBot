package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    private final String BOT_TOKEN;
    private final String BOT_NAME;
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final Storage storage = new Storage();
    private CrossZero cz;

    private long msgUserID;
    private long msgUserChatID;
    private String msgUserFirstName;

    Bot() throws InterruptedException {
        GetProperties properties = new GetProperties();
        BOT_TOKEN = properties.getToken();
        BOT_NAME = properties.getName();
        cz = new CrossZero();
        cz.setGameOngoing(false);
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
        SendMessage outMess;

        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage(); //Извлекаем из объекта сообщение пользователя
                String chatId = inMess.getChatId().toString(); //Достаем из inMess id чата пользователя

                msgUserID = inMess.getFrom().getId();
                msgUserChatID = inMess.getChatId();
                msgUserFirstName = inMess.getFrom().getFirstName();

                String response = parseMessage(inMess.getText()); //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                outMess = new SendMessage(); //Создаем объект класса SendMessage - наш будущий ответ пользователю

                //Добавляем в наше сообщение id чата и наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkup);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String parseMessage(String textMsg) throws SQLException {
        String response = null;

        cz.setUserID(msgUserID);
        cz.setUserChatId(msgUserChatID);
        cz.setUserName(msgUserFirstName);

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (cz.isGameOngoing()) {
            switch (textMsg) {
                case "Стоп игра" -> {
                    cz.setGameOngoing(false);
                    JDBSpostgreSQL jdbSpostgreSQL = new JDBSpostgreSQL();
                    jdbSpostgreSQL.insertUserRecord(msgUserID, msgUserFirstName, msgUserChatID);
                }
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
            case "/start" -> response = "Приветствую " + cz.getUserID() + ", бот умеет играть в Крестики-Нолики и знает много цитат!\nЖми /get или пользуйся клавиатурой внизу, чтобы получить случайную цитату или начать игру.";
            case "/get", "Просвяти" -> response = storage.getRandQuote();
            case "/game", "Новая игра" -> {
                cz = new CrossZero();
                cz.setGameOngoing(true);
                cz.setUserID(msgUserID);
                cz.setUserName(msgUserFirstName);
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
        if (cz.isGameOngoing()) {
            keyboardRow1.add(new KeyboardButton("Стоп игра"));
        }
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