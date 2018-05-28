package services

class StaticListDataService : DataService {
    override fun all(): List<HelloJson> = listOf(HelloJson(1, "number one"), HelloJson(2, "number two"))
}
