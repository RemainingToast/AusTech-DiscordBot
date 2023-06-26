package net.austechmc.discord.listeners;

import net.austechmc.discord.util.Constants;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println(event.getJDA().getSelfUser().getName() + " is ready for takeoff!");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final MessageChannelUnion channel = event.getChannel();
        final Message message = event.getMessage();

        if (channel.getIdLong() != Constants.MEDIA_CHANNEL_ID) {
            return;
        }

        message.addReaction(Constants.FIRE).queue();
        System.out.printf("Added fire reaction to message with id: %s \n", message.getIdLong());
    }

}
