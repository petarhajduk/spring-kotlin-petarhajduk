package com.infinumcourse.cars.repository

import com.infinumcourse.cars.entities.*
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*

@Repository
class DatabaseCarAndCarCheckUpRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CarCheckUpRepository{

    init {
        //there is probably a better way to initialize the database
        jdbcTemplate.update(
            "delete from cars where id >= 0",
            mapOf<String, Any>()
        )
        jdbcTemplate.update(
            "Insert into cars (manufacturer, carmodel, vin, id, addingdate, productionyear) values ('Fiat', 'Brava', 'U9U9BC9UCHTHEO4', 0, '2020-01-01', 2002)",
            mapOf<String, Any>()
        )
        jdbcTemplate.update(
            "Insert into cars (manufacturer, carmodel, vin, id, addingdate, productionyear) values ('Mazda', '6', 'N83B89G74BDJC9U', 1, '2020-07-05', 2008)",
            mapOf<String, Any>()
        )

        jdbcTemplate.update(
            "delete from checkups where id >= 0",
            mapOf<String, Any>()
        )
        jdbcTemplate.update(
            "insert into checkups (carid, checkupdatetime, id, worker, price) values (0, '2021-04-21', 0, 'Siniša', 300)",
            mapOf<String, Any>()
        )

        jdbcTemplate.update(
            "insert into checkups (carid, checkupdatetime, id, worker, price) values (0, '2020-04-21', 0, 'Siniša', 300)",
            mapOf<String, Any>()
        )

        jdbcTemplate.update(
            "insert into checkups (carid, checkupdatetime, id, worker, price) values (1, '2022-04-21', 0, 'Siniša', 300)",
            mapOf<String, Any>()
        )
    }

    override fun getCheckUpsByManufacturer(): Map<String, Long> {
        val map = mutableMapOf<String, Long>()
        val carsMap = jdbcTemplate.query(
            "select * from cars",
            mapOf<String, Car>()
        ){result,_ -> Car(result.getString("manufacturer"), result.getString("carmodel"),
            result.getString("vin"), result.getLong("id"),
            result.getDate("addingdate").toLocalDate(), result.getLong("productionyear"))}
        carsMap.sortBy { it.id }
        val carCheckUpMap = jdbcTemplate.query(
            "select * from checkups",
            mapOf<String, CarCheckUp>()
        ){result,_ -> CarCheckUp(result.getLong("carid"), result.getDate("checkupdatetime").toLocalDate(),
            result.getLong("id"), result.getString("worker"), result.getLong("price"))}
        carCheckUpMap.sortBy { it.id }

        for (car in carsMap){
            if (!map.containsKey(car.manufacturer)){
                map.put(car.manufacturer, 0)
            }
        }

        var manufacturer: String?
        var temp: Long?

        for (cup in carCheckUpMap){
            manufacturer = carsMap.elementAt(cup.carid.toInt())?.manufacturer
            if (manufacturer != null){
                temp = map[manufacturer]
                if (temp != null) map[manufacturer] = temp+1
                else throw RuntimeException()
            } else throw RuntimeException()
        }

        return map
    }

    override fun getCarInfo(id: Long): CarInfo {
        val list = jdbcTemplate.query(
            "select * from cars where id = :id",
            mapOf("id" to id)
        ){result,_ -> Car(result.getString("manufacturer"), result.getString("carmodel"),
            result.getString("vin"), result.getLong("id"),
            result.getDate("addingdate").toLocalDate(), result.getLong("productionyear"))}
        val car: Car = list.get(0)


        if (car == null) throw CarNotFoundException(id)
        lateinit var checkups: LinkedList<CarCheckUp>

        if (car != null) {
            checkups = car.checkUps
        } else throw RuntimeException()

        var checkUpNeeded = true

        for (c in checkups) {
            val period: Period = Period.between(c.checkUpDateTime, LocalDate.now())
            if (period.years == 0) {
                checkUpNeeded = false
                break
            }
        }
        return CarInfo(car, checkups, checkUpNeeded)
    }

    override fun getAllCars(): MutableList<Car> {
        return jdbcTemplate.query(
            "select * from cars",
            mapOf<String, Car>()
        ){result,_ -> Car(result.getString("manufacturer"), result.getString("carmodel"),
                            result.getString("vin"), result.getLong("id"),
                            result.getDate("addingdate").toLocalDate(), result.getLong("productionyear"))}
    }

    override fun insertCar(carAdder: CarAdder): Car {
        val id = jdbcTemplate.queryForObject(
            "select count(*) from cars",
            mapOf<String, Int>(),
            Int::class.java
        )
        val car = Car(carAdder.manufacturer,
            carAdder.carmodel,
            carAdder.vin,
            id.toLong(), LocalDate.now(), carAdder.productionYear)
        jdbcTemplate.update(
            "insert into cars values (:manufacturer, :carmodel, :vin, :id, :addingdate, :productionyear)",
            mapOf("manufacturer" to car.manufacturer, "carmodel" to car.carmodel, "vin" to car.vin, "id" to car.id, "addingdate" to car. addingDate, "productionyear" to car.productionYear)
        )
        return car
    }

    override fun insertCheckUp(carCheckUpAdder: CarCheckUpAdder): CarCheckUp {
        val id = jdbcTemplate.queryForObject(
            "select count(*) from checkups",
            mapOf<String, Int>(),
            Int::class.java
        )
        val cu = CarCheckUp(carCheckUpAdder.carid, carCheckUpAdder.checkUpDateTime, id.toLong(), carCheckUpAdder.worker, carCheckUpAdder.price)
        jdbcTemplate.update(
            "insert into checkups values (:carid, :checkupdatetime, :id, :worker, :price)",
            mapOf("carid" to cu.carid, "checkupdatetime" to cu.checkUpDateTime, "id" to cu.id, "worker" to cu.worker, "price" to cu.price)
        )
        return cu
    }

    override fun findById(id: Long): CarCheckUp {
        val ck = jdbcTemplate.queryForObject(
            "select * from checkups where id = :id",
            mapOf("id" to id),
            CarCheckUp::class.java
        )
        if (ck == null) throw CarCheckUpNotFoundException(id)
        return ck
    }

    override fun deleteById(id: Long): CarCheckUp {
        val car = jdbcTemplate.queryForObject(
            "select * from checkups where id = :id",
            mapOf("id" to id),
            CarCheckUp::class.java
        )

        jdbcTemplate.update(
            "delete from checkups where id = :id",
            mapOf("id" to id)
        )

        return car
    }

    override fun findAll(): List<CarCheckUp> {
        return jdbcTemplate.query(
            "select * from checkups",
            mapOf<String, Any>()
        ){result,_ -> CarCheckUp(result.getLong("carid"), result.getDate("checkupdatetime").toLocalDate(),
            result.getLong("id"), result.getString("worker"), result.getLong("price"))}
    }

}