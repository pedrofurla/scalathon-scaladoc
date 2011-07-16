package testing

import scala.collection.Seq
import scala.collection.mutable.ArrayStack

/** 
 * Testing a abstract class with ''type parameters''
 *  - With some ''type parameters''
 *  - With some ''type aliases''
 * 
 * @author Pedro
 *
 */
abstract class AbstractParentDoc[A[Z],X,B[X]] { self =>
	
	/** Testing a abstract ''type'' */
	type absType
	/** Testing a concrete ''type'' */
	type crtType = String
	
	type containerType = A[_]
	type containedType = X

	/** Testing a abstract inner class:
	 */
	abstract class AbsInnerClass[A[X]] {
		type containerType = B[_]
		type mixedType = B[A[_]]
	}

	/** Testing a concrete inner class */
	class CrtInnerClass extends AbsInnerClass[A]
	
	val valAbsString:String
	var varAbsString:String
	def defAbsMethod:String
	
	def toString:String
}

/**
 Testing ''wiki'' parsing.
 
A paragraph starting in the first column. Followed by a list

- List with no left whitespace
- Another Item
 
 This doccomment has no '*' in the begin of lines.
 
 Parent class ''with italics?'', '''bold''', a super classe
 
 A link in a new paragraph [[testing.AbstractParentDoc]]
 
 Ruler
 ---
 
 `mono` , __Important__ , N^21^ , K,,21,,
     
    - ABC
    - EFG
      - HIJ
    - HIJ
      - Some link [[testing.AbstractParentDoc]]
 
 = TIT 1 = AAA <- this disappers
 == TIT 2 ==
 === TIT 3 ===
 */
@deprecated("mensagem") 
abstract class ParentDoc[Z[X],T,G[Y]](x:Int)(implicit p1:String, p2: Int) extends AbstractParentDoc[Z,T,G] {
	
	
	def typeBounds[Z[_],Seq[A] >: Z[T] <: ArrayStack[A] ]:Unit = {}
	
	def this(nonSense:String) = this(1)("",5)
	
	/**
	 * Use case test with non-matching names
	 * @usecase def aaa
	 */
	def parentMethod = 0
	val a = 1
	implicit val implVal = 5
	
	/**
	 * Use case test with matching names
	 * @usecase def realUseCase
	 */
	def realUseCase = 0
	
	/** Just a implicit
	*/
	implicit var implvar = 5
	
	/** Hey, another implicit
	*/
	implicit def impldef = 5

	/** Testing how Scaladoc deals with anonymous classes */
	def newSuper = new AbstractParentDoc[Z,T,G] {
		
		type absType = Int
		/** Already declared */
		val valAbsString:String = ""
		/** Already declared */
		var varAbsString:String = ""
		/** Already declared */
		def defAbsMethod:String = ""
		
		/** Here comes the interesting part */
		def defUnaccessible = ""
		override def toString = ""		
	}
	
	override def toString = ""
}
