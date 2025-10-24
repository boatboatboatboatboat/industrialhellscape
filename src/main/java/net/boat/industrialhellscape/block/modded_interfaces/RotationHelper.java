package net.boat.industrialhellscape.block.modded_interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface takes in a VoxelShape hitbox (which may compose of multiple boxes), and a desired direction. It rotates all individual elements to the new direction.
//-----

public interface RotationHelper {

    static VoxelShape rotateVoxelCardinal(Direction directionRelativeToNorth, VoxelShape northShapeInput) {
        int timesToRotate = switch(directionRelativeToNorth) { //Convert desired direction into number of rotations Clockwise (CW)
            case WEST -> 1;
            case SOUTH -> 2;
            case EAST -> 3;
            default -> 0; //North. The submitted shapeInput should already be oriented North by default when modelled.
        };

        //Each rotation rotates Counterclockwise (Positive according to Right Hand Rule)
        return rotateVoxelYAxisIntTimes(timesToRotate, northShapeInput);
    }

    static VoxelShape rotateVoxelXAxisIntTimes(int timesToRotate, VoxelShape shapeInput) {
        VoxelShape[] placeHolder = new VoxelShape[]{shapeInput, Shapes.empty()};
        for (int i = 0; i < timesToRotate; i++) {
            placeHolder[0].forAllBoxes((pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ)

                    -> placeHolder[1] = Shapes.joinUnoptimized(placeHolder[1], Shapes.box( pMinX, pMinZ, 1-pMaxY, pMaxX, pMaxZ, 1-pMinY), BooleanOp.OR)); //Cartesian coordinate rotation.

            placeHolder[0] = placeHolder[1]; //After all operations are done for all boxes, set the array back to the original state. The first index has the geometry. The second index is cleared and becomes empty.
            placeHolder[1] = Shapes.empty();
        }

        return placeHolder[0];
    }

    static VoxelShape rotateVoxelYAxisIntTimes(int timesToRotate, VoxelShape shapeInput) {
        VoxelShape[] placeHolder = new VoxelShape[]{shapeInput, Shapes.empty()};

        for (int i = 0; i < timesToRotate; i++) {
            placeHolder[0].forAllBoxes((pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ)

                    -> placeHolder[1] = Shapes.joinUnoptimized(placeHolder[1], Shapes.box(pMinZ, pMinY, 1 - pMaxX, pMaxZ, pMaxY, 1 - pMinX), BooleanOp.OR)); //Cartesian coordinate rotation. See: precalculus.

            placeHolder[0] = placeHolder[1]; //After all operations are done for all boxes, set the array back to the original state. The first index has the geometry. The second index is cleared and becomes empty.
            placeHolder[1] = Shapes.empty();
        }

        return placeHolder[0];
    }
}
