package terra.type.ability;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.entities.abilities.*;

import static mindustry.Vars.*;

public class SappingFieldAbility extends Ability{
    public StatusEffect effect;
    public float duration = 60, reload = 100, range = 20;
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;
    public Color color = Pal.accent;

    protected float timer;

    SappingFieldAbility(){}

    public SappingFieldAbility(StatusEffect effect, float duration, float reload, float range){
        this.duration = duration;
        this.reload = reload;
        this.range = range;
        this.effect = effect;
    }
    public SappingFieldAbility(StatusEffect effect, float duration, float reload, float range, Color color, Effect activeEffect){
        this.duration = duration;
        this.reload = reload;
        this.range = range;
        this.effect = effect;
        this.color = color;
        this.activeEffect = activeEffect;
    }

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.add(Core.bundle.format("bullet.range", Strings.autoFixed(range / tilesize, 2)));
        t.row();
        t.add(abilityStat("firingrate", Strings.autoFixed(60f / reload, 2)));
        t.row();
        t.add((effect.hasEmoji() ? effect.emoji() : "") + "[stat]" + effect.localizedName);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload && (!onShoot || unit.isShooting)){
            Units.nearbyEnemies(unit.team, unit.x, unit.y, range, other -> {
                other.apply(effect, duration);
                applyEffect.at(other, parentizeEffects);
                float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
                activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, color, parentizeEffects ? unit : null);
            });

            timer = 0f;
        }
    }
}
