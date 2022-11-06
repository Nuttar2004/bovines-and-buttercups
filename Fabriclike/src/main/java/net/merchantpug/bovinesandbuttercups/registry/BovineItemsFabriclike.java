package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class BovineItemsFabriclike {
    public static final CustomFlowerItem CUSTOM_FLOWER = register(BovinesAndButtercups.asResource("custom_flower"), new CustomFlowerItem(BovineBlocks.CUSTOM_FLOWER.get(), new Item.Properties()));
    public static final CustomMushroomItem CUSTOM_MUSHROOM = register(BovinesAndButtercups.asResource("custom_mushroom"), new CustomMushroomItem(BovineBlocks.CUSTOM_MUSHROOM.get(), new Item.Properties()));
    public static final CustomHugeMushroomItem CUSTOM_MUSHROOM_BLOCK = register(BovinesAndButtercups.asResource("custom_mushroom_block"), new CustomHugeMushroomItem(BovineBlocks.CUSTOM_MUSHROOM_BLOCK.get(), new Item.Properties()));

    public static void init() {

    }

    public static <T extends Item> T register(ResourceLocation key, T item) {
        return Registry.register(Registry.ITEM, key, item);
    }
}
