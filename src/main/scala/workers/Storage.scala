package workers

object Storage {
  private var storage:Map[String, Long] = Map.empty
  def put(id:String, value:Long){
    storage += id -> value
  }
  def fetch(id:String) {
    storage(id)
  }
}
