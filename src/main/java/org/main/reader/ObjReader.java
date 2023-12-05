package org.main.reader;

import org.main.math.Vector;
import org.main.model.Group;
import org.main.model.Model;
import org.main.model.Polygon;
import org.main.reader.exeptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//сделать не статическими методы, добавить поддержку нормальных моделей косенко.
// 1) написать класс вектора у которого произвольное кол-во координат. изменить метод проверки длины векторов.
// сделать нормальный api чтобы можно было и строку и файл и путь до файла передавать. Сделать всё красивее
//поддержка групп, ещё понять как это работает
//обработка отрицкательных индексов, можно добавить отдельный класс который будет содержать ссылки, на такие полигоны
// а после того как прочитаешь весь файл, проходить по таким полигонам и ставить им положительные индексы.
//
public class ObjReader {
    public enum TypeOfVector {
        VERTEX_VECTOR,
        NORMAL_VECTOR
    }
    private static final String VERTEX_TOKEN = "v";
    private static final String TEXTURE_VERTEX_TOKEN = "vt";
    private static final String NORMAL_TOKEN = "vn";
    private static final String FACE_TOKEN = "f";
    private static final String COMMENT_TOKEN = "#";
    private static final String GROUP_TOKEN = "g";
    private static final Model model = new Model();
    private static Group currentGroup = null;
    private static int polygonIndex = 0;
    private static int lineIndex = 0;

    public static Model readFile(String pathOfFile) {
        File file = new File(pathOfFile);
        lineIndex = 0;
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IncorrectPathException();
        }
        while (scanner.hasNextLine()) {
            String content = scanner.nextLine();
            lineIndex++;

            if (content.isEmpty() || content.contains(COMMENT_TOKEN)) {
                continue;
            }

            String[] allWords = content.split("\\s+");
            String token = allWords[0];
            String[] wordsWithoutToken = Arrays.copyOfRange(allWords, 1, allWords.length);

            switch (token) {
                case VERTEX_TOKEN -> model.addVertex(parseVector(wordsWithoutToken, TypeOfVector.VERTEX_VECTOR));
                case TEXTURE_VERTEX_TOKEN -> model.addTextureVertex(parseTextureVector(wordsWithoutToken));
                case NORMAL_TOKEN -> model.addNormal(parseVector(wordsWithoutToken, TypeOfVector.NORMAL_VECTOR));
                case FACE_TOKEN -> processPolygon(wordsWithoutToken);
                case GROUP_TOKEN -> handleGroup(wordsWithoutToken);
                default -> throw new TokenException(lineIndex);
            }

        }

        int vertexCount = model.getVertexSize();
        int textureVertexCount = model.getTextureVertexSize();
        int normalsCount = model.getNormalSize();

        for (Polygon polygon : model.getPolygons()) {
            polygon.checkIndicesPolygon(vertexCount, textureVertexCount, normalsCount);
        }
        return model;
    }

    //vertex or normal vectors
    public static Vector parseVector(String[] wordsWithoutToken, TypeOfVector type) {
        checkVertexSize(wordsWithoutToken, 3,4);
        try {
            float x = Float.parseFloat(wordsWithoutToken[0]);
            float y = Float.parseFloat(wordsWithoutToken[1]);
            float z = Float.parseFloat(wordsWithoutToken[2]);

            if (type.equals(TypeOfVector.VERTEX_VECTOR) && wordsWithoutToken.length == 4) {
                float w = Float.parseFloat(wordsWithoutToken[3]);
                return new Vector(x,y,z,w);
            }

            return new Vector(x, y, z);
        } catch (NumberFormatException exception) {
            throw new ParseVerticesException("float", lineIndex);
        }
    }

    public static Vector parseTextureVector(String[] wordsWithoutToken) {
        checkVertexSize(wordsWithoutToken, 2, 3);
        try {
            float x = Float.parseFloat(wordsWithoutToken[0]);
            float y = Float.parseFloat(wordsWithoutToken[1]);

            if (wordsWithoutToken.length == 3) {
                float w = Float.parseFloat(wordsWithoutToken[2]);
                return new Vector(x,y,w);
            }
            return new Vector(x, y);
        } catch (NumberFormatException exe) {
            throw new ParseVerticesException("float", lineIndex);
        }
    }

    private static void checkVertexSize(String[] wordsWithoutToken, int startOfRange, int endOfRange) {
        int length = wordsWithoutToken.length;

        if (length >= startOfRange && length <= endOfRange) {
            return;
        }
        if (length < startOfRange) {
            throw new IncorrectCountOfArgumentsException(TypeOfError.FEW_VERTICES, lineIndex);
        }

        throw new IncorrectCountOfArgumentsException(TypeOfError.MANY_VERTICES, lineIndex);
    }

    protected static List<FacePart> processPolygonWords(String[] wordsWithoutToken) {
        List<FacePart> partsOfPolygon = new ArrayList<>();
        Set<TypeOfPolygon> typeOfAllParts = new HashSet<>();
        //EnumMap<TypeOfPolygon, Void> c = new EnumMap<>(TypeOfPolygon.class);

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

    private static Polygon makePolygon(String[] wordsWithoutToken) {
        List<FacePart> faceParts = processPolygonWords(wordsWithoutToken);
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

    protected static void processPolygon(String[] wordsWithoutToken) {
        Polygon polygon = makePolygon(wordsWithoutToken);

        if (!model.getPolygons().isEmpty()) {
            Polygon tmp = model.getPolygons().get(0);
            if (polygon.hasTextures() != tmp.hasTextures()) {
                throw new PolygonTextureException(lineIndex);
            }
        }
        model.addPolygon(polygon);
        if (currentGroup != null) {
            currentGroup.addNewIndex(polygonIndex);
        }
        polygonIndex++;
    }
    protected static void handleGroup(String[] wordsWithoutToken) {
        if (wordsWithoutToken.length == 0) {
             throw new GroupException(lineIndex);
        }
        if (currentGroup != null) {
            model.addGroup(currentGroup);
        }
        StringBuilder sb = new StringBuilder();
        for (String item: wordsWithoutToken) {
            sb.append(item).append(" ");
        }
        sb.trimToSize();
        currentGroup = new Group(sb.toString());
    }
}
