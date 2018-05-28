package services

data class HelloJson(val prop1: Int, val prop2: String)

interface DataService {
    fun all() : List<HelloJson>
}

class NullDataService : DataService {
    override fun all(): List<HelloJson> = emptyList()
}
