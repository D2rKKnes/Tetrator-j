package terra.type;

import arc.graphics.Color;
import arc.scene.ui.layout.Scl;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.StatusEffect;

public class RarityStatusEffect extends StatusEffect {

    public Color ringColor = color;
    public boolean drawRing = true;

    public RarityStatusEffect(String name) {
        super(name);
    }

    @Override
    public void draw(Unit unit, float time) {
        if (!Core.settings.getBool("qualityring", true) || !drawRing) return;

        Draw.z(52f);
        Draw.color(ringColor);
        float size = unit.hitSize * 2f;
        Draw.rect(Core.atlas.find("terra-rarity-ring"), unit.x, unit.y, size, size);
        Draw.reset();
    }
}
