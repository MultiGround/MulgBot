import 'package:nyxx/nyxx.dart';
import 'package:nyxx_interactions/interactions.dart';
import 'package:nyxx_lavalink/lavalink.dart';

SlashCommandBuilder voiceJoin(Nyxx bot, Cluster cluster) {
  return SlashCommandBuilder("attach", "봇을 음성 채널에 초대합니다", [])
    ..registerHandler((event) async {
      final senderState = event.interaction.memberAuthor?.voiceState;
      if(senderState != null){
        final channel = await bot.fetchChannel<VoiceGuildChannel>(senderState.channel!.id);
        cluster.getOrCreatePlayerNode(event.interaction.guild!.id);

        channel.connect();
      }
      else {
        await event.respond(MessageBuilder.embed(EmbedBuilder()
          ..color = DiscordColor.red
          ..description = "현재 음성 채널에 접속되어있지 않습니다!"));
      }
    });
}

SlashCommandBuilder voiceQueue(Nyxx bot, Cluster cluster){
  return SlashCommandBuilder("queue", "재생목록을 불러옵니다", [])
      ..registerHandler((event) async {
        final senderState = event.interaction.memberAuthor?.voiceState;
        if(senderState != null){
          final guildID = event.interaction.guild!.id;
          final node = cluster.getOrCreatePlayerNode(guildID);

          final player = node.players[guildID];
          print(player!.queue);
        }
        else {
          await event.respond(MessageBuilder.embed(EmbedBuilder()
            ..color = DiscordColor.red
            ..description = "현재 음성 채널에 접속되어있지 않습니다!"));
        }
      });
}

SlashCommandBuilder voiceSkip(Nyxx bot, Cluster cluster) {
  return SlashCommandBuilder("skip", "현재 재생중인 노래를 건너뜁니다.", [])
      ..registerHandler((event) async {
        final senderState = event.interaction.memberAuthor?.voiceState;

        if(senderState != null){
          final guildID = event.interaction.guild!.id;
          final node = cluster.getOrCreatePlayerNode(guildID);

          node.skip(guildID);
        }
        else if(senderState == null) {
          await event.respond(MessageBuilder.embed(EmbedBuilder()
            ..color = DiscordColor.red
            ..description = "현재 음성 채널에 접속되어있지 않습니다!"));
        }
        else {
          event.respond(MessageBuilder.embed(EmbedBuilder()
            ..color = DiscordColor.red
            ..description = "동일한 음성 채널에 접속중이지 않습니다!"));
        }
      });
}

SlashCommandBuilder voiceDetach(Nyxx bot, Cluster cluster){
  return SlashCommandBuilder("detach", "봇을 음성채널에서 내보냅니다.", [])
    ..registerHandler((event) async {
      final senderState = event.interaction.memberAuthor?.voiceState;
      if(senderState != null){
        final guildID = event.interaction.guild!.id;
        final node = cluster.getOrCreatePlayerNode(guildID);

        node.destroy(guildID);
      }
      else {
        await event.respond(MessageBuilder.embed(EmbedBuilder()
          ..color = DiscordColor.red
          ..description = "현재 음성 채널에 접속되어있지 않습니다!"));
      }
    });
}

SlashCommandBuilder voicePlay(Nyxx bot, Cluster cluster){
  return SlashCommandBuilder("play", "검색결과에서 제일 첫번째로 나오는 곡을 재생합니다.", [
    playerParam(bot, cluster)
  ]);
}

CommandOptionBuilder playerParam(Nyxx bot, Cluster cluster) {
  return CommandOptionBuilder(CommandOptionType.subCommand, "제목", "노래 제목")
    ..registerHandler((event) async {
      final senderState = event.interaction.memberAuthor?.voiceState;
      if(senderState != null){
        final guildID = event.interaction.guild!.id;
        final node = cluster.getOrCreatePlayerNode(guildID);
        final searchResult = await node.searchTracks(event.args[0].toString());
        node.play(guildID, searchResult.tracks[0]).queue();
      }
      else {
        await event.respond(MessageBuilder.embed(EmbedBuilder()
          ..color = DiscordColor.red
          ..description = "현재 음성 채널에 접속되어있지 않습니다!"));
      }
    });
}