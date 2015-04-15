package sort;

import static java.lang.System.getProperty;
import static java.lang.System.nanoTime;
import static sort.Sorting.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.EnumSet;

public class SortTiming {

  static int[] naturalRandomArray(int len, int maxVal) {
    int[] a = new int[len];
    Random rand = new Random();
    for(int i=0; i<len; ++i) {
      a[i] = rand.nextInt(maxVal+1);
    }
    return a;
  }

  static enum SequenceKind {
    UNSORTED, ALMOST_SORTED, REPEATED_VALUES;
  }

  static enum Algorithm {
    SSORT, ISORT,
    MSORT, MSORT_INS, MSORT_ALT, PAR_MSORT,
    QSORT, PAR_QSORT,
    JAVASORT, JAVASORT_PAR;
  }

  public static void sort(Algorithm algo, int[] a) {
    switch(algo) {
      case SSORT: ssort(a); break;
      case ISORT: isort(a); break;
      case MSORT: msortBasicEcologic(a); break;
      case MSORT_INS: msortIns(a); break;
      case MSORT_ALT: msortAlt(a); break;
      case PAR_MSORT: parallelMergeSort(a); break;
      case QSORT: qsortHoareIsort(a); break;
      case PAR_QSORT: parallelQuicksort(a); break;
      case JAVASORT: Arrays.sort(a); break;
      case JAVASORT_PAR: Arrays.parallelSort(a); break;
    }
  }

  static EnumSet<Algorithm> quadraticVsOptimal =
    EnumSet.range(Algorithm.SSORT, Algorithm.MSORT);

  static EnumSet<Algorithm> optimal =
    EnumSet.range(Algorithm.MSORT, Algorithm.JAVASORT_PAR);

  static double executionTime(Algorithm algo, int[] a) {
    long t1 = nanoTime();
    sort(algo, a);
    long t2 = nanoTime();
    if(!isSorted(a)) throw new RuntimeException("non ha ordinato");
    return (t2 - t1)/1E6;
  }

  /**
   * Crea una riga della tabella sull'output out, per sequenze di genere kind,
   * eseguendo tutti gli algoritmi algorithms, su un array di lunghezza len
   */
  static void runAllAlgorithms(EnumSet<Algorithm> algorithms, SequenceKind kind, PrintWriter out, int len) {
    int maxValue = (kind == SequenceKind.REPEATED_VALUES) ? len/3 : 9*len/10;
    int[] array = naturalRandomArray(len, maxValue);
    if(kind == SequenceKind.ALMOST_SORTED) {
      Arrays.sort(array);
      swap(array, len/3, 2*len/3);
    }

    out.printf("%6d;", len);
    System.out.println(len);
    for(Algorithm algo: algorithms) {
      System.out.println(algo.toString());
      int[] arrayCopy = Arrays.copyOf(array, array.length);
      double time = executionTime(algo, arrayCopy);
      out.printf("%9.2f;", time);
    }
    out.println();
    System.out.println();
  }

  /**
   * Crea una tabella sull'output out, per tutti i generi di sequenze,
   * eseguendo tutti gli algoritmi su array di lunghezze via via crescenti,
   * da step fino a maxLength
   */
  static void makeTable(EnumSet<Algorithm> algorithms, int step, int maxLength, String fileName) throws IOException {
    FileWriter outFile = new FileWriter(fileName, false);
    PrintWriter writer = new PrintWriter(outFile);
    writer.println("sep=;");
    writer.println("Prova a su array di interi: " + getProperty("os.name") + " Java "+ getProperty("java.version"));
    for(SequenceKind kind: SequenceKind.values()) {
      writer.println(kind.toString());
      System.out.println(kind.toString() + "\n");
      for(Algorithm algo: algorithms) writer.print(";" + algo.toString());
      writer.println();
      for(int len = step; len <= maxLength; len += step) {
        runAllAlgorithms(algorithms, kind, writer, len);
      }
      writer.println();
    }
    writer.close();
    System.out.println();
    System.out.println();
  }

  public static void main(String[] args) throws IOException {
    makeTable(quadraticVsOptimal, 50000, 150000, "quadraticTimes.csv");
    makeTable(optimal, 100000, 2000000, "optimalTimes.csv");
    System.out.println("finito!");
  }
}
