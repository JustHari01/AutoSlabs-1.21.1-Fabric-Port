# AutoSlabs — unofficial 1.21.1 Fabric port

This repository ports [AutoSlabs by Andrew6rant](https://github.com/Andrew6rant/Auto-Slabs) to Minecraft 1.21.1 on Fabric. It keeps the original slab-placement behavior and adds no new block IDs or items.

The port adds vertical slab states to vanilla slabs and other blocks that extend Minecraft's `SlabBlock`. While holding a slab, press **K** to cycle placement modes; hold Sneak while pressing K to cycle backwards. The server receives the selected mode, and the default mode preserves AutoSlabs placement behavior. It also keeps KleeSlabs behavior: mine one half of a double slab, or hold Sneak while mining to break both halves. The optional built-in **Distinct Slabs** resource pack is included.

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.16.5 or newer
- Fabric API
- [Better Runtime Resource Pack (BRRP) 1.21.1](https://www.curseforge.com/minecraft/mc-mods/better-runtime-resource-pack/files/5643189) — required for generated models and blockstates
- Mod Menu is optional and provides the configuration screen

BRRP is a separate required mod and is not bundled in this JAR.

## Build

Use JDK 21 and run `gradlew build`. The distributable JAR is produced in `build/libs/`.

## Attribution and license

This is an unofficial port and is not endorsed by the original author. It is based on the original AutoSlabs source and retains its MIT license and notices. Original project: [Andrew6rant/Auto-Slabs](https://github.com/Andrew6rant/Auto-Slabs).

BRRP is a separate dependency maintained by SolidBlock and is distributed under MPL-2.0. Its source and license are available at [SolidBlock-cn/BRRP](https://github.com/SolidBlock-cn/BRRP).
