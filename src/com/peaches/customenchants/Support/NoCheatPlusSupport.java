package com.peaches.customenchants.Support;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPExemptionManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class NoCheatPlusSupport {
    public static void addPlayer(@NotNull Player player) {
        NCPExemptionManager.exemptPermanently(player, CheckType.BLOCKBREAK);
        NCPExemptionManager.exemptPermanently(player, CheckType.BLOCKBREAK_FASTBREAK);
    }

    public static void RemovePlayer(@NotNull Player player) {
        NCPExemptionManager.unexempt(player, CheckType.BLOCKBREAK);
        NCPExemptionManager.unexempt(player, CheckType.BLOCKBREAK_FASTBREAK);
    }
}


