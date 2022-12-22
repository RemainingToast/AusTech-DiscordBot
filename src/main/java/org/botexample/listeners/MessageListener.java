package org.botexample.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.botexample.Constants;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final MessageChannelUnion channel = event.getChannel();
        final Message message = event.getMessage();

        switch (channel.getId()) {
            case "1051704280450613268" -> {
                message.addReaction(Constants.FIRE).queue();
                System.out.printf("Added fire reaction to message with id: %s", message.getIdLong());
            }
            case "1051826574967713812" -> {
                if (event.isWebhookMessage()) {
                    message.addReaction(Constants.THUMBS_UP).queue();
                    message.addReaction(Constants.THUMBS_DOWN).queue();

                    System.out.printf("Added initial reactions to message with id: %s", message.getIdLong());
                }
            }
        }
    }

    /**
    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        final MessageChannel channel = event.getChannel();

        if (channel.getIdLong() != 1051826574967713812L) return;

        final MessageReaction reaction = event.getReaction();
        final Guild guild = event.getGuild();

        final long id = event.getMessageIdLong();
        final int count = guild.getMembersWithRoles(guild.getRolesByName("member", true)).size();
        final float percentage = (reaction.getCount() * 100f) / count;

        System.out.printf("Reaction Added! Reaction: %s ID: %s MemberCount: %s ReactionCount: %s Percentage: %s",
                reaction.getReactionEmote().getAsCodepoints(),
                reaction.getMessageId(),
                count,
                reaction.getCount(),
                percentage
        );

        switch (reaction.getReactionEmote().getAsCodepoints()) {
            case Constants.THUMBS_UP: {
                if (reaction.getCount() >= count) {
                    System.out.printf("Application with overwhelming up votes! ID: %s Count: %s", id, count);
                }
            }
            case Constants.THUMBS_DOWN: {
                if (reaction.getCount() >= count) {
                    channel.deleteMessageById(id).queue();
                    System.out.printf("Deleted application with overwhelming down votes, ID: %s Count: %s", id, count);
                }
            }
        }
    }
    */
}