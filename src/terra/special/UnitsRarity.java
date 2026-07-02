package terra.special;

import arc.Core;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import terra.content.TerraStatusEffects;

public class UnitsRarity {
    private static final StatusEffect rarity0 = TerraStatusEffects.common;
    private static final StatusEffect rarity1 = TerraStatusEffects.uncommon;
    private static final StatusEffect rarity2 = TerraStatusEffects.rare;
    private static final StatusEffect rarity3 = TerraStatusEffects.epic;
    private static final StatusEffect rarity4 = TerraStatusEffects.legendary;
    private static final Float Chance4 = 1f / 100, Chance3 = 4f / 100 + Chance4, Chance2 = 12f / 100 + Chance3, Chance1 = 36f / 100 + Chance2;

    public static void init() {
        Vars.events.on(EventType.UnitCreateEvent.class, event -> {
            if (!Core.settings.getBool("unitsquality", false)) return;

            Unit unit = event.unit;
            if (unit == null) return;

            float rnd = Mathf.random();
            StatusEffect chosen = null;

            if (rnd < Chance4) {
                chosen = rarity4;
            } else if (rnd < Chance3) {
                chosen = rarity3;
            } else if (rnd < Chance2) {
                chosen = rarity2;
            } else if (rnd < Chance1) {
                chosen = rarity1;
            } else {
                chosen = rarity0;
            }

            if (chosen != null) {
                unit.apply(chosen, 60);
            }
        });
    }
}
