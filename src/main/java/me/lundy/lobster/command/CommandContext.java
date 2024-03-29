package me.lundy.lobster.command;

import me.lundy.lobster.Lobster;
import me.lundy.lobster.database.settings.SettingsManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandContext {

    private final SlashCommandInteractionEvent event;
    private final SettingsManager settingsManager;
    private Guild guild;
    private Member executor;
    private Member self;
    private Logger logger = LoggerFactory.getLogger(CommandContext.class);

    public CommandContext(SlashCommandInteractionEvent event) {
        this.settingsManager = new SettingsManager(Lobster.getInstance().getDatabase().getDataSource(), event.getGuild().getIdLong());
        this.event = event;

        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply(":warning: Unexpected error: `Guild is null`").setEphemeral(true).queue();
            return;
        }

        this.guild = guild;

        Member executor = event.getMember();
        if (executor == null) {
            event.reply(":warning: Unexpected error: `Executor is null`").setEphemeral(true).queue();
            return;
        }

        this.executor = executor;
        this.self = guild.getSelfMember();
    }

    public GuildVoiceState getSelfVoiceState() {
        return self.getVoiceState();
    }

    public GuildVoiceState getExecutorVoiceState() {
        return executor.getVoiceState();
    }

    public boolean selfInVoice() {
        return self.getVoiceState() != null && self.getVoiceState().inAudioChannel();
    }

    public boolean executorInVoice() {
        return executor.getVoiceState() != null && executor.getVoiceState().inAudioChannel();
    }

    public boolean inSameVoice() {
        AudioChannelUnion selfAudioChannel = getSelfVoiceState().getChannel();
        AudioChannelUnion executorAudioChannel = getSelfVoiceState().getChannel();
        return selfAudioChannel != null && executorAudioChannel != null && selfAudioChannel.getIdLong() == executorAudioChannel.getIdLong();
    }

    public SlashCommandInteractionEvent getEvent() {
        return event;
    }

    public Guild getGuild() {
        return guild;
    }

    public Member getExecutor() {
        return executor;
    }

    public Member getSelf() {
        return self;
    }

}
