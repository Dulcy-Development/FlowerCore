/*

public class UnbanCommand extends BaseCommand {
    @Override
    @Command(name = "unban", permission = "flower.punishment.unban", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unban (player)"));
            return;
        }

        String target = args.getArgs(0);
        //String reason = args.length() > 1 ? args.getArgs(1) : "no reason";
        //String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        //String silentornot = args.length() > 3 ? args.getArgs(3) : "";

        CommandSender bannedBy = args.getSender();

        //Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);
        if (target == null) {
            bannedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Utils.broadcastMessage(CC.translate("&cUnbanned: &4" + target));
        //Utils.broadcastMessage(CC.translate("&c" + FlowerCore.getInstance().getPlayerManager().getRank(bannedBy.getUniqueId()).getColor() + bannedBy.getDisplayName() + " &ahas unbanned " + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayer.getUniqueId()).getColor() + target + " &afor " + reason + ". &7(duration: " + duration + "&7) &r" + (silentornot.equalsIgnoreCase("-s") ? " [Silently]" : "")));
        //Punishment punishment = new Punishment(UUID.randomUUID(), targetPlayer == null ? target : targetPlayer.getName(), bannedBy.getUniqueId(), PunishmentType.BAN, reason, targetPlayer == null ? "No IP" : targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));
        FlowerCore.getInstance().getPlayerManager().removePunishment(Bukkit.getOfflinePlayer(target).getUniqueId(), PunishmentType.BAN, target);
    }
}

 */