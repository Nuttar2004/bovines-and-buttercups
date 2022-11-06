package net.merchantpug.bovinesandbuttercups.mixin;

import net.merchantpug.bovinesandbuttercups.content.block.CustomFlowerBlock;
import net.merchantpug.bovinesandbuttercups.content.block.CustomHugeMushroomBlock;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomFlowerBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.CustomMushroomBlock;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomHugeMushroomBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomMushroomBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.particle.ModelLocationParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Final protected RandomSource random;

    @Shadow public abstract double getX();

    @Shadow private EntityDimensions dimensions;

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public Level level;

    @Shadow public abstract Vec3 getDeltaMovement();

    @Shadow public abstract Level getLevel();

    @Inject(method = "spawnSprintParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void bovinesandbuttercups$spawnCustomFlowerSprintParticle(CallbackInfo ci, int i, int j, int k, BlockPos blockPos, BlockState blockState) {
        if ((blockState.getBlock() instanceof CustomFlowerBlock || blockState.getBlock() instanceof CustomMushroomBlock || blockState.getBlock() instanceof CustomHugeMushroomBlock) && blockState.hasBlockEntity()) {
            Vec3 vec3 = this.getDeltaMovement();
            if (this.getLevel().getBlockEntity(blockPos) instanceof CustomFlowerBlockEntity customFlowerBlockEntity) {
                this.level.addParticle(new ModelLocationParticleOption(customFlowerBlockEntity.getFlowerType().flowerModel().location(), customFlowerBlockEntity.getFlowerType().flowerModel().variant()), this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
            } else if (this.getLevel().getBlockEntity(blockPos) instanceof CustomMushroomBlockEntity customMushroomBlockEntity) {
                this.level.addParticle(new ModelLocationParticleOption(customMushroomBlockEntity.getMushroomType().mushroomModel().location(), customMushroomBlockEntity.getMushroomType().mushroomModel().variant()), this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
            } else if (this.getLevel().getBlockEntity(blockPos) instanceof CustomHugeMushroomBlockEntity customHugeMushroomBlockEntity) {
                this.level.addParticle(new ModelLocationParticleOption(customHugeMushroomBlockEntity.getMushroomType().hugeModel().location(), customHugeMushroomBlockEntity.getMushroomType().hugeModel().variant()), this.getX() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double)this.dimensions.width, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
            }
        }
    }
}
