package net.austechmc.discord.commands;

import me.koply.kcommando.internal.annotations.HandleSlash;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

// TODO: Waiting for KCommando Choices Support...
//  - Ping to Play Role
//  - Application Ping Role
public class PingCommand {
    @HandleSlash(
            name = "ping-me",
            desc = "Give your self pingable roles.",
            global = true,
            options = {}
    )
    public void pingCommand(SlashCommandInteractionEvent e) {

    }
}
