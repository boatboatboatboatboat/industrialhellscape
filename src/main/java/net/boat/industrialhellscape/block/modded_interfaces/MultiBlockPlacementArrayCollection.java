package net.boat.industrialhellscape.block.modded_interfaces;

public interface MultiBlockPlacementArrayCollection {
    /*
        arrays of 3-value vectors corresponding to X, Y, Z coordinates relative to block position

        First value should be 0,0,0 (the blockpos of the block itself is the origin) because
        that is where the player expects the block to be placed, in front of their cursor.
    */
    int[][] VERTICAL_DEBUG = { {0, 0, 0}, {0, 1, 0}, {0, 2, 0}, {0, 3, 0} };
    int[][] T_SHAPE_4B = { {0, 0, 0}, {-1, 0, 0}, {1, 0, 0}, {0, 1, 0} };
    int[][] L_SHAPE_4B = { {0, 0, 0}, {-1, 0, 0}, {-1, 1, 0}, {-1, 2, 0} };
    int[][] SNAKE_SHAPE = { {0, 0, 0}, {0, 1, 0}, {0, 1, -1}, {-1, 0, 0} };

    int[][] SIDEWAYS_PLACEMENT = { {0, 0, 0}, {1, 0, 0} };
    int[][] VERTICAL_PLACEMENT = { {0, 0, 0}, {0, 1, 0} };
    int[][] FRONTAL_PLACEMENT = { {0, 0, 0}, {0, 0, 1} };
    int[][] SLEEPABLE_BED_PLACEMENT = { {0, 0, 0}, {0, 0, -1} };
}
