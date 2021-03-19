package com.nextplugins.testserver.core.api.model.player.storage;

import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.dao.AccountDAO;
import com.nextplugins.testserver.core.api.model.player.utils.AccountUtils;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public final class AccountStorage {

    private final Map<UUID, Account> players = new HashMap<>();

    @Inject
    private AccountDAO accountDAO;

    public void init() {
        this.accountDAO.createTable();
    }

    public Account loadPlayer(Player player) {

        Account account = players.getOrDefault(player.getUniqueId(), null);
        if (account == null) {

            account = this.accountDAO.selectOne(player.getUniqueId());
            if (account == null) account = Account.createDefault(player);

            this.players.put(account.getPlayer().getUniqueId(), account);

        }

        AccountUtils.updateAttachment(account);
        return account;

    }

    public void purgeData(Account account) {
        this.players.remove(account.getPlayer().getUniqueId());
        this.accountDAO.update(account);
    }

    public Collection<Account> getOnlinePlayers() {
        return players.values();
    }

}
