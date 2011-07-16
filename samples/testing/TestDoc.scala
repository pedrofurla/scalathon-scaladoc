/**
 * Doc de package
 * @author Author goes here
 */
package testing

/** 
* This class contains some tests for
* 
* 1. Default values
* 1. Use case
* 
* Some mixing of list styles
*  - T1
*  - T2
*   - T2.1
*     a. T2.1.1
*     a. T2.1.2
*       i. T2.1.2.1
*       i. T2.1.2.1
*     a. T2.1.3
*   - T2.2
*  - T3
* 
* `Back-tick`
* 
* Naming a link: [[http://www.google.com some where]]
* 
* No names: [[http://www.google.com]]-ing
* 
* {{{
* 	some code here
* 			more code
* }}}
* 
* @version 2.8
* @since   1.0
* @author Pedro
*   @author ''fulano''
* @see This should emit a warning [[testing.ParentDoc the parent]]
* @see [[testing.TestDoc]]
*/
class TestDoc(val p2:String="AAA", val p3:Int=5, var p4:Double = 1.0, var aList:List[Int]= 1 :: 2 :: 3 :: Nil) 
	extends ParentDoc[List,Int,Seq](1)(p2,1) {
	
	type absType = Int
	
	override val a = 1
	
	implicit val b = 3

	/**
	 * Doc val c
	 * 
	 * @version 4
	 */
	@deprecated
	implicit val c = 4
	
	/**
Doc val d
	  
	  @version 4
	 */
	var d = 1
	
	implicit var e = 3

	/**
	 * Doc val f 
	 * @version 1
	 * @deprecated abc
	 */
	implicit var f = 4
	
	/**
	*  Method
	*               
	*  @version 3
	*  @since   4
	*  
	* @author Pedro2
	* @author ''fulano2''
	* @see ParentDoc
	* @see TestDoc
	*/
	implicit def method:this.type = this
	
	def other(start:Int=1) = new TestDoc(p2 = "A")
	
	/**
	 * Other method
	 * @param xs     the array of elements
	 * @param start  the start index
	 * @tparam A	nada mesmo
	 */
	@deprecated("anotado...") 
	def otherMethod[A](xs:A,start2:Int=10) = 5
	
	/**
	 * Simple 
	 * @deprecated Taglet depre. msg
	 * @author alalal
	 */
	def anotherMethod = 0;

  def implTest(x:Int)(implicit p:List[String],z:Int) = p
  def implTest2(implicit p:List[String]) = p
  
  val valAbsString:String = ""
  var varAbsString:String = ""
  def defAbsMethod:String = ""
	  
  /**
   * To test overloaded methods in the indexing process
   */
  def toDoSomething = ""
  def toDoSomething(x:Int) = ""
  def toDoSomething(x:String) = ""
    
  override def toString = ""
	  
  def contextBounds[A:List](x:A) = ""
	  
  def complexDefaultValue(aList:List[Int]= 1 :: 2 :: 3 :: Nil) = ""
}

/** 
* Teste de docs obj TestDoc
* @author Pedro
* @deprecated use TestDoc
*/
object TestDoc {
	/** Constroi...
	 * @return um TestDoc
	 * @deprecated Taglet Deprecated 
	*/
	def apply = new TestDoc;

  def main = {
    //new TestDoc()
  }
  override def toString = ""

}

/**
 * A super ultra mega class in lower hiearchy just to test subclassing ordering
 * 
 * Also testing templates nesting
 */
class Alpha extends TestDoc {

	class CrtAbsInnerClass extends AbsInnerClass {
		type SomeType = String
	}
	
	override def toString = ""
	
}

/** Some class to test indexing which references AnyRef
*/
class AnyRefUse(a:AnyRef) extends AnyRef {
	override def toString = ""
}
