(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.constant:MenuValues
     *
     * @description
     *
     */
    angular
        .module('project')
        .value('menuItems', [
            {
                name: 'MENU_HOME',
                state: 'project',
                icon: 'home',
                selected: true
            },
            {
                name: 'MENU_SEARCH',
                state: 'project.search',
                icon: 'search',
                selected: false,
                collapsed: true,
                children: [
                    {
                        name: 'MENU_SEARCH_ALL',
                        state: 'project.search.all',
                        icon: 'list-alt',
                        selected: false
                    },
                    {
                        name: 'MENU_SEARCH_AUTHOR',
                        state: 'project.search.author',
                        icon: 'user',
                        selected: false
                    },
                    {
                        name: 'MENU_SEARCH_SINGLE_DOCUMENT',
                        state: 'project.search.document',
                        icon: 'file-text',
                        selected: false
                    }
                ]
            },
            {
                name: 'MENU_MANAGEMENT',
                state: 'project.manage',
                icon: 'pie-chart',
                selected: false,
                collapsed: true,
                children: [
                    {
                        name: 'MENU_MANAGEMENT_ALL',
                        state: 'project.manage.list',
                        icon: 'list-alt',
                        selected: false
                    },
                    {
                        name: 'MENU_MANAGEMENT_CREATE',
                        state: 'project.manage.create',
                        icon: 'plus',
                        selected: false
                    },
                    {
                        name: 'MENU_MANAGEMENT_UPDATE',
                        state: 'project.manage.update',
                        icon: 'refresh',
                        selected: false
                    },
                    {
                        name: 'MENU_MANAGEMENT_DELETE',
                        state: 'project.manage.delete',
                        icon: 'trash',
                        selected: false
                    }
                ]
            },
            {
                name: 'MENU_SETTINGS',
                state: 'project.settings',
                icon: 'gears',
                selected: false
            },
            {
                name: 'MENU_ABOUT',
                state: 'project.about',
                icon: 'question',
                selected: false
            }
        ]);
}());