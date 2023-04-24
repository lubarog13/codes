
fun findMissingLetter(array: CharArray): Char {
    val newArr = array.get(0)..array.get(array.size - 1);
    for (i in 0..array.size) {
        if (newArr.elementAt(i)!=array.get(i)) return newArr.elementAt(i);
    }
    return '-';
}

fun invert(arr: IntArray): IntArray {
    return arr.map { it -> -1 * it }.toIntArray();
}

fun babyCount(x: String): Int? {
    val searchMap = mutableMapOf<Char, Int>('b' to  0, 'a' to 0, 'y' to  0)
    for (a in x.lowercase()) {
        if (searchMap.containsKey(a)) {
            searchMap[a] = searchMap[a]!! + 1;
        }
    }
    val min = arrayOf (searchMap['b']?.div(2), searchMap['a'], searchMap['y']).minByOrNull { it!! }
    return if (min==0) null else min
}

fun main(args: Array<String>) {
    println(babyCount("Happy babies boom ba by?"))
}