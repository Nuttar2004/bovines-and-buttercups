package net.merchantpug.bovinesandbuttercups.mixin.forge;

import net.merchantpug.bovinesandbuttercups.api.BovineRegistryUtil;
import net.merchantpug.bovinesandbuttercups.platform.Services;
import net.merchantpug.bovinesandbuttercups.registry.BovineItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin {

    @Inject(method = "shearInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", ordinal = 0, shift = At.Shift.AFTER), cancellable = true)
    private void bovinesandbuttercups$cancelItemDroppingIfUnnecessary(SoundSource source, CallbackInfoReturnable<List<ItemStack>> cir) {
        MushroomCow cow = (MushroomCow)(Object)this;
        if (Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().blockState().isEmpty() && Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().mushroomType().isEmpty()) {
            cir.setReturnValue(List.of(ItemStack.EMPTY));
        }
    }

    @Inject(method = "shearInternal", at = @At(value = "RETURN", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"), remap = false, cancellable = true)
    private void bovinesandbuttercups$modifyShearItem(SoundSource source, CallbackInfoReturnable<List<ItemStack>> cir) {
        List<ItemStack> list = new ArrayList<>();
        MushroomCow cow = (MushroomCow)(Object)this;
        if (Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().blockState().isPresent()) {
            for(int i = 0; i < 5; ++i) {
                list.add(new ItemStack(Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().blockState().get().getBlock()));
            }
            cir.setReturnValue(list);
        } else if (Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().getMushroomType().isPresent()) {
            ItemStack itemStack = new ItemStack(BovineItems.CUSTOM_MUSHROOM.get());
            CompoundTag compound = new CompoundTag();
            compound.putString("Type", BovineRegistryUtil.getMushroomTypeKey(Services.COMPONENT.getMushroomCowTypeFromCow(cow).configuration().getMushroom().getMushroomType().get()).toString());
            itemStack.getOrCreateTag().put("BlockEntityTag", compound);
            for(int i = 0; i < 5; ++i) {
                list.add(itemStack);
            }
            cir.setReturnValue(list);
        }
    }
}