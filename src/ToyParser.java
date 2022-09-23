import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.*;
import java.io.IOException;

public class ToyParser {
    public static List<String[]> getSyntax(Path file) {
        Charset charset = Charset.forName("UTF-8");

        List<String[]> syntax = new ArrayList<>();
            
        try {
            List<String> lines = Files.readAllLines(file, charset);
            for (String line : lines) {
                String[] tmp = line.split(" ", 5);
                syntax.add(tmp);
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return syntax;
    }
}
