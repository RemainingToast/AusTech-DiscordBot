package org.botexample;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.botexample.listeners.MessageListener;
import org.botexample.listeners.ReadyListener;

// TODO Accept Command - !accept <messageid> <userid> - Deletes Application Message, Gives User Role and DMs welcome message with relevant information.
// TODO Deny Command - !deny <messageid> - Deletes Application Message
// TODO Apply Command
// TODO FAQ Command
// TODO Edit/Send Embed Command
// TODO Info Embed with Apply and Content Pings Buttons

// TODO Reaction Roles
// TODO Twitch Notifier
public class DiscordBot {

    @Getter
    private static JDA JDA;
    public static void main(String[] args) {
        // A token must be provided.
        if (args.length < 1) {
            throw new IllegalStateException("You have to provide a token as the first argument!");
        }

        /* Start the JDA bot builder, letting you provide the token externally rather
         * than writing it in your program's code. args[0] is the token. */
        JDABuilder jdaBotBuilder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        jdaBotBuilder.disableCache(CacheFlag.VOICE_STATE);

        // Enable gateway intent
        jdaBotBuilder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        // Enable the bulk delete event - this means you'll have to handle it yourself!
        jdaBotBuilder.setBulkDeleteSplittingEnabled(false);

        // Set activity (like "playing Something")
        jdaBotBuilder.setActivity(Activity.playing("AusTech"));

        // Set event listeners
        jdaBotBuilder.addEventListeners(new MessageListener(), new ReadyListener());

        try {
            // create the instance of JDA
            JDA = jdaBotBuilder.build();

            // optionally block until JDA is ready
            JDA.awaitReady();
        } catch (InterruptedException e) {
            System.err.println("Couldn't login.");
            e.printStackTrace();
        }
    }
}
