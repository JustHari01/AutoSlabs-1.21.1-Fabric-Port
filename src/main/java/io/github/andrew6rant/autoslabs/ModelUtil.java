package io.github.andrew6rant.autoslabs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import pers.solid.brrp.v1.api.RuntimeResourcePack;

import java.nio.charset.StandardCharsets;

public final class ModelUtil {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private ModelUtil() {
    }

    public static void setup(RuntimeResourcePack pack, Block block) {
        Identifier blockId = Registries.BLOCK.getId(block);
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();
        String parent = namespace + ":block/" + path;

        Identifier northSouthTop = Identifier.of(namespace, "block/" + path + "_vertical_north_south_top");
        Identifier northSouthBottom = Identifier.of(namespace, "block/" + path + "_vertical_north_south_bottom");
        Identifier eastWestTop = Identifier.of(namespace, "block/" + path + "_vertical_east_west_top");
        Identifier eastWestBottom = Identifier.of(namespace, "block/" + path + "_vertical_east_west_bottom");

        // Preserve the upstream model geometry and parent-texture inheritance for modded slab blocks.
        addBox(pack, northSouthTop, parent, new int[]{0, 0, 0}, new int[]{16, 16, 8},
            face("north", "side", "north", 0, 0, 16, 16),
            face("east", "side", "east", 8, 0, 16, 16),
            face("south", "side", null, 0, 0, 16, 16),
            face("west", "side", "west", 0, 0, 8, 16),
            face("up", "top", "up", 0, 0, 16, 8),
            face("down", "bottom", "down", 0, 0, 16, 8));

        addBox(pack, northSouthBottom, parent, new int[]{0, 0, 8}, new int[]{16, 16, 16},
            face("north", "side", null, 0, 0, 16, 16),
            face("east", "side", "east", 0, 0, 8, 16),
            face("south", "side", "south", 0, 0, 16, 16),
            face("west", "side", "west", 8, 0, 16, 16),
            face("up", "top", "up", 0, 8, 16, 16),
            face("down", "bottom", "down", 0, 0, 16, 8));

        addBox(pack, eastWestTop, parent, new int[]{8, 0, 0}, new int[]{16, 16, 16},
            face("north", "side", "north", 0, 0, 8, 16),
            face("east", "side", "east", 0, 0, 16, 16),
            face("south", "side", "south", 8, 0, 16, 16),
            face("west", "side", null, 0, 0, 16, 16),
            face("up", "top", "up", 8, 0, 16, 16),
            face("down", "bottom", "down", 8, 0, 16, 16));

        addBox(pack, eastWestBottom, parent, new int[]{0, 0, 0}, new int[]{8, 16, 16},
            face("north", "side", "north", 8, 0, 16, 16),
            face("east", "side", null, 0, 0, 16, 16),
            face("south", "side", "south", 0, 0, 8, 16),
            face("west", "side", "west", 0, 0, 16, 16),
            face("up", "top", "up", 0, 0, 8, 16),
            face("down", "bottom", "down", 0, 0, 8, 16));

        JsonObject variants = new JsonObject();
        variants.add("type=bottom,vertical_type=north_south", modelVariant(northSouthBottom));
        variants.add("type=bottom,vertical_type=east_west", modelVariant(eastWestBottom));
        variants.add("type=top,vertical_type=north_south", modelVariant(northSouthTop));
        variants.add("type=top,vertical_type=east_west", modelVariant(eastWestTop));

        JsonObject blockState = new JsonObject();
        blockState.add("variants", variants);
        pack.addBlockState(blockId, GSON.toJson(blockState).getBytes(StandardCharsets.UTF_8));
    }

    private static void addBox(RuntimeResourcePack pack, Identifier modelId, String parent, int[] from, int[] to, Face... faces) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", parent);

        JsonObject element = new JsonObject();
        element.add("from", vector(from));
        element.add("to", vector(to));

        JsonObject modelFaces = new JsonObject();
        for (Face face : faces) {
            JsonObject faceJson = new JsonObject();
            faceJson.addProperty("texture", "#" + face.texture());
            if (face.cullFace() != null) {
                faceJson.addProperty("cullface", face.cullFace());
            }
            JsonArray uv = new JsonArray();
            uv.add(face.u0());
            uv.add(face.v0());
            uv.add(face.u1());
            uv.add(face.v1());
            faceJson.add("uv", uv);
            modelFaces.add(face.side(), faceJson);
        }
        element.add("faces", modelFaces);

        JsonArray elements = new JsonArray();
        elements.add(element);
        model.add("elements", elements);

        pack.addModel(modelId, GSON.toJson(model).getBytes(StandardCharsets.UTF_8));
    }

    private static JsonArray vector(int[] values) {
        JsonArray vector = new JsonArray();
        for (int value : values) {
            vector.add(value);
        }
        return vector;
    }

    private static Face face(String side, String texture, String cullFace, int u0, int v0, int u1, int v1) {
        return new Face(side, texture, cullFace, u0, v0, u1, v1);
    }

    private static JsonObject modelVariant(Identifier modelId) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", modelId.toString());
        return variant;
    }

    private record Face(String side, String texture, String cullFace, int u0, int v0, int u1, int v1) {
    }
}
