package terra.content;

import arc.Core;

import arc.struct.*;
import mindustry.content.*;
import mindustry.game.Objectives;
import mindustry.type.*;

import static terra.content.TerraSectorPresets.*;
import static terra.content.TerraUnitTypes.*;
import static terra.content.TerraBlocks.*;
import static terra.content.TerraItems.*;
import static terra.content.TerraLiquids.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.UnitTypes.*;

public class TerraVerilusTree {
    public static void load() {
        Planets.verilus.techTree = nodeRoot("verilus", coreSolaris, () -> {
            node(graphiteConveyor, () -> {
                node(graphiteJunction, () -> {
                    node(graphiteRouter, () -> {
                        node(container, () -> {
                            node(vault, () -> {});
                            node(unloader, () -> {});
                        });
                    });
                    node(graphiteGate, () -> {});
                });
                node(pulseConduit, () -> {
                    node(liquidJunction, () -> {
                        node(liquidRouter, () -> {
                            node(liquidContainer, () -> {
                                node(liquidTank, () -> {});
                            });
                        });
                    });
                    node(bridgeConduit, () -> {
                        node(phaseConduit, () -> {});
                    });
                });
                node(graphiteBridge, () -> {
                    node(smallDriver, () -> {
                        node(massDriver, () -> {});
                    });
                    node(phaseConveyor, () -> {});
                });
            });
            node(graphiteMiner, () -> {
                node(pulseDrill, () -> {
                    node(plasmaDrill, () -> {
                        node(beamMiningFacility, () -> {});
                    });
                    node(sandExtractor, () -> {
                        node(iceMelter, () -> {
                            node(cryofluidMixer, () -> {
                                node(multiMixer, () -> {});
                            });
                        });
                        node(powerNode, () -> {
                            node(powerNodeLarge, () -> {
                                node(multicellBattery, () -> {
                                    node(batteryLarge, () -> {});
                                });
                                node(forceProjector, () -> {});
                            });
                            node(solarPanel, () -> {
                                node(largeSolarPanel, () -> {});
                                node(photonPanel, () -> {
                                    node(photonPanelLarge, () -> {});
                                });
                                node(thoriumReactor, () -> {
                                    node(antimatterCollider, Seq.with(new AtWave(verilus, 50)), () -> {});
                                });
                                node(sapt, () -> {});
                            });
                        });
                        node(mechanicalWell, () -> {
                            node(bisiliconOven, () -> {
                                node(titaniumPress, () -> {
                                    node(diamondCrusher, () -> {
                                        node(diamondCoverer, () -> {});
                                    });
                                    node(phaseWeaver, () -> {});
                                });
                                node(darkSteelWorkshop, Seq.with(new Objectives.Research(diamondGlass)), () -> {
                                    node(crystalIncubator, () -> {});
                                });
                                node(electricalWell, () -> {});
                            });
                        });
                        node(logicProcessor, () -> {
                            node(logicDisplay, () -> {
                                node(largeLogicDisplay, () -> {});
                                node(tileLogicDisplay, () -> {});
                            });
                        });
                    });
                });
            });
            node(flight, () -> {
                node(titaniumWall, () -> {
                    node(titaniumWallLarge, () -> {
                        node(titaniumWallHuge, () -> {});
                    });
                    node(thoriumWall, () -> {
                        node(thoriumWallLarge, () -> {
                            node(thoriumWallHuge, () -> {});
                        });
                        node(phaseWall, () -> {
                            node(phaseWallLarge, () -> {
                                node(phaseWallHuge, () -> {});
                            });
                        });
                        node(darkSteelWall, () -> {
                            node(darkSteelWallLarge, () -> {});
                        });
                    });
                    node(metaglassWall, () -> {
                        node(metaglassWallLarge, () -> {
                            node(metaglassWallHuge, () -> {});
                        });
                    });
                });
                node(electricShock, () -> {
                    node(parallax, () -> {
                        node(segment, () -> {});
                    });
                    node(ejection, () -> {});
                });
                node(dynamics, () -> {
                    node(fracture, () -> {});
                });
                node(aircraft, () -> {
                    node(aircraftThoriumMissile, () -> {
                        node(aircraftThermoxiteMissile, Seq.with(new Objectives.Research(thermoxite)), () -> {});
                        node(aircraftFissileMissile, Seq.with(new Objectives.Research(fissileCrystals)), () -> {});
                    });
                });
                node(flightLeadMissile, () -> {
                    node(flightTitaniumMissile, () -> {});
                    node(flightMetaglassMissile, Seq.with(new Objectives.Research(metaglass)), () -> {});
                });
            });
            node(basicAssembler, () -> {
                node(flare, () -> {
                    node(horizon, () -> {
                        node(zenith, Seq.with(new AtWave(verilus, 15)), () -> {});
                    });
                });
                node(mono, () -> {
                    node(poly, () -> {
                        node(mega, Seq.with(new AtWave(verilus, 15)), () -> {});
                    });
                });
                node(wick, () -> {
                    node(dynamite, () -> {
                        node(incident, Seq.with(new AtWave(verilus, 15)), () -> {});
                    });
                });
                node(tau, () -> {});
                node(advancedAssembler, () -> {
                    node(antumbra, Seq.with(new AtWave(verilus, 33)), () -> {
                        node(eclipse, Seq.with(new AtWave(verilus, 75)), () -> {});
                    });
                    node(quad, Seq.with(new AtWave(verilus, 33)), () -> {
                        node(oct, Seq.with(new AtWave(verilus, 75)), () -> {});
                    });
                    node(catastrophe, Seq.with(new AtWave(verilus, 33)), () -> {
                        node(inevitability, Seq.with(new AtWave(verilus, 75)), () -> {
                            node(eternity, ItemStack.with(TerraItems.tesseract, 5, TerraItems.darkSteel, 8200, TerraItems.diamondGlass, 5000, TerraItems.thermoxite, 7800), Seq.with(new AtWave(verilus, 180)), () -> {
                                node(eternityMissile, () -> {});
                            });
                            node(inevitabilityCore, () -> {});
                        });
                        node(sapEnergyMissile, () -> {});
                    });
                });
                node(basicAssemblyDrone, () -> {});
            });
            nodeProduce(lead, () -> {
                nodeProduce(graphite, () -> {
                    nodeProduce(Items.sand, () -> {});
                    nodeProduce(silicon, () -> {
                        nodeProduce(titaniumPlate, () -> {});
                    });
                    nodeProduce(metaglass, () -> {
                        nodeProduce(Liquids.water, () -> {});
                    });
                });
                nodeProduce(carbon, () -> {
                    nodeProduce(diamondDust, () -> {
                        nodeProduce(diamondGlass, () -> {});
                    });
                    nodeProduce(carbonDioxide, () -> {});
                    nodeProduce(rawThermoxite, () -> {
                        nodeProduce(thermoxite, () -> {});
                    });
                });
                nodeProduce(titanium, () -> {
                    nodeProduce(thorium, () -> {
                        nodeProduce(phaseFabric, () -> {
                            nodeProduce(fissileMatter, () -> {});
                        });
                        nodeProduce(fissilePlasma, () -> {
                            nodeProduce(fissileCrystals, () -> {
                                nodeProduce(gammaCell, () -> {});
                            });
                        });
                        nodeProduce(darkSteel, () -> {
                            nodeProduce(tesseract, () -> {});
                        });
                    });
                    nodeProduce(Liquids.cryofluid, () -> {
                        //nodeProduce(cryotite, () -> {});
                    });
                });
            });
            node(TerraSectorPresets.verilus, Seq.with(new Objectives.Research(coreSolaris)), () -> {});
        });
    }

    public static class AtWave implements Objectives.Objective {
        public SectorPreset sector;
        public int wave;

        public AtWave(SectorPreset sector,int wave) {
            this.sector = sector;
            this.wave = wave;
        }

        @Override public boolean complete() {
            return sector.sector.hasSave() && sector.sector.save.getWave() >= wave;
        }

        @Override public String display() {
            return Core.bundle.format("requirement.omaloon-at-wave", wave, sector.localizedName);
        }
    }
}
