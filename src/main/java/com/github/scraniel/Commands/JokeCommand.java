package com.github.scraniel.Commands;

import com.github.scraniel.BotUtils;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JokeCommand implements ICommand {

    @Override
    public void run(Event event, String[] args) {

        // We only handle MessageRecievedEvents here
        if(!(event instanceof MessageReceivedEvent)){
            return;
        }

        MessageReceivedEvent messageEvent = (MessageReceivedEvent)event;

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

            // Dumb stream reading
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            joke = response.toString();
        } catch (Exception e) {}

        BotUtils.sendMessage(messageEvent.getChannel(), joke);
    }
}
