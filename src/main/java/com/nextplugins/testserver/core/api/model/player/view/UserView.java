package com.nextplugins.testserver.core.api.model.player.view;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.utils.DateUtils;
import com.nextplugins.testserver.core.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class UserView extends SimpleInventory {

    public UserView() {
        super(
                "core.account.main",
                "Visualizando perfil",
                3 * 9
        );
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        // TODO create callbacks

        User user = viewer.getPropertyMap().get("target");

        editor.setItem(10, InventoryItem.of(ItemBuilder.of(user.getName())
                .name("&6" + user.getName())
                .lore(
                        "",
                        " &fRegistrado em: &e" + DateUtils.format(user.getOfflinePlayer().getFirstPlayed()),
                        " &fÚltimo login: &e" + DateUtils.format(user.getOfflinePlayer().getLastPlayed()),
                        " &fStatus: " + (user.getOfflinePlayer().isOnline() ? "&aOnline" : "&cOffline"),
                        "",
                        " &fGrupo: " + user.getGroup().getPrefix(),
                        " &fPermissões: &e" + user.getPermissions().size() + " permissões",
                        ""
                )
                .result()));

        editor.setItem(14, InventoryItem.of(ItemBuilder.of(Material.BOOK_AND_QUILL)
                .name("&6Editar grupo")
                .lore(
                        " &7Clique aqui para mudar o grupo",
                        " &7do jogador &e" + user.getName() + "",
                        "",
                        "  &aGrupo atual &8➜ &b" + user.getGroup().getName(),
                        ""
                )
                .result())
                .defaultCallback(callback -> {



                }));

        editor.setItem(15, InventoryItem.of(ItemBuilder.of(Material.EMPTY_MAP)
                .name("&6Visualizar Permissões")
                .lore(
                        " &7Clique aqui para ver as permissões",
                        " &7que o jogador &e" + user.getName() + " &7possui",
                        "",
                        "  &aPermissões próprias &8➜ &b" + user.getPermissions().size() + " permissões",
                        "  &aPermissões do grupo &8➜ &b" + user.getGroup().getPermissions().size() + " permissões",
                        ""
                )
                .result()));

    }
}
