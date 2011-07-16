package testing

/**
 * A simple class to test constructor parameters
 * @param p1 P1
 * @param p2 P2
 * @param p3 P3
 */
class Constructors[A](p1:Int,p2:A,p3:String="ABC") {
	/** @param p2 P2 */
	def this(p2:A) = this(1,p2,"DEF")
	/** @param p2 P2 */
	def this(p1:Int) = this(p1,1.asInstanceOf[A],"DEF")
}