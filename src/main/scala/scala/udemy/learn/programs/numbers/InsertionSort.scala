package scala.udemy.learn.programs.numbers

import scala.annotation.tailrec

object InsertionSort extends App {
  /*
    we need to implement this method that will return a new list of int,
    which is the sorted (ascending order) version of list that we receive as argument.
   */
  private def sortList(list: List[Int]): List[Int] = {
    /*
    This will insert a number into a sortedList, and the result is going to be a
    list of Int, which is the sorted list in increasing order.
    Ex: insert(2, [1, 3, 4] = [1, 2, 3, 4]
    So we need to insert the number 2 at proper position in sortedList, without mutating it.
     */
    def insert(number: Int, sortedList: List[Int]): List[Int] = {
      if(sortedList.isEmpty || number < sortedList.head) number :: sortedList
        /*
        We have the recursive call in the middle of an operation here ::, so
        it will have to first evaluate the result of insert(number, sortedList.tail)
        and then prpend an element to that. SO the recursive call is not the last
        expression to be evaluated.
         */
      else sortedList.head :: insert(number, sortedList.tail)
    }

    if(list.isEmpty || list.tail.isEmpty) list
      /*
      sortList(list.tail) is an argument to the insert, so it means that we need to
      evaluate sortList(list.tail) first before we can do insert. SO the recursive call
      is not the last expression to be evaluated.
       */
    else insert(list.head, sortList(list.tail))
  }

  /*
  sortList([4, 3, 2, 1])
  insert(4, sortList([3, 2, 1])) => insert(4, [1, 2, 3]) = [1, 2, 3, 4]

  sortList([3, 2, 1])
  insert(3, sortList([2, 1])) => insert(3, [1, 2]) => [1, 2, 3]

  sortList([2, 1])
  insert(2, sortList([1]) => insert(2, [1]) => [1, 2]

  sortList([1]) = [1]
   */
  private val sortedList = sortList(List(4, 3, 2, 1))
  println(sortedList) // List[1, 2, 3, 4]

  private def sortBetter(list: List[Int]): List[Int] = {
    /*
    insertTailRec(4, [1, 2, 3, 5], []) =
    insertTailRec(4, [2, 3, 5], [1]) =
    insertTailRec(4, [3, 5], [2, 1]) =
    insertTailRec(4, [5], [3, 2, 1]) =
    [1, 2, 3] ++ (4 :: [5]) = [1, 2, 3, 4, 5]
     */
    @tailrec
    def insertTailrec(number: Int, sortedList: List[Int], acc: List[Int]): List[Int] = {
      if (sortedList.isEmpty || number <= sortedList.head)
        acc.reverse ++ (number :: sortedList)
      else insertTailrec(number, sortedList.tail, sortedList.head :: acc)
    }
    @tailrec
    def sortTailRec(list: List[Int], acc: List[Int]): List[Int] = {
      if(list.isEmpty || list.tail.isEmpty) acc
      else sortTailRec(list.tail, insertTailrec(list.head, acc, Nil))
    }
    sortTailRec(list, Nil)
  }

  // Sorting huge list
  private val hugeList = (1 to 100000).reverse.toList
  // Exception in thread "main" java.lang.StackOverflowError
  sortBetter(hugeList)

}
