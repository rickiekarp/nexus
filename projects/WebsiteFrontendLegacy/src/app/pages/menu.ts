export let MENU_ITEM = [
    {
        path: 'index',
        title: 'Dashboard',
        icon: 'dashboard'
    },
    {
        title: 'Pages',
        icon: 'user',
        children: [
            {
                path: 'charts',
                title: 'Home',
            },
            {
                path: 'charts',
                title: 'Projects',
            },
            {
                path: 'charts',
                title: 'Resume',
            },
            {
                path: 'profile',
                title: 'Contact',
            }
        ]
    },
    {
        path: 'ui',
        title: 'UI Elements',
        icon: 'paint-brush',
        children: [
            {
                path: 'grid',
                title: 'Bootstrap Grid'
            },
            {
                path: 'buttons',
                title: 'Buttons'
            },
            {
                path: 'notification',
                title: 'Notification'
            },
            {
                path: 'file-tree',
                title: 'File Tree'
            },
            {
                path: 'form',
                title: 'Forms',
                icon: 'check-square-o',
                children: [
                    {
                        path: 'form-inputs',
                        title: 'Form Inputs'
                    },
                    {
                        path: 'form-layouts',
                        title: 'Form Layouts'
                    },
                    {
                        path: 'file-upload',
                        title: 'File Upload'
                    }
                ]
            },
            {
                path: 'charts',
                title: 'Charts',
                icon: 'bar-chart',
                children: [
                    {
                        path: 'echarts',
                        title: 'Echarts'
                    }
                ]
            },
            {
                path: 'table',
                title: 'Tables',
                icon: 'table',
                children: [
                    {
                        path: 'basic-tables',
                        title: 'Basic Tables'
                    },
                    {
                        path: 'data-table',
                        title: 'Data Table'
                    }
                ]
            }
        ]
    }
];
