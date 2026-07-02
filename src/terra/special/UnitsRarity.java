package terra.special;

import arc.*;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.StatusEffect;
import terra.content.TerraStatusEffects;

public class UnitsRarity {
    private static final StatusEffect rarity0 = TerraStatusEffects.common;
    private static final StatusEffect rarity1 = TerraStatusEffects.uncommon;
    private static final StatusEffect rarity2 = TerraStatusEffects.rare;
    private static final StatusEffect rarity3 = TerraStatusEffects.epic;
    private static final StatusEffect rarity4 = TerraStatusEffects.legendary;
    private static final Float Chance4 = 1f / 100, Chance3 = 4f / 100 + Chance4, Chance2 = 12f / 100 + Chance3, Chance1 = 36f / 100 + Chance2;

    private static void applyRandomEffect(Unit unit) {
        if (!Core.settings.getBool("unitsquality", false)) return;
        if (unit == null) return;

        float rnd = Mathf.random();
        StatusEffect chosen = null;

        if (rnd < chance4) {
            chosen = rarity4;
        } else if (rnd < chance3) {
            chosen = rarity3;
        } else if (rnd < chance2) {
            chosen = rarity2;
        } else if (rnd < chance1) {
            chosen = rarity1;
        } else {
            chosen = rarity0;
        }

        if (chosen != null) {
            unit.apply(chosen, DURATION);
        }
    }
    
    public static void init() {
        Events.on(EventType.UnitCreateEvent.class, event -> {
            applyRandomEffect(event.unit);
        });

        Events.on(EventType.UnitSpawnEvent.class, event -> {
            applyRandomEffect(event.unit);
        });
    }
}
