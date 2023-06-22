package org.botexample.listeners;

import net.dv8tion.jda.api.Permission;
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

        if (channel.getIdLong() == Constants.MEDIA_CHANNEL_ID) {
            message.addReaction(Constants.FIRE).queue();
            System.out.printf("Added fire reaction to message with id: %s \n", message.getIdLong());
        }

        if (channel.getIdLong() == Constants.APPLICATION_CHANNEL_ID) {
            if (event.isWebhookMessage()) {
                message.addReaction(Constants.THUMBS_UP).queue();
                message.addReaction(Constants.THUMBS_DOWN).queue();
                message.addReaction(Constants.TRASH).queue();

                System.out.printf("Added initial reactions to message with id: %s \n", message.getIdLong());
            }
        }
    }

    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        final MessageChannel channel = event.getChannel();

        if (channel.getIdLong() != Constants.APPLICATION_CHANNEL_ID) return;

        final long id = event.getMessageIdLong();
        final Guild guild = event.getGuild();

        channel.retrieveMessageById(id).queue(message -> guild.findMembersWithRoles(guild.getRoleById(Constants.MEMBER_ROLE_ID)).onSuccess(memberList -> {
            final int memberCount = memberList.size();

            for (MessageReaction reaction : message.getReactions()) {
                if (!reaction.getEmoji().asUnicode().equals(event.getEmoji())) continue;

                final int realReactionCount = reaction.getCount() - 1;
                final float percentage = (realReactionCount * 100f) / memberCount;
                
                if (reaction.getEmoji().asUnicode().equals(Constants.THUMBS_UP)) {
                    if (realReactionCount >= memberCount || percentage >= 70) {
                        System.out.printf("Application with overwhelming up votes! ID: %s Count: %s Percentage: %s \n",
                                id,
                                realReactionCount,
                                percentage
                        );
                    }
                }

                if (reaction.getEmoji().asUnicode().equals(Constants.THUMBS_DOWN)) {
                    if (realReactionCount >= memberCount || percentage >= 70) {
                        System.out.printf("Deleted application with overwhelming down votes, ID: %s Count: %s Percentage: %s \n",
                                id,
                                realReactionCount,
                                percentage
                        );
                    }
                }

                if (reaction.getEmoji().asUnicode().equals(Constants.TRASH)) {
                    event.retrieveMember().queue(member -> {
                        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                            reaction.removeReaction(member.getUser()).queue();
                            return;
                        }

                        if (member.hasPermission(Permission.ADMINISTRATOR) || realReactionCount >= memberCount || percentage >= 70) {
                            channel.deleteMessageById(id).queue();
                            System.out.printf("Deleted application with overwhelming down votes (or by admin trash can), ID: %s Count: %s Percentage: %s \n",
                                    id,
                                    realReactionCount,
                                    percentage
                            );
                        }
                    });
                }
            }
        }));
    }
}
