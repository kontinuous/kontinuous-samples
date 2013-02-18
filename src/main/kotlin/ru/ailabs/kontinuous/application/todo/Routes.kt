package ru.ailabs.kontinuous.application.todo

import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.annotation.routes

/**
 * User: andrew
 * Date: 18.02.13
 * Time: 12:01
 */

routes class Routes {
    path("/")  val index = TaskController.index
}
