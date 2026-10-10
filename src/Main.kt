
fun main() {

    val manager = ArrayListManager()

    val list = manager.builder()
    val list2 = manager.builder()
    val list3 = manager.builder()

    manager.destroy()
    val list4 = manager.builder()


    println(ArrayListManager.getListCount())
}