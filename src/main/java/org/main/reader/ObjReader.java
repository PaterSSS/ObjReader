package org.main.reader;

import org.main.math.Vector2f;
import org.main.math.Vector3f;
import org.main.model.Model;
import org.main.reader.exeptions.IncorrectCountOfArgumentsException;
import org.main.reader.exeptions.IncorrectPathException;
import org.main.reader.exeptions.ParseVerticesException;
import org.main.reader.exeptions.TypeOfError;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
    private static final String VERTEX_TOKEN = "v";
    private static final String TEXTURE_VERTEX_TOKEN = "vt";
    private static final String NORMAL_TOKEN = "vn";
    private static final String FACE_TOKEN = "f";
    private static final String COMMENT_TOKEN = "#";

    public static Model readFile(String pathOfFile) {
        Model model = new Model();
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
                    case FACE_TOKEN -> model.addPolygon();
                }

            }
        } catch (FileNotFoundException e) {
            throw new IncorrectPathException();
        }
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
    private static void processPolygon(String[] wordsWithoutToken, int lineIndex) {

    }
}
