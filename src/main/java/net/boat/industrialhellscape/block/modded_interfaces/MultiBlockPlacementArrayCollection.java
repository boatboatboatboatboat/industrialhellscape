package net.boat.industrialhellscape.block.modded_interfaces;

public interface MultiBlockPlacementArrayCollection {
    /*
        arrays of 3-value vectors corresponding to X, Y, Z coordinates relative to block position

        First value should be 0,0,0 (the blockpos of the block itself is the origin) because
        that is where the player expects the block to be placed, in front of their cursor.

        MultiBlockPlacementInterface addresses player directional placement when handling these matrices.

        Block classes only check up to indice 2 (third indice). Theoretically,
        I can add more indices to encode flags for special per-state logic.
    */

    //For two-block multiblocks; 2 3D vectors required.
    int[][] SIDEWAYS_PLACEMENT = { {0, 0, 0}, {1, 0, 0} }; //places second block right of first block
    int[][] VERTICAL_PLACEMENT = { {0, 0, 0}, {0, 1, 0} }; // places second block above first block
    int[][] FRONTAL_PLACEMENT = { {0, 0, 0}, {0, 0, 1} }; //places second block behind first block

    /*
        (modded and vanilla) Minecraft bed placement is not
        consistent to other vanilla blocks for several reasons.
        The negative is a correction to this.
     */
    int[][] SLEEPABLE_BED_PLACEMENT = { {0, 0, 0}, {0, 0, -1} };
}
