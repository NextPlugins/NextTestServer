package com.nextplugins.testserver.core.api.model.player.storage;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.repository.UserRepository;
import lombok.Getter;
import lombok.val;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public final class UserStorage {

    @Getter private final AsyncLoadingCache<UUID, User> cache = Caffeine.newBuilder()
            .ticker(System::nanoTime)
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .evictionListener((RemovalListener<UUID, User>) (key, value, cause) -> saveOne(value))
            .removalListener((key, value, cause) -> saveOne(value))
            .buildAsync(this::selectOne);

    @Inject private UserRepository userRepository;

    public void init() {

        userRepository.createTable();
        NextTestServer.getInstance().getLogger().info("DAO do plugin iniciado com sucesso.");

    }

    public void saveOne(User account) {
        userRepository.update(account);
    }

    private User selectOne(UUID owner) {
        return userRepository.selectOne(owner);
    }

    /**
     * Used to get created accounts by uuid
     *
     * @param uuid player uuid
     * @return {@link User} found
     */
    @Nullable
    public User findAccountByName(UUID uuid) {

        try { return cache.get(uuid).get(); } catch (InterruptedException | ExecutionException exception) {
            Thread.currentThread().interrupt();
            exception.printStackTrace();
            return null;
        }

    }

    /**
     * Used to get accounts
     * If player is online and no have account, we will create a new for them
     * but, if is offline, will return null
     *
     * @param offlinePlayer player
     * @return {@link User} found
     */
    @Nullable
    public User findAccount(@NotNull OfflinePlayer offlinePlayer) {

        if (offlinePlayer.isOnline()) {

            val player = offlinePlayer.getPlayer();
            if (player != null) return findAccount(player);

        }

        return findAccountByName(offlinePlayer.getUniqueId());

    }

    /**
     * Used to get accounts
     *
     * @param player player to search
     * @return {@link User} found
     */
    @NotNull
    public User findAccount(@NotNull Player player) {

        User account = findAccountByName(player.getUniqueId());
        if (account == null) {

            account = User.createDefault(player).wrap();
            put(account);

        }

        return account;

    }

    public void put(User user) {
        cache.put(user.getUniqueId(), CompletableFuture.completedFuture(user));
    }

    public void unload() {
        cache.synchronous().invalidateAll();
    }

    public Collection<CompletableFuture<User>> getOnlinePlayers() {
        return cache.asMap().values();
    }

}
