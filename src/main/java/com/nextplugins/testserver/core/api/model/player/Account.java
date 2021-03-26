package com.nextplugins.testserver.core.api.model.player;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data
@Builder(builderMethodName = "create", buildMethodName = "wrap")
public class Account {

    @Inject private static GroupStorage groupStorage;

    private Player player;
    private PermissionAttachment attachment;
    private Group group;

    private List<String> permissions;

    public static AccountBuilder of(Player player) {

        PermissionAttachment attachment = player.addAttachment(NextTestServer.getInstance());

        return Account.create()
                .player(player)
                .attachment(attachment)
                .permissions(Lists.newArrayList())
                .group(groupStorage.getGroupByName("Membro"));

    }

    public static Account createDefault(Player player) {
        return Account.of(player).wrap();
    }

}
