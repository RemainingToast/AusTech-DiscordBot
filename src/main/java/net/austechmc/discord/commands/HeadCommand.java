package net.austechmc.discord.commands;

import me.koply.kcommando.internal.OptionType;
import me.koply.kcommando.internal.annotations.HandleSlash;
import me.koply.kcommando.internal.annotations.Option;
import net.austechmc.discord.util.Constants;
import net.austechmc.discord.util.Reflection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Reflection
public class HeadCommand {

    private static final String headUrl = "https://visage.surgeplay.com/head/%s";

    @HandleSlash(
            name = "player-head",
            desc = "Generate 3d perspective of a minecraft players head.",
            global = true,
            options = @Option(type = OptionType.STRING, name = "player-uuid", desc = "Target players unique uuid", required = true)
    )
    public void headCommand(SlashCommandInteractionEvent e) {
        final var playerOption = e.getOption("player-uuid");

        if (playerOption == null) {
            return;
        }

        final var playerString = playerOption.getAsString()
                .replaceAll("-", "");

        final var embed = new EmbedBuilder()
                .setImage(headUrl.formatted(playerString))
                .build();

        final var ephemeral = e.getChannel()
                .getIdLong() != Constants.BOT_CHANNEL_ID;

        e.replyEmbeds(embed)
                .setEphemeral(ephemeral)
                .queue();
    }
}
