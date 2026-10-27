package net.boat.industrialhellscape;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /*
        Client Config to disable client-side message upon world-join
        - For important instructions/announcements
     */
    static {
        ENABLE_SERVER_JOIN_MESSAGE = BUILDER
                .comment("Enable clientside message when joining a world.")
                .define("enable_server_join_message", true);
    }

    static final ModConfigSpec SPEC = BUILDER.build();

    private static final ModConfigSpec.BooleanValue ENABLE_SERVER_JOIN_MESSAGE;
    public static boolean enableWorldJoinMessage() { return ENABLE_SERVER_JOIN_MESSAGE.get(); }
}
