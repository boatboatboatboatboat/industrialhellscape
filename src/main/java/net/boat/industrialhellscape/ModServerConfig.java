package net.boat.industrialhellscape;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        GAS_STATION_PILL_DAMAGE = BUILDER
                .comment("n=0: No player/villager damage when ingested")
                .defineInRange("pill_damage", 512, 0, 512);
    }
    static {
        PEANUT_STATUE_TELEPORT_ATTEMPTS = BUILDER
                .comment("n=0: Teleport Disabled. n>1: Mitosis")
                .defineInRange("peanut_statue_teleport_attempts", 1, 0, 127);
    }

    static {
        PEANUT_STATUE_DISAPPEARS_ON_RANDOM_TICK = BUILDER
                .comment("Enable Peanut Statue disappearance on random tick")
                .define("enable_peanut_statue_random_tick_disappear", false);
    }

    static {
        HAVEN_TOOL_DESTROYS_PEANUT_STATUE = BUILDER
                .comment("Enable HAVEN Tool to remove Peanut Statue without triggering teleport")
                .define("enable_haven_tool_peanut_statue_removal", true);
    }

    static {
        SMOKE_ALARM_RANDOM_TICK = BUILDER
                .comment("Enable activated smoke alarm to produce low-battery sound on randomTicks")
                .define("enable_smoke_alarm_sound", true);
    }

    static final ModConfigSpec SPEC = BUILDER.build();

    private static final ModConfigSpec.IntValue GAS_STATION_PILL_DAMAGE, PEANUT_STATUE_TELEPORT_ATTEMPTS;
    private static final ModConfigSpec.BooleanValue PEANUT_STATUE_DISAPPEARS_ON_RANDOM_TICK, HAVEN_TOOL_DESTROYS_PEANUT_STATUE, SMOKE_ALARM_RANDOM_TICK;

    public static int gasStationPillDamage() { return GAS_STATION_PILL_DAMAGE.get(); }
    public static int peanutStatueTeleportAttemptsInt() { return PEANUT_STATUE_TELEPORT_ATTEMPTS.get(); }
    public static boolean peanutStatueDisappearsOnRandomTick() {return PEANUT_STATUE_DISAPPEARS_ON_RANDOM_TICK.get();}
    public static boolean havenToolDestroysPeanutStatue() {return HAVEN_TOOL_DESTROYS_PEANUT_STATUE.get();}
    public static boolean smokeAlarmPlaysSound() {return SMOKE_ALARM_RANDOM_TICK.get();}
}
