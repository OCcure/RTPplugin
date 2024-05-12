package me.occure.rtpplugin.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RTPcommand extends BukkitCommand {
    public RTPcommand(String rtp) {

        super(rtp);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, String alias, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        World world = player.getWorld();

        Location safeLocation = RandomLocation(world, player.getLocation());
        if (safeLocation != null) {
            player.teleport(safeLocation);
            player.sendMessage("tp완료!");
        } else {
            player.sendMessage("tp 장소를 찾지못했습니다 다시 실행해 주세요");
        }
        return true;
    }
    //랜덤한 좌표만들기
    private Location RandomLocation(World world, Location player) {
        Random random = new Random();
        for (int tries = 0; tries < 100; tries++) {
            int x = player.getBlockX() + (random.nextInt(10000 * 2 + 1) - 10000);
            int z = player.getBlockZ() + (random.nextInt(10000 * 2 + 1) - 10000);
            int y = findSafeY(world,x,z);
            //안전한 곳을 찾기위해 Y좌표 탐색
            if (y != -1) {
                return new Location(world, x, y, z);
            }
        }
        return null;  // 안전한 위치를 찾지 못한 경우
    }
    //생성된 X,Z 기준으로 탐색
    private int findSafeY(World world, int x, int z) {
        for ( int y = world.getMaxHeight() - 1; y > world.getMinHeight(); y--) {
            Material bottom = world.getBlockAt(x, y - 1, z).getType();
            Material middle = world.getBlockAt(x, y, z).getType();
            Material top = world.getBlockAt(x, y + 1, z).getType();
            //좌표의 블럭이 y값과 y+1이 공기이고 y-1은 플레이어가 밟을수 있는 블럭인지
            if (isSafeGround(bottom) && middle == Material.AIR && top == Material.AIR) {
                return y;
            }
        }
        return -1;
    }

    private boolean isSafeGround(Material material) {
        return material.isSolid() && !material.isOccluding();
    }
}



