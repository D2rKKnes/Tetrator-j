package terra.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.Scl;
import mindustry.graphics.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.StatusEffect;

public class RarityStatusEffect extends StatusEffect {

    public Color tintColor = Color.white;
    public Float alpha = 0.4f;
    public boolean drawAura = true;

    public RarityStatusEffect(String name) {
        super(name);
        if (!Core.settings.getBool("unitsquality", true)){hideDatabase = true;}
        else hideDatabase = false;
    }

    @Override
    public void draw(Unit unit, float time) {
        if (!Core.settings.getBool("qualityring", true) || !drawAura) return;

        float unitLayer = unit.type().flying ? Layer.flyingUnit : Layer.groundUnit;
        Draw.z(unitLayer - 2);
        Draw.color(tintColor, alpha);
        float size = unit.hitSize * 1.15f;
        Draw.rect(unit.type.shadowRegion, unit.x, unit.y, size, size, unit.rotation);
        Draw.reset();
    }
}
