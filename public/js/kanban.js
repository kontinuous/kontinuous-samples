var myapp = angular.module('kanban', ['ui']);

function TaskCtrl($scope) {

  $scope.tasks = [
    {
      name: 'todo',
      tasks: [
      ]
    },
    {
      name: 'progress',
      tasks: [
      ]
    },
    {
      name: 'done',
      tasks: [
      ]
    }
  ];
  $scope.task_filter = function(filter) {
    var filtered = [];
    angular.forEach($scope.tasks, function(task) {
      if(task.status === filter) {
        filtered.push(task);
      }
    });
    return filtered;
  };

  $scope.addTodo = function() {
    angular.forEach($scope.tasks, function(task_group) {
      if(task_group.name === 'todo') {
        task_group.tasks.push({ text: $scope.todoText });
      }
    });
    $scope.todoText = '';
  };

  $scope.total = function(filter) {
    var total = 0;
    angular.forEach($scope.tasks, function(task_group) {
      if(task_group.name === filter) {
        total = task_group.tasks.length;
      }
    });
    return total;
  };
}