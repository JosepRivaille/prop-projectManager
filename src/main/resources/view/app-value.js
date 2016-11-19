(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name projectManager.constant:MenuValues
     *
     * @description
     *
     */
    angular
        .module('projectManager')
        .value('menuItems', [
            {
                nodeId: 0,
                name: 'MENU_HOME',
                icon: 'home',
                selected: true
            },
            {
                nodeId: 1,
                name: 'MENU_SEARCH',
                icon: 'search',
                selected: false,
                collapsed: true,
                children: [
                    {
                        nodeId: 2,
                        name: 'MENU_SEARCH_ALL',
                        icon: 'list-alt',
                        selected: false
                    },
                    {
                        nodeId: 3,
                        name: 'MENU_SEARCH_AUTHOR',
                        icon: 'user',
                        selected: false
                    },
                    {
                        nodeId: 4,
                        name: 'MENU_SEARCH_SINGLE_DOCUMENT',
                        icon: 'file-text',
                        selected: false
                    }
                ]
            },
            {
                nodeId: 5,
                name: 'MENU_MANAGEMENT',
                icon: 'pie-chart',
                selected: false,
                collapsed: true,
                children: [
                    {
                        nodeId: 6,
                        name: 'MENU_MANAGEMENT_ALL',
                        icon: 'minus',
                        selected: false
                    },
                    {
                        nodeId: 7,
                        name: 'MENU_MANAGEMENT_CREATE',
                        icon: 'plus',
                        selected: false
                    },
                    {
                        nodeId: 8,
                        name: 'MENU_MANAGEMENT_UPDATE',
                        icon: 'refresh',
                        selected: false
                    },
                    {
                        nodeId: 9,
                        name: 'MENU_MANAGEMENT_DELETE',
                        icon: 'trash',
                        selected: false
                    }
                ]
            },
            {
                nodeId: 10,
                name: 'MENU_SETTINGS',
                icon: 'gears',
                selected: false,
                collapsed: true,
                children: [
                    {
                        nodeId: 11,
                        name: 'MENU_LANGUAGE',
                        icon: 'language',
                        selected: false
                    },
                    {
                        nodeId: 12,
                        name: 'MENU_SETTINGS_DELETE',
                        icon: 'user-times',
                        selected: false
                    },
                    {
                        nodeId: 13,
                        name: 'MENU_SETTINGS_LOGOUT',
                        icon: 'sign-out',
                        selected: false
                    }
                ]
            },
            {
                nodeId: 14,
                name: 'MENU_ABOUT',
                selected: false,
                icon: 'question'
            }
        ]);
}());