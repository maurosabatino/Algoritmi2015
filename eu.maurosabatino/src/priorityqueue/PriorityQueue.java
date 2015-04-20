package priorityqueue;

/**
 * Created by Mauro on 20/04/2015.
 */
public interface PriorityQueue<E, P extends Comparable<P>> {
    void add(E element, P priority);

    E first();

    E removeFirst();

    boolean isEmpty();

    boolean delete(E element);

    boolean setPriority(E element, P priority);
}
