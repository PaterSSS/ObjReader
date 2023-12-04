package org.main.reader;

import org.main.math.Vector2f;
import org.main.math.Vector3f;
import org.main.model.Model;
import org.main.model.Polygon;
import org.main.reader.exeptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ObjReader {
    private static final String VERTEX_TOKEN = "v";
    private static final String TEXTURE_VERTEX_TOKEN = "vt";
    private static final String NORMAL_TOKEN = "vn";
    private static final String FACE_TOKEN = "f";
    private static final String COMMENT_TOKEN = "#";
    private static final Model model = new Model();

    public static Model readFile(String pathOfFile) {
        File file = new File(pathOfFile);
        int lineCount = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String content = scanner.nextLine();
                lineCount++;

                if (content.isEmpty() || content.contains(COMMENT_TOKEN)) {
                    continue;
                }

                String[] allWords = content.split("\\s+");
                String token = allWords[0];
                String[] wordsWithoutToken = Arrays.copyOfRange(allWords, 1, allWords.length);

                switch (token) {
                    case VERTEX_TOKEN -> model.addVertex(parseVector3f(wordsWithoutToken, lineCount));
                    case TEXTURE_VERTEX_TOKEN -> model.addTextureVertex(parseVector2f(wordsWithoutToken, lineCount));
                    case NORMAL_TOKEN -> model.addNormal(parseVector3f(wordsWithoutToken, lineCount));
                    case FACE_TOKEN -> processPolygon(wordsWithoutToken, lineCount);
                    default -> throw new TokenException(lineCount);
                }

            }
        } catch (FileNotFoundException e) {
            throw new IncorrectPathException();
        }
        int vertexCount = model.getVertexSize();
        int textureVertexCount = model.getTextureVertexSize();
        int normalsCount = model.getNormalSize();

        for (Polygon polygon : model.getPolygons()) {
            polygon.checkIndicesPolygon(vertexCount,textureVertexCount, normalsCount);
        }
        return model;
    }

    private static Vector3f parseVector3f(String[] wordsWithoutToken, int lineIndex) {
        checkVertexSize(wordsWithoutToken, 3, lineIndex);
        try {
            float x = Float.parseFloat(wordsWithoutToken[0]);
            float y = Float.parseFloat(wordsWithoutToken[1]);
            float z = Float.parseFloat(wordsWithoutToken[2]);

            return new Vector3f(x, y, z);
        } catch (NumberFormatException exception) {
            throw new ParseVerticesException("float", lineIndex);
        }
    }

    private static Vector2f parseVector2f(String[] wordsWithoutToken, int lineIndex) {
        checkVertexSize(wordsWithoutToken, 2, lineIndex);
        try {
            float x = Float.parseFloat(wordsWithoutToken[0]);
            float y = Float.parseFloat(wordsWithoutToken[1]);

            return new Vector2f(x, y);
        } catch (NumberFormatException exe) {
            throw new ParseVerticesException("float", lineIndex);
        }
    }

    private static void checkVertexSize(String[] wordsWithoutToken, int properSize, int lineIndex) {
        if (wordsWithoutToken.length == properSize) {
            return;
        }
        if (wordsWithoutToken.length < properSize) {
            throw new IncorrectCountOfArgumentsException(TypeOfError.FEW_VERTICES, lineIndex);
        }
        throw new IncorrectCountOfArgumentsException(TypeOfError.MANY_VERTICES, lineIndex);
    }

    private static List<FacePart> processPolygonWords(String[] wordsWithoutToken, int lineIndex) {
        List<FacePart> partsOfPolygon = new ArrayList<>();
        Set<TypeOfPolygon> typeOfAllParts = new HashSet<>();

        for (String word : wordsWithoutToken) {
            FacePart tmp = FacePart.parseFacePart(word, lineIndex);
            partsOfPolygon.add(tmp);
            typeOfAllParts.add(tmp.getType());
        }

        if (partsOfPolygon.size() < 3) {
            throw new IncorrectCountOfArgumentsException(TypeOfError.FEW_IN_FACE, lineIndex);
        }
        if (typeOfAllParts.size() > 1) {
            throw new DifferentTypeOfPolygonException(lineIndex);
        }
        return partsOfPolygon;
    }

    private static Polygon makePolygon(String[] wordsWithoutToken, int lineIndex) {
        List<FacePart> faceParts = processPolygonWords(wordsWithoutToken, lineIndex);
        Polygon resultPolygon = new Polygon();
        List<Integer> vertexIndices = new ArrayList<>();
        List<Integer> textureVertexIndices = new ArrayList<>();
        List<Integer> normalsIndices = new ArrayList<>();


        for (FacePart fp : faceParts) {
            Integer vertexIndex = fp.getVertexIndex();
            if (vertexIndex != null) {
                vertexIndices.add(vertexIndex);
            }
            Integer textureVertexIndex = fp.getTextureVertexIndex();
            if (textureVertexIndex != null) {
                textureVertexIndices.add(textureVertexIndex);
            }
            Integer normalIndex = fp.getNormalIndex();
            if (normalIndex != null) {
                normalsIndices.add(normalIndex);
            }
        }
        resultPolygon.setVertexIndices(vertexIndices);
        resultPolygon.setTextureVertexIndices(textureVertexIndices);
        resultPolygon.setNormalIndices(normalsIndices);
        resultPolygon.setLineIndex(lineIndex);

        return resultPolygon;
    }

    private static void processPolygon(String[] wordsWithoutToken, int lineIndex) {
        Polygon polygon = makePolygon(wordsWithoutToken, lineIndex);

        if (!model.getPolygons().isEmpty()) {
            Polygon tmp = model.getPolygons().get(0);
            if (polygon.hasTextures() != tmp.hasTextures()) {
                throw new PolygonTextureException(lineIndex);
            }
        }
        model.addPolygon(polygon);
    }
}
