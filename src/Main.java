import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path newDir;
        // Создаём файл - источник...
        try (FileWriter fw = new FileWriter("Source.txt")){
            String data = "Some new stuff in this file...";
            fw.write(data);
        }
        catch (IOException e) {
            System.out.println("Error creating file.");
        }

        // Копируем содержание из источника в новый файл...
        try {
            FileReader fr = new FileReader("Source.txt");
            FileWriter fw = new FileWriter("Copy.txt");
            int data;
            while ((data = fr.read()) != -1){
                fw.write(data);
            }
            fr.close();
            fw.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            newDir = Files.createDirectories(Path.of("newDir"));
            if (Files.exists(Path.of(newDir + "/Source.txt"))) { // Существует ли файл в целевом каталоге?
                System.out.println("File Source.txt already exists!");
            } else {
                System.out.println("File Source.txt does not exist! Creating...");
                Files.move(Path.of("Source.txt"), Path.of(newDir + "/Source.txt"));
                System.out.println("Done!");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Сверяем содержимое двух файлов
        try {
            String dataSrc = Files.readString(Path.of(newDir + "/Source.txt"));
            String dataCopy = Files.readString(Path.of("Copy.txt"));
            System.out.println((dataCopy.equals(dataSrc)) ? "Contents of the files is equal!" : "Contents of the files differs!");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}