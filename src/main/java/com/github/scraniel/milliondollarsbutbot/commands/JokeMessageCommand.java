package com.github.scraniel.milliondollarsbutbot.commands;

import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.basebot.BotUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class JokeMessageCommand extends AbstractMessageCommand {

    private final String USER_AGENT = "Million Dollars But... Bot (https://github.com/scraniel/milliondollarsbut)";

    public JokeMessageCommand()
    {
        super();
    }

    @Override
    public String getMessage() {

        // In the future may add ability to search and stuff, but right now just grab a random joke
        String url = "https://icanhazdadjoke.com/";
        String joke = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            //add request headers
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept", "text/plain");

            joke = BotUtils.convertInputStreamToString(con.getInputStream());
        } catch (Exception e) {}

        return joke;
    }
}
