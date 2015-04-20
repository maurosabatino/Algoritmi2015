package sort;



public class Sorting {

    private Sorting(){}

    /**
     *
     * Selection sort
     * Si cerca l'elemento con il valore minimo all'interno dell'array, lo si mette al primo posto per mezzo di uno scambio
     * e si procede così per tutti gli elementi dell'array.
     * Passo generico
     * - a[0...i-1] parte già ordinata
     * - a[i...n-1] parte da ordinare
     * Condizione di uscita dal ciclo while
     * si itera l'array fino al penultimo elemento perchè sarà di sicuro al posto giusto essendo ci solo più lui.
     * @param a array di interi che si vuole ordinare
     */
    public static void ssort(int[] a) {
        int n = a.length;
        for(int i = 0;i<n-1;i++){
            int iMin = i;
            for(int j=i+1;j<n;j++)
                if(a[j] < a[iMin])iMin=j;
            swap(a, i, iMin);
        }
    }
    /**
     * algoritmo di ordinamento per inserimento
     * (Pre-Condizione) l'array che viene inserito in input viene considerato da ordinare (anche se è già ordinato)
     * (Post-condizione) l'array è ordinato per l'algoritmo insertion sort.
     * @param a array di interi dato in input, da ordinare.
     * @param i indice del primo elemento della parte non ordinata, inizialmente viene inizializzato ad 1 perchè l'array formato da un solo elemnto è banalmente ordinato, quindi la parte da ordinare parte da 1
     * @param x è l'elemento da inserire, minore di tutti gli altri elemnti già spostati
     */
    public static void isort(int[] a) {
        int n=a.length;
        for(int i=1;i<n;i++){
            int x = a[i];
            insert(x,i,a);
        }
    }
    /**
     * versione uno dell'inserion sort
     * metodo che effettua i confronti e l'inserimento nel posto corretto dell'elemento x
     * Passo Generico
     * - a[0...i-1] parte ordinata
     * - a[i...n-1] parte da ordinare
     * devo quindi percorrere all'indietro l'array a partire dall'indice i, trovare la posizione in cui va inserito x,
     * nel frattempo facendo slittare in avanti di una posizione gli elementi della parte ordinata.
     * Inizializzazione
     * inizialmente la parte da ordinare è a[0...0], quindi la parte da ordinare non è tutto l'array ma a[1...n-1].
     * @param x
     * @param i indice del primo elemento della parte non ordinata.
     * @param a
     * @param j indice del posto in cui l'elemento posto in a[i] va inserito
     */
    private void insert(int x,int i, int[]a){
        int j = i;
        for(j = i;j>0;j--){
            if(x>=a[j-1]) break;//x va inserito in a[i]
            a[j] = a[j-1];
        }
        a[j] = x;
    }
    /**
     * mergesort ecologico
     * si può evitare di allocare un array ausiliario ad ogni chiamata della merge andando ad intasare il garbage collector
     * @param a array di interi in input da ordinare per mezzo dell'algoritmo di mergesort.
     * @param aux array di interi ausiliario in cui vengono fusi i segmenti ordinati, ha dimensione uguale all'array a.
     */
    public static void msortBasicEcologic(int[] a) {
        int n = a.length;
        int [] aux = new int[n];
        msort(a,0,n-1,aux);
    }
    /**
     *
     * @param a porzione di array da esaminare e da ordinare a[first...last]. Inizialmente la parte da ordinare è l'intero array a[0...a.length-1]
     * @param first indice de primo elemento della parte da ordinare.
     * @param last indice dell'ultimo elemento della parte da ordinare.
     * @param b array ausiliario di interi di lunghezza a.length.
     */
    private static void msort(int[]a,int first,int last,int[]b){
        if(first<last){
            int m =(first+last)/2;
            msort(a,first,m,b);
            msort(a,m+1,last,b);
            merge(a,first,m,last,b);
        }
    }

