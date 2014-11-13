import com.redis._

class RedisDal{

  def getInfo(): Unit ={
    val redis = new RedisClient("localhost", 6379)
    val inf = redis.info("", null)
    println(inf)

  }

}