package me.orbus.breakcontrol;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import java.util.Arrays;

public class BlockBreakListener implements Listener {
    private final Breakcontrol plugin;

    public BlockBreakListener(Breakcontrol plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        // Список блоков, которые мы хотим защитить
        List<Material> protectedBlocks = Arrays.asList(
                Material.SPAWNER,
                Material.TRIAL_SPAWNER,
                Material.BUDDING_AMETHYST,
                Material.VAULT
        );

        if (protectedBlocks.contains(blockType)) {
            String message = null;
            if (!event.getPlayer().hasPermission("override") && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
                message = this.plugin.getDenyMessage();
            } else {
                message = this.plugin.getAllowMessage();
            }

            if (message != null && !message.equals("")) {
                event.getPlayer().sendMessage(message);
            }
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> blocksToProtect = new ArrayList<>();
        List<Material> protectedBlocks = Arrays.asList(
                Material.SPAWNER,
                Material.TRIAL_SPAWNER,
                Material.BUDDING_AMETHYST,
                Material.VAULT,
                Material.CHEST
        );
        for (Block block : event.blockList()) {
            if (protectedBlocks.contains(block.getType())) {
                blocksToProtect.add(block);
            }
        }

        // Удаляем защищённые блоки из списка разрушения
        event.blockList().removeAll(blocksToProtect);
    }
}
