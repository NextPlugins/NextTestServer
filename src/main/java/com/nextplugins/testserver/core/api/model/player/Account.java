package com.nextplugins.testserver.core.api.model.player;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import lombok.Builder;
import lombok.Data;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data
@Builder(builderMethodName = "create", buildMethodName = "wrap")
public class Account {

    @Inject private static GroupStorage groupStorage;

    @Nullable private Player player;
    @Nonnull private OfflinePlayer offlinePlayer;

    @Nullable private PermissionAttachment attachment;

    private String name;
    private UUID uniqueId;

    private Group group;
    private List<String> permissions;

    public static AccountBuilder createDefault(Player player) {

        val offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        val attachment = player.addAttachment(NextTestServer.getInstance());

        return Account.createDefault(offlinePlayer)
                .player(player)
                .attachment(attachment);

    }

    public static AccountBuilder createDefault(OfflinePlayer player) {

        return Account.create()
                .offlinePlayer(player)
                .name(player.getName())
                .uniqueId(player.getUniqueId())
                .permissions(Lists.newArrayList())
                .group(groupStorage.getGroupByName("Membro"));

    }

}
