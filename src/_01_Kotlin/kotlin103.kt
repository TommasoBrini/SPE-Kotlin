import java.util.Vector
import javax.swing.text.Position

class FooGeneric<A, B: CharSequence>

fun <T:Comparable<T>> maxOf3(first: T, second: T, third: T): T = when {
    first >= second && first >= third -> first
    second >= third -> second
    else -> third
}

interface ProvaGenericiMultipli<T, P, A, L, R, N, E>
    where P: Position, P: Vector<P>,
          A: CharSequence,
          L: Position {

          }
