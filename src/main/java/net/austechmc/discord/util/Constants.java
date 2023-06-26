package net.austechmc.discord.util;

import net.austechmc.discord.commands.HelpCommand;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class Constants {
    public static final Emoji THUMBS_UP = Emoji.fromUnicode("U+1F44D");
    public static final Emoji THUMBS_DOWN = Emoji.fromUnicode("U+1F44E");
    public static final Emoji FIRE = Emoji.fromUnicode("U+1F525");
    public static final Emoji TRASH = Emoji.fromUnicode("U+1F5D1");

    public static final long BOT_CHANNEL_ID = 1122884309804646410L;
    public static final long MEDIA_CHANNEL_ID = 1121311387055050823L;
    public static final long APPLICATION_CHANNEL_ID = 1105434379804754030L;
    public static final long MEMBER_ROLE_ID = 1105435087601934420L;

    public static final String COMMANDS_PATH = HelpCommand.class.getPackage().getName();
}
