package org.main.reader;

import org.main.reader.exeptions.IncorrectCountOfArgumentsException;
import org.main.reader.exeptions.ParseVerticesException;
import org.main.reader.exeptions.TypeOfError;

public class FacePart {
    private Integer vertexIndex;
    private Integer textureVertexIndex;
    private Integer normalIndex;
    private TypeOfPolygon type;

    static FacePart parseFacePart(String part, int lineIndex) {
        String[] vertices = part.split("/");
        FacePart facePart = new FacePart();

        if (vertices.length > 3) {
            throw new IncorrectCountOfArgumentsException(TypeOfError.MANY_IN_FACE, lineIndex);
        }
        if (vertices.length == 0) {
            throw new IncorrectCountOfArgumentsException(TypeOfError.FEW_IN_FACE, lineIndex);
        }

        String vertexIndex = vertices[0];
        try {
            facePart.vertexIndex = Integer.parseInt(vertexIndex);
            facePart.type = TypeOfPolygon.ONLY_VERTICES;
        } catch (NumberFormatException exe) {
            throw new ParseVerticesException("Integer", lineIndex);
        }

        if (vertices.length > 1 && !vertices[1].isEmpty()) {
            try {
                facePart.textureVertexIndex = Integer.parseInt(vertices[1]);
                facePart.type = TypeOfPolygon.VERTICES_TEXTURES;
            } catch (NumberFormatException exe) {
                throw new ParseVerticesException("Integer", lineIndex);
            }
        }

        if (vertices.length > 2) {
            try {
                facePart.normalIndex = Integer.parseInt(vertices[2]);
                facePart.type = TypeOfPolygon.VERTICES_TEXTURES_NORMALS;
            } catch (NumberFormatException exe) {
                throw new ParseVerticesException("Integer", lineIndex);
            }
        }
        return facePart;
    }

    public TypeOfPolygon getType() {
        return type;
    }

    public Integer getVertexIndex() {
        return vertexIndex;
    }

    public Integer getTextureVertexIndex() {
        return textureVertexIndex;
    }

    public Integer getNormalIndex() {
        return normalIndex;
    }
}
