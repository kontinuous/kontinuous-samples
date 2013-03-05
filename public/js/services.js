angular.module('kanbanServices', ['ngResource']).
    factory('Task', function($resource){
      return $resource('/boards/:boardId/tasks/:taskId', {}, {});
    }).
    factory('Board', function($resource){
      return $resource('/boards/:boardId', {}, {});
    }).
    config(['$routeProvider', function($routeProvider) {
      $routeProvider.
          when('/boards', {templateUrl: 'partials/boards-list.html',   controller: BoardCtrl}).
          when('/boards/:boardId', {templateUrl: 'partials/board-details.html', controller: TaskCtrl}).
          when('/boards/:boardId/edit', {templateUrl: 'partials/board-edit.html', controller: TaskCtrl}).
          otherwise({redirectTo: '/boards'});
    }]);
