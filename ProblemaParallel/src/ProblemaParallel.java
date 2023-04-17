import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProblemaParallel {
    public static void main(String[] args) throws InterruptedException {
        // Mostrar lista de los pascientes
        List<String> pascientes = new ArrayList<>();
        pascientes.add("Eduardo "+"Solicita "+"Diagnostico");
        pascientes.add("Roger "+"Solicita "+"Endoscopia ");
        pascientes.add("Sofia "+"Solicita "+"Consulta ");
        pascientes.add("Cintia "+"Solicita "+"Endoscopia ");
        pascientes.add("Ronny "+"Solicita "+"Consulta ");
        pascientes.add("Alexander "+"Solicita "+"Chequeo medico ");
        pascientes.add("Arturo "+"Solicita "+"Fisioterapia ");
        pascientes.add("Wendy "+"Solicita "+"Cirujia ");
        pascientes.add("Jhon "+"Solicita "+"Endoscopia ");
        pascientes.add("Deysi "+"Solicita "+"Cirujia ");
        pascientes.add("German "+"Solicita "+"Diagnostico");
        pascientes.add("Gary "+"Solicita "+"Chequeo medico ");
        pascientes.add("Ronald "+"Solicita "+"Consulta ");
        pascientes.add("Marcos "+"Solicita "+"Fisioterapia ");
        pascientes.add("Gonzalo "+"Solicita "+"Chequeo medico ");
        pascientes.add("Jean Pierre "+"Solicita "+"Diagnostico");
        pascientes.add("Lisbeth "+"Solicita "+"Cirujia ");
        pascientes.add("Lucero "+"Solicita "+"Endoscopia ");
        pascientes.add("Fredy "+"Solicita "+"Cirujia ");
        pascientes.add("Carlos "+"Solicita "+"Fisioterapia ");


        long startTime = System.currentTimeMillis();
        // Dividir la lista de los pascientes en partes
        int numThreads = 4;
        int size = pascientes.size() / numThreads;
        List<List<String>> parts = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * size;
            int end = (i == numThreads - 1) ? pascientes.size() : (i + 1) * size;
            parts.add(new ArrayList<>(pascientes.subList(start, end)));
        }

        // Crear hilos y mezclar la lista de los pascientes de forma aleatoria

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            List<String> part = parts.get(i);
            Thread thread = new Thread(() -> {
                Collections.shuffle(part, new Random());
            });
            thread.start();
            threads.add(thread);
        }

        // Esperar a que todos los hilos terminen
        for (Thread thread : threads) {
            thread.join();
        }

        // Mesclar las partes de la lista
        List<String> randomized = new ArrayList<>();
        for (List<String> part : parts) {
            randomized.addAll(part);
        }

        // Mostrar la lista de pascientes aleatorios
        System.out.println(randomized);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Tiempo transcurrido: " + elapsedTime + "ms");


        long initialTimeS = System.nanoTime();
        Collections.shuffle(pascientes);
        pascientes.stream().forEach(s -> System.out.println("Pasciente "+s));
        long endTimeS = System.nanoTime();
        System.out.println("Los resultados de la programación secuencial son de: "+ (endTimeS - initialTimeS) / 1_000_000_000.0 + " segundos");
        System.out.println(" ");

        long initialTimeP = System.nanoTime();

        pascientes.stream().parallel().forEach(s -> System.out.println("Pasciente "+s));
        long endTimeP = System.nanoTime();
        System.out.println("\nLos resultados de la programación paralela son de: "+ (endTimeP - initialTimeP) / 1_000_000_000.0 + " segundos");
    }
}