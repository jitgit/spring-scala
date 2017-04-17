package org.devd.scala.controller

import org.devd.scala.Hello
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class HelloEveryone {

  @RequestMapping(value = Array("/hi"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody()
  def hello = {
    val result = Hello("Dev", System.currentTimeMillis())
    println(s"$result")
    result
  }


}