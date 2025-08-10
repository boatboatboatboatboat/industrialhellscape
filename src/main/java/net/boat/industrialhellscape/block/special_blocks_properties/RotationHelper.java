package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface takes in a VoxelShape hitbox (which may compose of multiple boxes), and a desired direction. It rotates all individual elements to the new direction.
//-----

public interface RotationHelper {

    static VoxelShape rotateVoxelHorizontal(Direction directionToRotate, VoxelShape shapeInput) {

        //The placeHolder contains the original default voxelShape and an empty space. It is an array of size 2
        //An operation is applied to the current sub-shape

        VoxelShape[] placeHolder = new VoxelShape[]{shapeInput, Shapes.empty()};

        int timesToRotate = switch(directionToRotate) { //Convert desired direction into number of rotations Clockwise (CW)
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };

        for (int i = 0; i < timesToRotate; i++) {

            //The inputted VoxelShape is placed in a size 2 array as the first index. This is [0]. The second array index is empty.
            //That index has 90 degree CW operations conducted on it for each box subcomponent of the total VoxelShape input. Each one updating the second index of the array.
            //After all box components are rotated and collected in the second index, move [1] over to where [0] is, leaving nothing behind and replacing the old geometry.
            //Repeats for each 90 degree clockwise rotation required.

            //Returns [0] which holds the new geometry when the for-loop completes.


            placeHolder[0].forAllBoxes((pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ)

                    -> placeHolder[1] = Shapes.joinUnoptimized(placeHolder[1], Shapes.box( 1 - pMaxZ, pMinY, pMinX, 1 - pMinZ, pMaxY, pMaxX), BooleanOp.OR));

            placeHolder[0] = placeHolder[1]; //After all operations are done for all boxes, set the array back to the original state. The first index has the geometry. The second index is cleared and becomes empty.
            placeHolder[1] = Shapes.empty();
        }

        return placeHolder[0];
    }
}
