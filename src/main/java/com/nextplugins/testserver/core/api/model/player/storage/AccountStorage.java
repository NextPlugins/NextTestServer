package com.nextplugins.testserver.core.api.model.player.storage;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.repository.AccountRepository;
import lombok.Getter;
import lombok.var;
import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public final class AccountStorage {

    @Getter private final AsyncLoadingCache<UUID, Account> cache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .removalListener(this::saveOne)
            .buildAsync(this::selectOne);

    @Inject private AccountRepository accountRepository;

    public void init() {

        accountRepository.createTable();
        NextTestServer.getInstance().getLogger().info("DAO do plugin iniciado com sucesso.");

    }

    private void saveOne(UUID uuid, Account account, @NonNull RemovalCause removalCause) {
        accountRepository.update(account);
    }

    private @NotNull CompletableFuture<Account> selectOne(UUID uuid, @NonNull Executor executor) {
        return CompletableFuture.completedFuture(accountRepository.selectOne(uuid));
    }

    /**
     * Used to get accounts
     *
     * @param player offline player
     * @return {@link Account} found, if player is online and no have account, return a new account
     */
    @Nullable
    public Account findAccount(OfflinePlayer player) {
        try {

            var account = cache.get(player.getUniqueId()).get();
            if (account == null && player.isOnline()) {

                account = Account.createDefault(player).wrap();
                put(account);

            }

            return account;

        } catch (InterruptedException | ExecutionException ignored) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void put(Account account) {
        cache.put(account.getUniqueId(), CompletableFuture.completedFuture(account));
    }

    public void unload() {
        cache.synchronous().invalidateAll();
    }

    public Collection<Account> getOnlinePlayers() {
        return cache.synchronous().asMap().values();
    }

}
