package org.botexample.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.botexample.Constants;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final MessageChannelUnion channel = event.getChannel();
        final Message message = event.getMessage();

        switch (channel.getId()) {
            case "1051704280450613268" -> {
                message.addReaction(Constants.FIRE).queue();
                System.out.printf("Added fire reaction to message with id: %s \n", message.getIdLong());
            }
            case "1051826574967713812" -> {
                if (event.isWebhookMessage()) {
                    message.addReaction(Constants.THUMBS_UP).queue();
                    message.addReaction(Constants.THUMBS_DOWN).queue();

                    System.out.printf("Added initial reactions to message with id: %s \n", message.getIdLong());
                }
            }
        }
    }

    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        final MessageChannel channel = event.getChannel();

        if (channel.getIdLong() != 1051826574967713812L) return;

        final long id = event.getMessageIdLong();
        final Guild guild = event.getGuild();

        channel.retrieveMessageById(id).queue(message -> guild.findMembersWithRoles(guild.getRoleById(1051441766324260895L)).onSuccess(memberList -> {
            final int memberCount = memberList.size();

            for (MessageReaction reaction : message.getReactions()) {
                final int realReactionCount = reaction.getCount() - 1;
                final float percentage = (realReactionCount * 100f) / memberCount;

                System.out.printf("Reaction: %s MemberCount: %s ReactionCount: %s Percentage: %s \n",
                        reaction.getEmoji().getName(),
                        memberCount,
                        reaction.getCount(),
                        percentage
                );

                System.out.printf("Event Reaction: %s %s", event.getEmoji(), reaction.getEmoji() == event.getEmoji());

                if (reaction.getEmoji().getName().equals(Constants.THUMBS_UP.getName())) {
                    System.out.println("THUMBS UP");
                    if (realReactionCount >= memberCount || percentage >= 70) {
                        System.out.printf("Application with overwhelming up votes! ID: %s Count: %s Percentage: %s \n", id, realReactionCount, percentage);
                    }
                } else if (reaction.getEmoji().getName().equals(Constants.THUMBS_DOWN.getName())) {
                    System.out.println("THUMBS DOWN");
                    if (realReactionCount >= memberCount || percentage >= 70) {
//                        channel.deleteMessageById(id).queue();
                        System.out.printf("Deleted application with overwhelming down votes, ID: %s Count: %s Percentage: %s \n", id, realReactionCount, percentage);
                    }
                }
            }
        }));
    }
}
