package net.descroissant.explosivesmod.entity;

import net.descroissant.explosivesmod.ExplosivesMod;
import net.descroissant.explosivesmod.entity.custom.GrenadeProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExplosivesMod.MODID);

    public static final RegistryObject<EntityType<GrenadeProjectileEntity>> GRENADE_PROJECTILE =
            ENTITY_TYPES.register("grenade_projectile", () ->
                    EntityType.Builder.<GrenadeProjectileEntity>of(GrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("grenade_projectile"));




    public static void register(IEventBus eventBus) { ENTITY_TYPES. register(eventBus); }
}
