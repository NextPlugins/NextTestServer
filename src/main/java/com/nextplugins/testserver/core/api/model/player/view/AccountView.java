package com.nextplugins.testserver.core.api.model.player.view;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.utils.DateUtils;
import com.nextplugins.testserver.core.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class AccountView extends SimpleInventory {

    public AccountView() {
        super(
                "core.account.main",
                "Visualizando perfil",
                3 * 9
        );
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        // TODO create callbacks

        Account account = viewer.getPropertyMap().get("target");

        editor.setItem(10, InventoryItem.of(ItemBuilder.of(account.getName())
                .name("&6" + account.getName())
                .lore(
                        "",
                        " &fRegistrado em: &e" + DateUtils.format(account.getOfflinePlayer().getFirstPlayed()),
                        " &fÚltimo login: &e" + DateUtils.format(account.getOfflinePlayer().getLastPlayed()),
                        " &fStatus: " + (account.getOfflinePlayer().isOnline() ? "&aOnline" : "&cOffline"),
                        "",
                        " &fGrupo: " + account.getGroup().getPrefix(),
                        " &fPermissões: &e" + account.getPermissions().size() + " permissões",
                        ""
                )
                .result()));

        editor.setItem(14, InventoryItem.of(ItemBuilder.of(Material.BOOK_AND_QUILL)
                .name("&6Editar grupo")
                .lore(
                        " &7Clique aqui para mudar o grupo",
                        " &7do jogador &e" + account.getName() + "",
                        "",
                        "  &aGrupo atual &8➜ &b" + account.getGroup().getName(),
                        ""
                )
                .result())
                .defaultCallback(callback -> {



                }));

        editor.setItem(15, InventoryItem.of(ItemBuilder.of(Material.EMPTY_MAP)
                .name("&6Visualizar Permissões")
                .lore(
                        " &7Clique aqui para ver as permissões",
                        " &7que o jogador &e" + account.getName() + " &7possui",
                        "",
                        "  &aPermissões próprias &8➜ &b" + account.getPermissions().size() + " permissões",
                        "  &aPermissões do grupo &8➜ &b" + account.getGroup().getPermissions().size() + " permissões",
                        ""
                )
                .result()));

    }
}
