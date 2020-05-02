import { createApp } from './app';

const context = {
    vueOptions: { 
        el: '[data-vue-app-init]',
        template: '#app-template'
    }
}

const app = createApp(context);
