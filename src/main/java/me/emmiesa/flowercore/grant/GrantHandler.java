package me.emmiesa.flowercore.grant;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.FlowerCore;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 03/06/2024 - 21:24
 */
@Getter
@Setter
public class GrantHandler {

    /**
     * Set a grant to expired
     *
     * @param grant the grant
     * @param removedBy the person who removed the grant
     */
    public void setGrantExpired(Grant grant, String removedBy) {
        grant.setActive(false);
        Date date = new Date();
        grant.setRemovedAt(date);
        grant.setRemovedBy(removedBy);
        grant.setRemovedReason("Removed");
    }

    /**
     * Check if a grant is expired
     *
     * @param grant the grant
     * @return if the grant is expired
     */
    public boolean isGrantExpired(Grant grant) {
        return !grant.isActive();
    }

    /**
     * Set a grant to active
     *
     * @param grant the grant
     */
    public void setGrantActive(Grant grant) {
        grant.setActive(true);
        grant.setRemovedAt(null);
        grant.setRemovedBy(null);
        grant.setRemovedReason(null);
    }

    /**
     * Expire a grant
     *
     * @param grant the grant
     * @param time the time to expire the grant
     */
    public void expireGrant(Grant grant, Date time) {
        if (grant.getDuration() != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    setGrantExpired(grant, "Console");
                }
            }.runTaskLater(FlowerCore.getInstance(), time.getTime());
        }
    }

}