    /**
     * algoritmo di fusione, per fondere le le due sequenze a[first...mid],[mid+1...last] ordinate in una sequenza ordinata,
     * si eseguono i confronti per vedre quale elemento delle due sequenze è precedente nell'ordine
     * e con l'ultimo ciclo for vado a ricopiare l'array ausiliario ordinato nell'array di input a.
     * l'ottimizzazione per questo tipo di implementazione consiste nel considerare le due porzioni non più come due array separati, due partizioni di metà dimensione:
     * - a[fst...mid]
     * - a[mid+1...lst]
     * @param a array di interi che va fuso
     * @param fst indice del primo elemento della prima sequenza
     * @param mid indice dell'ultimo elemento della prima sequenza, mid+1 primo elemento della seconda sequenza
     * @param lst indice dell'ultimo elemento della seconda sequenza
     * @param c array ausiliario che viene usato come array in cui andare a mettere gli elementi delle due sequenze in ordine
     */
    private static void merge(int[]a,int fst,int mid,int lst,int[]c){
        int i=fst,j=mid+1,k=fst;
        while(i<=mid && j<=lst){
            if(a[i]<=a[j]) c[k++]=a[i++];
            else c[k++]=a[j++];
        }
        int h = mid, l = lst;
        while(h>=i) a[l--]=a[h--];
        for(int r = fst;r<k;r++) a[r]=c[r];
    }

    public static void msortIns(int[] a) {

    }

    public static void msortAlt(int[] a) {
        int n = a.length;
        int[]aux = a;
        MergeSortJavaAPIVersion(a,0,n-1,aux);
    }
    private static void MergeSortJavaAPIVersion(int[]a,int fst,int lst,int[]b){
        if(fst<lst){
            int m=(fst+lst)/2;
            MergeSortJavaAPIVersion(b,fst,m,a);
            MergeSortJavaAPIVersion(b,m+1,lst,a);
            MergeJavaAPIVersion(b,fst,m,lst,a);
        }
    }
    private static void MergeJavaAPIVersion(int[]a,int fst,int mid,int lst,int[]b){
        int i= fst, j=mid+1,k=fst;
        while(i<=mid && j<=lst){
            if(a[i]<=a[j]) b[k++]=a[i++];
            else b[k++]=a[j++];
        }
        while(i<=mid) b[k++]=a[i++];
        while(j<=lst) b[k++]=a[j++];
    }

    public static void parallelMergeSort(int[] a) {
    }

    public static void qsortHoareIsort(int[] a) {
        if (array ==null || array.length==0)return;
        int n = array.length;
        QuickSortHoare(array,0,n-1);
    }
    public static void QuickSortHoare(int[]array,int fst,int lst){
        if(fst>=lst) return;
        int p = QuickSortHoarePartition(array,fst,lst);
        QuickSortHoare(array,fst,p);
        QuickSortHoare(array,p+1,lst);
    }

    public static int QuickSortHoarePartition(int[]array,int fst,int lst){
        int pivot = array[fst];
        int i=fst-1;
        int j=lst+1;
        while(i<j){
            i++;while(array[i]<pivot) i++;
            j--;while(array[j]>pivot) j--;
            if(i<j)swap(array,i,j);
        }
        return j;
    }

    public static void parallelQuicksort(int[] a) {
    }

    public static boolean isSorted(int[] a) {
        int n = a.length;
        for(int i = 1; i < n-1; i++) if(a[i-1] > a[i]) return false;
        return true;

    }

    /**
     * metodo che scambia di posto due elementi in un array attraverso i loro indici.
     * @param a array nel quale si effettua lo scambio.
     * @param i indice del primo elemento da scambiare.
     * @param j indice del secondo elemento da scambiare.
     */
    public static void swap(int[] a, int i, int j) {
        int x=a[i];
        a[i]=a[j];
        a[j]=x;

    }
}
