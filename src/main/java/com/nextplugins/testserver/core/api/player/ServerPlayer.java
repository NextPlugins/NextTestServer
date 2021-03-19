package com.nextplugins.testserver.core.api.player;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.group.models.Group;
import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data
@Builder
public class ServerPlayer {

    private Player player;
    private Group group;
    private PermissionAttachment attachment;

    public static ServerPlayer createDefault(Player player) {

        PermissionAttachment attachment = player.addAttachment(NextTestServer.getInstance());

        return ServerPlayer.builder()
                .player(player)
                .attachment(attachment)
                .build();

    }

}
