package priorityqueue;

/**
 * Created by Mauro on 20/04/2015.
 */
public interface PriorityQueueStringDouble {
    void add ( String element , double priority) ;
    String first( ) ;
    String removeFirst ( ) ;
    boolean isEmpty ( ) ;
    boolean delete( String element ) ;
    boolean setPriority ( String element , double priority) ;
}
