package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.annotation.GET
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.annotation.POST

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 12:01
 */

routes class Routes {
    GET("/tasks")  val list = TaskController.list
    POST("/tasks")  val create = TaskController.create
    POST("/tasks/:id")  val update = TaskController.update
}
