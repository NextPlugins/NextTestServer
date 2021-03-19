package com.nextplugins.testserver.core.api.model.player;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data
@Builder(builderMethodName = "create", buildMethodName = "wrap")
public class Account {

    private Player player;
    private PermissionAttachment attachment;

    @Builder.Default private Group group = Group.MEMBER;

    public static AccountBuilder of(Player player) {

        PermissionAttachment attachment = player.addAttachment(NextTestServer.getInstance());

        return Account.create()
                .player(player)
                .attachment(attachment);

    }

    public static Account createDefault(Player player) {
        return Account.of(player).wrap();
    }

}
