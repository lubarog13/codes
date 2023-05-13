
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

fun longest(s1:String, s2:String):String {
    val st = s1.toList().toMutableSet()
    st.addAll(s2.toList())
    return st.toList().sorted().joinToString("")
}

fun multiplicationTable(size: Int): Array<IntArray> {
    val arr = Array<IntArray>(size) { IntArray(size) { (it+1) * 1 } };
    for (i in 1 until size) {
        arr[i][0] = arr[i-1][0] + 1;
        for (j in 1 until  size) {
           arr[i][j] = arr[i][0] * arr[0][j];
        }
    }
    return arr;
}

fun spinWords(sentence: String): String {
    return sentence.split(' ').joinToString(" ") { it -> if (it.length >= 5) it.reversed() else it };
}

fun tribonacci(signature: DoubleArray, n: Int): DoubleArray {
    var res = signature.clone()
    for (i in signature.size until n) {
        res += (res[i-3] + res[i-2] + res[i-1]);
    }
    return res;
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
    println(tribonacci(doubleArrayOf(1.0,1.0,1.0),10));
}