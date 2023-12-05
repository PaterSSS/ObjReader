package org.main;

import org.main.model.Model;
import org.main.reader.ObjReader;

public class Main {
    public static void main(String[] args) {
        Model model = ObjReader.readFile("C:\\Материалы ВУЗ\\3й семестр\\КГ\\projects\\Task3\\testToken.txt");
        System.out.println();
    }
}