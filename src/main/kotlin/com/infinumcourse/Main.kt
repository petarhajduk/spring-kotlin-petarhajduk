package com.infinumcourse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.Year
import java.util.*

class Car (val manufacturer: String,
           val carmodel: String,
           val vin: String,
           val id: Long, val addingDate: LocalDate, val productionYear: Year){
    val checkUps = LinkedList<CarCheckUp>()
}

class CarInfo(val car: Car, val checkUps: LinkedList<CarCheckUp>, checkUpNeeded: Boolean)

class CarCheckUp (val carid: Long,
                  val checkUpDateTime: LocalDateTime,
                  val id: Long, val worker: String, val price: Long)

interface CarCheckUpRepository{
    fun insert(manufacturer: String, carmodel: String, vin: String, productionYear: Year): Car
    fun insert(carid: Long, checkUpDateTime: LocalDateTime, worker: String, price: Long): CarCheckUp
    fun getCarInfo(id: Long): CarInfo
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): Map<Long, CarCheckUp>
    fun getAllCars(): Map<Long, Car>
    fun getCheckUpsByManufacturer(): Map<String, Long>
}

class CarCheckUpNotFoundException(id: Long): RuntimeException("Car check-up ID $id not found")
class CarNotFoundException(id: Long): java.lang.RuntimeException("Car ID $id not found")

@Component
class InMemoryCarAndCarCheckUpRepository() : CarCheckUpRepository{

    private val carsMap = mutableMapOf<Long, Car>()
    private val carCheckUpMap = mutableMapOf<Long, CarCheckUp>()
    //neki komentar

//    init {
//        println(dataSource)
//    }

    init {
        carsMap[0] = Car("Fiat", "Brava", "U9U9BC9UCHTHEO4", 0, LocalDate.now().minusMonths(5), Year.of(2008))
    }

    override fun getCheckUpsByManufacturer(): Map<String, Long> {
        val map = mutableMapOf<String, Long>()

        for (car in carsMap.values){
            if (!map.containsKey(car.manufacturer)){
                map.put(car.manufacturer, 0)
            }
        }

        var manufacturer: String
        var temp: Long

        for (cup in carCheckUpMap.values){
            manufacturer = carsMap.get(cup.carid)!!.manufacturer
            temp = map[manufacturer]!!
            map.set(manufacturer, temp)
        }

        return map
    }

    override fun getCarInfo(id: Long): CarInfo {
        if (carsMap.containsKey(id)) {
            throw CarNotFoundException(id)
        }

        val car = carsMap[id]
        val checkups = car!!.checkUps
        var checkUpNeeded = true

        for (c in checkups){
            val period: Period = Period.between(c.checkUpDateTime.toLocalDate(), LocalDate.now())
            if (period.years == 0) {
                checkUpNeeded = false
                break
            }
        }

        val carInfo = CarInfo(car, checkups, checkUpNeeded)
        return carInfo
    }

    override fun getAllCars(): Map<Long, Car> {
        return carsMap
    }

    override fun insert(manufacturer: String, carmodel: String, vin: String, productionYear: Year): Car {
        val id = ((carsMap.keys.maxOrNull() ?: 0) + 1)
        val car = Car(manufacturer,
            carmodel,
            vin,
            id, LocalDate.now(), productionYear)
        carsMap[id] = car
        return car
    }

    override fun insert(carid: Long,
                        checkUpDateTime: LocalDateTime,
                        worker: String, price: Long): CarCheckUp {
        val id = ((carCheckUpMap.keys.maxOrNull() ?: 0) + 1)
        val newCheckUp = CarCheckUp(carid, checkUpDateTime, id, worker, price)
        carCheckUpMap[id] = newCheckUp
        return newCheckUp
    }

//    override fun getCarById(id: Long): Car {
//        return carsMap[id] ?: throw CarNotFoundException(id)
//    }

    override fun findById(id: Long): CarCheckUp {
        return carCheckUpMap[id] ?: throw CarCheckUpNotFoundException(id)
    }

    override fun deleteById(id: Long): CarCheckUp {
        return carCheckUpMap.remove(id) ?: throw CarCheckUpNotFoundException(id)
    }

    override fun findAll(): Map<Long, CarCheckUp> {
        return this.carCheckUpMap.toMap()
    }

}

//@Component
//data class DataSource(
//    @Value("\${DBkey}")
//    val dbName: String,
//    @Value("\${usernamekey}")
//    val username: String,
//    @Value("\${passwordkey}")
//    val password: String
//)

fun main(args: Array<String>) {
    //val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(ApplicationConfiguration::class.java)
    runApplication<SpringBootMvcApplication>(*args)
}

//@Configuration
//@ComponentScan
//@PropertySource("classpath:application.properties")
//class ApplicationConfiguration

@SpringBootApplication
class SpringBootMvcApplication

