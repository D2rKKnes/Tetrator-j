package terra.world.blocks;

import arc.Core;
import arc.graphics.g2d.*;
import arc.util.Eachable;
import mindustry.annotations.Annotations.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.distribution.Conveyor;

public class CappedConveyor extends Conveyor {
    public TextureRegion[] topRegions = new TextureRegion[5];
    public TextureRegion capRegion;

    public CappedConveyor(String name) {
        super(name);
        rotate = true;
        update = true;
        group = BlockGroup.transportation;
        hasItems = true;
        itemCapacity = capacity;
        priority = TargetPriority.transport;
        conveyorPlacement = true;
        underBullets = true;

        ambientSound = Sounds.loopConveyor;
        ambientSoundVolume = 0.0022f;
        unloadable = false;
        noUpdateDisabled = false;
    }

    @Override
    public void load() {
        super.load();
        for (int i = 0; i < 5; i++) {
            this.topRegions[i] = Core.atlas.find(this.name + "-top-" + i);
        }
        this.capRegion = Core.atlas.find(this.name + "-cap");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{this.regions[0][0], this.topRegions[0]};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        int[] bits = this.getTiling(plan, list);
        if (bits == null) return;
        
        int index = bits[0];
        float sx = bits[1];
        float sy = bits[2];
        float r = plan.rotation * 90f;
        
        TextureRegion reg = this.regions[index][0];
        TextureRegion top = this.topRegions[index];
        
        Draw.rect(reg, plan.drawx(), plan.drawy(), reg.width * sx * Draw.scl, reg.height * sy * Draw.scl, r);
        if (top.found()) {
            Draw.rect(top, plan.drawx(), plan.drawy(), top.width * sx * Draw.scl, top.height * sy * Draw.scl, r);
        }
    }

    public class CappedConveyorBuild extends ConveyorBuild {
        public boolean capped = false;
        public boolean backCapped = false;

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            Building next = this.front();
            Building prev = this.back();
            
            this.capped = next == null || !next.block.hasItems;
            this.backCapped = (this.blendbits == 0) && (prev == null || !prev.block.hasItems);
        }

        @Override
        public void draw() {
            super.draw();
            
            int r = this.rotation;
            int b = this.blendbits;
            float sx = this.blendsclx;
            float sy = this.blendscly;
            
            float[] dx = {1f, 0f, -1f, 0f};
            float[] dy = {0f, 1f, 0f, -1f};

            for (int i = 0; i < 4; i++) {
                if ((this.blending & (1 << i)) != 0) {
                    int dir = (r - i + 4) % 4;
                    float rot = (i == 0) ? r * 90f : dir * 90f;
                    
                    Draw.z(Layer.blockUnder);
                    if (topRegions[0].found()) {
                        Draw.rect(topRegions[0], this.x + dx[dir] * 8f, this.y + dy[dir] * 8f, rot);
                    }
                }
            }

            Draw.z(Layer.block);
            TextureRegion top = topRegions[b];
            if (top != null && top.found()) {
                Draw.rect(top, this.x, this.y, top.width * sx * Draw.scl, top.height * sy * Draw.scl, r * 90f);
            }

            if (this.capped && capRegion.found()) {
                Draw.rect(capRegion, this.x, this.y, r * 90f);
            }
            if (this.backCapped && capRegion.found()) {
                Draw.rect(capRegion, this.x, this.y, r * 90f + 180f);
            }
        }
    }
}
