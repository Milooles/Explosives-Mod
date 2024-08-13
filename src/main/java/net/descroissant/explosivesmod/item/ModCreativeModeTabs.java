package net.descroissant.explosivesmod.item;

import net.descroissant.explosivesmod.ExplosivesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExplosivesMod.MODID);

    public static final RegistryObject<CreativeModeTab> EXPLOSIVES_TAB =
            CREATIVE_MODE_TABS.register("explosives", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GRENADE.get()))
                    .title(Component.translatable("creativetab.explosives_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GRENADE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
