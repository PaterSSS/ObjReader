package org.main.reader;

import org.main.model.Model;
import org.main.reader.exeptions.IncorrectPathException;

import java.io.File;
import java.io.FileNotFoundException;
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


            }
        } catch (FileNotFoundException e) {
            throw new IncorrectPathException();
        }
    }

}
