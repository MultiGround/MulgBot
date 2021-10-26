

import 'package:nyxx/nyxx.dart';
import 'package:nyxx_interactions/interactions.dart';

Future<void> whereServer(ButtonInteractionEvent event) async {
  await event.acknowledge(); // ack the interaction so we can send response later
  // Send followup to button click with id of button
  await event.editOriginalResponse(ComponentMessageBuilder()
      ..addEmbed((embed) {
        embed
          ..color = DiscordColor.blurple
          ..title="멀티그라운드 서버"
          ..description="giftshower.games";
        })
  );
}

Future<void> whereForum(ButtonInteractionEvent event) async {
  await event.acknowledge(); // ack the interaction so we can send response later

  // Send followup to button click with id of button
  await event.editOriginalResponse(MessageBuilder()
    ..embeds = [
      EmbedBuilder()
        ..color = DiscordColor.blurple
        ..title="멀티그라운드 포럼"
        ..url="https://forum.giftshower.games/"
    ]);
}

Future<void> acceptRule(ButtonInteractionEvent event, Guild guild, Nyxx bot) async {
  await event.acknowledge();

  await bot.httpEndpoints.addRoleToUser(guild.id, "895600744797995048".toSnowflake(), event.interaction.userAuthor!.id);

  await event.deleteOriginalResponse();
}