package me.emmiesa.flowercore.extras.scoreboard.assemble.events;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.extras.scoreboard.assemble.board.AssembleBoard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class AssembleBoardCreatedEvent extends Event {

    @Getter
    public static HandlerList handlerList = new HandlerList();
    private final AssembleBoard board;
    private boolean cancelled = false;

    public AssembleBoardCreatedEvent(AssembleBoard board) {
        this.board = board;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
