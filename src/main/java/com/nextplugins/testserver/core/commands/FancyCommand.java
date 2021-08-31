package com.nextplugins.testserver.core.commands;

import com.nextplugins.testserver.core.utils.ColorUtil;
import com.nextplugins.testserver.core.utils.TitleUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class FancyCommand {

    @Command(
            name = "clearchat",
            aliases = {"limparchat", "cc"},
            permission = "nextcore.command.clearchat"
    )
    public void onClearChat(Context<CommandSender> context) {
        String clearChat = StringUtils.repeat(" §c \n §c ", 100) + "§aO chat foi limpado por " + context.getSender().getName();
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(clearChat));
    }

    @Command(
            name = "clear",
            aliases = {"limpar"},
            description = "Limpar o seu inventário",
            permission = "nextcore.command.clear"
    )
    public void onClearCommand(Context<CommandSender> context, @Optional Player player) {

        val sender = context.getSender();
        if (player == null) {

            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ColorUtil.colored("&cUtilize /" + context.getLabel() + " <jogador>"));
                return;
            }

            clearInventory((Player) sender);
            sender.sendMessage(ColorUtil.colored("&aVocê limpou o seu inventário com sucesso."));
            return;

        }

        clearInventory(player);
        sender.sendMessage(ColorUtil.colored("&aVocê limpou o inventário de " + player.getName() + " com sucesso."));

    }

    private void clearInventory(Player target) {
        target.setItemOnCursor(null);
        target.getInventory().setArmorContents(new ItemStack[]{});
        target.getInventory().clear();
    }

    @Command(
            name = "fly",
            aliases = {"voar"},
            description = "Ativar o modo passarinho",
            permission = "nextcore.command.fly",
            target = CommandTarget.PLAYER
    )
    public void onFlyCommand(Context<Player> context) {

        val player = context.getSender();

        if (!player.getWorld().getName().equalsIgnoreCase("plot") && !player.hasPermission("nextcore.command.fly.admin")) {
            player.sendMessage(ColorUtil.colored("&cVocê não pode ativar o modo de voo neste mundo."));
            return;
        }

        player.setAllowFlight(!player.getAllowFlight());
        TitleUtils.sendTitle(player, "&6&LMODO VOO<nl>" + (player.getAllowFlight() ? "&aFoi ativado com sucesso" : "&cFoi desativado com sucesso"), 20, 20, 20);

    }

    @Command(
            name = "hat",
            aliases = {"chapeu"},
            description = "Colocar um item na cabeça",
            target = CommandTarget.PLAYER
    )
    public void onHatCommand(Context<Player> context) {
        val player = context.getSender();
        if (player.getItemInHand() == null
                || player.getItemInHand().getType() == Material.AIR
                || (player.getItemInHand().getType().getMaxDurability() != 0
                && !player.getItemInHand().getType().toString().toUpperCase().contains("HELMET"))) {
            player.sendMessage(ColorUtil.colored("&cEste item não é um item válido para servir como chapéu."));
            return;
        }

        val helmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(player.getItemInHand());
        player.setItemInHand(helmet);
        player.sendMessage(ColorUtil.colored("&aSe divirta com seu mais novo chapéu."));

    }

    @Command(
            name = "gamemode",
            aliases = {"gm"},
            description = "Mudar o modo de jogo",
            permission = "nextcore.command.gamemode"
    )
    public void onGamemode(Context<CommandSender> context,
                           String mode,
                           @Optional Player player) {

        GameMode gameMode;
        try {
            val number = Integer.parseInt(mode);
            gameMode = GameMode.getByValue(number);
        } catch (Exception exception) {
            try {
                gameMode = GameMode.valueOf(mode);
            } catch (Exception exception1) {
                context.sendMessage(ColorUtil.colored("&cEste modo de jogo é inválido."));
                return;
            }
        }

        if (player != null) {
            player.setGameMode(gameMode);
            context.sendMessage(ColorUtil.colored("&aVocê alterou o modo de jogo do jogador &f" + player.getName() + " &apara o &f" + GameModeParse.valueOf(gameMode.toString()).getFancyName()));
            return;
        }

        if (context.getSender() instanceof ConsoleCommandSender) {
            context.sendMessage(ColorUtil.colored("&cVocê não pode alterar o modo de jogo do console"));
            return;
        }

        val sender = (Player) context.getSender();

        sender.setGameMode(gameMode);
        context.sendMessage(ColorUtil.colored("&aVocê alterou o seu modo de jogo para &f" + GameModeParse.valueOf(gameMode.toString()).getFancyName()));


    }

    @AllArgsConstructor
    public enum GameModeParse {

        CREATIVE("Criativo"),
        SURVIVAL("Sobrevivência"),
        ADVENTURE("Aventura"),
        SPECTATOR("Espectador");

        @Getter
        private final String fancyName;
    }


}
