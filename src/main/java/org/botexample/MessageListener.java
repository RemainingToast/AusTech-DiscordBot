package org.botexample;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    // TODO Accept Commmand - !accept <messageid> <userid>
    // TODO Deny Command - !deny <messageid>
    // TODO Deletes Application Message, Gives User Role and DMs welcome message with relevant information.
    // TODO Reaction Roles
    // TODO Dockerfile

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final Message message = event.getMessage();

        if (event.isWebhookMessage()) {
            message.addReaction("U+1F44D").queue();
            message.addReaction("U+1F44E").queue();

//            message.getReactions().forEach(messageReaction -> {
//                if (messageReaction.getReactionEmote().getAsCodepoints().equals("U+1F44E")) {
//                    int i = message.getGuild().getMembersWithRoles(message.getGuild().getRolesByName("member", true)).size();
//                    if (messageReaction.getCount() >= i) {
//                        message.delete().queue();
//                    }
//                }
//            });

//            message.delete().queueAfter(7, TimeUnit.DAYS, (v) -> System.out.printf("Deleted message with id %s after seven days.", message.getIdLong()));

            System.out.printf("Added reactions to message with id: %s", message.getIdLong());
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (event.getReaction().getReactionEmote().getAsCodepoints().equals("U+1F44E")) {
            int i = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("member", true)).size();
            if (event.getReaction().getCount() >= i) {
                event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();
                System.out.printf("Deleted application with overwhelming down votes, ID: %s Count: %s", event.getMessageIdLong(), i);
            }
        }
    }
}
