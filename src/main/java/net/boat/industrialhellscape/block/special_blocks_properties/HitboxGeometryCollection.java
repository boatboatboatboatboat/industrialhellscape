package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface stores all custom hitbox geometries in one place to not clutter up the custom classes.
//-----

public interface HitboxGeometryCollection {

    static VoxelShape TOILET_NORTH(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0.375, 0.875, 0.75, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.375, 0.25, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.375, 0.375, 0.875, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 1, 0.000625, 0.8125, 1.375, 0.375625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.000625, 0.8125, 1, 0.375625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.125, 0.6875, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.125, 0.6875, 0.3125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.3125, 0.75, 0.75, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.5625, 0.75, 0.4375, 0.875), BooleanOp.OR);

        return shape;
    }

    static VoxelShape RED_MEDKIT_NORTH(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.09375, 0.125, 0.5, 0.90625, 0.75, 1), BooleanOp.OR);

        return shape;
    }

    static VoxelShape WHITE_MEDKIT_NORTH(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.5, 0.9375, 1, 1), BooleanOp.OR);

        return shape;
    }

    static VoxelShape SLIGHTLY_SMALLER_FULL_BLOCK(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375), BooleanOp.OR);

        return shape;
    }

    static VoxelShape FULL_BLOCK(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 1, 1), BooleanOp.OR);

        return shape;
    }

    static VoxelShape INNER_CORNER_UP(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.6875, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.6875, 0.3125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape INNER_CORNER_DOWN(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 1, 1, 0.3125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape INNER_CORNER_SIDE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.3125, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0, 1, 1, 0.3125), BooleanOp.OR);
        return shape;
    }

    static VoxelShape OUTER_CORNER_UP(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.6875, 0, 0.9375, 1, 0.3125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape OUTER_CORNER_DOWN(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.3125, 0.3125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape OUTER_CORNER_SIDE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0, 0.3125, 0.9375, 0.3125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape RETRO_COMPUTER(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.375, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.0625, 0.75, 0.9375, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.6125, 0.6875, 0.875, 0.628125), BooleanOp.OR);

        return shape;
    }

    static VoxelShape UPPER_SLAB(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 1, 1, 1), BooleanOp.OR);

        return shape;
    }

    //CLASSIC DESK
    static VoxelShape DESK_SOLO_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.6875, 0.25, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.25, 0.1875, 0.8125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.6875, 0.9375, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.25, 0.875, 0.8125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.125, 0.75, 0.8125, 0.1875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_LEFT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.6875, 0.25, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.25, 0.1875, 0.8125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.125, 1, 0.8125, 0.1875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_MIDDLE_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 1, 0.8125, 0.1875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_RIGHT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.6875, 0.9375, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.25, 0.875, 0.8125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 0.75, 0.8125, 0.1875), BooleanOp.OR);

        return shape;
    }

    //CLASSIC DESK DRAWER
    static VoxelShape DESK_DRAWER_SOLO_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.6875, 0.25, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.6875, 0.9375, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.8125, 0.8125), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_DRAWER_LEFT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.25, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.6875, 0.25, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.125, 1, 0.8125, 0.8125), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_DRAWER_MIDDLE_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 1, 0.8125, 0.8125), BooleanOp.OR);

        return shape;
    }
    static VoxelShape DESK_DRAWER_RIGHT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.0625, 0.9375, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.6875, 0.9375, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 0.875, 0.8125, 0.8125), BooleanOp.OR);

        return shape;
    }

    //METAL DESK
    static VoxelShape METAL_DESK_SOLO_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.125, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.875, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.125, 0.0625, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.3125, 0.125, 1, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.3125, 0, 0.875, 0.8125, 0.0625), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_LEFT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.125, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.125, 0.0625, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.3125, 0, 1, 0.8125, 0.0625), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_MIDDLE_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 1, 0.8125, 0.0625), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_RIGHT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.875, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.3125, 0.125, 1, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 0.875, 0.8125, 0.0625), BooleanOp.OR);

        return shape;
    }

    //METAL DESK DRAWER
    static VoxelShape METAL_DESK_DRAWER_SOLO_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.125, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.875, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.3125, 0, 0.875, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.125, 0.125, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.3125, 0.125, 1, 0.8125, 0.875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_DRAWER_LEFT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 0.125, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.3125, 0, 1, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.125, 0.125, 0.8125, 0.875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_DRAWER_MIDDLE_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 1, 0.8125, 0.9375), BooleanOp.OR);

        return shape;
    }
    static VoxelShape METAL_DESK_DRAWER_RIGHT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.875, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 0.875, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.3125, 0.125, 1, 0.8125, 0.875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape CASSETTE_PLAYER(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.0625, 0.8125, 0.1875, 0.8125), BooleanOp.OR);

        return shape;
    }
    static VoxelShape THIN_VERTICAL_ROD_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.375, 0.01, 0.375, 0.625, 0.99, 0.625), BooleanOp.OR);

        return shape;
    }
    static VoxelShape WORK_LIGHT_MOUNT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.03125, 0.375, 0.375, 0.3125, 0.625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.375, 0.375, 0.96875, 0.625, 0.6875), BooleanOp.OR);

        return shape;
    }
    static VoxelShape FLOOR_WORK_LIGHT_SHAPE(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.3125, 0.75, 0.625, 0.75), BooleanOp.OR);

        return shape;
    }
    //METAL DESK DRAWER 2 (Same as Metal Desk Drawer 1)
    static  VoxelShape METAL_DESK_DRAWER_2_SOLO_SHAPE(){
        return METAL_DESK_DRAWER_SOLO_SHAPE();
    }
    static  VoxelShape METAL_DESK_DRAWER_2_LEFT_SHAPE(){
        return METAL_DESK_DRAWER_LEFT_SHAPE();
    }
    static  VoxelShape METAL_DESK_DRAWER_2_MIDDLE_SHAPE(){
        return METAL_DESK_DRAWER_MIDDLE_SHAPE();
    }
    static  VoxelShape METAL_DESK_DRAWER_2_RIGHT_SHAPE(){
        return METAL_DESK_DRAWER_RIGHT_SHAPE();
    }

    static VoxelShape HANDRAIL(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1, 0.1, 0.1, 0.125, 0.5, 0.9), BooleanOp.OR);
        return shape;
    }
}
