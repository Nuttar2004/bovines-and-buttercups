package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.block.entity.*;
import net.merchantpug.bovinesandbuttercups.component.BovineEntityComponents;
import net.merchantpug.bovinesandbuttercups.data.block.FlowerType;
import net.merchantpug.bovinesandbuttercups.data.block.MushroomType;
import net.merchantpug.bovinesandbuttercups.data.entity.MushroomCowConfiguration;
import net.merchantpug.bovinesandbuttercups.entity.FlowerCow;
import net.merchantpug.bovinesandbuttercups.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.platform.services.IPlatformHelper;
import net.merchantpug.bovinesandbuttercups.registry.*;
import com.google.auto.service.AutoService;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.mixin.object.builder.CriteriaAccessor;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import java.util.Optional;
import java.util.function.Supplier;

@AutoService(IPlatformHelper.class)
public class QuiltPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Quilt";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return QuiltLoader.isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return QuiltLoader.isDevelopmentEnvironment();
    }


    @Override
    public <T extends Mob> SpawnEggItem createSpawnEggItem(Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, properties);
    }

    @Override
    public void setRenderLayer(Block block, RenderType renderType) {
        BlockRenderLayerMap.put(renderType, block);
    }

    @Override
    public CriterionTrigger<?> registerCriteria(CriterionTrigger<?> criterionTrigger) {
        return CriteriaAccessor.callRegister(criterionTrigger);
    }

    @Override
    public ResourceKey<Registry<ConfiguredCowType<?, ?>>> getConfiguredCowTypeResourceKey() {
        return ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("bovinesandbuttercups/configured_cow_type"));
    }

    @Override
    public Codec<ConfiguredCowType<?, ?>> getConfiguredCowTypeByNameCodec() {
        return BovineRegistriesFabriclike.CONFIGURED_COW_TYPE.byNameCodec();
    }

    @Override
    public ResourceKey<Registry<FlowerType>> getFlowerTypeResourceKey() {
        return ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("bovinesandbuttercups/flower_type"));
    }

    @Override
    public Codec<FlowerType> getFlowerTypeByNameCodec() {
        return BovineRegistriesFabriclike.FLOWER_TYPE.byNameCodec();
    }

    @Override
    public ResourceKey<Registry<MushroomType>> getMushroomTypeResourceKey() {
        return ResourceKey.createRegistryKey(BovinesAndButtercups.asResource("bovinesandbuttercups/mushroom_type"));
    }

    @Override
    public Codec<MushroomType> getMushroomTypeByNameCodec() {
        return BovineRegistriesFabriclike.MUSHROOM_TYPE.byNameCodec();
    }

    @Override
    public Codec<CowType<?>> getCowTypeCodec() {
        return BovineRegistriesFabriclike.COW_TYPE.byNameCodec();
    }

    @Override
    public ConfiguredCowType<MushroomCowConfiguration, ?> getMushroomCowTypeFromCow(MushroomCow cow) {
        return BovineEntityComponents.MUSHROOM_COW_TYPE_COMPONENT.get(cow).getMushroomCowType();
    }

    @Override
    public ResourceLocation getMushroomCowTypeKeyFromCow(MushroomCow cow) {
        return BovineEntityComponents.MUSHROOM_COW_TYPE_COMPONENT.get(cow).getMushroomCowTypeKey();
    }

    @Override
    public Optional<ResourceLocation> getPreviousMushroomCowTypeKeyFromCow(MushroomCow cow) {
        return Optional.ofNullable(BovineEntityComponents.MUSHROOM_COW_TYPE_COMPONENT.get(cow).getPreviousMushroomCowTypeKey());
    }

    @Override
    public void setMushroomCowType(MushroomCow cow, ResourceLocation key) {
        BovineEntityComponents.MUSHROOM_COW_TYPE_COMPONENT.get(cow).setMushroomCowType(key);
    }

    @Override
    public void setPreviousMushroomCowType(MushroomCow cow, ResourceLocation key) {
        BovineEntityComponents.MUSHROOM_COW_TYPE_COMPONENT.get(cow).setPreviousMushroomCowTypeKey(key);
    }

    @Override
    public BlockEntityType<CustomFlowerBlockEntity> getCustomFlowerBlockEntity() {
        return BovineBlockEntityTypesFabriclike.CUSTOM_FLOWER;
    }

    @Override
    public BlockEntityType<CustomMushroomBlockEntity> getCustomMushroomBlockEntity() {
        return BovineBlockEntityTypesFabriclike.CUSTOM_MUSHROOM;
    }

    @Override
    public BlockEntityType<CustomHugeMushroomBlockEntity> getCustomHugeMushroomBlockEntity() {
        return BovineBlockEntityTypesFabriclike.CUSTOM_MUSHROOM_BLOCK;
    }

    @Override
    public BlockEntityType<CustomFlowerPotBlockEntity> getCustomFlowerPotBlockEntity() {
        return BovineBlockEntityTypesFabriclike.POTTED_CUSTOM_FLOWER;
    }

    @Override
    public BlockEntityType<CustomMushroomPotBlockEntity> getCustomMushroomPotBlockEntity() {
        return BovineBlockEntityTypesFabriclike.POTTED_CUSTOM_MUSHROOM;
    }

    @Override
    public EntityType<FlowerCow> getMoobloomEntity() {
        return BovineEntityTypesFabriclike.MOOBLOOM;
    }

    @Override
    public CustomFlowerItem getCustomFlowerItem() {
        return BovineItemsFabriclike.CUSTOM_FLOWER;
    }

    @Override
    public CustomMushroomItem getCustomMushroomItem() {
        return BovineItemsFabriclike.CUSTOM_MUSHROOM;
    }

    @Override
    public CustomHugeMushroomItem getCustomHugeMushroomItem() {
        return BovineItemsFabriclike.CUSTOM_MUSHROOM_BLOCK;
    }
}