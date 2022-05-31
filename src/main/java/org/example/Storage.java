package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private final ArrayList<String> quoteList;
    Storage() {
        quoteList = new ArrayList<>();
        quoteList.add("Тише будешь - дальше едешь!");
        parser("https://citatnica.ru/citaty/mudrye-tsitaty-velikih-lyudej", "su-note-inner su-u-clearfix su-u-trim");
    }

    String getRandQuote()
    {
        //Из коллекции получаем цитату со случайным индексом и возвращаем ее
        int randValue = (int)(Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }

    void parser(String strURL, String className) {
        Document doc = null;
        try {
            //Получаем документ нужной нам страницы
            doc = Jsoup.connect(strURL).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Получаем группу объектов, обращаясь методом из Jsoup к определенному блоку
        assert doc != null;
        Elements elQuote = doc.getElementsByClass(className);

        //Достаем текст из каждого объекта поочереди и добавляем в наше хранилище
        elQuote.forEach(el -> quoteList.add(el.text()));
    }
}
