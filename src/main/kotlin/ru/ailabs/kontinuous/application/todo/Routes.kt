package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.annotation.GET
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.annotation.POST
import ru.ailabs.kontinuous.controller.AssetController

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 12:01
 */

routes class Routes {
    GET("/tasks")  val list = TaskController.list
    POST("/tasks")  val create = TaskController.create
    POST("/tasks/:id")  val update = TaskController.update
    GET("/assets/*file") val assets = AssetController.at
    GET("/") val root = TaskController.root
}
