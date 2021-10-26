import 'dart:io';

import 'package:nyxx/nyxx.dart';
import "package:nyxx_lavalink/lavalink.dart";
import 'package:nyxx_interactions/interactions.dart';
import 'Command.dart';
import "package:logging/logging.dart";
import 'Config.dart';
import 'VoiceCommand.dart';
import 'buttonIE.dart';

final token = Properties.Token;


void main() {
  Logger.root.level = Level.FINEST;
  Main().mulgbot();
}
class Main {
  void mulgbot() async {
    final bot = Nyxx(token, GatewayIntents.allPrivileged);
    final interaction = Interactions(bot);
    final cluster = Cluster(bot, Snowflake("777135853608370217"));

    //await cluster.addNode(NodeOptions(port: 52702));

    //CMD Register
    bot.onReady.listen((event) {
      interaction
        ..registerSlashCommand(pingToBot)
        ..registerSlashCommand(where)
        /*..registerSlashCommand(voiceJoin(bot, cluster))
        ..registerSlashCommand(voiceDetach(bot, cluster))
        ..registerSlashCommand(voiceQueue(bot, cluster))
        ..registerSlashCommand(voiceSkip(bot, cluster))
        ..registerSlashCommand(voicePlay(bot, cluster))*/
        ..registerButtonHandler("server", whereServer)
        ..registerButtonHandler("forum", whereForum)
        ..registerButtonHandler("joinAcc", (p0) async => {
          acceptRule(p0,await bot.fetchGuild("661514065679482900".toSnowflake()), bot)
        })
        ..sync();
    });

    bot.onGuildMemberAdd.listen((event) async {
      if(event.guild.id == "661514065679482900") {
        event.user.sendMessage(await hello(event));
        print("sans join");
      }
    });
  }
}
