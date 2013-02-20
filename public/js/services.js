angular.module('kanbanServices', ['ngResource']).
    factory('Task', function($resource){
      return $resource('/tasks/:taskId', {}, {});
    });
