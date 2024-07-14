package me.emmy.core.grant;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 25/05/2024 - 22:10
 */
@Getter
@Setter
public class Grant {
    private Date addedAt;
    private Date removedAt;
    private String duration;
    private String addedBy;
    private String reason;
    private String removedBy;
    private String rank;
    private String removedReason;
    private boolean active;
    private String server;

    /**
     * Constructor for Grant
     *
     * @param addedAt Date
     * @param removedAt Date
     * @param duration String
     * @param addedBy String
     * @param reason String
     * @param removedBy String
     * @param rank String
     * @param active boolean
     * @param server String
     */
    public Grant(Date addedAt, Date removedAt, String duration, String addedBy, String reason, String removedBy, String rank, String removedReason, boolean active, String server) {
        this.addedAt = addedAt;
        this.removedAt = removedAt;
        this.duration = duration;
        this.addedBy = addedBy;
        this.reason = reason;
        this.removedBy = removedBy;
        this.rank = rank;
        this.removedReason = removedReason;
        this.active = active;
        this.server = server;
    }
}
