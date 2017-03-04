package org.devd.scala.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class HelloEveryone {

  @RequestMapping(Array("/hi"))
  @ResponseBody
  def home = "Hello World!"

}