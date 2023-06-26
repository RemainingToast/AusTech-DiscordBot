package net.austechmc.discord.listeners;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class PublishListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannelType() != ChannelType.NEWS) {
            return;
        }

        event.getGuildChannel().asNewsChannel().crosspostMessageById(event.getMessageIdLong()).queue(message -> {
            System.out.println("Published message: " + message + " from " + event.getChannel().getName());
        }, Throwable::printStackTrace);
    }
}
