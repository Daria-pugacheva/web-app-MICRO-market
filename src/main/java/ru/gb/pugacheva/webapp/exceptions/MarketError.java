package ru.gb.pugacheva.webapp.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class MarketError {
    private List<String> messages;
    private Date date;

    public MarketError (List<String> messages){
        this.messages = messages;
        this.date = new Date();
    }

    public MarketError (String... messages){
        this(Arrays.asList(messages));
    }

    public MarketError(String message){
        this(List.of(message));
    }

//    public MarketError (String message){
//        this.messages = List.of(message);
//        this.date = new Date();
//    }

}