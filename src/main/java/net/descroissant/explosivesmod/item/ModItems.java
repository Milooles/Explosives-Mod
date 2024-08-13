package net.descroissant.explosivesmod.item;

import net.descroissant.explosivesmod.ExplosivesMod;
import net.descroissant.explosivesmod.item.custom.GrenadeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExplosivesMod.MODID);

    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade",
            () -> new GrenadeItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
