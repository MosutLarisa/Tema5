import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Interval {
    private double start, end;
    private int total = 0;
    private int in = 0;

    public Interval(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public void test(Double nr) {
        total++;
        if (nr >= start && nr <= end)
            in++;
    }

    public double procent() {
        if (total == 0)
            return 0;
        else
            return ((double) in / total) * 100;
    }

    public String toString() {
        return "[" + start + ", " + end + "]: " + procent() + "%";
    }
}

public class Calculator {
    public static void main(String[] args) {
        List<Interval> intervalList = new ArrayList<>();

        try (BufferedReader intervalReader = new BufferedReader(new FileReader("intervale.dat"));
             BufferedReader numberReader = new BufferedReader(new FileReader("numere.dat"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("statistica.dat"))) {

            String line;

            while ((line = intervalReader.readLine()) != null) {
                String[] parts = line.split(",");
                double start = Double.parseDouble(parts[0].substring(1));
                double end = Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
                intervalList.add(new Interval(start, end));
            }

            while ((line = numberReader.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                for (String num : numbers) {
                    if (!num.isEmpty()) {
                        Double nr = Double.parseDouble(num);
                        for (Interval interval : intervalList) {
                            interval.test(nr);
                        }
                    }
                }
            }

            for (Interval interval : intervalList) {
                writer.write(interval.toString());
                writer.newLine();
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}