//object CheckUpSystem{
//    val cars = LinkedList<Car>()
//    val checkUps = LinkedList<CarCheckUp>()
//    var i: Long = 0
//    init {
//        cars.add(Car("Mercedes", "G klasa", "CNNCNN7857NX865N8"))
//        cars.add(Car("Mercedes", "C klasa", "ZNE64N8246YN8368X"))
//        cars.add(Car("Zastava", "101", "NXNX883NXNX87MJR7"))
//        cars.add(Car("Citroen", "C2", "HIM344BRB88BF8BF8"))
//    }
//
//    init {
//        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusYears(2), i))
//        i++
//        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusYears(5).minusWeeks(17), i))
//        i++
//        checkUps.add(CarCheckUp(cars[0], LocalDateTime.now().minusMonths(4), i))
//        i++
//        checkUps.add(CarCheckUp(cars[3], LocalDateTime.now().minusYears(3).minusMonths(2), i))
//        i++
//        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusMonths(2), i))
//        i++
//        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusMonths(8).minusYears(1), i))
//        i++
//        checkUps.add(CarCheckUp(cars[1], LocalDateTime.now().minusYears(3).minusMonths(3), i))
//        i++
//        checkUps.add(CarCheckUp(cars[2], LocalDateTime.now().minusDays(15), i))
//        i++
//        checkUps.add(CarCheckUp(cars[2], LocalDateTime.now().minusYears(3).minusWeeks(5), i))
//        i++
//        checkUps.add(CarCheckUp(cars[3], LocalDateTime.now().minusWeeks(17).minusYears(1), i))
//        i++
//
//    }
//    fun isCheckUpNecessary(vin: String): Boolean {
//
//        for (checkUp in checkUps){
//            if (checkUp.car.vin == vin){
//                val period: Period = Period.between(checkUp.checkUpDateTime.toLocalDate(), LocalDate.now())
//                if (period.years > 0) continue
//                else return false
//            }
//        }
//        return true
//    }
//
//    fun addCheckUp(vin: String): CarCheckUp{
//        val vins = LinkedList<String>()
//        for (car in cars){
//            vins.add(car.vin)
//        }
//        if (!vins.contains(vin)){
//            throw IllegalArgumentException()
//        }
//
//        var ind = 0
//        while (ind < 4){
//            if (cars[ind].vin == vin) {
//                break
//            }
//            ind++
//        }
//        checkUps.add(CarCheckUp(cars.get(ind), LocalDateTime.now(), i))
//        return checkUps.last
//    }
//
//    fun getCheckUps(vin: String): LinkedList<CarCheckUp> {
//        val list = LinkedList<CarCheckUp>()
//        for (checkUp in checkUps){
//            if (checkUp.car.vin == vin) list.add(checkUp)
//        }
//        return list
//    }
//
//    fun countCheckUps(manufacturer: String): Int {
//        var i = 0
//        for (checkUp in checkUps){
//            if (checkUp.car.manufacturer == manufacturer) i++
//        }
//        return i
//    }
//
//}

//@Component
//class InfileCarCheckUpRepository(@Value("file:\${DBkey}.db") private val carCheckUpsFileResource: Resource) : CarCheckUpRepository {
//    init {
//        if (carCheckUpsFileResource.exists().not()){
//            carCheckUpsFileResource.file.createNewFile()
//        }
//    }
//
//    private fun String.convertToCarCheckUp(): CarCheckUp {
//        val tokens = split(",")
//        return CarCheckUp(id = tokens[0].toLong(), checkUpDateTime = LocalDateTime.parse(tokens[4]),
//            car = Car(
//                vin = tokens[1],
//                manufacturer = tokens[2],
//                carmodel = tokens[3]
//            ))
//    }
//
//    override fun insert(performedAt: LocalDateTime, car: Car): Long {
//        val file = carCheckUpsFileResource.file
//        val id = (file.readLines().filter { it.isNotEmpty() }.maxOfOrNull { line ->
//            line.split(",").first().toLong() } ?: 0) + 1
//
//        file.appendText("$id,${car.vin},${car.manufacturer}, ${car.carmodel},$performedAt\n")
//
//        return id
//    }
//
//    override fun findById(id: Long): CarCheckUp {
//        return carCheckUpsFileResource.file.readLines()
//            .filter { it.isNotEmpty() }
//            .find { line -> line.split(",").first().toLong() == id }
//            ?.convertToCarCheckUp() ?: throw CarCheckUpNotFoundException(id)
//    }
//
//    override fun deleteById(id: Long): CarCheckUp {
//        val checkUpLines = carCheckUpsFileResource.file.readLines()
//        var lineToDelete: String? = null
//        FileOutputStream(carCheckUpsFileResource.file).writer()
//            .use { fileOutputWriter -> checkUpLines.forEach { line ->
//                    if (line.split(",").first().toLong() == id) {
//                        lineToDelete = line
//                    } else {
//                        fileOutputWriter.appendLine(line)
//                    }
//                }
//            }
//        return lineToDelete?.convertToCarCheckUp() ?: throw CarCheckUpNotFoundException(id)
//    }
//
//    override fun findAll(): Map<Long, CarCheckUp> {
//        return carCheckUpsFileResource.file.readLines().map { line ->
//            line.convertToCarCheckUp() }.associateBy { it.id }
//    }
//
//}
