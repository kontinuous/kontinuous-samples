var myapp = angular.module('kanban', ['ui','kanbanServices']);

function TaskCtrl($scope, Task) {

  $scope.updateModel = function() {
    return Task.query(function(tasks, getResponseHeaders) {
      $scope.groupped = {}
      angular.forEach($scope.statuses, function(status) {
        $scope.groupped[status.name] = []
      });

      angular.forEach(tasks, function(task) {
        $scope.groupped[task.status].push(task);
      });
    });
  };

  $scope.groupped = {};

  $scope.tasks = $scope.updateModel();

  $scope.statuses = [
    {id: 1, name: "pending"},
    {id: 2, name: "inProgress"},
    {id: 3, name: "done"}
  ];

  $scope.addTodo = function() {
    var newTask = new Task({name:$scope.todoText});
    newTask.$save(function() {
      $scope.tasks = $scope.updateModel();
    });
    $scope.todoText = '';
  };

  $scope.syncModel = function (e, ui) {
    for(var status in $scope.groupped) {
      angular.forEach($scope.groupped[status], function(task) {
        if(task.status != status) {
          console.log(task);
          task.status = status;
          task.$save({taskId:task.id});
        }
      });
    }
  }
}