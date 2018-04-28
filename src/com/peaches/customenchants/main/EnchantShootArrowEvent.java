package com.peaches.customenchants.main;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

final class EnchantShootArrowEvent
        extends Event
        implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player _user;
    private final ItemStack _bow;
    private final Arrow _projectile;
    private boolean _cancelled;

    public EnchantShootArrowEvent(Player enchantUser, ItemStack bow, Arrow arrow) {
        this._user = enchantUser;
        this._bow = bow;
        this._projectile = arrow;
    }

    public Player getPlayer() {
        return this._user;
    }

    public ItemStack getBow() {
        return this._bow;
    }

    public Arrow getArrow() {
        return this._projectile;
    }

    public boolean isCancelled() {
        return this._cancelled;
    }

    public void setCancelled(boolean cancel) {
        this._cancelled = cancel;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }
}


