package net.austechmc.discord.commands.admin;

import me.koply.kcommando.internal.OptionType;
import me.koply.kcommando.internal.annotations.HandleSlash;
import me.koply.kcommando.internal.annotations.Option;
import me.koply.kcommando.internal.annotations.Perm;
import net.austechmc.discord.util.Reflection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Reflection
public class ApplyCommand {

    private final Button applyButton = Button
            .primary("apply-btn", " Click Here to Apply")
            .withUrl("https://forms.gle/5Mhpe8pQ8BbA2aTAA");

    private final EmbedBuilder messageEmbed = new EmbedBuilder()
            .setAuthor(
                    "General Applications",
                    "https://forms.gle/4V4ZZ1niVJYU9hBn8"
            ).setDescription(
                    """
                    - Redstone engineers
                    - Survival grinders
                    - Farm designers
                    - Builders
                    """
            ).setImage("https://imgur.com/5UkcZr5.png");

    @HandleSlash(
            name = "apply-button",
            desc = "Add AusTech Application Button to a Message.",
            global = true,
            options = {
                    @Option(type = OptionType.BOOLEAN, name = "send-embed", desc = "Send Default Embed.", required = true),
                    @Option(type = OptionType.STRING, name = "message-id", desc = "Message ID to add button to."),
                    @Option(type = OptionType.STRING, name = "image-url", desc = "Custom Image URL")
            }
    )
    @Perm({"ADMINISTRATOR"})
    public void addApplyButton(SlashCommandInteractionEvent e) {
        final var messageId = e.getOption("message-id");
        final var sendEmbed = e.getOption("send-embed");
        final var imageUrl = e.getOption("image-url");

        if (imageUrl != null) {
            messageEmbed.setImage(imageUrl.getAsString());
        }

        // this could be probably be better i just hacked it together

        if (sendEmbed != null && sendEmbed.getAsBoolean()) {
            e.getChannel().sendMessageEmbeds(messageEmbed.build()).queue(success -> {
                success.editMessageComponents(ActionRow.of(applyButton))
                        .queue(l -> e.reply("Apply Button Successfully Added!")
                                .setEphemeral(true)
                                .queue());
            }, throwable -> e.deferReply(true)
                    .setContent(throwable.getMessage())
                    .queue());
            return;
        }

        if (messageId == null) {
            e.deferReply(true)
                    .setContent("Message ID is required.")
                    .queue();
            return;
        }

        e.getChannel().retrieveMessageById(messageId.getAsLong()).queue(success -> {
            success.editMessageComponents(ActionRow.of(applyButton))
                    .queue(l -> e.reply("Apply Button Successfully Added!")
                    .setEphemeral(true)
                    .queue());
        }, throwable -> e.deferReply(true)
                .setContent(throwable.getMessage())
                .queue());
    }

}
