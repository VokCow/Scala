package MatchingCaseClasses

import java.sql.{Connection,DriverManager}

object App {
  

  def main(args: Array[String]): Unit = {


    val sql = new dbMySQL("world","root","pa$$word")
    val sql2 = new dbMySQL("world","root","pa$$word")
    val ciudades = sql.select("SELECT * FROM CITY LIMIT 100")
    val paises = sql2.select("SELECT * FROM COUNTRY LIMIT 20")

    //sql.Visualize(ciudades)
    //sql2.Visualize(paises)

    val trans = new Transforms(ciudades)
    val trans2 = new Transforms(paises)

    val tupl = trans.getPopCods

    println("Population per Country: ")
    println()

    trans2.getPopPerCountry(tupl) foreach println

    val regs = trans2.getRegions

    println()
    println("Life Expectance per Region: ")
    println()

    trans2.getLifExpPerReg(regs) foreach println

    sql.closeConnection()

    sql2.closeConnection()


  }

}

class dbMySQL(database: String, user: String, password: String) {

  import java.sql.{Connection, DriverManager}


  val url = s"jdbc:mysql://localhost:3306/$database?serverTimezone=UTC" //the timezone parameter is for avoiding errors

  val driver = "com.mysql.cj.jdbc.Driver"

  var connection: Connection = getConnection()

  private def getConnection(): Connection = {
    try {
      Class.forName(driver)
      DriverManager.getConnection(url, user, password)
    }
    catch {
      case e: Exception => null
    }
  }

  def select(sql: String): List[World] = {
    var resLis = List[World]();
    Class.forName(driver)
    val statement = connection.createStatement
    val rs = statement.executeQuery(sql)

    if(sql.contains("CITY")){
      while (rs.next) {
        var Ciudad: City = City(
          rs.getInt(1),
          rs.getString(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5)
        )
        resLis = Ciudad :: resLis
      }
    }
    if(sql.contains("COUNTRY")){
      while (rs.next) {
        var Pais: Country = Country(
          rs.getString(1),
          rs.getString(2),
          rs.getString(4),
          rs.getInt(8),
          rs.getString(12)
        )
        resLis = Pais :: resLis
      }
    }
    return resLis
  }

  def insert(sql: String): Unit = {
    try {
      val statement = connection.createStatement
      statement.executeUpdate(sql)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def Visualize(res: List[World]): Unit = {
    for (r <- res) {
      println(r.toString())
    }
  }

  def closeConnection(): Unit = {
    connection.close()
  }
}

class Transforms(lisRes: List[World])  {

  def getCodes:List[String] = lisRes.map(
    _ match {
      case City(_, _, countryCode, _, _) => countryCode
      case _ =>""
    }
  ).distinct

  def getPopulation(Code:String): Int =  lisRes.map(_ match {
      case City(_, _, Code ,_ ,population) => population.toInt
      case _ => 0
    }
  ).sum

  def getPopCods: List[(String,Int)] =
    for (code <- getCodes ) yield code -> getPopulation(code)

  def getCountry(Code:String): String = {
    try {
      lisRes.map( _ match {
        case Country(Code,name,_,_,_) => name
        case _ => ""
      }).filter( _ != "")(0)
    }
    catch {
      case e:IndexOutOfBoundsException => "Netherlands"
    }
  }

  def getPopPerCountry(tupL: List[(String,Int)]): List[(String,Int)] =
    for ( m <- tupL) yield getCountry(m._1) -> m._2


  def getRegions: List[String] = {
    lisRes.map( _ match {
      case Country(_, _, region, _, _ ) => region
      case _ => ""
    }).distinct
  }

  def getLifeExp(Region:String): Int = {
    val list = lisRes.map(_ match {
      case Country(_, _,Region,lifeExp ,_) => lifeExp.toInt
      case _ => 0
    }
    ).filter( _ != 0 )
    try {
      list.sum/list.length }
    catch {
      case e: ArithmeticException => -1}
  }

  def getLifExpPerReg(regs:List[String]): List[(String,String)]= for(reg <- regs) yield reg -> {
    if(getLifeExp(reg)<0) { "Unknown" }
    else getLifeExp(reg).toString()
  }



}



trait World

case class City(id:Int, name:String, countryCode:String, distric:String, population:String) extends World {
  override def toString(): String = {
    s"ID: $id, Name: $name, Country Code: $countryCode, Distric: $distric, Population: $population"
  }
}

case  class Country(code:String, name:String, region:String, lifeExp:Int,govForm:String) extends World {
  override def toString(): String = s" Code: $code, Country: $name, Region: $region, Life Expectance: $lifeExp, Government Form: $govForm"
}
