import 'dart:io';
import 'package:nyxx/nyxx.dart';
import 'package:nyxx_interactions/interactions.dart';
import "package:nyxx_lavalink/lavalink.dart";

final pingToBot = SlashCommandBuilder("ping", "Ping!" , [])
    ..registerHandler((event) async {
      await event.acknowledge();
      await event.respond(MessageBuilder.content("Pong!"));
    });

final where = SlashCommandBuilder("where", "서버 관련 주소 찾기", [])
    ..registerHandler((event) async {
      await event.acknowledge();
      final componentMsgBuilder = ComponentMessageBuilder()
        ..addEmbed((embed) {
          embed
            ..title ="주소를 선택해주세요!";
        })
        ..addComponentRow(ComponentRowBuilder()
        ..addComponent(ButtonBuilder("서버", "server", ComponentStyle.primary))
        ..addComponent(ButtonBuilder("포럼", "forum", ComponentStyle.primary)));

      await event.respond(componentMsgBuilder);
    });

Future<ComponentMessageBuilder> hello(GuildMemberAddEvent event) async {
  final dmChannel = await event.user.dmChannel;
  final cmpMsg = ComponentMessageBuilder()
  ..addEmbed((embed) {
    embed
      ..color = DiscordColor.cornflowerBlue
      ..title = "멀티그라운드에 오신 것을 환영합니다!"
      ..description = "디스코드에 입장하기 전, 규칙을 반드시 숙지해주세요!\n"
        "*규칙을 읽지 않음으로써 생기는 불이익은 모두 유저의 책임입니다! 주의해주세요!*\n"
        "**아래 버튼을 눌러 서버에서 채팅을 시작하세요!**";
  })
  ..addComponentRow(ComponentRowBuilder()
    ..addComponent(ButtonBuilder("알겠습니다", "joinAcc", ComponentStyle.primary)
    ));

  return cmpMsg;
}

