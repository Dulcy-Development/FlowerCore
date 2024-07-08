package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class HealCommand extends BaseCommand {
    @Override
    @Command(name = "heal", permission = "delta.staff.heal", inGameOnly = true, description = "Heals a player", usage = "/heal [player]")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("heal.healed")));
            return;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        target.setHealth(20);
        target.setFoodLevel(20);
        target.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("heal.healed-by"))
                .replace("%player%", player.getName()));
    }
}