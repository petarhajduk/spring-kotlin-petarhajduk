import java.sql.DriverManager.println
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*

class Car (val manufacturer: String,val carmodel: String,val vin: String){

}

class CarCheckUp (val car: Car, val checkUpDateTime: LocalDateTime, val id: Long){

}

object CheckUpSystem{
    val cars = LinkedList<Car>()
    val checkUps = LinkedList<CarCheckUp>()
    var i: Long = 0
    init {
        cars.add(Car("Mercedes", "G klasa", "CNNCNN7857NX865N8"))
        cars.add(Car("Mercedes", "C klasa", "ZNE64N8246YN8368X"))
        cars.add(Car("Zastava", "101", "NXNX883NXNX87MJR7"))
        cars.add(Car("Citroen", "C2", "HIM344BRB88BF8BF8"))
    }

    init {
        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusYears(2), i))
        i++
        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusYears(5).minusWeeks(17), i))
        i++
        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusMonths(4), i))
        i++
        checkUps.add(CarCheckUp(cars[3], LocalDateTime.now().minusYears(3).minusMonths(2), i))
        i++
        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusMonths(2), i))
        i++
        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusMonths(8).minusYears(1), i))
        i++
        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusYears(3).minusMonths(3), i))
        i++
        checkUps.add(CarCheckUp(cars[2], LocalDateTime.now().minusDays(15), i))
        i++
        checkUps.add(CarCheckUp(cars[2], LocalDateTime.now().minusYears(3).minusWeeks(5), i))
        i++
        checkUps.add(CarCheckUp(cars[3], LocalDateTime.now().minusWeeks(17).minusYears(1), i))
        i++

    }
    fun isCheckUpNecessary(vin: String): Boolean {

        for (checkUp in checkUps){
            if (checkUp.car.vin == vin){
                val period: Period = Period.between(checkUp.checkUpDateTime.toLocalDate(), LocalDate.now())
                if (period.years > 0) continue
                else return false
            }
        }
        return true
    }

    fun addCheckUp(vin: String): CarCheckUp{
        val vins = LinkedList<String>()
        for (car in cars){
            vins.add(car.vin)
        }
        if (!vins.contains(vin)){
            throw IllegalArgumentException()
        }

        var ind = 0
        while (ind < 4){
            if (cars[ind].vin == vin) {
                break
            }
            ind++
        }
        checkUps.add(CarCheckUp(cars.get(ind), LocalDateTime.now(), i))
        return checkUps.last
    }

    fun getCheckUps(vin: String): LinkedList<CarCheckUp> {
        val list = LinkedList<CarCheckUp>()
        for (checkUp in checkUps){
            if (checkUp.car.vin == vin) list.add(checkUp)
        }
        return list
    }

    fun countCheckUps(manufacturer: String): Int {
        var i = 0
        for (checkUp in checkUps){
            if (checkUp.car.manufacturer == manufacturer) i++
        }
        return i
    }

}

interface CarCheckUpRepository{
    fun insert(performedAt: LocalDateTime, car: Car): Long
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): Map<Long, CarCheckUp>
}

class CarCheckUpNotFoundException(id: Long): RuntimeException("Car check-up ID $id not found")

class InMemoryCarCheckUpRepository : CarCheckUpRepository{

    private val carCheckUpMap = mutableMapOf<Long, CarCheckUp>()

    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val id = ((carCheckUpMap.keys.maxOrNull() ?: 0) + 1).toLong()
        carCheckUpMap[id] = CarCheckUp(id = id, checkUpDateTime = performedAt, car = car)
        return id
    }

    override fun findById(id: Long): CarCheckUp {

    }

    override fun deleteById(id: Long): CarCheckUp {

    }

    override fun findAll(): Map<Long, CarCheckUp> {

    }

}

fun main(args: Array<String>) {
    for (c in CheckUpSystem.checkUps) println(c.checkUpDateTime.toString())

    println(CheckUpSystem.countCheckUps("Mercedes").toString())
    println(CheckUpSystem.addCheckUp("CNNCNN7857NX865N8").checkUpDateTime.toString())
    println(CheckUpSystem.countCheckUps("Mercedes").toString())
    val test = CheckUpSystem.getCheckUps("CNNCNN7857NX865N8")
    for (t in test){
        println(t.car.carmodel + "-" + t.checkUpDateTime)
    }
    println(CheckUpSystem.isCheckUpNecessary("CNNCNN7857NX865N8").toString())
    println(CheckUpSystem.isCheckUpNecessary("HIM344BRB88BF8BF8").toString())
}
