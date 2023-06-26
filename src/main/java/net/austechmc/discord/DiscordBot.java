package net.austechmc.discord;

import lombok.Getter;
import me.koply.kcommando.KCommando;
import me.koply.kcommando.integration.impl.jda.JDAIntegration;
import net.austechmc.discord.listeners.ApplicationListener;
import net.austechmc.discord.listeners.PublishListener;
import net.austechmc.discord.listeners.ReadyListener;
import net.austechmc.discord.util.Constants;
import net.austechmc.discord.util.Reflection;
import net.austechmc.discord.util.ReflectionUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.reflections.Reflections;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

// TODO Edit/Send Embed Command
// TODO Info Embed with Apply and Content Pings Buttons
public class DiscordBot {

    @Getter
    private static JDA JDA;
    @Getter
    private static KCommando KCMD;
    @Getter
    private static Reflections RCMD;

    public static void main(String[] args) {
        // A token must be provided.
        if (args.length < 1) {
            throw new IllegalStateException("You have to provide a token as the first argument!");
        }

        /* Start the JDA bot builder, letting you provide the token externally rather
         * than writing it in your program's code. args[0] is the token. */
        var builder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.VOICE_STATE);

        // Enable gateway intent
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        // Enable the bulk delete event - this means you'll have to handle it yourself!
        builder.setBulkDeleteSplittingEnabled(false);

        // Set activity (like "playing Something")
        builder.setActivity(Activity.playing("Applications Open \uD83D\uDE80")); // 🚀

        // Set event listeners
        builder.addEventListeners(
                new ApplicationListener(),
                new PublishListener(),
                new ReadyListener()
        );

        try {
            // create the instance of JDA
            JDA = builder.build();

            // optionally block until JDA is ready
            JDA.awaitReady();

            // create command instance
            KCMD = new KCommando(new JDAIntegration(JDA))
                    .addPackage(Constants.COMMANDS_PATH) // packages to analyze
                    .setVerbose(true) // for logging
                    .build();

            // create reflections instance
            RCMD = new Reflections(Constants.COMMANDS_PATH);

            for (Class<?> clazz :
                    RCMD.get(SubTypes.of(TypesAnnotated.with(Reflection.class)).asClass())
            ) {
                KCMD.registerObject(ReflectionUtil.createNewClassInstance(clazz, new Class[]{}, new Object[]{}));
            }

        } catch (InterruptedException e) {
            System.err.println("Couldn't login.");
            e.printStackTrace();
        }
    }
}
