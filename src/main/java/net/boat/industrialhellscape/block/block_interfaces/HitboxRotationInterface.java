package net.boat.industrialhellscape.block.block_interfaces;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface takes in a VoxelShape hitbox (which may compose of multiple boxes), and a desired direction. It rotates all individual elements to the new direction.
//An array is used because the Cartesian rotation operation will be repeated on the same Voxelshapes again and again.
//Source: https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/
//-----

public interface HitboxRotationInterface {

    static VoxelShape rotateVoxelCardinal(Direction directionRelativeToNorth, VoxelShape northShapeInput) {
        int timesToRotate = switch(directionRelativeToNorth) { //Convert desired direction into number of rotations Counterclockwise (CCW)
            case WEST -> 1;
            case SOUTH -> 2;
            case EAST -> 3;
            default -> 0; //North. The submitted shapeInput should already be oriented North by default when modelled.
            //(Unless the hitbox is for an instance of Industrial Hellscape's CornerBlock block class. Then the submitted shapeInput should be facing WEST by default.)
        };

        //Each rotation rotates Counterclockwise (Positive according to Right Hand Rule)
        return rotateVoxelYAxisIntTimes(timesToRotate, northShapeInput);
    }

    static VoxelShape rotateVoxelXAxisIntTimes(int timesToRotate, VoxelShape shapeInput) {
        VoxelShape[] placeHolder = new VoxelShape[]{shapeInput, Shapes.empty()}; //Assign the inputted VoxelShape into a 1x2 empty array, in the first indice.

        for (int i = 0; i < timesToRotate; i++) { //For loop until target direction is reached
            placeHolder[0].forAllBoxes(

                    //hitboxShape: Lower and upper bound coordinates (min coordinate and max coordinate in 3D space)
                    (pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ)

                            -> placeHolder[1] = Shapes.join(placeHolder[1], Shapes.box( pMinX, pMinZ, 1-pMaxY, pMaxX, pMaxZ, 1-pMinY), BooleanOp.OR)
            );

            placeHolder[0] = placeHolder[1]; //After all operations are done for all boxes, set the first index with the updated geometry.
            placeHolder[1] = Shapes.empty(); //The second index is cleared and becomes empty.
            //The array (and results within) is ready to be reused for the next iteration of the for loop.
        }

        return placeHolder[0];
    }

    static VoxelShape rotateVoxelYAxisIntTimes(int timesToRotate, VoxelShape shapeInput) {
        VoxelShape[] placeHolder = new VoxelShape[]{shapeInput, Shapes.empty()};  //Assign the inputted VoxelShape into a 1x2 empty array, in the first index.

        for (int i = 0; i < timesToRotate; i++) {
            placeHolder[0].forAllBoxes(

                    //hitboxShape: Lower and upper bound coordinates (min coordinate and max coordinate in 3D space)
                    (pMinX, pMinY, pMinZ, pMaxX, pMaxY, pMaxZ)

                    -> placeHolder[1] = Shapes.join(placeHolder[1], Shapes.box(pMinZ, pMinY, 1 - pMaxX, pMaxZ, pMaxY, 1 - pMinX), BooleanOp.OR)
            );

            placeHolder[0] = placeHolder[1]; //After all operations are done for all boxes, set the first index with the updated geometry.
            placeHolder[1] = Shapes.empty(); //The second index is cleared and becomes empty.
            //The array (and results within) is ready to be reused for the next iteration of the for loop.
        }

        return placeHolder[0];
    }
}
