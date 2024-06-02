package com.mrbysco.telepastries;

import com.mojang.logging.LogUtils;
import com.mrbysco.telepastries.config.TeleConfig;
import com.mrbysco.telepastries.handler.ExplosionHandler;
import com.mrbysco.telepastries.init.TeleRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class TelePastries {
	public static final Logger LOGGER = LogUtils.getLogger();

	public TelePastries(IEventBus eventBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, TeleConfig.commonSpec);
		eventBus.register(TeleConfig.class);

		eventBus.addListener(this::sendImc);

		TeleRegistry.BLOCKS.register(eventBus);
		TeleRegistry.ITEMS.register(eventBus);
		TeleRegistry.CREATIVE_MODE_TABS.register(eventBus);

		NeoForge.EVENT_BUS.addListener(ExplosionHandler::onExplosion);
	}

	public void sendImc(InterModEnqueueEvent event) {
		if (ModList.get().isLoaded("theoneprobe")) {
			com.mrbysco.telepastries.compat.top.TeleTOPCompat.register();
		}
	}
}
