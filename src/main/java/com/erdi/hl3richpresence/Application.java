package com.erdi.hl3richpresence;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Application implements Runnable {
    public static final String APP_ID = "1119552096426020934";

    private DiscordEventHandlers eventHandlers;

    private void setupHandlers() {
        eventHandlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> {
            DiscordRichPresence richPresence = new DiscordRichPresence.Builder("Multiplayer")
                    .setDetails("Episode 3")
                    .setParty("", 3, 3)
                    .setSecrets("", "")
                    .setSmallImage("hl3", "Half Life 3 Episode 3")
                    .setBigImage("hl3", "Half Life 3 Episode 3")
                    .setStartTimestamps(System.currentTimeMillis())
                    .setEndTimestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1))
                    .build();
            DiscordRPC.discordUpdatePresence(richPresence);
        }).build();
    }

    @Override
    public void run() {
        setupHandlers();
        DiscordRPC.discordInitialize(APP_ID, eventHandlers, false);
        DiscordRPC.discordRegister(APP_ID, "");

        JFrame frame = new JFrame("Half Life 3");
        frame.setSize(50, 50);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing Discord hook.");
            DiscordRPC.discordShutdown();
        }));

        while(true)
            DiscordRPC.discordRunCallbacks();
    }
}
