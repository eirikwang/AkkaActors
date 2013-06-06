package workers

case class HandleException(message: String) extends RuntimeException(message)
case class UnexpectedException(message: String) extends RuntimeException(message)
