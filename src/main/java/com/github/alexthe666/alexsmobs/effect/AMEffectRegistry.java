package com.github.alexthe666.alexsmobs.effect;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.*;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = AlexsMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AMEffectRegistry {
    public static final Effect KNOCKBACK_RESISTANCE = new EffectKnockbackResistance();
    public static final Effect LAVA_VISION = new EffectLavaVision();
    public static final Effect SUNBIRD_BLESSING = new EffectSunbird(false);
    public static final Effect SUNBIRD_CURSE = new EffectSunbird(true);
    public static final Effect POISON_RESISTANCE = new EffectPoisonResistance();
    public static final Potion KNOCKBACK_RESISTANCE_POTION = new Potion(new EffectInstance(KNOCKBACK_RESISTANCE, 3600)).setRegistryName("alexsmobs:knockback_resistance");
    public static final Potion LONG_KNOCKBACK_RESISTANCE_POTION = new Potion(new EffectInstance(KNOCKBACK_RESISTANCE, 9600)).setRegistryName("alexsmobs:long_knockback_resistance");
    public static final Potion STRONG_KNOCKBACK_RESISTANCE_POTION = new Potion(new EffectInstance(KNOCKBACK_RESISTANCE, 1800, 1)).setRegistryName("alexsmobs:strong_knockback_resistance");
    public static final Potion LAVA_VISION_POTION = new Potion(new EffectInstance(LAVA_VISION, 3600)).setRegistryName("alexsmobs:lava_vision");
    public static final Potion LONG_LAVA_VISION_POTION = new Potion(new EffectInstance(LAVA_VISION, 9600)).setRegistryName("alexsmobs:long_lava_vision");
    public static final Potion SPEED_III_POTION = new Potion(new EffectInstance(Effects.SPEED, 2200, 2)).setRegistryName("alexsmobs:speed_iii");
    public static final Potion POISON_RESISTANCE_POTION = new Potion(new EffectInstance(POISON_RESISTANCE, 3600)).setRegistryName("alexsmobs:poison_resistance");
    public static final Potion LONG_POISON_RESISTANCE_POTION = new Potion(new EffectInstance(POISON_RESISTANCE, 9600)).setRegistryName("alexsmobs:long_poison_resistance");

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
        try {
            for (Field f : AMEffectRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Effect) {
                    event.getRegistry().register((Effect) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        try {
            for (Field f : AMEffectRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Potion) {
                    event.getRegistry().register((Potion) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        AMEffectRegistry.onInitItems();
    }

    public static ItemStack createPotion(Potion potion){
        return  PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion);
    }

    public static void onInitItems(){
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(createPotion(Potions.STRENGTH)), Ingredient.fromItems(AMItemRegistry.BEAR_FUR), createPotion(KNOCKBACK_RESISTANCE_POTION));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.fromStacks(createPotion(KNOCKBACK_RESISTANCE_POTION)), Ingredient.fromItems(Items.REDSTONE), createPotion(LONG_KNOCKBACK_RESISTANCE_POTION)));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.fromStacks(createPotion(KNOCKBACK_RESISTANCE_POTION)), Ingredient.fromItems(Items.GLOWSTONE_DUST), createPotion(STRONG_KNOCKBACK_RESISTANCE_POTION)));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(AMItemRegistry.LAVA_BOTTLE), Ingredient.fromItems(AMItemRegistry.BONE_SERPENT_TOOTH), createPotion(LAVA_VISION_POTION));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.fromStacks(createPotion(LAVA_VISION_POTION)), Ingredient.fromItems(Items.REDSTONE), createPotion(LONG_LAVA_VISION_POTION)));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(AMItemRegistry.KOMODO_SPIT_BOTTLE), Ingredient.fromItems(AMItemRegistry.CENTIPEDE_LEG), new ItemStack(AMItemRegistry.POISON_BOTTLE));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(AMItemRegistry.POISON_BOTTLE), Ingredient.fromItems(AMItemRegistry.RATTLESNAKE_RATTLE), createPotion(POISON_RESISTANCE_POTION));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.fromStacks(createPotion(POISON_RESISTANCE_POTION)), Ingredient.fromItems(Items.REDSTONE), createPotion(LONG_POISON_RESISTANCE_POTION)));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.fromStacks(createPotion(Potions.STRONG_SWIFTNESS)), Ingredient.fromItems(AMItemRegistry.GAZELLE_HORN), createPotion(SPEED_III_POTION)));

    }
}
