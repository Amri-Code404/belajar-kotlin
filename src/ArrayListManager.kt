class ArrayListManager {

    companion object {
        private var arrayListCount: Int = 0
        fun getListCount(): Int = arrayListCount
    }

    // Status utama manager
    var isAvailable: Boolean = true
        private set // Hanya bisa diubah dari dalam class ini

    // Wadah pusat penampung data asli
    private val masterList = ArrayList<ArrayList<Any>>()

    fun builder(): ManagedList {
        check(isAvailable) { "Manager sudah dihancurkan!" }

        val newList = ArrayList<Any>()
        masterList.add(newList)

        // "Nomor Pointer" adalah index terakhir di masterList
        val idPointer = masterList.size - 1

        ArrayListManager.arrayListCount++

        // Kita oper ID dan referensi Manager ini (this) ke si pembungkus
        return ManagedList(idPointer, this)
    }

    // Fungsi internal yang hanya bisa diakses oleh ManagedList untuk mengambil data asli
    fun getActualList(id: Int): ArrayList<Any> {
        check(isAvailable) { "Akses ditolak: Manager sudah dihancurkan!" }
        return masterList[id]
    }

    fun destroy() {
        if (!isAvailable) return

        masterList.forEach { it.clear() }
        masterList.clear()
        ArrayListManager.arrayListCount = 0
        isAvailable = false
        println("Manager Destroyed: Semua data asli di masterList dihapus total!")
    }
}

// --- CLASS PEMBUNGKUS (Bertindak sebagai Pointer Aman) ---
class ManagedList(
    private val idPointer: Int,
    private val manager: ArrayListManager // Memegang referensi ke managernya
) {

    fun add(item: Any) {
        // 1. Cek dulu apakah managernya masih hidup
        check(manager.isAvailable) { "Gagal menambah data: Manager dengan ID Pointer #$idPointer sudah tidak aktif!" }

        // 2. Jika aman, minta data asli ke manager berdasarkan Nomor Pointer
        val actualList = manager.getActualList(idPointer)
        actualList.add(item)
    }

    fun tampilkanIsi() {
        if (!manager.isAvailable) {
            println("Pointer #$idPointer: Data sudah tidak bisa diakses (Manager Dead)")
            return
        }
        val actualList = manager.getActualList(idPointer)
        println("Isi Pointer #$idPointer: $actualList")
    }
}