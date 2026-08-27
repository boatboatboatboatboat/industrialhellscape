package net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PeanutStatueBlock extends Modelled2Block implements Fallable {
    public static final BooleanProperty DIAGONAL = BooleanProperty.create("diagonal");
    public Player miningPlayer;

    public PeanutStatueBlock(Properties pProperties, int[][] multiBlockPlacementMatrix, VoxelShape[] hitboxShapeArray) {
        super(pProperties, multiBlockPlacementMatrix, hitboxShapeArray);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(DIAGONAL, false)
                        .setValue(BlockStateProperties.WATERLOGGED, false)
        );
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        float degreeRotation = pContext.getRotation();
        BlockState state = this.defaultBlockState();
        return degreesToDirection(state, degreeRotation);
    }

    private BlockState degreesToDirection(BlockState state, float degrees) {
        if(checkFloatInRange(degrees,-22.5,22.5)) {
            return state.setValue(FACING, Direction.NORTH).setValue(DIAGONAL,false);
        }
        else if(checkFloatInRange(degrees,22.5,67.5)) {
            return state.setValue(FACING, Direction.EAST).setValue(DIAGONAL,true);
        }
        else if(checkFloatInRange(degrees,67.5,112.5)) {
            return state.setValue(FACING, Direction.EAST).setValue(DIAGONAL,false);
        }
        else if(checkFloatInRange(degrees,112.5,157.5)) {
            return state.setValue(FACING, Direction.SOUTH).setValue(DIAGONAL,true);
        }
        else if(checkFloatInRange(degrees,157.5,180)) {
            return state.setValue(FACING, Direction.SOUTH).setValue(DIAGONAL,false);
        }
        else if(checkFloatInRange(degrees,-180,-157.5)) {
            return state.setValue(FACING, Direction.SOUTH).setValue(DIAGONAL,false);
        }
        else if(checkFloatInRange(degrees,-157.5,-112.5)) {
            return state.setValue(FACING, Direction.WEST).setValue(DIAGONAL,true);
        }
        else if(checkFloatInRange(degrees,-112.5,-67.5)) {
            return state.setValue(FACING, Direction.WEST).setValue(DIAGONAL,false);
        }
        else if(checkFloatInRange(degrees,-67.5,-22.5)) {
            return state.setValue(FACING, Direction.NORTH).setValue(DIAGONAL,true);
        }
        return state;
    }

    private boolean checkFloatInRange(float degrees, double lowerBound, double upperBound) {
        return ((float) lowerBound <= degrees && degrees < (float) upperBound);
    }

    private void particleCircle(LevelAccessor level, BlockPos pos ) {
        if(level.players().getFirst() != null) {
            Level candidateLevel = level.players().getFirst().level();

            if(candidateLevel.isClientSide) { //ClientSide VFX
                int radius = 1;
                int xPos = pos.getX();
                int yPos = pos.getY();
                int zPos = pos.getZ();
                double particleXPos;
                double particleZPos;

                for (int j = 0; j < 120; ++j) {
                    particleXPos = xPos + radius * cos(j);
                    particleZPos = zPos + radius * sin(j);

                    float f = (candidateLevel.random.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (candidateLevel.random.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (candidateLevel.random.nextFloat() - 0.5F) * 0.2F;
                    candidateLevel.addParticle(ParticleTypes.PORTAL, particleXPos, yPos, particleZPos, f, f1, f2);
                }
            }
        }
    }

    private void particleLine(LevelAccessor level, BlockPos pos, BlockPos destinationPos) {
        if(level.players().getFirst() != null) {
            Level candidateLevel = level.players().getFirst().level();
            if(candidateLevel.isClientSide) { //Make VFX client (only works from onDestroyedByPlayer() )
                for (int j = 0; j < 128; ++j) {
                    double d0 = level.getRandom().nextDouble();
                    float f = (level.getRandom().nextFloat() - 0.5F) * 0.5F;
                    float f1 = (level.getRandom().nextFloat() - 0.5F) * 0.5F;
                    float f2 = (level.getRandom().nextFloat() - 0.5F) * 0.5F;
                    double d1 = Mth.lerp(d0, pos.getX(), destinationPos.getX()) + (level.getRandom().nextDouble() - (double) 0.5F) + (double) 0.5F;
                    double d2 = Mth.lerp(d0, pos.getY(), destinationPos.getY()) + level.getRandom().nextDouble() - (double) 0.5F;
                    double d3 = Mth.lerp(d0, pos.getZ(), destinationPos.getZ()) + (level.getRandom().nextDouble() - (double) 0.5F) + (double) 0.5F;
                    level.addParticle(ParticleTypes.PORTAL, d1, d2, d3, f, f1, f2);
                }
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player pPlayer, boolean willHarvest, FluidState fluid) {
        if(!level.isClientSide) {
            this.miningPlayer = pPlayer;
            particleCircle(level, pos);
            particleLine(level,pos,pos);

            //duplication
            teleport(state,level,pos,pos, pPlayer,10,10,10);
            teleport(state,level,pos,pos, pPlayer,10,10,10);
        }
        return level.isClientSide() ? level.setBlock(pos, fluid.createLegacyBlock(), 11) : level.removeBlock(pos, false);
    }
    /*
    onRemove() is called whenever removeMultiBlock() is called for each surviving section, forcing them to teleport away from their fate.

    This means any means of erasing them from existence will never be clean so long as the teleport method is inside onRemove
     */
//    @Override
//    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) { //SERVERSIDE ONLY. LEVEL USED HERE IS ONLY SERVERSIDE
//        if(!level.isClientSide) {
//            teleport(state,level,pos,pos,null, 10,10,10);
//        }
//    }


    @Override
    protected void onProjectileHit(Level level, @NotNull BlockState state, BlockHitResult hit, @NotNull Projectile projectile) {
        BlockPos hitPos = hit.getBlockPos();
        BlockState hitState = level.getBlockState(hitPos);
        particleCircle(level, hitPos);
        particleLine(level,hitPos, hitPos);
        if(!level.isClientSide) {
            if(hitState.getBlock() instanceof PeanutStatueBlock) {
                if(projectile.getOwner() != null) {
                    Entity projectileOwner = projectile.getOwner();
                    BlockPos projectileSourcePos = projectile.getOwner().getOnPos();
                    state = stateToLookAtEntityAttacker(projectileOwner, projectileSourcePos, state);
                }
                teleport(state,level,hitPos,hitPos, null,10,10,10);
            }
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(stack.is(ModItems.INHELL_HAVEN_DEVICE.get())) {
            level.removeBlock(pos,false);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onExplosionHit(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Explosion explosion, @NotNull BiConsumer<ItemStack, BlockPos> dropConsumer) {
        particleCircle(level, pos);
        particleLine(level,pos,pos);
        if(!level.isClientSide) {
            teleport(state,level,pos,pos, null,10,10,10);
        }
        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    private void teleport(BlockState state, Level level, BlockPos currentPos, BlockPos destinationPos, Player player, int AABBx, int AABBy, int AABBz) {
        WorldBorder worldborder = level.getWorldBorder();
        int posCheckingAttempts = (AABBx*AABBy*AABBz);
        BlockPos testPos = destinationPos; //initialize variables

        if(!level.isClientSide) {
            for(int i = 0; i < posCheckingAttempts; ++i) {
                testPos = destinationPos.offset(level.random.nextInt(AABBx) - level.random.nextInt(AABBx), level.random.nextInt(AABBy) - level.random.nextInt(AABBy), level.random.nextInt(AABBz) - level.random.nextInt(AABBz));

                boolean candidatePosIsAir = level.getBlockState(testPos).isAir();
                boolean candidatePosAboveIsAir = level.getBlockState(testPos.above()).isAir();
                boolean candidatePosFloorIsSpawnable = level.getBlockState(testPos.below()).isValidSpawn(level,testPos, EntityType.PLAYER);
                boolean blockSpaceIsAir = candidatePosIsAir && candidatePosAboveIsAir;

                if (blockSpaceIsAir && candidatePosFloorIsSpawnable && worldborder.isWithinBounds(testPos)) { //common sided logic: exit for loop and return
                    playPeanutNoises(level, testPos, 1);
                    if(player != null) { //nullable, but if there is a player, make peanut state face player
                        state = stateToLookAtEntityAttacker(player,testPos,state);
                    }

                    //set a new block at testPos as an originBlock. May be facing a player.
                    level.setBlock(testPos,state.setValue(PART, 0),3); //Needs an origin block placed down for constructMultiBlock to work properly
                    MultiBlockPlacementInterface.constructMultiBlock(level,testPos,multiBlockPlacementMatrix, PART); //only bottom half teleports if this is commented out
                    MultiBlockPlacementInterface.removeRemainingMultiBlock(level, this, currentPos, PART, state, multiBlockPlacementMatrix);
                    return; //exit for-loop. teleport complete
                }
            } //end of for-loop
        } //end of serverside logic
        particleLine(level,testPos, currentPos);
    }
//    @Override
//    public boolean isRandomlyTicking(@NotNull BlockState pState) {
//        return true;
//    }
//
//    //issue narros to here
//    @Override
//    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource pRandom) {
//        if(!level.isClientSide) { //therefore is server side
//            playPeanutNoises(level, pos, (float) 1.5);
//
//            List<ServerPlayer> playersOnline = level.players();
//            if(playersOnline.size() > 1) { return;} //If player is alone on server
//            if (!level.isAreaLoaded(pos, 1)) { return;} //if area is loaded for peanut
//            if(state.getValue(PART) == 1) { return;} //both halves should not tick at the same time.
//
//            Player victim = playersOnline.getFirst();
//            BlockPos victimPos = victim.getOnPos();
//            Direction victimDirection = victim.getDirection();
//            Direction whereToSpawnBehindVictim = victimDirection.getOpposite();
//            BlockPos posBehindVictim = victimPos.relative(whereToSpawnBehindVictim, 6);
//            float randomFloat = level.getRandom().nextFloat();
//
//            if(randomFloat < 0.5) {
//                teleport(state, level, pos, posBehindVictim, victim,3,3,3);
//            }
//             else if(randomFloat > 0.5) { //IF NOT ENCASED IN IF-STATEMENT, THE LEVEL SETS THE PEANUTS BACK WHEN THEY SHOULD BE DESTROYED
//                BlockState facingPlayerState = stateToLookAtEntityAttacker(victim,pos,state);
//                level.setBlock(pos,facingPlayerState,3);
//                level.setBlock(pos.above(),facingPlayerState.setValue(PART,1),3);
//            }
//        }
//    }

    private BlockState stateToLookAtEntityAttacker(Entity entity, BlockPos newPos, BlockState state) {
        BlockPos playerPos = entity.getOnPos();
        float pPlayerX = playerPos.getX();
        float pBlockX = newPos.getX();
        float xLength = pPlayerX - pBlockX;

        float pPlayerZ = playerPos.getZ();
        float pBlockZ = newPos.getZ();
        float zLength = pPlayerZ - pBlockZ;

        float degreeAngle = (float) Math.toDegrees(Math.atan2(xLength, zLength));
        //Source: https://minecraft.wiki/w/Rotation
        degreeAngle+= 180;
        degreeAngle*= -1;

        if (Math.abs(degreeAngle) > 180) {
            degreeAngle = -360 * Math.signum(degreeAngle) + degreeAngle;
        }
        //degrees relative to MC's absolute coordinate system, facing towards player from a certain position
        degreeAngle = (float) Math.ceil(degreeAngle);

        state = degreesToDirection(state, degreeAngle);
        return state;
    }

    private BlockState stateFromDegree(float degreeAngle, BlockState state) {
        //degreeAngle+= 180;
        degreeAngle*= -1;

        if (Math.abs(degreeAngle) > 180) {
            degreeAngle = -360 * Math.signum(degreeAngle) + degreeAngle;
        }
        degreeAngle = (float) Math.ceil(degreeAngle);

        state = degreesToDirection(state, degreeAngle);
        return state;
    }

    private static void playPeanutNoises(Level pLevel, BlockPos pPos, float pitch) {
        switch(pLevel.getRandom().nextInt(3)) {
            case 0 -> {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        ModSounds.PEANUT_STATUE_SOUND_1.get(), SoundSource.BLOCKS, 1f, pitch, 0);
            }
            case 1 -> {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        ModSounds.PEANUT_STATUE_SOUND_2.get(), SoundSource.BLOCKS, 1f, pitch, 0);
            }
            default -> {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        ModSounds.PEANUT_STATUE_SOUND_3.get(), SoundSource.BLOCKS, 1f, pitch, 0);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIAGONAL);
    }
}
