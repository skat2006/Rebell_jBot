package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private String BOT_TOKEN = null;
    private String BOT_NAME = null;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private boolean gameOngoing = false;
    private Storage storage;
    private CrossZero cz;

    Bot() throws URISyntaxException {
        storage = new Storage();
        readFile();
        initKeyboard();
    }

    public void readFile() throws URISyntaxException {
        List<String> lines;
        File file;

        URL resource = getClass().getClassLoader().getResource("settings.cfg");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            file = new File(resource.toURI());
            //System.out.println(resource.toURI());
        }

        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            BOT_TOKEN = lines.get(0);
            BOT_NAME = lines.get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                outMess = new SendMessage();

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
        String response = "";

        switch (textMsg) {
            case "/start" -> response = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
            case "/get", "Просвяти" -> response = storage.getRandQuote();
            case "Новая игра" -> {
                cz = new CrossZero();
                gameOngoing = true;
                response = "Начнем игру! И та-а-ак... Крестики-Нолики!!!\n\n" + cz.drawingOutput()
                        + "\nВаш ход - первый! Левое поле - игровое, а на правом поле вы можете видеть соответствие цифр клетке, сделайте свой ход...";
            }
        }

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (gameOngoing) {
            switch (textMsg) {
                case "1" -> {
                    gameOngoing = cz.gameOngoing(1);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "2" -> {
                    gameOngoing = cz.gameOngoing(2);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "3" -> {
                    gameOngoing = cz.gameOngoing(3);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "4" -> {
                    gameOngoing = cz.gameOngoing(4);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "5" -> {
                    gameOngoing = cz.gameOngoing(5);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "6" -> {
                    gameOngoing = cz.gameOngoing(6);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "7" -> {
                    gameOngoing = cz.gameOngoing(7);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "8" -> {
                    gameOngoing = cz.gameOngoing(8);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                case "9" -> {
                    gameOngoing = cz.gameOngoing(9);
                    if (gameOngoing) {
                        response = cz.drawingOutput() + "\nВаш ход...";
                    } else {
                        response = cz.drawingOutput();
                    }
                }
                default -> response = "Сообщение не распознано";
            }
        }

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
        keyboardRow1.add(new KeyboardButton("Просвяти"));
        keyboardRow1.add(new KeyboardButton("Новая игра"));
        if (gameOngoing) {
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
