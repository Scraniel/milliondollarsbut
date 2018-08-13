package com.github.scraniel.Commands;

import com.github.scraniel.BotUtils;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.net.HttpURLConnection;
import java.net.URL;

public class JokeCommand extends MessageCommand {

    @Override
    public String getMessage(String[] args) {

        // In the future may add ability to search and stuff, but right now just grab a random joke
        String url = "https://icanhazdadjoke.com/";
        String joke = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            //add request headers
            con.setRequestProperty("User-Agent", BotUtils.USER_AGENT);
            con.setRequestProperty("Accept", "text/plain");

            joke = BotUtils.convertInputStreamToString(con.getInputStream());

        } catch (Exception e) {}

        return joke;
    }
}
