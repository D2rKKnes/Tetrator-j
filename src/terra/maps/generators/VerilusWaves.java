package terra.maps.generators;

import arc.func.Intc;
import arc.math.*;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.content.*;
import mindustry.game.SpawnGroup;
import mindustry.type.UnitType;
import terra.content.*;

import static terra.content.TerraUnitTypes.*;
import static mindustry.content.UnitTypes.*;

public class VerilusWaves {
    public static UnitType pick(UnitType[][] species, int tier, Rand rand){
        return rand.select(species[tier]);
    }
    public static Seq<SpawnGroup> generate(Rand rand) {
        UnitType[][] species = {
            {flare, wick}, //index 0
            {horizon, poly, dynamite}, //index 1
            {poly, poly, dynamite}, //special, index 2
            {zenith, mega, incident}, //index 3
            {mega, mega, incident}, //special, index 4
            {antumbra, quad, catastrophe}, //index 5
            {quad, quad, catastrophe}, //special, index 6
            {eclipse, oct, inevitability}, //index 7
            {oct, oct, inevitability}, //special, index 8
            {eclipse, inevitability} //boss only, index 9
        };
        UnitType[][] fspec = species;

        Seq<SpawnGroup> out = new Seq<>();

        out.add(new SpawnGroup(UnitTypes.flare){{
            begin = -2;
            end = 10;
            unitScaling = 2.6f;
            max = 13;
            shieldScaling = 35f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 1;
            end = 7;
            unitScaling = 0.6f;
            spacing = 3;
            max = 10;
            shieldScaling = 42f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 3;
            end = 5;
            unitScaling = 1f;
            spacing = 2;
            max = 2;
            shieldScaling = 115f;
            shields = 185f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 4;
            end = 17;
            unitScaling = 2.2f;
            spacing = 2;
            max = 10;
            shieldScaling = 31f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 7;
            end = 11;
            unitScaling = 0.7f;
            max = 10;
            shieldScaling = 39f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 8;
            end = 20;
            unitScaling = 0.25f;
            unitAmount = 2;
            spacing = 2;
            max = 10;
            shieldScaling = 56f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 9;
            end = 17;
            unitScaling = 0.25f;
            spacing = 4;
            max = 6;
            shieldScaling = 78f;
            effect = StatusEffects.overdrive;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 11;
            end = 47;
            unitScaling = 3.5f;
            unitAmount = 3;
            max = 15;
            shieldScaling = 48f;
            shields = 218f;
            effect = StatusEffects.overdrive;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 13;
            end = 18;
            unitScaling = 0.3f;
            unitAmount = 2;
            spacing = 5;
            max = 5;
            shieldScaling = 48f;
            shields = 115f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 13;
            end = 18;
            unitScaling = 0.5f;
            unitAmount = 3;
            spacing = 5;
            max = 5;
            shieldScaling = 48f;
            shields = 115f;
        }});
        out.add(new SpawnGroup(UnitTypes.zenith){{
            begin = 13;
            end = 18;
            unitScaling = 1f;
            spacing = 5;
            max = 10;
            shieldScaling = 300f;
            shields = 750f;
            effect = StatusEffects.boss;
        }});
        out.add(new SpawnGroup(UnitTypes.mega){{
            begin = 13;
            end = 18;
            unitScaling = 1f;
            spacing = 5;
            max = 10;
            shieldScaling = 300f;
            shields = 750f;
            effect = StatusEffects.boss;
        }});
        out.add(new SpawnGroup(TerraUnitTypes.incident){{
            begin = 13;
            end = 18;
            unitScaling = 1f;
            spacing = 5;
            max = 10;
            shieldScaling = 300f;
            shields = 750f;
            effect = StatusEffects.boss;
        }});

        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 15;
            end = 21;
            unitScaling = 1f;
            unitAmount = 7;
            spacing = 2;
            max = 15;
            shieldScaling = 68f;
        }});
        out.add(new SpawnGroup(pick(fspec, 3, rand)){{
            begin = 15;
            end = 51;
            unitScaling = 2.8f;
            unitAmount = 7;
            spacing = 3;
            max = 10;
            shieldScaling = 52f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 16;
            end = 26;
            unitScaling = 0.7f;
            unitAmount = 4;
            spacing = 3;
            shieldScaling = 22f;
            shields = 500f;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 19;
            unitScaling = 5.8f;
            unitAmount = 3;
            max = 15;
            shieldScaling = 42f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 22;
            unitScaling = 4.5f;
            spacing = 2;
            max = 5;
            shieldScaling = 54f;
        }});
        out.add(new SpawnGroup(pick(fspec, 3, rand)){{
            begin = 22;
            unitScaling = 6.6f;
            max = 8;
            shieldScaling = 38f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 25;
            spacing = 3;
            max = 22;
            unitScaling = 3.3f;
            shieldScaling = 56f;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 26;
            spacing = 3;
            max = 21;
            unitScaling = 3.7f;
            shieldScaling = 52f;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 27;
            spacing = 3;
            max = 20;
            unitScaling = 4.2f;
            shieldScaling = 48f;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 3, rand)){{
            begin = 30;
            max = 22;
            unitScaling = 3.6f;
            shields = 500f;
            shieldScaling = 56f;
            unitAmount = 3;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 31;
            end = 51;
            spacing = 20;
            unitScaling = 1f;
            shields = 750f;
            shieldScaling = 212f;
            effect = StatusEffects.boss;
        }});
        
        out.add(new SpawnGroup(pick(fspec, 4, rand)){{
            begin = 31;
            end = 51;
            spacing = 2;
            max = 10;
            unitScaling = 1.5f;
            shields = 1500f;
            shieldScaling = 26f;
            unitAmount = 4;
        }});
        out.add(new SpawnGroup(pick(fspec, 2, rand)){{
            begin = 34;
            max = 15;
            unitScaling = 5.5f;
            shields = 500f;
            shieldScaling = 100f;
            unitAmount = 5;
        }});
        out.add(new SpawnGroup(pick(fspec, 2, rand)){{
            begin = 34;
            spacing = 2;
            max = 5;
            unitScaling = 4.2f;
            shields = 500f;
            shieldScaling = 100f;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 35;
            spacing = 2;
            max = 10;
            unitScaling = 6.6f;
            shields = 256f;
            shieldScaling = 32f;
            unitAmount = 2;
        }});
        out.add(new SpawnGroup(pick(fspec, 1, rand)){{
            begin = 36;
            spacing = 2;
            max = 8;
            unitScaling = 7.7f;
            shields = 256f;
            shieldScaling = 32f;
            unitAmount = 2;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 38;
            spacing = 3;
            max = 8;
            unitScaling = 4.5f;
            shields = 500f;
            shieldScaling = 48f;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 42;
            spacing = 3;
            max = 7;
            unitScaling = 4.2f;
            shields = 500f;
            shieldScaling = 48f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 48;
            max = 15;
            unitScaling = 10f;
            shields = 500f;
            shieldScaling = 50f;
            unitAmount = 5;
            effect = StatusEffects.fast;
            items = new ItemStack(Items.blastCompound, 100);
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 49;
            spacing = 3;
            max = 6;
            unitScaling = 3.8f;
            shields = 500f;
            shieldScaling = 48f;
            unitAmount = 2;
        }});
        out.add(new SpawnGroup(pick(fspec, 6, rand)){{
            begin = 51;
            end = 51;
            unitScaling = 1f;
            shields = 4000f;
            shieldScaling = 212f;
            unitAmount = 2;
            effect = StatusEffects.boss;
        }});
        
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 57;
            spacing = 2;
            max = 4;
            unitScaling = 11.1f;
            shieldScaling = 74f;
        }});
        out.add(new SpawnGroup(pick(fspec, 3, rand)){{
            begin = 61;
            spacing = 3;
            max = 10;
            unitScaling = 5.5f;
            shieldScaling = 58f;
            unitAmount = 2;
        }});
        out.add(new SpawnGroup(pick(fspec, 3, rand)){{
            begin = 66;
            spacing = 3;
            max = 10;
            unitScaling = 5.5f;
            shieldScaling = 58f;
            unitAmount = 3;
        }});
        out.add(new SpawnGroup(pick(fspec, 2, rand)){{
            begin = 71;
            end = 81;
            unitScaling = 1.8f;
            shieldScaling = 99f;
            unitAmount = 8;
            effect = StatusEffects.shielded;
        }});
        out.add(new SpawnGroup(pick(fspec, 6, rand)){{
            begin = 72;
            end = 81;
            unitScaling = 3.8f;
            shieldScaling = 45f;
            unitAmount = 3;
        }});
        out.add(new SpawnGroup(pick(fspec, 4, rand)){{
            begin = 75;
            spacing = 3;
            max = 12;
            unitScaling = 2.6f;
            shieldScaling = 89f;
            unitAmount = 4;
        }});
        out.add(new SpawnGroup(pick(fspec, 4, rand)){{
            begin = 78;
            spacing = 2;
            max = 12;
            unitScaling = 3.8f;
            shieldScaling = 89f;
            unitAmount = 5;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 81;
            max = 3;
            unitScaling = 6f;
            shieldScaling = 58f;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 82;
            spacing = 2;
            max = 4;
            unitScaling = 5.5f;
            shieldScaling = 62f;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 83;
            spacing = 3;
            max = 5;
            unitScaling = 5f;
            shieldScaling = 66f;
        }});
        out.add(new SpawnGroup(pick(fspec, 0, rand)){{
            begin = 86;
            max = 28;
            unitScaling = 7.1f;
            shields = 2000f;
            shieldScaling = 25f;
            unitAmount = 14;
            effect = StatusEffects.fast;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 86;
            spacing = 3;
            max = 6;
            unitScaling = 7.1f;
            shields = 5000f;
            shieldScaling = 100f;
            unitAmount = 2;
            effect = StatusEffects.overdrive;
        }});
        out.add(new SpawnGroup(pick(fspec, 5, rand)){{
            begin = 98;
            spacing = 40;
            max = 12;
            unitScaling = 1.4f;
            shields = 5000f;
            shieldScaling = 100f;
            unitAmount = 3;
        }});
        out.add(new SpawnGroup(pick(fspec, 6, rand)){{
            begin = 98;
            spacing = 40;
            max = 12;
            unitScaling = 1.4f;
            shields = 5000f;
            shieldScaling = 100f;
            unitAmount = 3;
        }});
        out.add(new SpawnGroup(pick(fspec, 9, rand)){{
            begin = 98;
            spacing = 40;
            max = 6;
            unitScaling = 2.7f;
            shields = 50000f;
            shieldScaling = 72f;
            effect = StatusEffects.boss;
        }});
        out.add(new SpawnGroup(pick(fspec, 8, rand)){{
            begin = 98;
            spacing = 40;
            max = 6;
            unitScaling = 2.7f;
            shields = 50000f;
            shieldScaling = 72f;
            effect = StatusEffects.boss;
        }});
        
        out.add(new SpawnGroup(pick(fspec, 7, rand)){{
            begin = 117;
            spacing = 7;
            max = 3;
            unitScaling = 9.1f;
            shieldScaling = 66f;
        }});
        out.add(new SpawnGroup(pick(fspec, 7, rand)){{
            begin = 130;
            spacing = 5;
            max = 4;
            unitScaling = 11.1f;
            shieldScaling = 72f;
        }});
        out.add(new SpawnGroup(pick(fspec, 7, rand)){{
            begin = 177;
            spacing = 3;
            max = 5;
            unitScaling = 14.3f;
            shieldScaling = 83f;
        }});
        out.add(new SpawnGroup(TerraUnitTypes.eternity){{
            begin = 198;
            spacing = 18;
            max = 4;
            unitScaling = 5f;
            shieldScaling = 10000f / 18;
            effect = StatusEffects.boss;
        }});

        return out;
    }
}
