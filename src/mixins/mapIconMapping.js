const mapIconMapping = {
    methods: {
        getIconDetails(name) {
            const icon = {
            };
            switch (name) {
            case 'cities':
                icon.name = 'city';
                break;
            case 'towns':
                icon.name = 'home';
                break;
            case 'islands':
                icon.name = 'boat';
                break;
            case 'regions':
                icon.name = 'map';
                break;
            case 'acco':
                icon.name = 'bed';
                break;
            case 'acti':
                icon.name = 'walk';
                break;
            case 'attr':
                icon.name = 'camera';
                break;
            case 'even':
                icon.name = 'events';
                break;
            case 'cate':
                icon.name = 'food';
                break;
            case 'reta':
                icon.name = 'shopping-bag';
                break;
            case 'featured':
                icon.name = 'star';
                break;
            case 'twnv':
                icon.name = '';
                break;
            case 'serv':
                icon.name = 'icentre-information';
                break;
            default:
                break;
            }
            return icon;
        },
    },
};

export default mapIconMapping;
